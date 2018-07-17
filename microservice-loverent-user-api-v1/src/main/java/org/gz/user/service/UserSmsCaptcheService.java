package org.gz.user.service;

import org.gz.common.resp.ResponseResult;
import org.gz.user.hystrix.UserSmsCaptcheServiceHystrixImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;

@FeignClient(name="microservice-loverent-user-v1", fallback = UserSmsCaptcheServiceHystrixImpl.class)
public interface UserSmsCaptcheService {

	@RequestMapping("/sms/sendCaptche")
	ResponseResult<String> sendCaptche(JSONObject body);
}
