package org.gz.liquidation.service.repayment;

import java.util.List;

import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.dto.RepaymentStatisticsResp;
import org.gz.liquidation.entity.RepaymentStatistics;
/**
 * 
 * @Description:TODO	还款统计服务接口
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月30日 	Created
 */
public interface RepaymentStatisticsService {
	/**
	 * 
	 * @Description: TODO 新增单条记录
	 * @param repaymentStatistics
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月30日
	 */
	void add(RepaymentStatistics repaymentStatistics);
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
	 * @Description: TODO 批量新增或者更新履约次数
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
	ResponseResult<List<RepaymentStatisticsResp>> queryList(List<String> list);
}
