package org.gz.liquidation.common.dto;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * @Description:TODO	账单详情DTO
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年1月2日 	Created
 */
public class RepaymentDetailResp {
	/**
	 * 账期  格式 3/12-一共12期当前第3期
	 */
	private String bill;
	/**
	 * 已缴纳滞纳金
	 */
	private BigDecimal settledLateFees;
	/**
	 * 已还租金
	 */
	private BigDecimal settledRent;
	/**
	 * 买断金额
	 */
	private BigDecimal buyoutAmount;
	/**
	 * 签约价值
	 */
	private BigDecimal signedAmount;
	/**
	 * 逾期天数
	 */
	private Integer overdueDay;
	/**
	 * 账单开始时间
	 */
	private Date billStart;
	/**
	 * 账单结束时间
	 */
	private Date billEnd;
	/**
	 * 状态  0正常 1 违约
	 */
	private Integer state;
	/**
	 * 第几期
	 */
	private Integer peroidOf;
	/**
	 * 期数
	 */
	private Integer peroids;
	/**
	 * 滞纳金
	 */
	private BigDecimal lateFees;
	/**
	 * 减免金额
	 */
	private BigDecimal remissionAmount;
	public String getBill() {
		return bill;
	}
	public void setBill(String bill) {
		this.bill = bill;
	}
	public BigDecimal getSettledLateFees() {
		return settledLateFees;
	}
	public void setSettledLateFees(BigDecimal settledLateFees) {
		this.settledLateFees = settledLateFees;
	}
	public BigDecimal getSettledRent() {
		return settledRent;
	}
	public void setSettledRent(BigDecimal settledRent) {
		this.settledRent = settledRent;
	}
	public BigDecimal getBuyoutAmount() {
		return buyoutAmount;
	}
	public void setBuyoutAmount(BigDecimal buyoutAmount) {
		this.buyoutAmount = buyoutAmount;
	}
	public Integer getOverdueDay() {
		return overdueDay;
	}
	public void setOverdueDay(Integer overdueDay) {
		this.overdueDay = overdueDay;
	}
	public BigDecimal getSignedAmount() {
		return signedAmount;
	}
	public void setSignedAmount(BigDecimal signedAmount) {
		this.signedAmount = signedAmount;
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
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getPeroidOf() {
		return peroidOf;
	}
	public void setPeroidOf(Integer peroidOf) {
		this.peroidOf = peroidOf;
	}
	public Integer getPeroids() {
		return peroids;
	}
	public void setPeroids(Integer peroids) {
		this.peroids = peroids;
	}
	public BigDecimal getLateFees() {
		return lateFees;
	}
	public void setLateFees(BigDecimal lateFees) {
		this.lateFees = lateFees;
	}
	public BigDecimal getRemissionAmount() {
		return remissionAmount;
	}
	public void setRemissionAmount(BigDecimal remissionAmount) {
		this.remissionAmount = remissionAmount;
	}
	
}
