package org.gz.liquidation.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.liquidation.entity.RepaymentStatistics;

@Mapper
public interface RepaymentStatisticsMapper {
	/**
	 * 
	 * @Description: TODO 新增单条记录
	 * @param repaymentStatistics
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月30日
	 */
	void insertSelective(RepaymentStatistics repaymentStatistics);
	/**
	 * 
	 * @Description: TODO 批量新增或者更新履约次数
	 * @param list
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月30日
	 */
	void insertPerformanceCountBatch(List<RepaymentStatistics> list);
	/**
	 * 
	 * @Description: TODO 批量新增或者更新违约次数
	 * @param list
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月30日
	 */
	void insertBreachCountBatch(List<RepaymentStatistics> list);
	/**
	 * 
	 * @Description: TODO 根据订单号查询列表
	 * @param list	订单号集合
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月30日
	 */
	List<RepaymentStatistics> queryList(List<String> list);
	/**
	 * 
	 * @Description: TODO 查询单个对象
	 * @param repaymentStatistics
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月30日
	 */
	RepaymentStatistics selectOne(RepaymentStatistics repaymentStatistics);
}
