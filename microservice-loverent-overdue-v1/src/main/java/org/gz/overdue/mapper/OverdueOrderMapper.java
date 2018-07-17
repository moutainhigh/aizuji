package org.gz.overdue.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.overdue.entity.OverdueOrder;
import org.gz.overdue.entity.OverdueOrderPage;
@Mapper
public interface OverdueOrderMapper {
	/**
	 * 插入记录
	 * @param OverdueOrder
	 */
	void add(OverdueOrder overdueOrder);
	
	/**
	 * 根据ID删除记录
	 * @param id
	 */
	void deleteById(String id);
	
	/**
	 * 根据ID修改记录
	 * @param settleOrder
	 */
	void update(OverdueOrder overdueOrder);
	
	/**
	 * 根据条件查询记录(分页)
	 * @param settleOrderPage
	 */
	List<OverdueOrder> queryList(OverdueOrderPage overdueOrderPage);
	
	/**
	 * 根据条件查询记录(分页)
	 * @param settleOrderPage
	 */
	List<OverdueOrder> query(OverdueOrder overdueOrder);
	
	/**
	 * 根据条件查询记录总数
	 */
	Integer queryCount(OverdueOrderPage overdueOrderPage);
	
	/**
	 * 删除逾期过期数据
	 * @param overdueOrder
	 * @return
	 */
	Integer deleteOverdueData(OverdueOrder overdueOrder);
}
