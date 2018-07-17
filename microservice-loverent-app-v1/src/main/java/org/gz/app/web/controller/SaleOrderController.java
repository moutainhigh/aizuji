package org.gz.app.web.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.dto.SaleOrderSubmitDto;
import org.gz.app.feign.SaleOrderServiceClient;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.StringUtils;
import org.gz.order.common.dto.SaleOrderReq;
import org.gz.order.common.entity.RentInvoice;
import org.gz.order.common.entity.UserHistory;
import org.gz.user.dto.UserInfoDto;
import org.gz.user.entity.AddressInfo;
import org.gz.user.entity.AppUser;
import org.gz.user.service.AddressInfoService;
import org.gz.user.service.UserInfoService;
import org.gz.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.timevale.tgtext.text.pdf.co;

/**
 * 售卖订单入口
 * 
 * @author yangdx
 *
 */
@RestController
@RequestMapping("/saleOrder")
@Slf4j
public class SaleOrderController extends AppBaseController {
	
	@Autowired
	private SaleOrderServiceClient saleOrderServiceClient;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AddressInfoService addressInfoService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 提交订单
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(value="/submitSaleOrder", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<String> submitSaleOrder(@Valid @RequestBody SaleOrderSubmitDto dto, BindingResult bindingResult, HttpServletRequest request) {
		log.info("--> execute submitApplyInfo, params: {}", JSONObject.toJSONString(dto));
		
		ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
		if (validateResult != null) {
			log.error("--【提交购机订单】 --参数验证失败, validateResult: {}", JSONObject.toJSONString(validateResult));
			return validateResult;
		}
		
		List<String> list = getUserFields(request, "userId");
		Long userId = Long.valueOf(list.get(0));
		
		SaleOrderReq saleOrderReq = convertSubmitOrderReq(Long.valueOf(userId), dto);
		log.info("---> submitOrder, saleOrderReq: {}", JSONObject.toJSONString(saleOrderReq));
		if (saleOrderReq == null) {
			log.info("---> submitOrder, build SaleOrderReq failed, saleOrderReq is null");
			ResponseResult<String> result = new ResponseResult<>();
			result.setErrCode(-1);
			result.setErrMsg("订单提交异常");
			return result;
		}
		
		ResponseResult<String> result = saleOrderServiceClient.addSaleOrder(saleOrderReq);
		if (result.getErrCode() == 0) {
			try {
				//更新用户最新地址
				updateUserAddress(Long.valueOf(userId), dto);
			} catch (Exception e) {
				log.error("-->submitApplyInfo success, update user info failed, e: {}", e);
			}
		}
		return result;
	}
	

	//组装订单提交数据
	private SaleOrderReq convertSubmitOrderReq(Long userId, SaleOrderSubmitDto orderDto) {
		
		ResponseResult<AppUser> userRst = userService.queryUserById(userId);
		
		if (userRst.getErrCode() != 0) {
			log.error("convertSubmitOrderReq failed, userRst :{}", JSONObject.toJSONString(userRst));
			return null;
		}
		
		SaleOrderReq saleOrderReq = new SaleOrderReq();
		
		UserHistory userHistory = new UserHistory();
		ResponseResult<UserInfoDto> userInfoRst = userInfoService.queryByUserId(userId);
		UserInfoDto dto = userInfoRst.getData();
		if (dto != null) {
			log.info("---> convertSubmitOrderReq.query UserInfoDto: {}", JSONObject.toJSONString(dto));
			BeanUtils.copyProperties(dto, userHistory);	
		}
		userHistory.setId(null);
		log.info("---> convertSubmitOrderReq convert userHistory: {}", JSONObject.toJSONString(userHistory));
		log.info("---> convertSubmitOrderReq convert DeviceType: {}", userHistory.getDeviceType());
		log.info("---> convertSubmitOrderReq convert OsType: {}", userHistory.getOsType());
		saleOrderReq.setUserHistory(userHistory);
		
		String receiverUserName = orderDto.getReceiverUserName();
		String receiverPhoneNum = orderDto.getReceiverPhoneNum();
		
		AppUser user = userRst.getData();
		//用户信息
		saleOrderReq.setUserId(userId);
		saleOrderReq.setPhoneNum(StringUtils.isEmpty(receiverPhoneNum) ? user.getPhoneNum() : receiverPhoneNum);			//手机号
		saleOrderReq.setRealName(StringUtils.isEmpty(receiverUserName) ? user.getRealName() : receiverUserName);			//收货人
		saleOrderReq.setIdNo(user.getIdNo());					//身份证
		//订单信息
		saleOrderReq.setProv(orderDto.getAddrProvince());		//收货地址-省
		saleOrderReq.setCity(orderDto.getAddrCity());			//收货地址-市
		saleOrderReq.setArea(orderDto.getAddrArea());			//收货地址-区域
		saleOrderReq.setAddress(orderDto.getAddrDetail());		//详细地址
		saleOrderReq.setImei(orderDto.getImei());			//设备imei
		saleOrderReq.setChannelType(orderDto.getChannelType());		//下载渠道
		saleOrderReq.setCouponId(orderDto.getCouponId());		//优惠券id
		saleOrderReq.setSnCode(orderDto.getSnCode());
		saleOrderReq.setProductNo(orderDto.getProductNo());
		saleOrderReq.setChannelNo(orderDto.getChannelNo());
		
		Integer brokenScreenBuyed = orderDto.getBrokenScreenBuyed();
		saleOrderReq.setBrokenScreenBuyed(brokenScreenBuyed == null ? false : (brokenScreenBuyed == 1) ? true: false);
		
		String couponAmount = orderDto.getCouponAmount();	//优惠券金额
		if (!StringUtils.isEmpty(couponAmount)) {
			saleOrderReq.setCouponAmount(couponAmount);
		}
		
		//开票信息
		Integer applyInvoice = orderDto.getApplyInvoice();		//是否开发票
		saleOrderReq.setApplyInvoice(applyInvoice == 1);
		
		if (applyInvoice == 1) {
			RentInvoice rentInvoice = new RentInvoice();
			rentInvoice.setContent(orderDto.getInvoiceContent());			//发票内容 
			rentInvoice.setCreateOn(new Date());
			rentInvoice.setFee(new BigDecimal(orderDto.getInvoiceFee()).setScale(2, RoundingMode.DOWN));	//发票金额 
			rentInvoice.setInvoiceNumber(orderDto.getInvoiceNumber());		//税号 
			rentInvoice.setTitle(orderDto.getInvoiceTitle());				//发票抬头 
			rentInvoice.setTitleType(orderDto.getTitleType());				//发票抬头类型 0:个人 1:企业单位 
			saleOrderReq.setRentInvoice(rentInvoice);
		}
		
		return saleOrderReq;
	}
	
	private void updateUserAddress(Long userId, SaleOrderSubmitDto dto) {
		//收货地址-省
		String addrProvince = dto.getAddrProvince();
		//收货地址-市
		String addrCity = dto.getAddrCity();
		//收货地址-地区
		String addrArea = dto.getAddrArea();
		//收货地址-详细地址
		String addrDetail = dto.getAddrDetail();
		
		AddressInfo addressInfo = new AddressInfo();
		addressInfo.setUserId(userId);
		addressInfo.setAddrCity(addrCity);
		addressInfo.setAddrArea(addrArea);
		addressInfo.setAddrDetail(addrDetail);
		addressInfo.setAddrProvince(addrProvince);
		
		addressInfoService.updateAddressByUserId(addressInfo);
	}
	
}
