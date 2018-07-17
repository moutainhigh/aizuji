package org.gz.workorder.service.atom;

import org.gz.common.resp.ResponseResult;
import org.gz.workorder.dto.SearchRecordQueryDto;
import org.gz.workorder.entity.SearchRecord;

public interface SearchRecordAtomService {

	public SearchRecordQueryDto queryPageSearchRecord(SearchRecordQueryDto queryDto);

	public ResponseResult<String> add(SearchRecord searchRecord);
}
