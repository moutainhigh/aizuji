package org.gz.aliOrder.dto;

import java.io.Serializable;

public class AddOrderResp implements Serializable {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1599219258700759733L;
  /**
   * 租机编号
   */
  private String            rentRecordNo;
  /**
   * 逾期时间
   */
  private String overdueTime;
  
  private String transactionId;

  public String getRentRecordNo() {
    return rentRecordNo;
  }
  
  public void setRentRecordNo(String rentRecordNo) {
    this.rentRecordNo = rentRecordNo;
  }
  
  public String getOverdueTime() {
    return overdueTime;
  }
  
  public void setOverdueTime(String overdueTime) {
    this.overdueTime = overdueTime;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }
  
  
}
