package org.gz.liquidation.common.dto;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * @Description:	应付款dto
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年1月3日 	Created
 */
public class DuesDetailResp {
	/**
	 * 账期  格式 3/12-一共12期当前第3期
	 */
	private String bill;
	/**
	 * 滞纳金
	 */
	private BigDecimal lateFees;
	/**
	 * 月租金
	 */
	private BigDecimal rent;
	/**
	 * 应付租金
	 */
	private BigDecimal rentPayable;
	/**
	 * 状态
	 */
	private Integer state;
	/**
	 * 状态描述
	 */
	private String stateDesc;
	/**
	 * 逾期天数
	 */
	private int overdueDay;
	/**
	 * 账期开始
	 */
	private Date billStart;
	/**
	 * 账期结束
	 */
	private Date billEnd;
	/**
	 * 未结清租金
	 */
	private BigDecimal outstandingRent;
	/**
	 * 租期缴纳日期
	 */
	private Date paymentDueDate;
	/**
	 * 本期应付总额
	 */
	private BigDecimal currentBalance;
	/**
	 * 总欠款
	 */
	private BigDecimal statementBalance;
	
	public String getBill() {
		return bill;
	}
	public void setBill(String bill) {
		this.bill = bill;
	}
	public BigDecimal getLateFees() {
		return lateFees;
	}
	public void setLateFees(BigDecimal lateFees) {
		this.lateFees = lateFees;
	}
	public BigDecimal getRent() {
		return rent;
	}
	public void setRent(BigDecimal rent) {
		this.rent = rent;
	}
	public BigDecimal getRentPayable() {
		return rentPayable;
	}
	public void setRentPayable(BigDecimal rentPayable) {
		this.rentPayable = rentPayable;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getStateDesc() {
		switch (this.state) {
		case 1:
			this.stateDesc = "已逾期";
			break;

		default:
			this.stateDesc = "正常";
			break;
		}
		return stateDesc;
	}
	public void setStateDesc(String stateDesc) {
		this.stateDesc = stateDesc;
	}
	public int getOverdueDay() {
		return overdueDay;
	}
	public void setOverdueDay(int overdueDay) {
		this.overdueDay = overdueDay;
	}
	public Date getBillStart() {
		return billStart;
	}
	public void setBillStart(Date billStart) {
		this.billStart = billStart;
	}
	public Date getBillEnd() {
		return billEnd;
	}
	public void setBillEnd(Date billEnd) {
		this.billEnd = billEnd;
	}
	public BigDecimal getOutstandingRent() {
		return outstandingRent;
	}
	public void setOutstandingRent(BigDecimal outstandingRent) {
		this.outstandingRent = outstandingRent;
	}
	public Date getPaymentDueDate() {
		return paymentDueDate;
	}
	public void setPaymentDueDate(Date paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}
	public BigDecimal getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(BigDecimal currentBalance) {
		this.currentBalance = currentBalance;
	}
	public BigDecimal getStatementBalance() {
		return statementBalance;
	}
	public void setStatementBalance(BigDecimal statementBalance) {
		this.statementBalance = statementBalance;
	}
}
