package org.gz.workorder.hystrix;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.workorder.entity.UpdateRecord;
import org.gz.workorder.service.UpdateRecordService;
import org.springframework.stereotype.Component;

/**
 * hystrix impl
 * 
 * @author phd
 *
 */
@Component
public class UpdateRecordServiceHystrixImpl implements UpdateRecordService {

	@Override
	public ResponseResult<String> add(UpdateRecord updateRecord) {
		return ResponseResult.build(ResponseStatus.SERVICE_CALL_OVERTIME.getCode(), ResponseStatus.SERVICE_CALL_OVERTIME.getMessage(), null);
	}

}
