package org.gz.app.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.configure.AlipayConfigure;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.IPUtil;
import org.gz.common.utils.SessionUtil;
import org.gz.user.commons.UserConstants;
import org.gz.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;

/**
 * 
 * @author yangdx
 *
 */
@RestController
@RequestMapping("/thirdParty")
@Slf4j
public class EmpowerFromThirdParty extends AppBaseController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/getAlipayLoginSignData")
	@ResponseBody
	public ResponseResult<Map<String, String>> getAlipayLoginSignData(HttpServletRequest request) {
		ResponseResult<Map<String, String>> result = new ResponseResult<>();
		
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("apiname", "com.alipay.account.auth");
		dataMap.put("method", "alipay.open.auth.sdk.code.get");
		dataMap.put("app_id", AlipayConfigure.APPID);
		dataMap.put("app_name", "aizuji");
		dataMap.put("biz_type", "openservice");
		dataMap.put("pid", AlipayConfigure.PID);
		dataMap.put("product_id", "APP_FAST_LOGIN");
		dataMap.put("scope", "kuaijie");
		dataMap.put("target_id", buildTargetId());
		dataMap.put("auth_type", "AUTHACCOUNT");
		dataMap.put("sign_type", AlipayConfigure.SIGNTYPE);
		
		String signData = null;
		try {
			signData = AlipaySignature.rsaSign(dataMap, AlipayConfigure.RSA_PRIVATE_KEY, AlipayConfigure.CHARSET);
		} catch (AlipayApiException e) {
			log.error("--->【支付宝登录】 --rsaSign failed, e: {}", e);
			result.setErrCode(-1);
			result.setErrMsg("签名失败");
			return result;
		}
		dataMap.put("sign", signData);
		
		result.setData(dataMap);
		return result;
	}
	
	/**
	 * 授权操作
	 * @return
	 */
	@RequestMapping("/empower")
	@ResponseBody
	public ResponseResult<Map<String, String>> empower(@RequestBody JSONObject body, 
			HttpServletRequest request, HttpServletResponse response) {
		log.info("--->【支付宝登录】 start execute empower, params: {}", body.toJSONString());
		body.put("ipAddr", IPUtil.getRemoteAddress(request));
		ResponseResult<Map<String, String>> result = userService.thirdPartyEmpower(body);
		if (result.getErrCode() == 0) {
			Map<String, String> empData = result.getData();
			if (UserConstants.EMPOWER_STATUS_SUCCESS.equals(empData.get("status"))) {
				//授权成功
				SessionUtil.addCookie(empData.get("token"), response);
			}	
		}
		return result;
	}
	
	/**
	 * 小程序授权操作
	 * @return
	 */
	@RequestMapping("/xcxEmpower")
	@ResponseBody
	public ResponseResult<Map<String, String>> xcxEmpower(@RequestBody JSONObject body, 
			HttpServletRequest request, HttpServletResponse response) {
		log.info("--->【支付宝登录】 start execute xcxEmpower, params: {}", body.toJSONString());
		body.put("ipAddr", IPUtil.getRemoteAddress(request));
		ResponseResult<Map<String, String>> result = userService.thirdPartyXcxEmpower(body);
		
		return result;
	}

	/**
	 * 绑定手机号
	 * @return
	 */
	@RequestMapping("/bindPhone")
	@ResponseBody
	public ResponseResult<Map<String, String>> bindPhone(@RequestBody JSONObject body, 
			HttpServletRequest request, HttpServletResponse response) {
		body.put("ipAddr", IPUtil.getRemoteAddress(request));
		ResponseResult<Map<String, String>> result = userService.bindPhone(body);
		if (result.getErrCode() == 0) {
			Map<String, String> empData = result.getData();
			if (UserConstants.BIND_PHONE_SUCCESS.equals(empData.get("status"))) {
				//授权成功
				SessionUtil.addCookie(empData.get("token"), response);
			}	
		}
		return result;
	}
	
	public static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	public String buildTargetId() {
		return format.format(new Date());
	}
}
