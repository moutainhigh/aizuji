package org.gz.order.common.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gz.order.common.entity.RentRecord;

public class OrderDetailResp extends RentRecord implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 状态提示信息
     */
    private String            tipInfo;
    /**
     * 状态描述
     */
    private String            stateDesc;
  /**
   * 商品状态描述
   */
  private String productStateDesc;

  /**
   * 起租时间
   */
  private Date startRentTime;
  /**
   * 归还提交时间
   */
  private Date recycleTime;
  /**
   * 发货时间
   */
  private Date sendOutTime;

  /**
   * 账期 格式 3/12-一共12期当前第3期
   */
  private String bill;
  /**
   * 已缴纳滞纳金
   */
  private BigDecimal settledLateFees;
  /**
   * 已还租金
   */
  private BigDecimal settledRent;
  /**
   * 买断金额
   */
  private BigDecimal buyoutAmount;

  /**
   * 买断时间
   */
  private Date buyOutTime;
  /**
   * 签收时间
   */
  private Date receivedTime;

  /**
   * 待支付到期取消时间
   */
  private Date waitPayTime;

  /**
   * 待支付总金额
   */
  private BigDecimal waitPayAmount;

    /////////////////////////////////////////////////////////////// extends对象
    /**
     * 租机编号
     */
    private String            rentRecordNo;
    /**
     * 产品id
     */
    private Integer           productId;
    /**
     * 产品编号
     */
    private String            productNo;
    /**
     * 物料分类id
     */
    private Integer           materielClassId;
    /**
     * 物料分类名称
     */
    private String            materielClassName;
    /**
     * 物料品牌id
     */
    private Integer           materielBrandId;
    /**
     * 物料品牌名称
     */
    private String            materielBrandName;
    /**
     * 物料型号id
     */
    private Integer           materielModelId;
    /**
     * 物料型号名称
     */
    private String            materielModelName;
    /**
     * 物料规格名称
     */
    private String            materielSpecName;
    /**
     * 产品租期（月）
     */
    private Integer           leaseTerm;
    /**
     * 租金
     */
    private BigDecimal        leaseAmount;
    /**
     * 产品一次性保险费
     */
    private BigDecimal        premium;
    /**
     * 溢价
     */
  private BigDecimal floatAmount;
    /**
     * 产品签约价值
     */
    private BigDecimal        signContractAmount;
    /**
     * 芝麻信用
     */
    private Integer           sesameCredit;
    /**
     * 物料编号
     */
    private String            materielNo;
    /**
     * 物料名称
     */
    private String            matreielName;
    /**
     * 产品缩率图
     */
    private String            thumbnail;
    /**
     * 手机号码
     */
    private String            phoneNum;
    /**
     * 真实姓名
     */
    private String            realName;
    /**
     * 身份证号
     */
    private String            idNo;
    /**
     * 省份
     */
    private String            prov;
    /**
     * 城市
     */
    private String            city;
    /**
     * 地区
     */
    private String            area;
    /**
     * 详细地址
     */
    private String            address;
    /**
     * 紧急联系人
     */
    private String            emergencyContact;
    /**
     * 紧急联系人电话
     */
    private String            emergencyContactPhone;
    /**
     * 紧急联系人关系 （0配偶 1亲属 2朋友）
     */
    private Integer           relationship;
    /**
     * 身份证正面地址
     */
    private String            cardFaceUrl;
    /**
     * 身份证反面地址
     */
    private String            cardBackUrl;
    /**
   * 人脸识别认证照片url地址
   */
  private String faceAuthUrl;
  /**
   * 实名认证状态 0-未认证 1-已认证
   */
    private Boolean           realnameCertState;
    /**
     * 户籍地址
     */
    private String            residenceAddress;
    /**
     * 签发机关
     */
    private String            issuingAuthority;
    /**
     * 身份证有效日期开始时间 2017-12-10
     */
    private String            effectiveStartDate;
    /**
     * 结束有效日期
     */
    private String            effectiveEndDate;
    /**
     * 学历
     */
    private Integer           education;

  /**
   * 显示价值
   */
  private BigDecimal showAmount;
  /**
   * 碎屏险金额
   */
  private BigDecimal brokenScreenAmount;
  /**
   * 是否购买碎屏险 0否 1是
   */
  private Boolean brokenScreenBuyed;

  /**
   * 新旧程度配置Id
   */
  private Integer materielNewConfigId;

  /**
   * 新旧程度配置值
   */
  private String configValue;

    public String getRentRecordNo() {
        return rentRecordNo;
    }

    public void setRentRecordNo(String rentRecordNo) {
        this.rentRecordNo = rentRecordNo;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public Integer getMaterielClassId() {
        return materielClassId;
    }

    public void setMaterielClassId(Integer materielClassId) {
        this.materielClassId = materielClassId;
    }

    public String getMaterielClassName() {
        return materielClassName;
    }

    public void setMaterielClassName(String materielClassName) {
        this.materielClassName = materielClassName;
    }

    public Integer getMaterielBrandId() {
        return materielBrandId;
    }

    public void setMaterielBrandId(Integer materielBrandId) {
        this.materielBrandId = materielBrandId;
    }

    public String getMaterielBrandName() {
        return materielBrandName;
    }

    public void setMaterielBrandName(String materielBrandName) {
        this.materielBrandName = materielBrandName;
    }

    public Integer getMaterielModelId() {
        return materielModelId;
    }

    public void setMaterielModelId(Integer materielModelId) {
        this.materielModelId = materielModelId;
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

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

  public BigDecimal getFloatAmount() {
    return floatAmount;
    }

  public void setFloatAmount(BigDecimal floatAmount) {
    this.floatAmount = floatAmount;
    }

    public BigDecimal getSignContractAmount() {
        return signContractAmount;
    }

    public void setSignContractAmount(BigDecimal signContractAmount) {
        this.signContractAmount = signContractAmount;
    }

    public Integer getSesameCredit() {
        return sesameCredit;
    }

    public void setSesameCredit(Integer sesameCredit) {
        this.sesameCredit = sesameCredit;
    }

    public String getMaterielNo() {
        return materielNo;
    }

    public void setMaterielNo(String materielNo) {
        this.materielNo = materielNo;
    }

    public String getMatreielName() {
        return matreielName;
    }

    public void setMatreielName(String matreielName) {
        this.matreielName = matreielName;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public Integer getRelationship() {
        return relationship;
    }

    public void setRelationship(Integer relationship) {
        this.relationship = relationship;
    }

    public String getCardFaceUrl() {
        return cardFaceUrl;
    }

    public void setCardFaceUrl(String cardFaceUrl) {
        this.cardFaceUrl = cardFaceUrl;
    }

    public String getCardBackUrl() {
        return cardBackUrl;
    }

    public void setCardBackUrl(String cardBackUrl) {
        this.cardBackUrl = cardBackUrl;
    }

    public Boolean getRealnameCertState() {
        return realnameCertState;
    }

    public void setRealnameCertState(Boolean realnameCertState) {
        this.realnameCertState = realnameCertState;
    }

    public String getResidenceAddress() {
        return residenceAddress;
    }

    public void setResidenceAddress(String residenceAddress) {
        this.residenceAddress = residenceAddress;
    }

    public String getIssuingAuthority() {
        return issuingAuthority;
    }

    public void setIssuingAuthority(String issuingAuthority) {
        this.issuingAuthority = issuingAuthority;
    }

    public String getEffectiveStartDate() {
        return effectiveStartDate;
    }

    public void setEffectiveStartDate(String effectiveStartDate) {
        this.effectiveStartDate = effectiveStartDate;
    }

    public String getEffectiveEndDate() {
        return effectiveEndDate;
    }

    public void setEffectiveEndDate(String effectiveEndDate) {
        this.effectiveEndDate = effectiveEndDate;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

  public Date getRecycleTime()
    {
      return recycleTime;
    }

  public void setRecycleTime(Date recycleTime)
    {
      this.recycleTime = recycleTime;
    }

  public Date getSendOutTime()
    {
      return sendOutTime;
    }

  public void setSendOutTime(Date sendOutTime)
    {
      this.sendOutTime = sendOutTime;
    }

  public Date getStartRentTime()
    {
      return startRentTime;
    }

  public void setStartRentTime(Date startRentTime)
    {
      this.startRentTime = startRentTime;
    }

  public String getStateDesc()
    {
      return stateDesc;
    }

  public void setStateDesc(String stateDesc)
    {
      this.stateDesc = stateDesc;
    }

  public String getTipInfo()
    {
      return tipInfo;
    }

  public void setTipInfo(String tipInfo)
    {
      this.tipInfo = tipInfo;
    }

  public String getFaceAuthUrl()
    {
      return faceAuthUrl;
    }

  public void setFaceAuthUrl(String faceAuthUrl)
    {
      this.faceAuthUrl = faceAuthUrl;
    }

  public String getBill() {
    return bill;
  }

  public void setBill(String bill) {
    this.bill = bill;
  }

  public BigDecimal getSettledLateFees() {
    return settledLateFees;
  }

  public void setSettledLateFees(BigDecimal settledLateFees) {
    this.settledLateFees = settledLateFees;
  }

  public BigDecimal getSettledRent() {
    return settledRent;
  }

  public void setSettledRent(BigDecimal settledRent) {
    this.settledRent = settledRent;
  }

  public BigDecimal getBuyoutAmount() {
    return buyoutAmount;
  }

  public void setBuyoutAmount(BigDecimal buyoutAmount) {
    this.buyoutAmount = buyoutAmount;
  }

  public String getProductStateDesc() {
    return productStateDesc;
  }

  public void setProductStateDesc(String productStateDesc) {
    this.productStateDesc = productStateDesc;
  }

  public Date getBuyOutTime() {
    return buyOutTime;
  }

  public void setBuyOutTime(Date buyOutTime) {
    this.buyOutTime = buyOutTime;
  }

  public Date getReceivedTime() {
    return receivedTime;
  }

  public void setReceivedTime(Date receivedTime) {
    this.receivedTime = receivedTime;
  }

  public BigDecimal getShowAmount() {
    return showAmount;
  }

  public void setShowAmount(BigDecimal showAmount) {
    this.showAmount = showAmount;
  }

  public Date getWaitPayTime() {
    return waitPayTime;
  }

  public void setWaitPayTime(Date waitPayTime) {
    this.waitPayTime = waitPayTime;
  }

  public BigDecimal getBrokenScreenAmount() {
    return brokenScreenAmount;
  }

  public void setBrokenScreenAmount(BigDecimal brokenScreenAmount) {
    this.brokenScreenAmount = brokenScreenAmount;
  }

  public Boolean getBrokenScreenBuyed() {
    return brokenScreenBuyed;
  }

  public void setBrokenScreenBuyed(Boolean brokenScreenBuyed) {
    this.brokenScreenBuyed = brokenScreenBuyed;
  }

  public Integer getMaterielNewConfigId() {
    return materielNewConfigId;
  }

  public void setMaterielNewConfigId(Integer materielNewConfigId) {
    this.materielNewConfigId = materielNewConfigId;
  }

  public String getConfigValue() {
    return configValue;
  }

  public void setConfigValue(String configValue) {
    this.configValue = configValue;
  }

  public BigDecimal getWaitPayAmount() {
    return waitPayAmount;
  }

  public void setWaitPayAmount(BigDecimal waitPayAmount) {
    this.waitPayAmount = waitPayAmount;
  }

}
