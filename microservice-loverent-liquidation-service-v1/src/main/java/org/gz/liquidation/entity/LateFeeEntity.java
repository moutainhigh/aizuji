package org.gz.liquidation.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @Description:滞纳金实体
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月8日 	Created
 */
public class LateFeeEntity implements Serializable {
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 订单号
     */
    private String orderSN;

    /**
     * 账户金额
     */
    private BigDecimal amount;

    /**
     * 已还金额
     */
    private BigDecimal repayAmount;

    /**
     * 余额
     */
    private BigDecimal currentBalance;

    /**
     * 备注
     */
    private String remark;
    /**
     * 是否有效标识 1 有效 0 无效
     */
    private int enableFlag;
    /**
     * 创建时间
     */
    private Date createOn;

    /**
     * 更新时间
     */
    private Date updateOn;
    /**
     * 更新人id
     */
    private Long updateBy;
    /**
     * 账单日期
     */
    private Date billDate;
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

    public String getOrderSN() {
        return orderSN;
    }

    public void setOrderSN(String orderSN) {
        this.orderSN = orderSN;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getRepayAmount() {
        return repayAmount;
    }

    public void setRepayAmount(BigDecimal repayAmount) {
        this.repayAmount = repayAmount;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(int enableFlag) {
		this.enableFlag = enableFlag;
	}

	public Date getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }

    public Date getUpdateOn() {
        return updateOn;
    }

    public void setUpdateOn(Date updateOn) {
        this.updateOn = updateOn;
    }
    
    public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}
	
	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
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
        LateFeeEntity other = (LateFeeEntity) that;
        return (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getOrderSN() == null ? other.getOrderSN() == null : this.getOrderSN().equals(other.getOrderSN()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getOrderSN() == null) ? 0 : getOrderSN().hashCode());
        result = prime * result + ((getAmount() == null) ? 0 : getAmount().hashCode());
        result = prime * result + ((getRepayAmount() == null) ? 0 : getRepayAmount().hashCode());
        result = prime * result + ((getCurrentBalance() == null) ? 0 : getCurrentBalance().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreateOn() == null) ? 0 : getCreateOn().hashCode());
        result = prime * result + ((getUpdateOn() == null) ? 0 : getUpdateOn().hashCode());
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
        sb.append(", orderSN=").append(orderSN);
        sb.append(", amount=").append(amount);
        sb.append(", repayAmount=").append(repayAmount);
        sb.append(", currentBalance=").append(currentBalance);
        sb.append(", remark=").append(remark);
        sb.append(", createOn=").append(createOn);
        sb.append(", updateOn=").append(updateOn);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}