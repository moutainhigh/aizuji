package org.gz.liquidation.common.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
/**
 * 
 * @Description:TODO	还款请求dto
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年1月8日 	Created
 */
public class RepaymentReq {
	private Long userId;
	/**
	 * 订单号
	 */
	@NotEmpty(message="orderSN订单号不能为空")
	private String orderSN;
	/**
	 * 支付金额
	 */
	private BigDecimal amount;
	/**
	 * 还款类型 还款类型 1 租金 2 买断金
	 */
	@NotNull(message="repaymentType还款类型不能为空")
	private Integer repaymentType;
	/**
	 * 交易流水号
	 */
	private String transactionSN;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getOrderSN() {
		return orderSN;
	}
	public void setOrderSN(String orderSN) {
		this.orderSN = orderSN;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Integer getRepaymentType() {
		return repaymentType;
	}
	public void setRepaymentType(Integer repaymentType) {
		this.repaymentType = repaymentType;
	}
	public String getTransactionSN() {
		return transactionSN;
	}
	public void setTransactionSN(String transactionSN) {
		this.transactionSN = transactionSN;
	}
	
}
