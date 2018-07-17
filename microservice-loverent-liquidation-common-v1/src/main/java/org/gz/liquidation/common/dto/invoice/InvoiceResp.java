package org.gz.liquidation.common.dto.invoice;

import java.math.BigDecimal;
import java.util.Date;

import org.gz.common.entity.BaseEntity;

/**
 * 
 * @Description:订单发票响应DTO
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月30日 	Created
 */
public class InvoiceResp extends BaseEntity {
  
  /**
   * 
   */
  private static final long serialVersionUID = 3903155078193777846L;
  /**
   * 订单编号
   */
  private String rentRecordNo;
  /**
   * 签约价格
   */
  private BigDecimal signContractAmount;
  /**
   * 手机号码
   */
  private String phoneNum;
  /**
   * 真实姓名
   */
  private String realName;
  /**
   * 型号
   */
  private String materielModelName;
  /**
   * 规格
   */
  private String materielSpecName;
  /**
   * 成色
   */
  private String configValue;
  /**
   * 发票抬头类型 0:个人 1:企业单位 
   */
  private Integer titleType;
  /**
   * 发票抬头 
   */
  private String title;
  /**
   * 税号 
   */
  private String invoiceNumber;
  /**
   * 发票内容 
   */
  private String content;
  /**
   * 发票金额 
   */
  private BigDecimal fee;
  /**
 * 碎屏保障金额
 */
private BigDecimal feeTwo;
/**
 * 开票类型 1纸质发票 2电子发票
 */
private Integer invoiceType;
/**
 * 支付时间
 */
private Date payTime;
 /**
  * 是否已经开票 0：否 1：是
  */
private Integer invoiceEnd;

public String getRentRecordNo() {
  return rentRecordNo;
}

public void setRentRecordNo(String rentRecordNo) {
  this.rentRecordNo = rentRecordNo;
}

public BigDecimal getSignContractAmount() {
  return signContractAmount;
}

public void setSignContractAmount(BigDecimal signContractAmount) {
  this.signContractAmount = signContractAmount;
}

public String getPhoneNum() {
  return phoneNum;
}

public void setPhoneNum(String phoneNum) {
  this.phoneNum = phoneNum;
}

public String getRealName() {
  return realName;
}

public void setRealName(String realName) {
  this.realName = realName;
}

public String getMaterielModelName() {
  return materielModelName;
}

public void setMaterielModelName(String materielModelName) {
  this.materielModelName = materielModelName;
}

public String getMaterielSpecName() {
  return materielSpecName;
}

public void setMaterielSpecName(String materielSpecName) {
  this.materielSpecName = materielSpecName;
}

public String getConfigValue() {
  return configValue;
}

public void setConfigValue(String configValue) {
  this.configValue = configValue;
}

public Integer getTitleType() {
  return titleType;
}

public void setTitleType(Integer titleType) {
  this.titleType = titleType;
}

public String getTitle() {
  return title;
}

public void setTitle(String title) {
  this.title = title;
}

public String getInvoiceNumber() {
  return invoiceNumber;
}

public void setInvoiceNumber(String invoiceNumber) {
  this.invoiceNumber = invoiceNumber;
}

public String getContent() {
  return content;
}

public void setContent(String content) {
  this.content = content;
}

public BigDecimal getFee() {
  return fee;
}

public void setFee(BigDecimal fee) {
  this.fee = fee;
}

public BigDecimal getFeeTwo() {
  return feeTwo;
}

public void setFeeTwo(BigDecimal feeTwo) {
  this.feeTwo = feeTwo;
}

public Integer getInvoiceType() {
  return invoiceType;
}

public void setInvoiceType(Integer invoiceType) {
  this.invoiceType = invoiceType;
}

public Date getPayTime() {
  return payTime;
}

public void setPayTime(Date payTime) {
  this.payTime = payTime;
}

public Integer getInvoiceEnd() {
  return invoiceEnd;
}

public void setInvoiceEnd(Integer invoiceEnd) {
  this.invoiceEnd = invoiceEnd;
}



}
