package org.gz.order.common.entity;

import java.io.Serializable;
import java.util.Date;

public class OrderPhoneVerifyInfo implements Serializable {
    
    /**
     * 主键id
     */
    private Long   id;

    /**
     * 电核结果（1:身份不符 2:联系方式不符 3:地址不符 4:无人接听 5:信息属实 6:其他信息不符 7:其他）
     */
    private Byte   phoneVerifyResult;

    /**
     * 建议（1：建议通过，2：建议拒绝，3：建议再电联）
     */
    private Byte   suggest;

    /**
     * 备注
     */
    private String remark;

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 审核时间 
     */
    private Date   updateOn;
    /**
     * 创建时间 
     */
    private Date   createOn;
    /**
     * 创建人 
     */
    private Long   createBy;
    /**
     * 更新人 
     */
    private Long   updateBy;

    /**
     * 创建人名字
     */
    private String createName;

    /**
     * 更新人名字
     */
    private String updateName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getPhoneVerifyResult() {
        return phoneVerifyResult;
    }

    public void setPhoneVerifyResult(Byte phoneVerifyResult) {
        this.phoneVerifyResult = phoneVerifyResult;
    }

    public Byte getSuggest() {
        return suggest;
    }

    public void setSuggest(Byte suggest) {
        this.suggest = suggest;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getUpdateOn() {
        return updateOn;
    }

    public void setUpdateOn(Date updateOn) {
        this.updateOn = updateOn;
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

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
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
}
