package org.gz.workorder.service;

import lombok.extern.slf4j.Slf4j;

import org.gz.common.resp.ResponseResult;
import org.gz.workorder.dto.SearchRecordQueryDto;
import org.gz.workorder.entity.SearchRecord;
import org.gz.workorder.service.atom.SearchRecordAtomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SearchRecordServiceImpl implements SearchRecordService {
	
	@Autowired
	private SearchRecordAtomService searchRecordAtomService;

	@Override
	public ResponseResult<SearchRecordQueryDto> queryPageSearchRecord(@RequestBody SearchRecordQueryDto queryDto) {
		ResponseResult<SearchRecordQueryDto> result = new ResponseResult<>();
		SearchRecordQueryDto dto = searchRecordAtomService.queryPageSearchRecord(queryDto);
		result.setData(dto);
		return result;
	}

	@Override
	public ResponseResult<String> add(@RequestBody SearchRecord searchRecord) {
		return searchRecordAtomService.add(searchRecord);
	}
	

}
