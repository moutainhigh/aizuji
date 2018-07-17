package org.gz.liquidation.common.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.gz.common.entity.BaseEntity;

/**
 * 
 * @Description:TODO	退款记录响应DTO
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年1月22日 	Created
 */
public class RefundLogResp extends BaseEntity {
    /**
     * 自增id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户姓名
     */
    private String userRealName;

    /**
     * 操作人
     */
    private Integer createBy;

    /**
     * 退款时间
     */
    private Date createOn;

    /**
     * 操作人姓名
     */
    private String operator;

    /**
     * 订单号
     */
    private String orderSN;

    /**
     * 退款金额
     */
    private BigDecimal amount;

    /**
     * 减免原因
     */
    private String refundDesc;
    /**
     * 截图url
     */
    private String imgURL;
    /**
     * 退款科目
     */
    private String accountCode;

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

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Date getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
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

    public String getRemissionDesc() {
        return refundDesc;
    }

    public void setRemissionDesc(String remissionDesc) {
        this.refundDesc = remissionDesc;
    }

	public String getRefundDesc() {
		return refundDesc;
	}

	public void setRefundDesc(String refundDesc) {
		this.refundDesc = refundDesc;
	}

	public String getImgURL() {
		return imgURL;
	}

	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
    
    
}