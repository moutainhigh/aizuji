package org.gz.user.service;

import lombok.extern.slf4j.Slf4j;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.gz.user.exception.UserServiceException;
import org.gz.user.service.atom.UserSmsCaptcheAtomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@Slf4j
public class UserSmsCaptcheServiceImpl implements UserSmsCaptcheService {

	@Autowired
	private UserSmsCaptcheAtomService userSmsCaptcheAtomService;
	
	@Override
	public ResponseResult<String> sendCaptche(@RequestBody JSONObject body) {
		log.info("start execute sendCaptche");
		ResponseResult<String> result = new ResponseResult<>();
		try {
			userSmsCaptcheAtomService.sendCaptche(body);
		} catch (UserServiceException e) {
			 log.error("sendCaptche failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("sendCaptche failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

}
