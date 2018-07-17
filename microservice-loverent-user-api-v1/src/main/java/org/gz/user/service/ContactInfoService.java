package org.gz.user.service;

import org.gz.common.resp.ResponseResult;
import org.gz.user.dto.ContactInfoQueryDto;
import org.gz.user.hystrix.ContactInfoServiceHystrixImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="microservice-loverent-user-v1", fallback = ContactInfoServiceHystrixImpl.class)
public interface ContactInfoService {
	
	@RequestMapping("/contact/queryContactInfoByPage")
	public ResponseResult<ContactInfoQueryDto> queryContactInfoByPage(@RequestBody ContactInfoQueryDto queryDto);
	
}
