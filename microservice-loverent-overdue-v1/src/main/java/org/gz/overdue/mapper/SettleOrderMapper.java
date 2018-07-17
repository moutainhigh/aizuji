package org.gz.overdue.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.overdue.entity.SettleOrder;
import org.gz.overdue.entity.SettleOrderPage;
@Mapper
public interface SettleOrderMapper {
	
	/**
	 * 插入记录
	 * @param SettleOrder
	 */
	void add(SettleOrder settleOrder);
	
	/**
	 * 根据ID删除记录
	 * @param id
	 */
	void deleteById(String id);
	
	/**
	 * 根据ID修改记录
	 * @param settleOrder
	 */
	void update(SettleOrder settleOrder);
	
	/**
	 * 根据条件查询记录(分页)
	 * @param settleOrderPage
	 */
	List<SettleOrder> queryList(SettleOrderPage settleOrderPage);
	
	/**
	 * 根据条件查询记录总数
	 */
	Integer queryCount(SettleOrder settleOrder);
}
