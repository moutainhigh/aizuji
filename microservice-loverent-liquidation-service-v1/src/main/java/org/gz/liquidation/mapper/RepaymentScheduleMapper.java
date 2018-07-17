package org.gz.liquidation.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gz.liquidation.common.dto.QueryDto;
import org.gz.liquidation.entity.RepaymentSchedule;

/**
 * 
 * @Description:	还款计划mapper接口
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月20日 	Created
 */
@Mapper
public interface RepaymentScheduleMapper {

	/** 
	* @Description: 生成还款计划
	* @param @param admin
	* @param @return
	* @throws 
	*/
	void addRepaymentScheduleBatch(List<RepaymentSchedule> list);
	/**
	 * 
	 * @Description: 查询列表
	 * @param repaymentSchedule
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月23日
	 */
	List<RepaymentSchedule> selectList(RepaymentSchedule repaymentSchedule);
	/**
	 * 
	 * @Description: 查询还款日
	 * @param orderSN
	 * @return 返回第一期还款日
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月27日
	 */
	RepaymentSchedule queryRepaymentDate(String orderSN);
	/**
	 * 
	 * @Description: 查询未偿还金额
	 * @param repaymentSchedule
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月28日
	 */
	BigDecimal selectSumCurrentBalance(RepaymentSchedule repaymentSchedule);
	/**
	 * 
	 * @Description: 批量更新状态
	 * @param list
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月29日
	 */
	void updateBatch(List<RepaymentSchedule> list);
	/**
	 * 
	 * @Description: 统计已还金额
	 * @param orderSN 订单号
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月19日
	 */
	BigDecimal selectSumActualRepayment(String orderSN);
	/**
	 * 
	 * @Description: 分页查询
	 * @param queryDto
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月2日
	 */
	List<RepaymentSchedule> queryPage(QueryDto queryDto);
	/**
	 * 
	 * @Description: 查询还款数据列表
	 * @param repaymentSchedule
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月5日
	 */
	List<RepaymentSchedule> selectRepaymentList(RepaymentSchedule repaymentSchedule);
	/**
	 * 
	 * @Description: 批量更新是否有效标识
	 * @param list
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月5日
	 */
	void batchUpdateEnableFlag(List<RepaymentSchedule> list);
	/**
	 * 
	 * @Description:  批量更新逾期天数和违约状态
	 * @param list
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月9日
	 */
	void updateOverdueDayBatch(List<RepaymentSchedule> list);
	/**
	 * 
	 * @Description: 查询最新的还款记录
	 * @param repaymentSchedule
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月11日
	 */
	RepaymentSchedule selectLatest(RepaymentSchedule repaymentSchedule);
	/**
	 * 
	 * @Description: 查询本期租金是否结清
	 * @param repaymentSchedule
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月28日
	 */
	int queryCurrentRentIsSettled(RepaymentSchedule repaymentSchedule);
	/**
	 * 
	 * @Description: 批量失效滞纳金数据
	 * @param list
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月6日
	 */
	void batchDisableLateFeeData(List<RepaymentSchedule> list);
	/**
	 * 
	 * @Description: 检查订单是否已经结清
	 * @param orderSN
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月7日
	 */
	int queryOrderIsSettled(String orderSN);
	/**
	 * 
	 * @Description: 计算逾期天数
	 * @param repaymentSchedule
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月8日
	 */
	int sumOverdueDay(RepaymentSchedule repaymentSchedule);
	/**
	 * 
	 * @Description: 查询逾期数据
	 * @param date 还款日期
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月9日
	 */
	List<RepaymentSchedule> selectOverdueList(Date date);
	/**
	 * 
	 * @Description: 根据主键id更新属性不为null的列
	 * @param repaymentSchedule
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月19日
	 */
	void updateByPrimaryKeySelective(RepaymentSchedule repaymentSchedule);
	/**
	 * 
	 * @Description: 统计总租金
	 * @param orderSN
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月27日
	 */
	BigDecimal sumRent(String orderSN);
	/**
	 * 
	 * @Description: 查询逾期数据(发送逾期短信提醒)
	 * @param repaymentSchedule
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月29日
	 */
	List<RepaymentSchedule> queryOverdueList(@Param("array") String[] array,@Param("minDay") int minDay,@Param("maxDay") int maxDay);
	/**
	 * 
	 * @Description: 查询需要发送短信提醒的数据集合(交租日前3天提醒)
	 * @param date 交租日期
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月29日
	 */
	List<RepaymentSchedule> queryRepaymentRentList(Date date);
}
