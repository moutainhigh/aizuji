package org.gz.liquidation.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.gz.common.constants.SmsType;
import org.gz.common.utils.UUIDUtils;
import org.gz.liquidation.common.dto.ManualSettleReq;
import org.gz.liquidation.common.dto.repayment.ZmRepaymentScheduleReq;
import org.gz.liquidation.common.entity.RepaymentScheduleReq;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.liquidation.entity.AccountRecord;
import org.gz.liquidation.entity.RepaymentDetailEntity;
import org.gz.liquidation.entity.RepaymentSchedule;
import org.gz.liquidation.entity.TransactionRecord;
import org.gz.sms.constants.SmsChannelType;
import org.gz.sms.dto.SmsDto;
/**
 * 
 * @Description:实体类工具类
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月14日 	Created
 */
public class EntityUtils {
	/**
	 * 
	 * @Description: 构建科目流水实体
	 * @param userId
	 * @param orderSN
	 * @param accountCode
	 * @param transactionSN
	 * @param flowType
	 * @param amount
	 * @param remark
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月13日
	 */
	public static AccountRecord createAccountRecord(Long userId,String orderSN,String accountCode,String transactionSN,String flowType,BigDecimal amount,String remark){
		AccountRecord accountRecord = new AccountRecord();
		accountRecord.setTransactionSN(transactionSN);
		accountRecord.setOrderSN(orderSN);
		accountRecord.setFundsSN(UUIDUtils.genDateUUID(accountCode));
		accountRecord.setCreateBy(userId);
		accountRecord.setAccountCode(accountCode);
		accountRecord.setCreateOn(new Date());
		accountRecord.setFlowType(flowType);
		accountRecord.setAmount(amount);
		if(StringUtils.isNotBlank(remark)){
			accountRecord.setRemark(remark);
		}
		return accountRecord;
		
	}
	
	/**
	 * 
	 * @Description: 构建还款计划实体
	 * @param repaymentScheduleReq
	 * @param periodOf
	 * @param paymentDueDate
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月2日
	 */
	public static RepaymentSchedule createRepaymentSchedule(RepaymentScheduleReq repaymentScheduleReq,int periodOf,Date paymentDueDate){
		RepaymentSchedule rs = new RepaymentSchedule();
		rs.setRealName(repaymentScheduleReq.getRealName());
		rs.setPhone(repaymentScheduleReq.getPhone());
		rs.setState(LiquidationConstant.STATE_NORMAL);
		// 商品官网价值
		rs.setGoodsValue(repaymentScheduleReq.getGoodsValue());
		rs.setOrderSN(repaymentScheduleReq.getOrderSN());
		rs.setCreateBy(repaymentScheduleReq.getUserId());
		rs.setUpdateBy(repaymentScheduleReq.getUserId());
		rs.setPeriodOf(periodOf);
		rs.setPeriods(repaymentScheduleReq.getPeriods());
		rs.setCreateOn(new Date());
		rs.setUpdateOn(new Date());
		rs.setDue(repaymentScheduleReq.getRent());
		rs.setCurrentBalance(repaymentScheduleReq.getRent());
		rs.setPaymentDueDate(paymentDueDate);
		rs.setSettleWay("");
		// 设置默认值
		rs.setActualRepayment(new BigDecimal(0));
		rs.setSettleFlag(LiquidationConstant.SETTLE_FLAG_NO);
		// 还款类型-租金
		rs.setRepaymentType(LiquidationConstant.REPAYMENT_TYPE_RENT);
		return rs;
		
	}
	/**
	 * 
	 * @Description: 构建还款计划实体
	 * @param zmRepaymentScheduleReq
	 * @param periodOf
	 * @param paymentDueDate
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月27日
	 */
	public static RepaymentSchedule createRepaymentSchedule(ZmRepaymentScheduleReq zmRepaymentScheduleReq,int periodOf,Date paymentDueDate){
		RepaymentSchedule rs = new RepaymentSchedule();
		rs.setRealName(zmRepaymentScheduleReq.getRealName());
		rs.setPhone(zmRepaymentScheduleReq.getPhone());
		rs.setState(LiquidationConstant.STATE_NORMAL);
		// 商品官网价值
		rs.setGoodsValue(zmRepaymentScheduleReq.getGoodsValue());
		rs.setOrderSN(zmRepaymentScheduleReq.getOrderSN());
		rs.setCreateBy(zmRepaymentScheduleReq.getUserId());
		rs.setUpdateBy(zmRepaymentScheduleReq.getUserId());
		rs.setPeriodOf(periodOf);
		rs.setPeriods(zmRepaymentScheduleReq.getPeriods());
		rs.setCreateOn(new Date());
		rs.setUpdateOn(new Date());
		rs.setDue(zmRepaymentScheduleReq.getRent());
		rs.setCurrentBalance(zmRepaymentScheduleReq.getRent());
		rs.setPaymentDueDate(paymentDueDate);
		rs.setSettleWay("");
		// 设置默认值
		rs.setActualRepayment(new BigDecimal(0));
		rs.setSettleFlag(LiquidationConstant.SETTLE_FLAG_NO);
		// 还款类型-租金
		rs.setRepaymentType(LiquidationConstant.REPAYMENT_TYPE_RENT);
		return rs;
		
	}
	
	/**
	 * 
	 * @Description: 构建还款明细实体
	 * @param userId
	 *            用户id
	 * @param orderSN
	 *            订单号
	 * @param accountCode
	 *            科目编码
	 * @param transactionSN
	 *            交易流水号
	 * @param billDate
	 *            账单日
	 * @param amount
	 *            还款金额
	 * @param remark
	 *            备注(非必填)
	 * @return
	 * @throws createBy:liaoqingji
	 *             createDate:2018年3月14日
	 */
	public static RepaymentDetailEntity createRepaymentDetailEntity(Long userId, String orderSN, String accountCode,
			String transactionSN, Date billDate, BigDecimal amount, String remark) {
		RepaymentDetailEntity e = new RepaymentDetailEntity();
		e.setUserId(userId);
		e.setAccountCode(accountCode);
		if(transactionSN != null){
			e.setTransactionSN(transactionSN);
		}
		e.setOrderSN(orderSN);
		if (null != billDate) {
			e.setBillDate(billDate);
		}
		e.setCreateBy(userId);
		e.setCreateOn(new Date());
		e.setRepaymentAmount(amount);
		if (StringUtils.isNotBlank(remark)) {
			e.setRemark(remark);
		}
		return e;
	}
	/**
	 * 
	 * @Description: 手动清偿构建交易流水实体类
	 * @param manualSettleReq 手动清偿请求参数
	 * @param sourceType 来源类型
	 * @param transactionSource 交易来源
	 * @param remark 备注
	 * @param state 交易状态
	 * @param realName 姓名
	 * @param phone 手机号
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月14日
	 */
	public static TransactionRecord createTransactionRecord(ManualSettleReq manualSettleReq, String sourceType,
			String transactionSource, String remark, Integer state, String realName, String phone) {
		
		Date date = new Date();
		TransactionRecord transactionRecord = new TransactionRecord();
		transactionRecord.setTransactionSN(manualSettleReq.getTransactionSN());
		transactionRecord.setOrderSN(manualSettleReq.getOrderSN());
		transactionRecord.setCreateBy(manualSettleReq.getCreateBy());
		transactionRecord.setTransactionAmount(manualSettleReq.getAmount());
		transactionRecord.setTransactionType(manualSettleReq.getTransactionType());
		transactionRecord.setRealName(realName);
		transactionRecord.setPhone(phone);
		transactionRecord.setTransactionWay(manualSettleReq.getTransactionWay());
		transactionRecord.setTransactionSource(transactionSource);
		transactionRecord.setSourceType(sourceType);
		transactionRecord.setState(state);
		if(StringUtils.isNotBlank(remark)){
			transactionRecord.setRemark(remark);
		}

		transactionRecord.setVersion(UUIDUtils.genDateUUID(""));
		transactionRecord.setCreateOn(date);
		transactionRecord.setUpdateOn(date);
		// 完成时间
		transactionRecord.setFinishTime(manualSettleReq.getFinishTime());
		return transactionRecord;
	}
	/**
	 * 
	 * @Description: 构建发送短信实体
	 * @param smsType
	 * @param phoneNum
	 * @param data
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月29日
	 */
	public static SmsDto createSmsDto(SmsType smsType, String phoneNum,String data){
		List<String> datas = new ArrayList<>();
		datas.add(data);
		SmsDto dto = new SmsDto();
	    dto.setChannelType(SmsChannelType.SMS_CHANNEL_TYPE_Y_T_X);
	    dto.setPhone(phoneNum);
	    dto.setSmsType(smsType.getType());
	    dto.setTemplateId(smsType.getType());
	    dto.setDatas(datas);
		return dto;
	}
	/**
	 * 
	 * @Description: 使用还款计划实体构建交易记录实体
	 * @param repaymentSchedule 还款计划实体
	 * @param transactionSN 交易流水号
	 * @param transactionType 交易类型
	 * @param transactionWay  交易方式
	 * @param transactionSource 交易来源
	 * @param sourceType	来源类型
	 * @param remark	备注
	 * @param state	状态
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月31日
	 */
	public static TransactionRecord createTransactionRecord(RepaymentSchedule repaymentSchedule, String transactionSN,
			String transactionType, String transactionWay, String transactionSource, String sourceType, String remark,
			Integer state) {
		Date date = new Date();
		TransactionRecord transactionRecord = new TransactionRecord();
		transactionRecord.setTransactionSN(transactionSN);
		transactionRecord.setOrderSN(repaymentSchedule.getOrderSN());
		transactionRecord.setCreateBy(repaymentSchedule.getCreateBy());
		transactionRecord.setTransactionAmount(repaymentSchedule.getCurrentBalance());
		transactionRecord.setTransactionType(transactionType);
		transactionRecord.setRealName(repaymentSchedule.getRealName());
		transactionRecord.setPhone(repaymentSchedule.getPhone());
		transactionRecord.setTransactionWay(transactionWay);
		transactionRecord.setTransactionSource(transactionSource);
		transactionRecord.setSourceType(sourceType);
		transactionRecord.setState(state);
		if(StringUtils.isNotBlank(remark)){
			transactionRecord.setRemark(remark);
		}

		transactionRecord.setVersion(UUIDUtils.genDateUUID(""));
		transactionRecord.setCreateOn(date);
		transactionRecord.setUpdateOn(date);
		return transactionRecord;
	}
}
