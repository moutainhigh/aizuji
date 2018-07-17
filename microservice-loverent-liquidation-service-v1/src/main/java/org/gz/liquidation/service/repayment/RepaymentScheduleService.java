package org.gz.liquidation.service.repayment;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.dto.BuyoutResp;
import org.gz.liquidation.common.dto.DuesDetailResp;
import org.gz.liquidation.common.dto.ManualSettleReq;
import org.gz.liquidation.common.dto.PaymentReq;
import org.gz.liquidation.common.dto.QueryDto;
import org.gz.liquidation.common.dto.RepaymentDetailResp;
import org.gz.liquidation.common.dto.RepaymentReq;
import org.gz.liquidation.common.dto.RepaymentScheduleResp;
import org.gz.liquidation.common.entity.BuyoutPayReq;
import org.gz.liquidation.common.entity.ReadyBuyoutReq;
import org.gz.liquidation.common.entity.RepaymentScheduleReq;
import org.gz.liquidation.entity.RepaymentSchedule;
import org.gz.liquidation.entity.TransactionRecord;

/**
 * 
 * @Description:	还款计划服务接口
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月20日 	Created
 */
public interface RepaymentScheduleService {
	/**
	 * 
	 * @Description: 生成还款计划
	 * @param repaymentScheduleReq
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月20日
	 */
	ResponseResult<RepaymentScheduleReq> addRepaymentSchedule(RepaymentScheduleReq repaymentScheduleReq);
	/**
	 * 
	 * @Description: 查询列表
	 * @param repaymentSchedule
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月25日
	 */
	List<RepaymentSchedule> selectList(RepaymentSchedule repaymentSchedule);
	/**
	 * 
	 * @Description: 计算买断金
	 * @param readyBuyoutReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月25日
	 */
	ResponseResult<BuyoutResp> readyBuyout(ReadyBuyoutReq readyBuyoutReq);
	/**
	 * 
	 * @Description: 执行买断
	 * @param transactionRecord
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月26日
	 */
	ResponseResult<?> doBuyout(TransactionRecord transactionRecord);
	/**
	 * 
	 * @Description: 买断支付回调处理
	 * @param paymentReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月27日
	 */
	ResponseResult<?> buyoutCallback(PaymentReq paymentReq);
	/**
	 * 
	 * @Description: 查询本期还款日
	 * @param orderSN 订单号
	 * @return	本期还款日
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月27日
	 */
	ResponseResult<Date> queryRepaymentDate(String orderSN);
	/**
	 * 
	 * @Description: 验证是否符合买断情景
	 * @param orderSN 订单号
	 * @param type 买断类型 1:正常 2:逾期
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月28日
	 */
	ResponseResult<?> validBuyout(String orderSN,int type);
	/**
	 * 
	 * @Description: 执行买断支付
	 * @param buyoutPayReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月28日
	 */
	ResponseResult<?> doBuyoutPay(BuyoutPayReq buyoutPayReq);
	/**
	 * 
	 * @Description: 批量更新状态
	 * @param state
	 * @param list
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月29日
	 */
	void updateBatch(List<RepaymentSchedule> list);
	/**
	 * 
	 * @Description: 核销还款计划账单
	 * @param repaymentReq
	 * @param list
	 * @param settleWay
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月2日
	 */
	List<RepaymentSchedule> chargeOff(RepaymentReq repaymentReq,List<RepaymentSchedule> list,String settleWay);
	/**
	 * 
	 * @Description: 批量新增还款计划
	 * @param list
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月2日
	 */
	void addRepaymentScheduleBatch(List<RepaymentSchedule> list);
	/**
	 * 
	 * @Description: 查询订单还款详情
	 * @param orderSN	订单号
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月2日
	 */
	ResponseResult<RepaymentDetailResp> queryRepaymentDetail(String orderSN);
	/**
	 * 
	 * @Description: 查询应付款
	 * @param orderSN	订单号
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月3日
	 */
	ResponseResult<DuesDetailResp> queryDuesDetail(String orderSN);
	/**
	 * 
	 * @Description: 
	 * @param repaymentScheduleReq 买断核销
	 * @param type	买断类型 1:正常  2:逾期
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月8日
	 */
	ResponseResult<?> buyoutWriteOff(RepaymentScheduleReq repaymentScheduleReq,int type);
	/**
	 * 
	 * @Description: 查询买断订单详情
	 * @param orderSN	订单号
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月24日
	 */
	ResponseResult<RepaymentDetailResp> queryBuyoutOrderDetail(String orderSN);
	/**
	 * 
	 * @Description: 分页查询
	 * @param queryDto
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月2日
	 */
	ResultPager<RepaymentScheduleResp> queryPage(QueryDto queryDto);
	/**
	 * 
	 * @Description: 查询未偿还金额
	 * @param orderSN 订单号
	 * @param repaymentType	还款类型 1 租金 2滞纳金 3 折旧违约金
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月2日
	 */
	BigDecimal selectSumCurrentBalance(String orderSN,Integer repaymentType);
	/**
	 * 
	 * @Description: 查询未还滞纳金和已还滞纳金
	 * @param orderSN
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月2日
	 */
	Map<String,BigDecimal> selectLateFees(String orderSN);
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
	 * @Description: 手动结算
	 * @param manualSettleReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月5日
	 */
	ResponseResult<?> manualSettle(ManualSettleReq manualSettleReq);
	/**
	 * 
	 * @Description: 手动结算租金
	 * @param manualSettleReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月5日
	 */
	ResponseResult<?> manualSettleRent(ManualSettleReq manualSettleReq);
	/**
	 * 
	 * @Description: 手动结算买断
	 * @param manualSettleReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月5日
	 */
	ResponseResult<?> manualSettleBuyout(ManualSettleReq manualSettleReq);
	/**
	 * 
	 * @Description: 结算数据处理
	 * @param updateList 更新数据集合列表
	 * @param addList	新增数据集合列表
	 * @param settleWay	结清方式
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月5日
	 */
	void settleDataHandle(List<RepaymentSchedule> updateList,List<RepaymentSchedule> addList,String settleWay);
	/**
	 * 
	 * @Description: 计算两个月份相差多少个月
	 * @param start 第一个月份
	 * @param end  第二个月份
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月26日
	 */
	int differMonth(Date start,Date end);
	/**
	 * 
	 * @Description: 根据主键id更新
	 * @param repaymentSchedule
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月17日
	 */
	void updateByPrimaryKeySelective(RepaymentSchedule repaymentSchedule);
	/**
	 * 
	 * @Description: 手动清偿:首期支付
	 * @param manualSettleReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月10日
	 */
	ResponseResult<?> manualSettleDownPayment(ManualSettleReq manualSettleReq);
    /**
     * 
     * @Description: 扣款成功
     * @param transactionSN 交易流水号
     * @param repaymentSchedule
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年4月2日
     */
    void deductSuccessfully(String transactionSN,RepaymentSchedule repaymentSchedule);
}
