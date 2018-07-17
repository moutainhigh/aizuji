package org.gz.user.service;

import java.util.Map;

import org.gz.common.resp.ResponseResult;
import org.gz.user.dto.UserInfoDto;
import org.gz.user.entity.AppUser;
import org.gz.user.hystrix.UserServiceHystrixImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;

@FeignClient(name="microservice-loverent-user-v1", fallback = UserServiceHystrixImpl.class)
public interface UserService {
	
	@RequestMapping(value="/user/login", method=RequestMethod.POST)
	public ResponseResult<String> login(@RequestBody JSONObject body);
	
	@RequestMapping(value="/user/logout", method=RequestMethod.POST)
	public ResponseResult<String> logout(@RequestParam(name="token") String token);
	
	@RequestMapping(value="/user/register", method=RequestMethod.POST)
	public ResponseResult<String> register(@RequestBody JSONObject body);
	
	@RequestMapping(value="/user/queryUserById", method=RequestMethod.POST)
	public ResponseResult<AppUser> queryUserById(@RequestParam(name="userId") Long userId);
	
	@RequestMapping(value="/user/updateUser", method=RequestMethod.POST)
	public ResponseResult<?> updateUser(@RequestBody AppUser user);
	
	@RequestMapping(value="/user/modifyPassword", method=RequestMethod.POST)
	public ResponseResult<?> modifyPassword(@RequestBody JSONObject body);
	
	@RequestMapping(value="/user/saveContacts", method=RequestMethod.POST)
	public ResponseResult<?> saveContacts(@RequestBody JSONObject body);
	
	@RequestMapping(value="/user/thirdPartyEmpower", method=RequestMethod.POST)
	public ResponseResult<Map<String, String>> thirdPartyEmpower(@RequestBody JSONObject body);

	@RequestMapping(value="/user/bindPhone", method=RequestMethod.POST)
	public ResponseResult<Map<String, String>> bindPhone(@RequestBody JSONObject body);
	
	@RequestMapping(value="/user/saveOcrResult", method=RequestMethod.POST)
	public ResponseResult<String> saveOcrResult(@RequestBody JSONObject body);
	
	@RequestMapping(value="/user/resetPassword", method=RequestMethod.POST)
	public ResponseResult<String> resetPassword(@RequestBody AppUser user);
	
	@RequestMapping(value="/user/queryByCondition", method=RequestMethod.POST)
	public ResponseResult<UserInfoDto> queryByCondition(@RequestBody AppUser user);

	@RequestMapping(value="/user/queryUserByMobile", method=RequestMethod.POST)
	public ResponseResult<AppUser> queryUserByMobile(@RequestParam("mobile") String mobile);

	@RequestMapping(value="/user/saveH5FaceResult", method=RequestMethod.POST)
	public ResponseResult<?> saveH5FaceResult(JSONObject body);

	@RequestMapping(value="/user/thirdPartyXcxEmpower", method=RequestMethod.POST)
	public ResponseResult<Map<String, String>> thirdPartyXcxEmpower(@RequestBody JSONObject body);
	
}
