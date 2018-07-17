package org.gz.liquidation.service.repayment.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.JsonUtils;
import org.gz.common.utils.UUIDUtils;
import org.gz.liquidation.common.Enum.AccountEnum;
import org.gz.liquidation.common.Enum.TransactionTypeEnum;
import org.gz.liquidation.common.dto.PaymentReq;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.liquidation.entity.AccountRecord;
import org.gz.liquidation.entity.TransactionRecord;
import org.gz.liquidation.service.account.AccountRecordService;
import org.gz.liquidation.service.coupon.CouponService;
import org.gz.liquidation.service.order.OrderService;
import org.gz.liquidation.service.repayment.ReceivablesService;
import org.gz.liquidation.service.repayment.RepaymentScheduleService;
import org.gz.liquidation.service.repayment.RepaymentService;
import org.gz.liquidation.service.transactionrecord.TransactionRecordService;
import org.gz.order.common.Enum.BackRentState;
import org.gz.order.common.dto.UpdateOrderStateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class ReceivablesServiceImpl implements ReceivablesService {

	@Autowired
	private TransactionRecordService transactionRecordService;
	@Autowired
	private AccountRecordService accountRecordService;
	
	@Autowired
	private RepaymentScheduleService repaymentScheduleService;

	@Autowired
	private OrderService orderService;
	
	@Resource
	private RepaymentService repaymentService;
	
	@Resource
	private CouponService couponService;
	
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public ResponseResult<?> payDownpayment(TransactionRecord transactionRecord) {
		// 首期款科目入账
		AccountRecord accountRecord = new AccountRecord();
		accountRecord.setAccountCode(AccountEnum.SQK.getAccountCode());
		accountRecord.setAmount(transactionRecord.getTransactionAmount());
		accountRecord.setCreateBy(transactionRecord.getCreateBy());
		accountRecord.setCreateOn(new Date());
		accountRecord.setFlowType(LiquidationConstant.IN);
		accountRecord.setFundsSN(UUIDUtils.genDateUUID(AccountEnum.SQK.getAccountCode()));
		accountRecord.setTransactionSN(transactionRecord.getTransactionSN());
		accountRecord.setOrderSN(transactionRecord.getOrderSN());
		accountRecord.setRemark("首期款");
		accountRecordService.addAccountRecord(accountRecord);
		
		try {
			UpdateOrderStateReq updateOrderState = new UpdateOrderStateReq();
			updateOrderState.setRentRecordNo(transactionRecord.getOrderSN());
			updateOrderState.setState(BackRentState.WaitSignup.getCode());
			updateOrderState.setCreateBy(transactionRecord.getCreateBy());
			updateOrderState.setCreateMan(transactionRecord.getRealName());
			// 更改订单状态为待签约
			ResponseResult<String> responseResult = orderService.updateOrderState(updateOrderState);
			log.info("payDownpayment 调用订单服务更新订单状态返回结果:{}",JsonUtils.toJsonString(responseResult));
		} catch (Exception e) {
			log.error("首期款支付修改订单状态异常:{}", e.getMessage());
		}
		
		return ResponseResult.buildSuccessResponse();
	}

	@Transactional
	@Override
	public ResponseResult<?> payCallBackHandler(TransactionRecord transactionRecord,String state) {
		int transactionState = LiquidationConstant.STATE_FAILURE;
		
		log.info("payCallBackHandler param--->>transactionRecord:{} state:{}",transactionRecord,state);
		// 处理支付回调或者主动查询支付订单
		if(LiquidationConstant.SUCCESS.equalsIgnoreCase(state)){
			transactionState = LiquidationConstant.STATE_SUCCESS;
			String transactonType = transactionRecord.getTransactionType();
			TransactionTypeEnum typeEnum = TransactionTypeEnum.getEnum(transactonType);
			
			// 是否使用优惠券
			if(StringUtils.isNotBlank(transactionRecord.getAttr1())){
				couponService.use(transactionRecord);
				// 总金额 = 支付金额+优惠券金额
				transactionRecord.setTransactionAmount(transactionRecord.getTransactionAmount().add(transactionRecord.getCouponFee()));
			}
			
			switch (typeEnum) {
			
			case FIRST_RENT:// 首期款
				
				this.payDownpayment(transactionRecord);
				break;
			case BUYOUT:// 买断
				
				PaymentReq paymentReq = new PaymentReq();
				paymentReq.setState(LiquidationConstant.STATE_SUCCESS);
				paymentReq.setRemark(transactionRecord.getRemark());
				paymentReq.setTransactionSN(transactionRecord.getTransactionSN());
				ResponseResult<?> responseResult = repaymentScheduleService.buyoutCallback(paymentReq);
				log.info("buyoutCallback responseResult:{}",responseResult);
				break;
				
			case RENT:// 租金
				
				repaymentService.repayRent(transactionRecord);
				break;
			case RETURN:
				
				break;
			case SALE:
				repaymentService.sell(transactionRecord);
				break;
			default:
				log.warn(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 该交易流水未找到对应的业务交易类型! >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
				break;
			}
		}
		
		// 更新支付流水状态成功或者失败
		Date now = new Date();
		transactionRecord.setState(transactionState);
		transactionRecord.setUpdateOn(now);
		if(transactionRecord.getFinishTime() == null){
			transactionRecord.setFinishTime(now);
		}
		log.info(LiquidationConstant.LOG_PREFIX+" payCallBackHandler 开始更新流水状态");
		transactionRecordService.updateState(transactionRecord);
		log.info(LiquidationConstant.LOG_PREFIX+" payCallBackHandler 更新流水状态结束");
		return ResponseResult.buildSuccessResponse();
	}

}
