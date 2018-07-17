package org.gz.order.common.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.gz.order.common.entity.RentInvoice;
import org.gz.order.common.entity.UserHistory;

/**
 * 售卖订单申请下单请求对象 Author Version Date Changes 临时工 1.0 2018年3月12日 Created
 */
public class SaleOrderReq implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 6756755595572456221L;
  /**
   * 订单来源渠道
   */
  private String channelNo;
  /**
   * 产品编号
   */
  @NotBlank(message = "产品编号不能为空")
  @NotNull(message = "产品编号不能为null")
  private String productNo;

  /**
   * 手机sn编码
   */
  @NotBlank(message = "sn编码不能为空")
  @NotNull(message = "sn编码不能为null")
  private String snCode;
  /**
   * IMEI
   */
  @NotBlank(message = "imei不能为空")
  @NotNull(message = "imei不能为null")
  private String imei;

  /**
   * 是否申请开发票 0：否 1：是
   */
  @NotNull(message = "applyInvoice不能为null")
  private Boolean applyInvoice;
  /**
   * 用户下载渠道
   */
  private String channelType;

  /**
   * 优惠券id串如果多个按逗号隔开
   */
  private String couponId;

  /**
   * 用户对象（用于风控）
   */
  private UserHistory userHistory;

  /**
   * 开票信息（先根据applyInvoice判断 如果true表示需要开票这个里面才会塞入开票信息）
   */
  private RentInvoice rentInvoice;

  /**
   * 用户ID
   */
  @NotNull(message = "userId不能为null")
  @Positive(message = "非法的userId")
  private Long userId;

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
   * 详细地址
   */
  private String address;

  /**
   * 是否购买碎屏险 0否 1是
   */
  private Boolean brokenScreenBuyed;

  /**
   * 优惠券金额 按，隔开
   */
  private String couponAmount;

  public String getChannelNo() {
    return channelNo;
  }

  public void setChannelNo(String channelNo) {
    this.channelNo = channelNo;
  }

  public String getProductNo() {
    return productNo;
  }

  public void setProductNo(String productNo) {
    this.productNo = productNo;
  }

  public String getSnCode() {
    return snCode;
  }

  public void setSnCode(String snCode) {
    this.snCode = snCode;
  }

  public String getImei() {
    return imei;
  }

  public void setImei(String imei) {
    this.imei = imei;
  }

  public Boolean getApplyInvoice() {
    return applyInvoice;
  }

  public void setApplyInvoice(Boolean applyInvoice) {
    this.applyInvoice = applyInvoice;
  }


  public String getChannelType() {
    return channelType;
  }

  public void setChannelType(String channelType) {
    this.channelType = channelType;
  }

  public UserHistory getUserHistory() {
    return userHistory;
  }

  public void setUserHistory(UserHistory userHistory) {
    this.userHistory = userHistory;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
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


  public RentInvoice getRentInvoice() {
    return rentInvoice;
  }

  public void setRentInvoice(RentInvoice rentInvoice) {
    this.rentInvoice = rentInvoice;
  }

  public String getCouponId() {
    return couponId;
  }

  public void setCouponId(String couponId) {
    this.couponId = couponId;
  }


  public Boolean getBrokenScreenBuyed() {
    return brokenScreenBuyed;
  }

  public void setBrokenScreenBuyed(Boolean brokenScreenBuyed) {
    this.brokenScreenBuyed = brokenScreenBuyed;
  }

  public String getCouponAmount() {
    return couponAmount;
  }

  public void setCouponAmount(String couponAmount) {
    this.couponAmount = couponAmount;
  }

}
