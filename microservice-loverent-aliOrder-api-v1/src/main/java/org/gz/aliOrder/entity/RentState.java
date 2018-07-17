package org.gz.aliOrder.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * RentState 实体类
 * 
 * @author pk
 * @date 2017-12-13
 */
public class RentState implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2691557538416146238L;
    /**
     * id
     */
    private Long id;
    /**
     * 租机编号 
     */
    private String rentRecordNo;
    /**
     * 创建人ID 
     */
    private Long createBy;
    /**
     * 创建时间 
     */
    private Date createOn;
    /**
     * 0提交 1 待审批 2 已拒绝 3 待支付 4 待签约 5 已取消 6 待配货 7 待拣货 8 待发货 9 发货中 10 正常履约 11
     * 提前解约中 12 提前解约 13 换机订单状态 14 维修订单状态 15 已逾期 16 回收中 17 提前买断 18 提前回收 19 正常买断
     * 20 已回收
     */
    private Integer state;
    /**
     * 创建人用户名
     */
    private String  createMan;
    /**
     * 原因
     */
    private String            remark;

    public void setRentRecordNo(String rentRecordNo) {
        this.rentRecordNo = rentRecordNo;
    }
    
    public String getRentRecordNo() {
        return this.rentRecordNo;
    }
    
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }
    
    public Long getCreateBy() {
        return this.createBy;
    }
    
    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }
    
    public Date getCreateOn() {
        return this.createOn;
    }
    
    public void setState(Integer state) {
        this.state = state;
    }
    
    public Integer getState() {
        return this.state;
    }
    
    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public String getCreateMan() {
        return this.createMan;
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

}