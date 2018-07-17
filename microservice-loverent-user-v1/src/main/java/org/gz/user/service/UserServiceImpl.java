package org.gz.user.service;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.gz.cache.manager.RedisManager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.gz.user.dto.UserInfoDto;
import org.gz.user.entity.AppUser;
import org.gz.user.exception.UserServiceException;
import org.gz.user.service.atom.UserAtomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@Slf4j
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserAtomService userAtomService;
	
	@Autowired
	private RedisManager redisManager;
	
	@Override
	public ResponseResult<String> login(@RequestBody JSONObject body) {
		log.info("start execute login");
		ResponseResult<String> result = new ResponseResult<>();
		try {
			String token = userAtomService.login(body);
			result.setData(token);
		} catch (UserServiceException e) {
			 log.error("login failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("login failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

	@Override
	public ResponseResult<String> logout(String token) {
		log.info("start execute logout, token： {}", token);
		ResponseResult<String> result = new ResponseResult<>();
		try {
			userAtomService.logout(token);
		} catch (UserServiceException e) {
			 log.error("logout failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("logout failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

	@Override
	public ResponseResult<String> register(@RequestBody JSONObject body) {
		log.info("start execute register");
		ResponseResult<String> result = new ResponseResult<>();
		try {
			String token = userAtomService.register(body);
			result.setData(token);
		} catch (UserServiceException e) {
			 log.error("register failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("register failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

	@Override
	public ResponseResult<AppUser> queryUserById(@RequestParam(name="userId") Long userId) {
		log.info("start execute queryUserById");
		/*try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		ResponseResult<AppUser> result = new ResponseResult<>();
		try {
			AppUser user = userAtomService.queryUserById(userId);
			result.setData(user);
		} catch (UserServiceException e) {
			 log.error("queryUserById failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("queryUserById failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

	@Override
	public ResponseResult<?> updateUser(@RequestBody AppUser user) {
		log.info("start execute queryUserById");
		ResponseResult<?> result = new ResponseResult<>();
		try {
			userAtomService.updateUser(user);
		} catch (UserServiceException e) {
			 log.error("queryUserById failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("queryUserById failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

	@Override
	public ResponseResult<?> modifyPassword(@RequestBody JSONObject body) {
		log.info("start execute modifyPassword");
		ResponseResult<?> result = new ResponseResult<>();
		try {
			userAtomService.modifyPassword(body);
		} catch (UserServiceException e) {
			 log.error("modifyPassword failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("modifyPassword failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

	@Override
	public ResponseResult<?> saveContacts(@RequestBody JSONObject body) {
		log.info("start execute saveContacts, body: {}", body.toJSONString());
		ResponseResult<?> result = new ResponseResult<>();
		try {
			userAtomService.saveContacts(body);
		} catch (UserServiceException e) {
			 log.error("saveContacts failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("saveContacts failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

	@Override
	public ResponseResult<Map<String, String>> thirdPartyEmpower(@RequestBody JSONObject body) {
		log.info("start execute thirdPartyEmpower");
		ResponseResult<Map<String, String>> result = new ResponseResult<>();
		try {
			Map<String, String> map = userAtomService.thirdPartyEmpower(body);
			result.setData(map);
		} catch (UserServiceException e) {
			 log.error("thirdPartyEmpower failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("thirdPartyEmpower failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

	@Override
	public ResponseResult<Map<String, String>> bindPhone(@RequestBody JSONObject body) {
		log.info("start execute bindPhone");
		ResponseResult<Map<String, String>> result = new ResponseResult<>();
		try {
			Map<String, String> map = userAtomService.bindPhone(body);
			result.setData(map);
		} catch (UserServiceException e) {
			 log.error("bindPhone failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("bindPhone failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

	@Override
	public ResponseResult<String> saveOcrResult(@RequestBody JSONObject body) {
		log.info("start execute saveOcrResult");
		ResponseResult<String> result = new ResponseResult<>();
		try {
			userAtomService.saveOcrResult(body);
		} catch (UserServiceException e) {
			 log.error("saveOcrResult failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("saveOcrResult failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

	@Override
	public ResponseResult<String> resetPassword(@RequestBody AppUser user) {
		log.info("start execute resetPassword");
		ResponseResult<String> result = new ResponseResult<>();
		try {
			userAtomService.resetPassword(user);
		} catch (UserServiceException e) {
			 log.error("resetPassword failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("resetPassword failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

	@Override
	public ResponseResult<UserInfoDto> queryByCondition(@RequestBody AppUser user) {
		log.info("start execute queryByCondition, params: {}", JSONObject.toJSONString(user));
		ResponseResult<UserInfoDto> result = new ResponseResult<>();
		try {
			UserInfoDto dto = userAtomService.queryByCondition(user);
			result.setData(dto);
		} catch (UserServiceException e) {
			 log.error("queryByCondition failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("queryByCondition failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

	@Override
	public ResponseResult<AppUser> queryUserByMobile(String mobile) {
		log.info("start execute queryUserByMobile, params: {}", mobile);
		ResponseResult<AppUser> result = new ResponseResult<>();
		try {
			AppUser user = userAtomService.queryUserByMobile(mobile);
			result.setData(user);
		} catch (UserServiceException e) {
			 log.error("queryUserByMobile failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("queryUserByMobile failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

	@Override
	public ResponseResult<?> saveH5FaceResult(@RequestBody JSONObject body) {
		log.info("start execute saveH5FaceResult");
		ResponseResult<String> result = new ResponseResult<>();
		try {
			userAtomService.saveH5FaceResult(body);
		} catch (UserServiceException e) {
			 log.error("saveH5FaceResult failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("saveH5FaceResult failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

	@Override
	public ResponseResult<Map<String, String>> thirdPartyXcxEmpower(@RequestBody JSONObject body) {
		log.info("start execute thirdPartyXcxEmpower");
		ResponseResult<Map<String, String>> result = new ResponseResult<>();
		try {
			Map<String, String> map = userAtomService.thirdPartyXcxEmpower(body);
			result.setData(map);
		} catch (UserServiceException e) {
			 log.error("thirdPartyXcxEmpower failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("thirdPartyXcxEmpower failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

}
