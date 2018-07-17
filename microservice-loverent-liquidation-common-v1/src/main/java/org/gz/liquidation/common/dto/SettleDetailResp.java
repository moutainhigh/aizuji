package org.gz.liquidation.common.dto;

import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @Description:	强制结清页面结果dto
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年1月3日 	Created
 */
public class SettleDetailResp {
	/**
	 * 账期  格式 3/12-一共12期当前第3期
	 */
	private String bill;
	/**
	 * 滞纳金
	 */
	private BigDecimal lateFee;
	/**
	 * 应付租金总额(月租金*期数)
	 */
	@ApiModelProperty(value = "应付租金总额", required = true)
	private BigDecimal totalRentPayable;
	/**
	 * 已付租金总额
	 */
	@ApiModelProperty(value = "已付租金总额", required = true)
	private BigDecimal totalRentPaid;
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
	 * 减免金额
	 */
	@ApiModelProperty(value = "减免金额", required = true)
	private BigDecimal remissionFee;
	/**
	 * 应付总额
	 */
	@ApiModelProperty(value = "应付总额", required = true)
	@Setter @Getter
	private BigDecimal currentBalance;
	/**
	 * 实付总额
	 */
	@ApiModelProperty(value = "实付总额", required = true)
	private BigDecimal statementBalance;
	
	public String getBill() {
		return bill;
	}
	public void setBill(String bill) {
		this.bill = bill;
	}
	public BigDecimal getLateFee() {
		return lateFee;
	}
	public void setLateFee(BigDecimal lateFee) {
		this.lateFee = lateFee;
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
	public BigDecimal getRemissionFee() {
		return remissionFee;
	}
	public void setRemissionFee(BigDecimal remissionFee) {
		this.remissionFee = remissionFee;
	}
	public BigDecimal getStatementBalance() {
		return statementBalance;
	}
	public void setStatementBalance(BigDecimal statementBalance) {
		this.statementBalance = statementBalance;
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
	public BigDecimal getTotalRentPayable() {
		return totalRentPayable;
	}
	public void setTotalRentPayable(BigDecimal totalRentPayable) {
		this.totalRentPayable = totalRentPayable;
	}
	public BigDecimal getTotalRentPaid() {
		return totalRentPaid;
	}
	public void setTotalRentPaid(BigDecimal totalRentPaid) {
		this.totalRentPaid = totalRentPaid;
	}
}
