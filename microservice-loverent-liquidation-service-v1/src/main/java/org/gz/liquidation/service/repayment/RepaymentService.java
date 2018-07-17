package org.gz.liquidation.service.repayment;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.dto.SettleDetailResp;
import org.gz.liquidation.common.dto.alipay.ZhimaOrderCreditPayReq;
import org.gz.liquidation.common.dto.alipay.ZhimaOrderCreditPayResponse;
import org.gz.liquidation.common.dto.repayment.ZmStatementDetailResp;
import org.gz.liquidation.common.dto.repayment.ZmRepaymentScheduleReq;
import org.gz.liquidation.entity.RepaymentSchedule;
import org.gz.liquidation.entity.TransactionRecord;

/**
 * 
 * @Description:还款服务接口
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年2月28日 	Created
 */
public interface RepaymentService {
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
	 * @Description: 偿还租金（如果有滞纳金，则优先偿还滞纳金）
	 * @param transactionRecord
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月8日
	 */
	ResponseResult<?> repayRent(TransactionRecord transactionRecord);
	/**
	 * 
	 * @Description: 销售
	 * @param transactionRecord
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月2日
	 */
	ResponseResult<?> sell(TransactionRecord transactionRecord);
	/**
	 * 
	 * @Description: 检查订单是否已经结清
	 * @param orderSN
	 * @return true 是 false 否
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月7日
	 */
	boolean queryOrderIsSettled(String orderSN);
	/**
	 * 
	 * @Description: 查询最新租金结清记录
	 * @param orderSN 订单号
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月8日
	 */
	RepaymentSchedule selectLatestSettledRent(String orderSN);
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
	 * @Description: 统计已还金额
	 * @param orderSN 订单号
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月9日
	 */
	BigDecimal selectSumActualRepayment(String orderSN);
	/**
	 * 
	 * @Description: 偿还公共方法
	 * @return 更新后的还款计划实体集合
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月15日
	 */
	List<RepaymentSchedule> repay(TransactionRecord transactionRecord,String settleWay);
	/**
	 * 
	 * @Description: 结清详情页查询
	 * @param orderSN
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月27日
	 */
	ResponseResult<SettleDetailResp> settleDetail(String orderSN);
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
	 * @Description: 生成芝麻订单还款计划
	 * @param zmRepaymentScheduleReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月27日
	 */
	ResponseResult<?> addZmRepaymentSchedule(ZmRepaymentScheduleReq zmRepaymentScheduleReq);
	/**
	 * 
	 * @Description: 自动扣款
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月28日
	 */
    void doAutomaticDeductions(List<RepaymentSchedule> list);
	/**
	 * 
	 * @Description: 查询逾期数据(发送逾期短信提醒)
	 * @param repaymentSchedule
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月29日
	 */
    List<RepaymentSchedule> selectOverdueList(String[] array,int minDay,int maxDay);
    /**
     * 
     * @Description: 发送逾期短信提醒(逾期天数为1-4天(包含边界))
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年3月29日
     */
    void sendOverdueSms(String[] array,Integer minDay,Integer maxDay);
    /**
     * 
     * @Description:交租短信提醒
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年3月29日
     */
    void sendRepayRentSms();
    /**
     * 
     * @Description: 芝麻订单信用支付回调
     * @param zhimaOrderCreditPayResponse
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年3月31日
     */
    ResponseResult<?> zhimaOrderCreditPayCallback(ZhimaOrderCreditPayResponse zhimaOrderCreditPayResponse);
    /**
     * 
     * @Description: 芝麻信用支付
     * @param zhimaOrderCreditPayReq 信用支付请求参数DTO
     * @param transactionSN 交易流水
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年3月31日
     */
    ResponseResult<?> zhimaOrderCreditPay(ZhimaOrderCreditPayReq zhimaOrderCreditPayReq,String transactionSN);
    /**
     * 
     * @Description: 芝麻订单还款详情
     * @param orderSN
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年4月3日
     */
    ResponseResult<ZmStatementDetailResp> queryZmStatementDetail(String orderSN);
}
