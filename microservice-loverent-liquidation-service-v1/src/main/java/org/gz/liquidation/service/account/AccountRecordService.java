package org.gz.liquidation.service.account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.dto.AccountRecordResp;
import org.gz.liquidation.common.dto.DownpaymentStatisticsReq;
import org.gz.liquidation.common.dto.DownpaymentStatisticsResp;
import org.gz.liquidation.common.dto.QueryDto;
import org.gz.liquidation.common.dto.RevenueStatisticsResp;
import org.gz.liquidation.entity.AccountRecord;
/**
 * 
 * @Description:	科目流水记录服务
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月22日 	Created
 */
public interface AccountRecordService {
	/**
	 * 
	 * @Description: 新增科目流水记录
	 * @param accountRecord
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月22日
	 */
	ResponseResult<AccountRecord> addAccountRecord(AccountRecord accountRecord);
	/**
	 * 
	 * @Description: 根据主键查询流水记录
	 * @param id
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月22日
	 */
	ResponseResult<AccountRecord> selectByPrimaryKey(Long id);
	/**
	 * 
	 * @Description: 根据条件查询列表
	 * @param accountRecord
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月22日
	 */
	List<AccountRecord> selectList(AccountRecord accountRecord);
	/**
	 * 
	 * @Description: 批量新增
	 * @param list
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月23日
	 */
	void insertBatch(List<AccountRecord> list);
	/**
	 * 
	 * @Description: 统计科目金额总额
	 * @param accountRecord
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月23日
	 */
	BigDecimal selectSumAmount(AccountRecord accountRecord);
	/**
	 * 
	 * @Description: 查询流入流出统计某一科目金额
	 * @param accountRecord
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月25日
	 */
	List<AccountRecord> selectAmount(AccountRecord accountRecord);
	/**
	 * 
	 * @Description: 首期支付统计
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月4日
	 */
	ResponseResult<DownpaymentStatisticsResp> queryDownpaymentStatistics(DownpaymentStatisticsReq downpaymentStatisticsReq);
	/**
	 * 
	 * @Description: 分页查询
	 * @param queryDto
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月30日
	 */
	ResultPager<AccountRecordResp> selectPage(QueryDto queryDto);
	/**
	 * 
	 * @Description: 收入统计
	 * @param map
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月1日
	 */
	ResponseResult<RevenueStatisticsResp> selectRevenue(Map<String,Object> map);
	/**
	 * 
	 * @Description: 根据订单号统计滞纳金和减免滞纳金总额
	 * @param list
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月7日
	 */
	List<AccountRecord> sumLatefee(List<String> list);
	/**
	 * 
	 * @Description: 查询减免费用
	 * @param orderSN 订单号
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月27日
	 */
	BigDecimal queryRemissionFee(String orderSN);
}
