package org.gz.liquidation.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.gz.common.entity.BaseEntity;

/**
 * 
 * @Description:还款记录实体类
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月12日 	Created
 */
public class RepaymentDetailEntity extends BaseEntity {
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 还款金额
     */
    private BigDecimal repaymentAmount;

    /**
     * 科目编码
     */
    private String accountCode;

    /**
     * 订单号
     */
    private String orderSN;

    /**
     * 账单日期
     */
    private Date billDate;

    /**
     * 交易流水号
     */
    private String transactionSN;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人id
     */
    private Long createBy;

    /**
     * 还款日期
     */
    private Date createOn;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getRepaymentAmount() {
        return repaymentAmount;
    }

    public void setRepaymentAmount(BigDecimal repaymentAmount) {
        this.repaymentAmount = repaymentAmount;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getOrderSN() {
        return orderSN;
    }

    public void setOrderSN(String orderSN) {
        this.orderSN = orderSN;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public String getTransactionSN() {
        return transactionSN;
    }

    public void setTransactionSN(String transactionSN) {
        this.transactionSN = transactionSN;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        RepaymentDetailEntity other = (RepaymentDetailEntity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getRepaymentAmount() == null ? other.getRepaymentAmount() == null : this.getRepaymentAmount().equals(other.getRepaymentAmount()))
            && (this.getAccountCode() == null ? other.getAccountCode() == null : this.getAccountCode().equals(other.getAccountCode()))
            && (this.getOrderSN() == null ? other.getOrderSN() == null : this.getOrderSN().equals(other.getOrderSN()))
            && (this.getBillDate() == null ? other.getBillDate() == null : this.getBillDate().equals(other.getBillDate()))
            && (this.getTransactionSN() == null ? other.getTransactionSN() == null : this.getTransactionSN().equals(other.getTransactionSN()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getCreateOn() == null ? other.getCreateOn() == null : this.getCreateOn().equals(other.getCreateOn()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getRepaymentAmount() == null) ? 0 : getRepaymentAmount().hashCode());
        result = prime * result + ((getAccountCode() == null) ? 0 : getAccountCode().hashCode());
        result = prime * result + ((getOrderSN() == null) ? 0 : getOrderSN().hashCode());
        result = prime * result + ((getBillDate() == null) ? 0 : getBillDate().hashCode());
        result = prime * result + ((getTransactionSN() == null) ? 0 : getTransactionSN().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateOn() == null) ? 0 : getCreateOn().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", repaymentAmount=").append(repaymentAmount);
        sb.append(", accountCode=").append(accountCode);
        sb.append(", orderSN=").append(orderSN);
        sb.append(", billDate=").append(billDate);
        sb.append(", transactionSN=").append(transactionSN);
        sb.append(", remark=").append(remark);
        sb.append(", createBy=").append(createBy);
        sb.append(", createOn=").append(createOn);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}