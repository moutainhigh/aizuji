package org.gz.order.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderRentAfterRsp implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * 租机编号
   */
  private String rentRecordNo;
  /**
   * 真实姓名
   */
  private String realName;
  /**
   * 身份证号
   */
  private String idNo;

  /**
   * 签约成功时间
   */
  private Date signTime;

  /**
   * 产品租期（月）
   */
  private Integer leaseTerm;
  /**
   * 租金
   */
  private BigDecimal leaseAmount;

  /**
   * 订单状态
   */
  private Integer state;
  /**
   * 状态描述
   */
  private String stateDesc;
  /**
   * 还机时间
   */
  private Date backTime;

  public String getRentRecordNo() {
    return rentRecordNo;
  }

  public void setRentRecordNo(String rentRecordNo) {
    this.rentRecordNo = rentRecordNo;
  }

  public String getRealName() {
    return realName;
  }

  public void setRealName(String realName) {
    this.realName = realName;
  }

  public String getIdNo() {
    return idNo;
  }

  public void setIdNo(String idNo) {
    this.idNo = idNo;
  }

  public Date getSignTime() {
    return signTime;
  }

  public void setSignTime(Date signTime) {
    this.signTime = signTime;
  }

  public Integer getLeaseTerm() {
    return leaseTerm;
  }

  public void setLeaseTerm(Integer leaseTerm) {
    this.leaseTerm = leaseTerm;
  }

  public BigDecimal getLeaseAmount() {
    return leaseAmount;
  }

  public void setLeaseAmount(BigDecimal leaseAmount) {
    this.leaseAmount = leaseAmount;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public String getStateDesc() {
    return stateDesc;
  }

  public void setStateDesc(String stateDesc) {
    this.stateDesc = stateDesc;
  }

  public Date getBackTime() {
    return backTime;
  }

  public void setBackTime(Date backTime) {
    this.backTime = backTime;
  }


}
