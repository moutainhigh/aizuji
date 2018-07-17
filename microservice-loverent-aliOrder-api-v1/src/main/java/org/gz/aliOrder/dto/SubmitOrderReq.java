package org.gz.aliOrder.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SubmitOrderReq implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 3156245952765267629L;

  /**
   * 租机编号
   */
  private String rentRecordNo;
  /**
   * 芝麻订单编号
   */
  private String zmOrderNo;

  /**
   * 手机号码
   */
  private String phoneNum;
  /**
   * 真实姓名
   */
  private String realName;
  /**
   * 身份证号
   */
  private String idNo;
  /**
   * 省份
   */
  private String prov;
  /**
   * 城市
   */
  private String city;
  /**
   * 地区
   */
  private String area;
  /**
   * address
   */
  private String address;
/**
   * 较差、中等、良好、优秀、极好五个级别 
   */
  private String zmGrade;
  /**
   * 信用免押金额
   */
  private BigDecimal creditAmount;
  /**
   * 芝麻分控产品级联合结果Y/N
   */
  private String zmRisk;
  /**
   * 人脸审核结果Y/N
   */
  private String zmFace;

  /**
   * 芝麻渠道来源
   */
  private String zmChannelId;
  /**
   * PREAUTH 预授权 WITHHOLD 代扣免押 ALL 两者都支持
   */
  private String fundType;

  public String getZmOrderNo() {
    return zmOrderNo;
  }

  public void setZmOrderNo(String zmOrderNo) {
    this.zmOrderNo = zmOrderNo;
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

  public String getIdNo() {
    return idNo;
  }

  public void setIdNo(String idNo) {
    this.idNo = idNo;
  }

  public String getProv() {
    return prov;
  }

  public void setProv(String prov) {
    this.prov = prov;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getZmGrade() {
    return zmGrade;
  }

  public void setZmGrade(String zmGrade) {
    this.zmGrade = zmGrade;
  }

  public BigDecimal getCreditAmount() {
    return creditAmount;
  }

  public void setCreditAmount(BigDecimal creditAmount) {
    this.creditAmount = creditAmount;
  }

  public String getZmRisk() {
    return zmRisk;
  }

  public void setZmRisk(String zmRisk) {
    this.zmRisk = zmRisk;
  }

  public String getZmFace() {
    return zmFace;
  }

  public void setZmFace(String zmFace) {
    this.zmFace = zmFace;
  }

  public String getZmChannelId() {
    return zmChannelId;
  }

  public void setZmChannelId(String zmChannelId) {
    this.zmChannelId = zmChannelId;
  }

  public String getFundType() {
    return fundType;
  }

  public void setFundType(String fundType) {
    this.fundType = fundType;
  }

  public String getRentRecordNo() {
    return rentRecordNo;
  }

  public void setRentRecordNo(String rentRecordNo) {
    this.rentRecordNo = rentRecordNo;
  }

}
