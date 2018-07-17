package org.gz.user.hystrix;


import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.gz.user.service.UserSmsCaptcheService;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

/**
 * hystrix impl
 * 
 * @author yangdx
 *
 */
@Component
public class UserSmsCaptcheServiceHystrixImpl implements UserSmsCaptcheService {

	@Override
	public ResponseResult<String> sendCaptche(JSONObject body) {
		ResponseResult<String> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	
}
