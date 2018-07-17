package org.gz.order.common.dto;

import java.io.Serializable;

import org.gz.order.common.entity.RentRecordExtends;
import org.gz.order.common.entity.UserHistory;

public class SubmitOrderReq extends RentRecordExtends implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 用户对象（用于风控）
   */
  private UserHistory userHistory;

  /**
   * 用户ID
   */
  private Long userId;
  /**
   * 创建人用户名
   */
  private String createMan;
  /**
   * 经度
   */
  private String lng;
  /**
   * 纬度
   */
  private String lat;

  /**
   * 手机型号
   */
  private String phoneModel;

  /**
   * 设备imei号
   */
  private String imei;

  /**
   * 优惠券id串如果多个按逗号隔开
   */
  private String couponId;

  /**
   * 优惠券金额 按，隔开
   */
  private String couponAmount;

  public Long getUserId()
    {
      return userId;
    }

  public void setUserId(Long userId)
    {
      this.userId = userId;
    }

  public String getCreateMan()
    {
      return createMan;
    }

  public void setCreateMan(String createMan)
    {
      this.createMan = createMan;
    }

  public String getLng()
    {
      return lng;
    }

  public void setLng(String lng)
    {
      this.lng = lng;
    }

  public String getLat()
    {
      return lat;
    }

  public void setLat(String lat)
    {
      this.lat = lat;
    }

  public String getPhoneModel() {
    return phoneModel;
  }

  public void setPhoneModel(String phoneModel) {
    this.phoneModel = phoneModel;
  }

  public String getImei() {
    return imei;
  }

  public void setImei(String imei) {
    this.imei = imei;
  }

  public UserHistory getUserHistory() {
    return userHistory;
  }

  public void setUserHistory(UserHistory userHistory) {
    this.userHistory = userHistory;
  }

  public String getCouponId() {
    return couponId;
  }

  public void setCouponId(String couponId) {
    this.couponId = couponId;
  }

  public String getCouponAmount() {
    return couponAmount;
  }

  public void setCouponAmount(String couponAmount) {
    this.couponAmount = couponAmount;
  }

}
