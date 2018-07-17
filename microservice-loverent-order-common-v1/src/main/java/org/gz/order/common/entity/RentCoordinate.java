package org.gz.order.common.entity;

import java.io.Serializable;

/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */

import java.util.Date;

/**
 * RentCoordinate 实体类
 * 
 * @author pk
 * @date 2017-12-20
 */
public class RentCoordinate implements Serializable
  {


    /**
  * 
  */
  private static final long serialVersionUID = -2391609375809484130L;
  /**
   * id
   */
    private Long id;
    /**
     * 租机编号 
     */
    private String rentRecordNo;
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
   * 创建时间
   */
    private Date createOn;
    /**
   * 1 申请 2签约
   */
    private Integer state;
  /**
   * 是否有效 0无效 1有效
   */
  private Boolean valid;

    public void setRentRecordNo(String rentRecordNo) {
        this.rentRecordNo = rentRecordNo;
    }
    
    public String getRentRecordNo() {
        return this.rentRecordNo;
    }
    
    public void setLng(String lng) {
        this.lng = lng;
    }
    
    public String getLng() {
        return this.lng;
    }
    
    public void setLat(String lat) {
        this.lat = lat;
    }
    
    public String getLat() {
        return this.lat;
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

    public Long getId()
      {
        return id;
      }

    public void setId(Long id)
      {
        this.id = id;
      }

  public Boolean getValid()
    {
      return valid;
    }

  public void setValid(Boolean valid)
    {
      this.valid = valid;
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
    
}
