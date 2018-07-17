package org.gz.liquidation.common.dto.repayment;

import java.math.BigDecimal;
import java.util.Date;

import org.gz.liquidation.common.utils.LiquidationConstant;
/**
 * 
 * @Description: 芝麻还款计划响应DTO
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月20日 	Created
 */
public class ZmRepaymentScheduleResp {
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 订单号
	 */
	private String orderSN;
	/**
	 * 分期数
	 */
	private Integer periods;
	/**
	 * 还款日期
	 */
	private Date paymentDueDate;
	/**
	 * 本期应还租金
	 */
	private BigDecimal due;
	/**
	 * 实还租金
	 */
	private BigDecimal actualRepayment;
	/**
	 * 未还租金
	 */
	private BigDecimal currentBalance;
	/**
	 * 是否结清 0 否 1是
	 */
	private Integer settleFlag;
	/**
	 * 结清日期
	 */
	private Date settleDate;
	/**
	 * 违约状态
	 */
	private Integer state;
	/**
	 * 第几期账期
	 */
	private Integer periodOf;
	/**
	 * 是否结清
	 */
	private String settleDesc;
	/**
	 * 还款时间
	 */
	private Date repaymentTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public Date getPaymentDueDate() {
		return paymentDueDate;
	}
	public void setPaymentDueDate(Date paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}
	public BigDecimal getDue() {
		return due;
	}
	public void setDue(BigDecimal due) {
		this.due = due;
	}
	public BigDecimal getActualRepayment() {
		return actualRepayment;
	}
	public void setActualRepayment(BigDecimal actualRepayment) {
		this.actualRepayment = actualRepayment;
	}
	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}
	public Integer getSettleFlag() {
		return settleFlag;
	}
	public void setSettleFlag(Integer settleFlag) {
		this.settleFlag = settleFlag;
	}
	public Date getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(Date settleDate) {
		this.settleDate = settleDate;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getPeriodOf() {
		return periodOf;
	}
	public void setPeriodOf(Integer periodOf) {
		this.periodOf = periodOf;
	}
	public String getSettleDesc() {
		settleDesc = "待还款";
		if(LiquidationConstant.SETTLE_FLAG_YES == this.settleFlag){
			settleDesc = "已还款";
		}
		return settleDesc;
	}
	public void setSettleDesc(String settleDesc) {
		this.settleDesc = settleDesc;
	}
	public Date getRepaymentTime() {
		return repaymentTime;
	}
	public void setRepaymentTime(Date repaymentTime) {
		this.repaymentTime = repaymentTime;
	}
	
}
