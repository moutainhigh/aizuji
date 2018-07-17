/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.order.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

/**
 * OrderCreditDetail 实体类
 * 
 * @author liuyt
 * @date 2017-12-26
 */
public class OrderCreditDetail implements Serializable {

    /**
     * 主键id 
     */
    private Long id;
    /**
     * 审核类型（1：一审；2：二审；3：三审） 
     */
    @NotNull(message = "审核类型不能为null")
    @Pattern(regexp = "1|2|3", message = "审核类型有误")
    private Byte creditType;

    /**
     * 审核结果（1:审核拒绝；2：审核通过） 
     */
    @NotNull(message = "审核结果不能为null")
    @Pattern(regexp = "1|2", message = "审核结果有误")
    private Byte creditResult;

    /**
     * 备注 
     */
    private String remark;

    /**
     * 订单编号 
     */
    @NotNull(message = "订单编号不能为null")
    @Length(max = 20, message = "订单编号最多20位")
    private String orderNo;

    /**
     * 审核时间 
     */
    private Date creditOn;
    /**
     * 创建时间 
     */
    private Date createOn;
    /**
     * 创建人 
     */
    private Long createBy;
    /**
     * 更新人 
     */
    private Long updateBy;

    /**
     * 创建人名字
     */
    private String createName;

    /**
     * 更新人名字
     */
    private String updateName;

    /**
     * 拒绝理由（1:未满18岁,2:严重逾期,3:恶意刷单,4:地址伪造,5:身份伪造,6:联系方式不符,7:购买信息不符,8:其他）
     */
    private Byte   refusalReason;

    /**
     * 用户id
     */
    private Long   userId;

    /**
     * 命中规则
     */
    private String hitRule;

    public void setCreditType(Byte creditType) {
        this.creditType = creditType;
    }
    
    public Byte getCreditType() {
        return this.creditType;
    }
    
    public void setCreditResult(Byte creditResult) {
        this.creditResult = creditResult;
    }
    
    public Byte getCreditResult() {
        return this.creditResult;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getRemark() {
        return this.remark;
    }
    
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    
    public String getOrderNo() {
        return this.orderNo;
    }
    
    public void setCreditOn(Date creditOn) {
        this.creditOn = creditOn;
    }
    
    public Date getCreditOn() {
        return this.creditOn;
    }
    
    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }
    
    public Date getCreateOn() {
        return this.createOn;
    }
    
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }
    
    public Long getCreateBy() {
        return this.createBy;
    }
    
    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }
    
    public Long getUpdateBy() {
        return this.updateBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public Byte getRefusalReason() {
        return refusalReason;
    }

    public void setRefusalReason(Byte refusalReason) {
        this.refusalReason = refusalReason;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getHitRule() {
        return hitRule;
    }

    public void setHitRule(String hitRule) {
        this.hitRule = hitRule;
    }
    
}
