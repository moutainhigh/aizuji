package org.gz.overdue.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.overdue.entity.ContactRecord;
import org.gz.overdue.entity.ContactRecordPage;
import org.gz.overdue.entity.SettleOrder;
@Mapper
public interface ContactRecordMapper {
	/**
	 * 插入记录
	 * @param SettleOrder
	 */
	void add(ContactRecord contactRecord);
	
	/**
	 * 根据ID删除记录
	 * @param id
	 */
	void deleteById(String id);
	
	/**
	 * 根据ID修改记录
	 * @param settleOrder
	 */
	void update(ContactRecord contactRecord);
	
	/**
	 * 根据条件查询记录(分页)
	 * @param settleOrderPage
	 */
	List<ContactRecord> queryList(ContactRecordPage contactRecordPage);
	
	/**
	 * 根据条件查询记录总数
	 */
	Integer queryCount(ContactRecordPage contactRecordPage);
}
