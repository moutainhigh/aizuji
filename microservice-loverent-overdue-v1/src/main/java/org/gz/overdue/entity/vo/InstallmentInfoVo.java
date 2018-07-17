package org.gz.overdue.entity.vo;

import java.math.BigDecimal;

import org.gz.common.entity.BaseEntity;

public class InstallmentInfoVo extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7959988904676829980L;
	/**
	 * 订单编号
	 */
	private String orderSN;
	/**
	 * 期数
	 */
	private Integer periods;
	/**
	 * 还款日
	 */
	private String paymentDueDate;
	/**
	 * 租金
	 */
	private BigDecimal rentFee;
	/**
	 * 结清状态 0否 1是
	 */
	private Integer settleFlag;
	/**
	 * 结清时间
	 */
	private String settleDate;
	/**
	 * 本期逾期天数
	 */
	private Integer overdueDay;
	/**
	 * 买断金
	 */
	private BigDecimal buyFee;
	/**
	 * 滞纳金
	 */
	private BigDecimal overdueFee;
	public String getOrderSN() {
		return orderSN;
	}
	public void setOrderSN(String orderSN) {
		this.orderSN = orderSN;
	}
	public Integer getPeriods() {
		return periods;
	}
	public void setPeriods(Integer periods) {
		this.periods = periods;
	}
	public String getPaymentDueDate() {
		return paymentDueDate;
	}
	public void setPaymentDueDate(String paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}
	public BigDecimal getRentFee() {
		return rentFee;
	}
	public void setRentFee(BigDecimal rentFee) {
		this.rentFee = rentFee;
	}
	public Integer getSettleFlag() {
		return settleFlag;
	}
	public void setSettleFlag(Integer settleFlag) {
		this.settleFlag = settleFlag;
	}
	public String getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
	public Integer getOverdueDay() {
		return overdueDay;
	}
	public void setOverdueDay(Integer overdueDay) {
		this.overdueDay = overdueDay;
	}
	public BigDecimal getBuyFee() {
		return buyFee;
	}
	public void setBuyFee(BigDecimal buyFee) {
		this.buyFee = buyFee;
	}
	public BigDecimal getOverdueFee() {
		return overdueFee;
	}
	public void setOverdueFee(BigDecimal overdueFee) {
		this.overdueFee = overdueFee;
	}
}
