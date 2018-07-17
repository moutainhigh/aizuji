package org.gz.user.service;

import org.gz.common.resp.ResponseResult;
import org.gz.user.dto.UserInfoDto;
import org.gz.user.entity.UserInfo;
import org.gz.user.hystrix.UserInfoServiceHystrixImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="microservice-loverent-user-v1", fallback=UserInfoServiceHystrixImpl.class)
public interface UserInfoService {

	/**
	 * 查询用户信息
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/userinfo/queryByUserId", method=RequestMethod.POST)
	public ResponseResult<UserInfoDto> queryByUserId(@RequestParam(name="userId") Long userId);
	
	/**
	 * 更新用户信息
	 * @param userInfo
	 * @return
	 */
	@RequestMapping(value="/userinfo/updateUserInfo", method=RequestMethod.POST)
	public ResponseResult<String> updateUserInfo(@RequestBody UserInfo userInfo);
	
}
