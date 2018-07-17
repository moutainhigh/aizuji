package org.gz.aliOrder.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @description 订单创建成功异步通知更新信用免押积分和芝麻单号 Author Version Date Changes 临时工 1.0
 * 2018年3月26日 Created
 */
public class UpdateCreditAmountReq implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = -7444522780333832716L;
  /**
   * 订单编号
   */
  @NotNull(message = "订单编号不能为null")
  @NotBlank(message = "订单编号不能为空")
  private String rentRecordNo;
  /**
   * 信用免押金额
   */
  private BigDecimal creditAmount;

  /**
   * PREAUTH 预授权 WITHHOLD 代扣免押 ALL 两者都支持
   */
  private String fundType;

  /**
   * 芝麻订单编号
   */
  private String zmOrderNo;

  public String getRentRecordNo() {
    return rentRecordNo;
  }

  public void setRentRecordNo(String rentRecordNo) {
    this.rentRecordNo = rentRecordNo;
  }

  public BigDecimal getCreditAmount() {
    return creditAmount;
  }

  public void setCreditAmount(BigDecimal creditAmount) {
    this.creditAmount = creditAmount;
  }

  public String getFundType() {
    return fundType;
  }

  public void setFundType(String fundType) {
    this.fundType = fundType;
  }

  public String getZmOrderNo() {
    return zmOrderNo;
  }

  public void setZmOrderNo(String zmOrderNo) {
    this.zmOrderNo = zmOrderNo;
  }

}
