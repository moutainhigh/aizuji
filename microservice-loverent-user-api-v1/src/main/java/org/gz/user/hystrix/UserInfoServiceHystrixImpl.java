package org.gz.user.hystrix;

import lombok.extern.slf4j.Slf4j;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.gz.user.dto.UserInfoDto;
import org.gz.user.entity.UserInfo;
import org.gz.user.service.UserInfoService;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserInfoServiceHystrixImpl implements UserInfoService {

	@Override
	public ResponseResult<UserInfoDto> queryByUserId(Long userId) {
		log.error("---->execute UserInfoServiceHystrixImpl.queryByUserId fallback");
		ResponseResult<UserInfoDto> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<String> updateUserInfo(UserInfo userInfo) {
		log.error("---->execute UserInfoServiceHystrixImpl.updateUserInfo fallback");
		ResponseResult<String> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

}
