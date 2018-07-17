package org.gz.user.service;

import lombok.extern.slf4j.Slf4j;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.gz.user.dto.UserInfoDto;
import org.gz.user.entity.UserInfo;
import org.gz.user.exception.UserServiceException;
import org.gz.user.service.atom.UserInfoAtomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {
	
	@Autowired
	private UserInfoAtomService userInfoServiceAtomService;

	@Override
	public ResponseResult<UserInfoDto> queryByUserId(@RequestParam("userId") Long userId) {
		log.info("start execute UserInfoServiceImpl.queryByUserId");
		ResponseResult<UserInfoDto> result = new ResponseResult<>();
		try {
			UserInfoDto userInfoDto = userInfoServiceAtomService.queryByUserId(userId);
			result.setData(userInfoDto);
		} catch (UserServiceException e) {
			 log.error("execute UserInfoServiceImpl.queryByUserId failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("execute UserInfoServiceImpl.queryByUserId failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

	@Override
	public ResponseResult<String> updateUserInfo(@RequestBody UserInfo userInfo) {
		log.info("start execute UserInfoServiceImpl.updateUserInfo");
		ResponseResult<String> result = new ResponseResult<>();
		try {
			userInfoServiceAtomService.updateUserInfo(userInfo);
		} catch (UserServiceException e) {
			 log.error("execute UserInfoServiceImpl.updateUserInfo failed, UserServiceException: {}", e);
			 result.setErrCode(e.getCode());
			 result.setErrMsg(e.getMessage());
		} catch (Exception e) {
			log.error("execute UserInfoServiceImpl.updateUserInfo failed, Exception: {}", e);
			 ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

}
