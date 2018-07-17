package org.gz.workorder.service;

import org.gz.common.resp.ResponseResult;
import org.gz.workorder.dto.SearchRecordQueryDto;
import org.gz.workorder.entity.SearchRecord;
import org.gz.workorder.hystrix.SearchRecordServiceHystrixImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="microservice-loverent-workorder-v1", fallback = SearchRecordServiceHystrixImpl.class)
public interface SearchRecordService {
	
	@RequestMapping("/searchrecord/querypagesearchrecord")
	public ResponseResult<SearchRecordQueryDto> queryPageSearchRecord(@RequestBody SearchRecordQueryDto queryDto);
	
	@RequestMapping("/searchrecord/add")
	public ResponseResult<String> add(@RequestBody SearchRecord searchRecord);
}
