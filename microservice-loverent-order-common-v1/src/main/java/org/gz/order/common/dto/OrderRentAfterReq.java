package org.gz.order.common.dto;

import java.io.Serializable;

/**
 * 租后管理请求req Author Version Date Changes 临时工 1.0 2018年1月19日 Created
 */
public class OrderRentAfterReq implements Serializable {

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

}
