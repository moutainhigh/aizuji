package org.gz.user.hystrix;


import java.util.Map;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.gz.user.dto.UserInfoDto;
import org.gz.user.entity.AppUser;
import org.gz.user.service.UserService;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

/**
 * hystrix impl
 * 
 * @author yangdx
 *
 */
@Component
public class UserServiceHystrixImpl implements UserService {

	@Override
	public ResponseResult<String> login(JSONObject body) {
		System.err.println("============== fallback call UserServiceHystrixImpl login==================");
		ResponseResult<String> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<String> logout(String token) {
		ResponseResult<String> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<String> register(JSONObject body) {
		ResponseResult<String> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<AppUser> queryUserById(Long userId) {
		System.err.println("============== call queryUserById fallback ==================");
		ResponseResult<AppUser> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<?> updateUser(AppUser user) {
		ResponseResult<String> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<?> modifyPassword(JSONObject body) {
		ResponseResult<String> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<?> saveContacts(JSONObject body) {
		ResponseResult<String> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<Map<String, String>> thirdPartyEmpower(JSONObject body) {
		ResponseResult<Map<String, String>> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<Map<String, String>> bindPhone(JSONObject body) {
		ResponseResult<Map<String, String>> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<String> saveOcrResult(JSONObject body) {
		ResponseResult<String> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<String> resetPassword(AppUser user) {
		ResponseResult<String> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<UserInfoDto> queryByCondition(AppUser user) {
		ResponseResult<UserInfoDto> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<AppUser> queryUserByMobile(String mobile) {
		ResponseResult<AppUser> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<?> saveH5FaceResult(JSONObject body) {
		ResponseResult<AppUser> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<Map<String, String>> thirdPartyXcxEmpower(
			JSONObject body) {
		ResponseResult<Map<String, String>> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}
}
