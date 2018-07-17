package org.gz.workorder.service;

import org.gz.common.resp.ResponseResult;
import org.gz.workorder.entity.UpdateRecord;
import org.gz.workorder.hystrix.SearchRecordServiceHystrixImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="microservice-loverent-workorder-v1", fallback = SearchRecordServiceHystrixImpl.class)
public interface UpdateRecordService {
	
	@RequestMapping("/updaterecord/add")
	public ResponseResult<String> add(@RequestBody UpdateRecord updateRecord);
	
}
