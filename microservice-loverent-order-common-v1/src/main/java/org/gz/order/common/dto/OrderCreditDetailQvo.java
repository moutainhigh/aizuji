package org.gz.order.common.dto;

import java.io.Serializable;

import org.gz.common.entity.QueryPager;

public class OrderCreditDetailQvo extends QueryPager implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7475816096659666896L;

    /**
     * 审核类型（1：电审，2：终审）
     */
    private Byte              creditType;

    /**
     * 订单号
     */
    private String            orderNo;

    /**
     * 审核结果
     */
    private Byte              creditResult;

    /**
     * 更新人
     */
    private Long              updateBy;

    /**
     * 用户id
     */
    private Long              userId;

    public Byte getCreditType() {
        return creditType;
    }

    public void setCreditType(Byte creditType) {
        this.creditType = creditType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Byte getCreditResult() {
        return creditResult;
    }

    public void setCreditResult(Byte creditResult) {
        this.creditResult = creditResult;
    }
}
