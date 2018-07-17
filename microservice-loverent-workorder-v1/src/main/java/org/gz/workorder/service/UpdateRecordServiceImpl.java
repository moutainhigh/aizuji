package org.gz.workorder.service;

import lombok.extern.slf4j.Slf4j;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.workorder.entity.UpdateRecord;
import org.gz.workorder.service.atom.UpdateRecordAtomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class UpdateRecordServiceImpl implements UpdateRecordService {
	
	@Autowired
	private UpdateRecordAtomService updateRecordAtomService;

	@Override
	public ResponseResult<String> add(@RequestBody UpdateRecord updateRecord) {
		try {
			updateRecordAtomService.add(updateRecord);
		} catch (Exception e) {
			log.error("UpdateRecordServiceImpl add : {}",e);
			return ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(), ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(), null);
		}
		return ResponseResult.buildSuccessResponse();
	}
	

}
