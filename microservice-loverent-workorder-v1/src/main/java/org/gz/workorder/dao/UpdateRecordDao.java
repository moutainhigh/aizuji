package org.gz.workorder.dao;

import org.apache.ibatis.annotations.Mapper;
import org.gz.workorder.entity.UpdateRecord;

/**
 * UpdateRecord Dao
 * 
 * @author phd
 * @date 2018-01-17
 */
@Mapper
public interface UpdateRecordDao {

	void add(UpdateRecord record);
	
}
