package org.gz.workorder.service.atom.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.workorder.dao.SearchRecordDao;
import org.gz.workorder.dto.SearchRecordQueryDto;
import org.gz.workorder.entity.SearchRecord;
import org.gz.workorder.service.atom.SearchRecordAtomService;
import org.gz.workorder.supports.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SearchRecordAtomServiceImpl implements SearchRecordAtomService {

	@Autowired
	private SearchRecordDao searchRecordDao;
	
	@Override
	public SearchRecordQueryDto queryPageSearchRecord(SearchRecordQueryDto queryDto) {
		Pagination<SearchRecord> pagination = new Pagination<>();
		pagination.setPage(queryDto.getCurrPage());
		pagination.setLimit(queryDto.getPageSize());
		Map<String, Object> condition = new HashMap<>();
		condition.put("operator", queryDto.getOperator());
		pagination.setParams(condition);
		List<SearchRecord> searchRecords = searchRecordDao.queryPageSearchRecord(pagination);
        queryDto.setTotalNum(pagination.getTotal());
		queryDto.setData(searchRecords);
		return queryDto;
	}

	@Override
	public ResponseResult<String> add(SearchRecord searchRecord) {
		try {
			searchRecordDao.add(searchRecord);
			return ResponseResult.buildSuccessResponse();
		} catch (Exception e) {
			log.error("SearchRecordAtomServiceImpl add : {}",e);
			return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), ResponseStatus.DATA_CREATE_ERROR.getMessage(), null);
		}
		
	}

	

}
