package org.gz.order.common.entity;



import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * RentRecord 实体类
 * 
 * @author pk
 * @date 2017-12-13
 */
public class RentRecord implements Serializable {


    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
   * id
   */
    private Long id;
    /**
     * 租机编号 
     */
    private String rentRecordNo;
    /**
     * 用户Id 
     */
    private Long userId;
    /**
     * 租机订单扩展表ID 
     */
    private Long extendId;
    /**
     * 订单来源渠道 
     */
    private String channelNo;
    /**
     * 物流单号 
     */
    private String logisticsNo;
    /**
   * 还机时填写的物流单号
   */
  private String returnLogisticsNo;
  /**
   * 库存状态：0无货、1有货、2未更新库存
   */
    private Integer stockFlag;
    /**
     * 手机sn编码 
     */
    private String snCode;
    /**
     * IMEI 
     */
    private String imei;
    /**
     * 申请时间 
     */
    private Date applyTime;
    /**
   * 审核成功时间
   */
    private Date approvalTime;
    /**
   * 提前解约申请时间
   */
  private Date terminateApplyTime;
    /**
   * 支付成功时间
   */
    private Date payTime;
    /**
   * 签约成功时间
   */
    private Date signTime;
    /**
   * 还机到期时间
   */
  private Date backTime;
    /**
     * 创建时间 
     */
    private Date createOn;
    /**
     * 更新时间 
     */
    private Date updateOn;
    /**
   * 0提交 1 待审批 2 已拒绝 3 待支付 4 待签约 5 已取消 6 待配货 7 待拣货 8 待发货 9 发货中 10 正常履约（已经签收） 11
   * 提前解约中 12 提前解约 13 换机订单状态 14 维修订单状态 15 已逾期 16 归还中 17 提前买断 18 提前归还（废弃） 19 正常买断
   * 20 已归还 21提前买断中 22正常买断中 23提前归还中（废弃） 24履约完成 25 强制买断中 26 强制买断 27定损完成 28 强制履约中
   * 29 强制履约完成 30 强制归还中 31 强制定损完成 32 强制归还完成 33退货中 34已退货
   */
    private Integer state;
  /**
   * 审核状态 1进入一审 2进入二审 3进入三审
   */
  private Integer creditState;
  /**
   * 签约前合同存放地址
   */
  private String agreementUrl;
  /**
   * 签约盖章之后的合同地址
   */
  private String sealAgreementUrl;
  /**
   * 电子签章存根号
   */
  private String evid;
  /**
   * 电子签章传回的唯一id用于第三方上传合同失败记录
   */
  private String signServiceId;
  /**
   * 折损费
   */
  private BigDecimal discountFee;
  /**
   * 产品类型 1:租赁 2:以租代购 3:出售
   */
  private Integer productType;
  /**
   * 是否申请开发票 0：否 1：是
   */
  private Boolean applyInvoice;
  /**
   * 是否已经开票 0：否 1：是
   */
  private Boolean invoiceEnd;
  /**
   * 用户下载渠道
   */
  private String channelType;

  /**
   * 优惠券id串如果多个按逗号隔开
   */
  private String couponId;
  /**
   * 优惠券金额 按，隔开
   */
  private String couponAmount;
  /**
   * 售卖订单总支付价格
   */
  private BigDecimal salePayAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRentRecordNo() {
        return rentRecordNo;
    }

    public void setRentRecordNo(String rentRecordNo) {
        this.rentRecordNo = rentRecordNo;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getExtendId() {
        return extendId;
    }
    
    public void setExtendId(Long extendId) {
        this.extendId = extendId;
    }
    
    public String getChannelNo() {
        return channelNo;
    }
    
    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }
    
    public String getLogisticsNo() {
        return logisticsNo;
    }
    
    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }
    
    public Integer getStockFlag() {
        return stockFlag;
    }
    
    public void setStockFlag(Integer stockFlag) {
        this.stockFlag = stockFlag;
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
    
    public Date getApplyTime() {
        return applyTime;
    }
    
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }
    
    public Date getApprovalTime() {
        return approvalTime;
    }
    
    public void setApprovalTime(Date approvalTime) {
        this.approvalTime = approvalTime;
    }
    
    public Date getTerminateApplyTime() {
        return terminateApplyTime;
    }

    public void setTerminateApplyTime(Date terminateApplyTime) {
        this.terminateApplyTime = terminateApplyTime;
    }

    public Date getPayTime() {
        return payTime;
    }
    
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
    
    public Date getSignTime() {
        return signTime;
    }
    
    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }
    
    public Date getBackTime() {
        return backTime;
    }

    public void setBackTime(Date backTime) {
        this.backTime = backTime;
    }

    public Date getCreateOn() {
        return createOn;
    }
    
    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }
    
    public Date getUpdateOn() {
        return updateOn;
    }
    
    public void setUpdateOn(Date updateOn) {
        this.updateOn = updateOn;
    }
    
    public Integer getState() {
        return state;
    }
    
    public void setState(Integer state) {
        this.state = state;
    }

  public String getReturnLogisticsNo()
    {
      return returnLogisticsNo;
    }

  public void setReturnLogisticsNo(String returnLogisticsNo)
    {
      this.returnLogisticsNo = returnLogisticsNo;
    }

  public String getAgreementUrl()
    {
      return agreementUrl;
    }

  public void setAgreementUrl(String agreementUrl)
    {
      this.agreementUrl = agreementUrl;
    }

  public String getSealAgreementUrl()
    {
      return sealAgreementUrl;
    }

  public void setSealAgreementUrl(String sealAgreementUrl)
    {
      this.sealAgreementUrl = sealAgreementUrl;
    }

  public String getEvid()
    {
      return evid;
    }

  public void setEvid(String evid)
    {
      this.evid = evid;
    }

  public String getSignServiceId()
    {
      return signServiceId;
    }

  public void setSignServiceId(String signServiceId)
    {
      this.signServiceId = signServiceId;
    }

  public Integer getCreditState() {
    return creditState;
  }

  public void setCreditState(Integer creditState) {
    this.creditState = creditState;
  }

  public BigDecimal getDiscountFee() {
    return discountFee;
  }

  public void setDiscountFee(BigDecimal discountFee) {
    this.discountFee = discountFee;
  }

  public Integer getProductType() {
    return productType;
  }

  public void setProductType(Integer productType) {
    this.productType = productType;
  }

  public Boolean getApplyInvoice() {
    return applyInvoice;
  }

  public void setApplyInvoice(Boolean applyInvoice) {
    this.applyInvoice = applyInvoice;
  }

  public Boolean getInvoiceEnd() {
    return invoiceEnd;
  }

  public void setInvoiceEnd(Boolean invoiceEnd) {
    this.invoiceEnd = invoiceEnd;
  }

  public String getChannelType() {
    return channelType;
  }

  public void setChannelType(String channelType) {
    this.channelType = channelType;
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

  public BigDecimal getSalePayAmount() {
    return salePayAmount;
  }

  public void setSalePayAmount(BigDecimal salePayAmount) {
    this.salePayAmount = salePayAmount;
  }
    
    
}