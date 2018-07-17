package org.gz.app.web.controller;

import org.gz.common.resp.ResponseResult;
import org.gz.user.service.UserSmsCaptcheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/sms")
public class SmsCaptcheController {

	@Autowired
	private UserSmsCaptcheService userSmsCaptcheService;
	
	/**
	 * APP 短信验证码发送
	 * @param body
	 * @return
	 */
	@RequestMapping(value="/sendCaptche", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<String> sendCaptche(@RequestBody JSONObject body) {
		ResponseResult<String> result = userSmsCaptcheService.sendCaptche(body);
		return result;
	}
}
