package org.gz.liquidation.common.dto;

import java.math.BigDecimal;
/**
 * 
 * @Description:TODO	首期支付统计dto
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年1月4日 	Created
 */
public class DownpaymentStatisticsResp {
	/**
	 * 首期支付总交易笔数
	 */
	private int totalTransactions;
	/**
	 * 首期支付总收入
	 */
	private BigDecimal totalDownpayment;
	/**
	 * 预期总收入(未扣减成本)
	 */
	private BigDecimal expectedRevenue;
	/**
	 * 待完成首期支付
	 */
	private BigDecimal pendingDownpayment;
	/**
	 * 首期租金收入
	 */
	private BigDecimal totalFirstRent;
	 /**
     * 保险费
     */
    private BigDecimal totalInsurance;
    /**
     * 溢价金
     */
    private BigDecimal totalPremium;
    /**
     * 待完成首期支付笔数
     */
    private int pendingDownpaymentPayments;
	public int getTotalTransactions() {
		return totalTransactions;
	}
	public void setTotalTransactions(int totalTransactions) {
		this.totalTransactions = totalTransactions;
	}
	public BigDecimal getTotalDownpayment() {
		return totalDownpayment;
	}
	public void setTotalDownpayment(BigDecimal totalDownpayment) {
		this.totalDownpayment = totalDownpayment;
	}
	public BigDecimal getExpectedRevenue() {
		return expectedRevenue;
	}
	public void setExpectedRevenue(BigDecimal expectedRevenue) {
		this.expectedRevenue = expectedRevenue;
	}
	public BigDecimal getPendingDownpayment() {
		return pendingDownpayment;
	}
	public void setPendingDownpayment(BigDecimal pendingDownpayment) {
		this.pendingDownpayment = pendingDownpayment;
	}
	public BigDecimal getTotalFirstRent() {
		return totalFirstRent;
	}
	public void setTotalFirstRent(BigDecimal totalFirstRent) {
		this.totalFirstRent = totalFirstRent;
	}
	public BigDecimal getTotalInsurance() {
		return totalInsurance;
	}
	public void setTotalInsurance(BigDecimal totalInsurance) {
		this.totalInsurance = totalInsurance;
	}
	public BigDecimal getTotalPremium() {
		return totalPremium;
	}
	public void setTotalPremium(BigDecimal totalPremium) {
		this.totalPremium = totalPremium;
	}
	public int getPendingDownpaymentPayments() {
		return pendingDownpaymentPayments;
	}
	public void setPendingDownpaymentPayments(int pendingDownpaymentPayments) {
		this.pendingDownpaymentPayments = pendingDownpaymentPayments;
	}
    
}
