package org.gz.app.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.ca.component.EsignManager;
import org.gz.app.feign.RentRecordServiceClient;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.StringUtils;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.order.common.dto.UpdateOrderStateReq;
import org.gz.user.entity.AppUser;
import org.gz.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

/**
 * 签约controller
 * 
 * @author yangdx
 *
 */
@RequestMapping("/esign")
@RestController
@Slf4j
public class ESignController extends AppBaseController {
	
	@Autowired
	private EsignManager esignManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RentRecordServiceClient rentRecordServiceClient;

	/**
	 * 发送签约短信验证码
	 * @return
	 */
	@RequestMapping("/sendSignCaptcha")
	@ResponseBody
	public ResponseResult<String> sendSignCaptcha(HttpServletRequest request) {
		List<String> list = getUserFields(request, "userId");
		
		Long userId = Long.valueOf(list.get(0));
		
		ResponseResult<AppUser> userResult = userService.queryUserById(userId);
		
		AppUser user = userResult.getData();
		
		return esignManager.sendMessageOfSign(
				userId, 
				user.getRealName(), 
				user.getIdNo(),
				user.getPhoneNum());
	}
	
	/**
	 * 开始签约
	 * @return
	 */
	@RequestMapping("/sign")
	@ResponseBody
	public ResponseResult<String> sign(@RequestBody JSONObject body, HttpServletRequest request) {
		List<String> list = getUserFields(request, "userId");
		
		Long userId = Long.valueOf(list.get(0));
		
		//经度
		String lat = body.getString("lat");
		//纬度
		String lng = body.getString("lng");
		//手机型号
		String deviceType = body.getString("deviceType");
		
		String smsCode = body.getString("smsCode");
		String orderNo = body.getString("orderNo");
		if (StringUtils.isEmpty(smsCode)) {
			return ResponseResult.build(-1, "未获取到签约验证码", null);
		}
		if (StringUtils.isEmpty(orderNo)) {
			return ResponseResult.build(-1, "未获取到订单编号", null);
		}
		
		//查询用户信息
		ResponseResult<AppUser> userResult = userService.queryUserById(userId);
		
		AppUser user = userResult.getData();
		
		//查询订单信息
		ResponseResult<OrderDetailResp> orderResult = rentRecordServiceClient.queryOrderDetail(orderNo);
		OrderDetailResp orderDetailResp = orderResult.getData();
		String agreementUrl = orderDetailResp.getAgreementUrl();
		if (StringUtils.isEmpty(agreementUrl)) {
			return ResponseResult.build(-1, "未获取到签约合同模板", null);
		}
		if (agreementUrl.startsWith("http")) {
			//https://osstest-01.oss-cn-beijing.aliyuncs.com/pdf/agreement/temp/3SO1801070000000018.pdf
			int index = agreementUrl.indexOf("/pdf");
			agreementUrl = agreementUrl.substring(index + 1, agreementUrl.length());
		}
		
		//开始签约
		ResponseResult<Map<String, String>> result = esignManager.sign(
				userId, 
				user.getRealName(), 
				user.getIdNo(), 
				user.getPhoneNum(), 
				agreementUrl, 
				smsCode);
		log.info("---> sign success, result: {}", JSONObject.toJSONString(result));
		
		Map<String, String> data = result.getData();
		
		if (data != null && !"-1".equals(data.get("status"))) {
			//success
			UpdateOrderStateReq updateOrderState = new UpdateOrderStateReq();
			updateOrderState.setCreateBy(userId);
			updateOrderState.setCreateMan(user.getRealName());
			updateOrderState.setLat(lat);
			updateOrderState.setLng(lng);
			updateOrderState.setPhoneModel(deviceType);
			updateOrderState.setRentRecordNo(orderNo);
			
			updateOrderState.setSignServiceId(data.get("signServiceId"));
			
			String status = data.get("status");
			if ("40".equals(status)) {
				//上传oss完成
				updateOrderState.setSealAgreementUrl(data.get("signedPdfPath"));
			} else if ("50".equals(status)) {
				//保存存证完成
				updateOrderState.setSealAgreementUrl(data.get("signedPdfPath"));
				updateOrderState.setEvid(data.get("evid"));
			}
			
			ResponseResult<String> orderRst = rentRecordServiceClient.sureSign(updateOrderState);
			log.info("---> call order service sureSign, result: {}", JSONObject.toJSONString(orderRst));
			if (orderRst.getErrCode() != 0) {
				return orderRst;
			}
			
			return ResponseResult.build(0, "签约成功", null);
		} else {
			return ResponseResult.build(-1, "签约失败", null);
		}
		
	}
}
