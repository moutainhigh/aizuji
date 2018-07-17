package org.gz.aliOrder.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gz.aliOrder.entity.RentRecord;


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
   * 需冻结押金 = 总押金- 信用免押金
   */
  private BigDecimal frozenAmount;

  /**
   * 订单取消时间
   */
  private Date cancleTime;

    /////////////////////////////////////////////////////////////// extends对象
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
   * 新旧程度配置值
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

  public String getTipInfo() {
    return tipInfo;
  }

  public void setTipInfo(String tipInfo) {
    this.tipInfo = tipInfo;
  }

  public String getStateDesc() {
    return stateDesc;
  }

  public void setStateDesc(String stateDesc) {
    this.stateDesc = stateDesc;
  }

  public String getProductStateDesc() {
    return productStateDesc;
  }

  public void setProductStateDesc(String productStateDesc) {
    this.productStateDesc = productStateDesc;
  }

  public Date getStartRentTime() {
    return startRentTime;
  }

  public void setStartRentTime(Date startRentTime) {
    this.startRentTime = startRentTime;
  }

  public Date getRecycleTime() {
    return recycleTime;
  }

  public void setRecycleTime(Date recycleTime) {
    this.recycleTime = recycleTime;
  }

  public Date getSendOutTime() {
    return sendOutTime;
  }

  public void setSendOutTime(Date sendOutTime) {
    this.sendOutTime = sendOutTime;
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

  public Date getWaitPayTime() {
    return waitPayTime;
  }

  public void setWaitPayTime(Date waitPayTime) {
    this.waitPayTime = waitPayTime;
  }

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

  public BigDecimal getShowAmount() {
    return showAmount;
  }

  public void setShowAmount(BigDecimal showAmount) {
    this.showAmount = showAmount;
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

  public BigDecimal getFrozenAmount() {
    return frozenAmount;
  }

  public void setFrozenAmount(BigDecimal frozenAmount) {
    this.frozenAmount = frozenAmount;
  }

  public Date getCancleTime() {
    return cancleTime;
  }

  public void setCancleTime(Date cancleTime) {
    this.cancleTime = cancleTime;
  }

}
