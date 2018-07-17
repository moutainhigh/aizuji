package org.gz.liquidation.common.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.gz.common.entity.BaseEntity;
/**
 * 
 * @Description:	订单还款Dto
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年1月19日 	Created
 */
public class OrderRepaymentResp extends BaseEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3434373496801495376L;
	/**
	 * 订单号
	 */
	private String orderSN;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 姓名
	 */
	private String realName;
	private String phone;
	/**
	 * 身份证
	 */
	private String identityCard;
	/**
	 * 租期截止日
	 */
	private Date rentEndDate;
	/**
	 * 状态
	 */
	private Integer state;
	/**
	 * 订单状态描述
	 */
	private String stateDesc;
	/**
	 * 每月还款日
	 */
	private String repaymentDate;
	/**
	 * 履约描述
	 */
	private String performanceDesc;
	/**
	 * 已还租金
	 */
	private BigDecimal totalRepaymentRent;
	/**
	 * 已还滞纳金
	 */
	private BigDecimal totalLateFees;
	/**
	 * 应还租金
	 */
	private BigDecimal currentRent;
	/**
	 * 应还滞纳金
	 */
	private BigDecimal currentLateFees;
	/**
	 * 减免滞纳金
	 */
	private BigDecimal remissionLateFee;
	/**
	 * 第几期
	 */
	private Integer periodOf;
	
	/**
	 * 分期数
	 */
	private Integer periods;
	
	public String getOrderSN() {
		return orderSN;
	}
	public void setOrderSN(String orderSN) {
		this.orderSN = orderSN;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	public Date getRentEndDate() {
		return rentEndDate;
	}
	public void setRentEndDate(Date rentEndDate) {
		this.rentEndDate = rentEndDate;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getStateDesc() {
		return stateDesc;
	}
	public void setStateDesc(String stateDesc) {
		this.stateDesc = stateDesc;
	}
	public String getRepaymentDate() {
		return repaymentDate;
	}
	public void setRepaymentDate(String repaymentDate) {
		this.repaymentDate = repaymentDate;
	}
	public String getPerformanceDesc() {
		return performanceDesc;
	}
	public void setPerformanceDesc(String performanceDesc) {
		this.performanceDesc = performanceDesc;
	}
	public BigDecimal getTotalRepaymentRent() {
		return totalRepaymentRent;
	}
	public void setTotalRepaymentRent(BigDecimal totalRepaymentRent) {
		this.totalRepaymentRent = totalRepaymentRent;
	}
	public BigDecimal getTotalLateFees() {
		return totalLateFees;
	}
	public void setTotalLateFees(BigDecimal totalLateFees) {
		this.totalLateFees = totalLateFees;
	}
	public BigDecimal getCurrentRent() {
		return currentRent;
	}
	public void setCurrentRent(BigDecimal currentRent) {
		this.currentRent = currentRent;
	}
	public BigDecimal getCurrentLateFees() {
		return currentLateFees;
	}
	public void setCurrentLateFees(BigDecimal currentLateFees) {
		this.currentLateFees = currentLateFees;
	}
	public BigDecimal getRemissionLateFee() {
		return remissionLateFee;
	}
	public void setRemissionLateFee(BigDecimal remissionLateFee) {
		this.remissionLateFee = remissionLateFee;
	}
	public Integer getPeriodOf() {
		return periodOf;
	}
	public void setPeriodOf(Integer periodOf) {
		this.periodOf = periodOf;
	}
	public Integer getPeriods() {
		return periods;
	}
	public void setPeriods(Integer periods) {
		this.periods = periods;
	}
	
}
