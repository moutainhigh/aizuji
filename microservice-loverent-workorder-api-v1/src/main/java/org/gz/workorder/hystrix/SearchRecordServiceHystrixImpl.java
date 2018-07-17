package org.gz.workorder.hystrix;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.workorder.dto.SearchRecordQueryDto;
import org.gz.workorder.entity.SearchRecord;
import org.gz.workorder.service.SearchRecordService;
import org.springframework.stereotype.Component;

/**
 * hystrix impl
 * 
 * @author phd
 *
 */
@Component
public class SearchRecordServiceHystrixImpl implements SearchRecordService {


	@Override
	public ResponseResult<SearchRecordQueryDto> queryPageSearchRecord(SearchRecordQueryDto queryDto) {
		return ResponseResult.build(ResponseStatus.SERVICE_CALL_OVERTIME.getCode(), ResponseStatus.SERVICE_CALL_OVERTIME.getMessage(), null);
	}

	@Override
	public ResponseResult<String> add(SearchRecord searchRecord) {
		return ResponseResult.build(ResponseStatus.SERVICE_CALL_OVERTIME.getCode(), ResponseStatus.SERVICE_CALL_OVERTIME.getMessage(), null);
	}
}
