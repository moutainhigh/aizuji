package org.gz.liquidation.common.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.gz.common.entity.BaseEntity;

/**
 * 
 * @Description:TODO	减免记录
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年1月22日 	Created
 */
public class RemissionLogResp extends BaseEntity {
    /**
     * 自增id
     */
    private Long id;

    /**
     * 用户姓名
     */
    private String userRealName;

    /**
     * 减免时间
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
     * 减免金额
     */
    private BigDecimal amount;

    /**
     * 减免原因
     */
    private String remissionDesc;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
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
        return remissionDesc;
    }

    public void setRemissionDesc(String remissionDesc) {
        this.remissionDesc = remissionDesc;
    }
}