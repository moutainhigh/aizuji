/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.aliOrder.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * RentRecordExtends 实体类
 * 
 * @author pk
 * @date 2018-03-26
 */
public class RentRecordExtends implements Serializable {

    /**
  * 
  */
  private static final long serialVersionUID = -2454746807836366062L;
  /**
   * 主键id
   */
    private Long id;
    /**
     * 用户编号 
     */
    private Long userId;
    /**
     * 租机编号 
     */
    private String rentRecordNo;
    /**
     * 产品id 
     */
    private Integer productId;
    /**
     * 产品编号 
     */
    private String productNo;
    /**
     * 物料分类id 
     */
    private Integer materielClassId;
    /**
     * 物料分类名称 
     */
    private String materielClassName;
    /**
     * 物料品牌id 
     */
    private Integer materielBrandId;
    /**
     * 物料品牌名称 
     */
    private String materielBrandName;
    /**
     * 物料型号id 
     */
    private Integer materielModelId;
    /**
     * 物料型号名称 
     */
    private String materielModelName;
    /**
     * 物料规格名称 
     */
    private String materielSpecName;
    /**
     * 产品租期（月） 
     */
    private Integer leaseTerm;
    /**
     * 显示价值 
     */
    private BigDecimal showAmount;
    /**
     * 租金 
     */
    private BigDecimal leaseAmount;
    /**
     * 产品一次性保险费 
     */
    private BigDecimal premium;
    /**
     * 溢价 
     */
    private BigDecimal floatAmount;
    /**
     * 产品签约价值 
     */
    private BigDecimal signContractAmount;
    /**
     * 芝麻信用 
     */
    private Integer sesameCredit;
    /**
     * 物料id 
     */
    private String materielNo;
    /**
     * 物料名称 
     */
    private String matreielName;
    /**
     * 产品缩率图 
     */
    private String thumbnail;
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
     * 新旧程度配置Id 
     */
    private Integer materielNewConfigId;
    /**
     *  新旧程度配置值 
     */
    private String configValue;
    /**
     * 碎屏险金额 
     */
    private BigDecimal brokenScreenAmount;
    /**
     * 是否购买碎屏险 0否 1是 
     */
    private Boolean brokenScreenBuyed;
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

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getUserId() {
        return this.userId;
    }
    
    public void setRentRecordNo(String rentRecordNo) {
        this.rentRecordNo = rentRecordNo;
    }
    
    public String getRentRecordNo() {
        return this.rentRecordNo;
    }
    
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    
    public Integer getProductId() {
        return this.productId;
    }
    
    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }
    
    public String getProductNo() {
        return this.productNo;
    }
    
    public void setMaterielClassId(Integer materielClassId) {
        this.materielClassId = materielClassId;
    }
    
    public Integer getMaterielClassId() {
        return this.materielClassId;
    }
    
    public void setMaterielClassName(String materielClassName) {
        this.materielClassName = materielClassName;
    }
    
    public String getMaterielClassName() {
        return this.materielClassName;
    }
    
    public void setMaterielBrandId(Integer materielBrandId) {
        this.materielBrandId = materielBrandId;
    }
    
    public Integer getMaterielBrandId() {
        return this.materielBrandId;
    }
    
    public void setMaterielBrandName(String materielBrandName) {
        this.materielBrandName = materielBrandName;
    }
    
    public String getMaterielBrandName() {
        return this.materielBrandName;
    }
    
    public void setMaterielModelId(Integer materielModelId) {
        this.materielModelId = materielModelId;
    }
    
    public Integer getMaterielModelId() {
        return this.materielModelId;
    }
    
    public void setMaterielModelName(String materielModelName) {
        this.materielModelName = materielModelName;
    }
    
    public String getMaterielModelName() {
        return this.materielModelName;
    }
    
    public void setMaterielSpecName(String materielSpecName) {
        this.materielSpecName = materielSpecName;
    }
    
    public String getMaterielSpecName() {
        return this.materielSpecName;
    }
    
    public void setLeaseTerm(Integer leaseTerm) {
        this.leaseTerm = leaseTerm;
    }
    
    public Integer getLeaseTerm() {
        return this.leaseTerm;
    }
    
    public void setShowAmount(BigDecimal showAmount) {
        this.showAmount = showAmount;
    }
    
    public BigDecimal getShowAmount() {
        return this.showAmount;
    }
    
    public void setLeaseAmount(BigDecimal leaseAmount) {
        this.leaseAmount = leaseAmount;
    }
    
    public BigDecimal getLeaseAmount() {
        return this.leaseAmount;
    }
    
    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }
    
    public BigDecimal getPremium() {
        return this.premium;
    }
    
    public void setFloatAmount(BigDecimal floatAmount) {
        this.floatAmount = floatAmount;
    }
    
    public BigDecimal getFloatAmount() {
        return this.floatAmount;
    }
    
    public void setSignContractAmount(BigDecimal signContractAmount) {
        this.signContractAmount = signContractAmount;
    }
    
    public BigDecimal getSignContractAmount() {
        return this.signContractAmount;
    }
    
    public void setSesameCredit(Integer sesameCredit) {
        this.sesameCredit = sesameCredit;
    }
    
    public Integer getSesameCredit() {
        return this.sesameCredit;
    }
    
    public void setMaterielNo(String materielNo) {
        this.materielNo = materielNo;
    }
    
    public String getMaterielNo() {
        return this.materielNo;
    }
    
    public void setMatreielName(String matreielName) {
        this.matreielName = matreielName;
    }
    
    public String getMatreielName() {
        return this.matreielName;
    }
    
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    
    public String getThumbnail() {
        return this.thumbnail;
    }
    
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    
    public String getPhoneNum() {
        return this.phoneNum;
    }
    
    public void setRealName(String realName) {
        this.realName = realName;
    }
    
    public String getRealName() {
        return this.realName;
    }
    
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }
    
    public String getIdNo() {
        return this.idNo;
    }
    
    public void setProv(String prov) {
        this.prov = prov;
    }
    
    public String getProv() {
        return this.prov;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getCity() {
        return this.city;
    }
    
    public void setArea(String area) {
        this.area = area;
    }
    
    public String getArea() {
        return this.area;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getAddress() {
        return this.address;
    }
    
    public void setMaterielNewConfigId(Integer materielNewConfigId) {
        this.materielNewConfigId = materielNewConfigId;
    }
    
    public Integer getMaterielNewConfigId() {
        return this.materielNewConfigId;
    }
    
    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }
    
    public String getConfigValue() {
        return this.configValue;
    }
    
    public void setBrokenScreenAmount(BigDecimal brokenScreenAmount) {
        this.brokenScreenAmount = brokenScreenAmount;
    }
    
    public BigDecimal getBrokenScreenAmount() {
        return this.brokenScreenAmount;
    }
    
    public void setBrokenScreenBuyed(Boolean brokenScreenBuyed) {
        this.brokenScreenBuyed = brokenScreenBuyed;
    }
    
    public Boolean getBrokenScreenBuyed() {
        return this.brokenScreenBuyed;
    }
    
    public void setZmGrade(String zmGrade) {
        this.zmGrade = zmGrade;
    }
    
    public String getZmGrade() {
        return this.zmGrade;
    }
    
    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }
    
    public BigDecimal getCreditAmount() {
        return this.creditAmount;
    }
    
    public void setZmRisk(String zmRisk) {
        this.zmRisk = zmRisk;
    }
    
    public String getZmRisk() {
        return this.zmRisk;
    }
    
    public void setZmFace(String zmFace) {
        this.zmFace = zmFace;
    }
    
    public String getZmFace() {
        return this.zmFace;
    }

    
    public Long getId() {
      return id;
    }

    
    public void setId(Long id) {
      this.id = id;
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
    
}
