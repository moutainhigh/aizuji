package org.gz.liquidation.common.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

public class TransactionRecordReq {
	private Long id;
	/**
     * 交易流水号
     */
	@NotEmpty
    private String transactionSN;
	/**
	 * 交易金额
	 */
	private BigDecimal amount;
	/**
	 * 交易方式
	 */
	private String transactionWay;
	/**
	 * 交易类型
	 */
	private String transactionType;
	/**
	 * 交易发起时间
	 */
	private Date startDate;
	/**
	 * 交易完成时间
	 */
	private Date endDate;
	/**
	 * 备注
	 */
	private String remark;

	public String getTransactionSN() {
		return transactionSN;
	}

	public void setTransactionSN(String transactionSN) {
		this.transactionSN = transactionSN;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getTransactionWay() {
		return transactionWay;
	}

	public void setTransactionWay(String transactionWay) {
		this.transactionWay = transactionWay;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
    
}
