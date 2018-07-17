package org.gz.overdue.entity.vo;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.gz.common.entity.BaseEntity;

public class OverdueOrderInfoVo extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7959988904676829980L;
	/**
	 * 订单编号
	 */
	@NotNull(message = "订单编号不能为null")
	private String orderSN;
	/**
	 * 减免金额
	 */
	private BigDecimal remissionAmount;
	
	/**
	 * 1:租金 2:买断金 3:归还金
	 */
	private Integer paymentType;
	
	/**
	 * 金额
	 */
	private BigDecimal amount;
	
	/**
	 * 减免原因
	 */
	private String remark;
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public BigDecimal getRemissionAmount() {
		return remissionAmount;
	}

	public void setRemissionAmount(BigDecimal remissionAmount) {
		this.remissionAmount = remissionAmount;
	}

	public String getOrderSN() {
		return orderSN;
	}

	public void setOrderSN(String orderSN) {
		this.orderSN = orderSN;
	}
	
}
