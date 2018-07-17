package org.gz.liquidation.common.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.gz.common.entity.BaseEntity;
import org.gz.liquidation.common.Enum.AccountEnum;

/**
 * 
 * @Description:TODO	科目流水记录
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月22日 	Created
 */
public class AccountRecordResp extends BaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8240973487626060602L;

	private Long id;

    /**
     * 会计科目编码
     */
    private String accountCode;
    /**
     * 科目描述
     */
    private String accountDesc;
    /**
     * 资金流水号
     */
    private String fundsSN;

    /**
     * 订单号
     */
    private String orderSN;

    /**
     * 交易流水号
     */
    private String transactionSN;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 资金方向(流入/流出)
     */
    private String flowType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createOn;
    private Long createBy;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getAccountDesc() {
		this.accountDesc = AccountEnum.getAccountDesc(this.accountCode);
		return this.accountDesc;
	}

	public void setAccountDesc(String accountDesc) {
		
		this.accountDesc = accountDesc;
	}

	public String getFundsSN() {
		return fundsSN;
	}

	public void setFundsSN(String fundsSN) {
		this.fundsSN = fundsSN;
	}

	public String getOrderSN() {
		return orderSN;
	}

	public void setOrderSN(String orderSN) {
		this.orderSN = orderSN;
	}

	public String getTransactionSN() {
		return transactionSN;
	}

	public void setTransactionSN(String transactionSN) {
		this.transactionSN = transactionSN;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getFlowType() {
		return flowType;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
    
}