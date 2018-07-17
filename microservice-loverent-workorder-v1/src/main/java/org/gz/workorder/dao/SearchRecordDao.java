package org.gz.workorder.dao;

import org.apache.ibatis.annotations.Mapper;
import org.gz.workorder.entity.SearchRecord;
import org.gz.workorder.supports.Pagination;

import java.util.List;

/**
 * SearchRecord Dao
 * 
 * @author phd
 * @date 2018-01-17
 */
@Mapper
public interface SearchRecordDao{
	


	public List<SearchRecord> queryPageSearchRecord(Pagination<SearchRecord> pagination);

	public void add(SearchRecord searchRecord);
}
