package org.gz.liquidation.utils;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.Enum.LiquidationErrorCode;
import org.gz.liquidation.common.Enum.TransactionTypeEnum;
import org.gz.liquidation.common.dto.ManualSettleReq;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.liquidation.service.latefee.LateFeeService;
import org.gz.liquidation.service.order.OrderService;
import org.gz.liquidation.service.repayment.RepaymentScheduleService;
import org.gz.order.common.Enum.BackRentState;
import org.gz.order.common.dto.OrderDetailResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * 
 * @Description:手动清偿验证工具类
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月23日 	Created
 */
@Component
public class ManualSettleValidator {
	
	@Autowired
	private OrderService orderService;
	
	@Resource
	private LateFeeService lateFeeService;
	
	@Resource
	private RepaymentScheduleService repaymentScheduleService;
	
	/**
	 * 
	 * @Description: 验证手动清偿买断
	 * @param manualSettleReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月23日
	 */
	public ResponseResult<?> validateBuyout(ManualSettleReq manualSettleReq) {
		
		String orderSN = manualSettleReq.getOrderSN();
		
		// 校验订单是否为强制买断中
		ResponseResult<OrderDetailResp> rr = orderService.queryBackOrderDetail(orderSN);
		if(rr.isSuccess()){
			int state = rr.getData().getState();
			if (manualSettleReq.getTransactionType().equals(TransactionTypeEnum.OVERDUE_BUYOUT.getCode())) {
				
				if(state != BackRentState.ForceBuyIng.getCode() && state != BackRentState.Overdue.getCode()){
					return ResponseResult.build(LiquidationErrorCode.MANUAL_SETTLE_OVEDUE_BUYOUT_STATE_ERROR.getCode(),
							LiquidationErrorCode.MANUAL_SETTLE_OVEDUE_BUYOUT_STATE_ERROR.getMessage(), null);
				}
				
			}else{// 正常买断校验
				
				if(state != BackRentState.NormalPerformance.getCode() && state != BackRentState.EndPerformance.getCode()){
					return ResponseResult.build(LiquidationErrorCode.MANUAL_SETTLE_OVEDUE_BUYOUT_STATE_ERROR.getCode(),
							LiquidationErrorCode.MANUAL_SETTLE_OVEDUE_BUYOUT_STATE_ERROR.getMessage(), null);
				}
			}
			
			return ResponseResult.buildSuccessResponse();
			
		}else{
			return rr;
		}
		
	}
	/**
	 * 
	 * @Description: 验证回收
	 * @param orderSN 订单号
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月23日
	 */
	public ResponseResult<?> validateReturn(String orderSN) {
		
		ResponseResult<OrderDetailResp> rr = orderService.queryBackOrderDetail(orderSN);
		if(rr.isSuccess()){
			
			int state = rr.getData().getState();
			if (state != BackRentState.DamageCheck.getCode() && state != BackRentState.ForceDamageCheck.getCode()) {
				
				return ResponseResult.build(LiquidationErrorCode.MANUAL_SETTLE_RETURN_ERROR.getCode(),
						LiquidationErrorCode.MANUAL_SETTLE_RETURN_ERROR.getMessage(), null);
			}
			
			return ResponseResult.buildSuccessResponse();
		}else{
			return rr;
		}
		
	}
	/**
	 * 
	 * @Description: 验证租金支付金额
	 * @param orderSN
	 * @param amount
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月26日
	 */
	public ResponseResult<?> validateRepayRent(String orderSN,BigDecimal amount) {
		BigDecimal unsettledLateFee = lateFeeService.lateFeePayable(orderSN);
		BigDecimal unsettledRent = repaymentScheduleService.selectSumCurrentBalance(orderSN, LiquidationConstant.REPAYMENT_TYPE_RENT);
		
		BigDecimal total = unsettledLateFee.add(unsettledRent);
		if(amount.compareTo(total) == 1){// 支付金额大于所有未结清的金额
			return ResponseResult.build(LiquidationErrorCode.PAYMENT_RENT_MAX_AMOUNT_ERROR.getCode(),
					LiquidationErrorCode.PAYMENT_RENT_MAX_AMOUNT_ERROR.getMessage(), null);
		}
		
		ResponseResult<OrderDetailResp> rr = orderService.queryBackOrderDetail(orderSN);
		if(rr.isSuccess()){
			
			int state = rr.getData().getState();
			if (state != BackRentState.NormalPerformance.getCode() && state != BackRentState.Overdue.getCode()) {
				
				return ResponseResult.build(LiquidationErrorCode.MANUAL_SETTLE_REPAY_RENT_ERROR.getCode(),
						LiquidationErrorCode.MANUAL_SETTLE_REPAY_RENT_ERROR.getMessage(), null);
			}
			
			return ResponseResult.buildSuccessResponse();
		}else{
			return rr;
		}
		
	}
}
