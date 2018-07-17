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
import java.util.Date;

/**
 * RentRecord 实体类
 * 
 * @author pk
 * @date 2018-03-26
 */
public class RentRecord implements Serializable {


    /**
  * 
  */
  private static final long serialVersionUID = 7419561853681005739L;
  /**
   * id
   */
    private Long id;
    /**
   * 请求芝麻的唯一标识
   */
  private String transactionId;
  /**
   * 租机编号
   */
    private String rentRecordNo;
    /**
     * 芝麻订单编号 
     */
    private String zmOrderNo;
    /**
     * 用户Id 
     */
    private Long userId;
    /**
     * 芝麻用户Id 
     */
    private String zmUserId;
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
     *  1 待审批 2 已拒绝 3 待支付 4 待签约 5 已取消 6 待配货 7 待拣货 8 待发货 9 发货中 10 正常履约（已经签收） 11 提前解约中 12 提前解约 13 换机订单状态 14 维修订单状态 15 已逾期 16 归还中 17 提前买断 18 提前归还（废弃） 19 正常买断 20 已归还   21提前买断中 22正常买断中 23提前归还中（废弃） 24履约完成 25 强制买断中 26 强制买断 27定损完成 28 强制履约中 29 强制履约完成 30 强制归还中 31 强制定损完成 32 强制归还完成 33退货中 34已退货 
     */
    private Integer state;
    /**
     * 审核状态 1进入一审 2进入二审 3进入三审 
     */
    private Integer creditState;
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

    public void setRentRecordNo(String rentRecordNo) {
        this.rentRecordNo = rentRecordNo;
    }
    
    public String getRentRecordNo() {
        return this.rentRecordNo;
    }
    
    public void setZmOrderNo(String zmOrderNo) {
        this.zmOrderNo = zmOrderNo;
    }
    
    public String getZmOrderNo() {
        return this.zmOrderNo;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getUserId() {
        return this.userId;
    }
    
    public void setZmUserId(String zmUserId) {
        this.zmUserId = zmUserId;
    }
    
    public String getZmUserId() {
        return this.zmUserId;
    }
    
    public void setExtendId(Long extendId) {
        this.extendId = extendId;
    }
    
    public Long getExtendId() {
        return this.extendId;
    }
    
    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }
    
    public String getChannelNo() {
        return this.channelNo;
    }
    
    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }
    
    public String getLogisticsNo() {
        return this.logisticsNo;
    }
    
    public void setReturnLogisticsNo(String returnLogisticsNo) {
        this.returnLogisticsNo = returnLogisticsNo;
    }
    
    public String getReturnLogisticsNo() {
        return this.returnLogisticsNo;
    }
    
    public void setStockFlag(Integer stockFlag) {
        this.stockFlag = stockFlag;
    }
    
    public Integer getStockFlag() {
        return this.stockFlag;
    }
    
    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }
    
    public String getSnCode() {
        return this.snCode;
    }
    
    public void setImei(String imei) {
        this.imei = imei;
    }
    
    public String getImei() {
        return this.imei;
    }
    
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }
    
    public Date getApplyTime() {
        return this.applyTime;
    }
    
    public void setApprovalTime(Date approvalTime) {
        this.approvalTime = approvalTime;
    }
    
    public Date getApprovalTime() {
        return this.approvalTime;
    }
    
    public void setTerminateApplyTime(Date terminateApplyTime) {
        this.terminateApplyTime = terminateApplyTime;
    }
    
    public Date getTerminateApplyTime() {
        return this.terminateApplyTime;
    }
    
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }
    
    public Date getPayTime() {
        return this.payTime;
    }
    
    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }
    
    public Date getSignTime() {
        return this.signTime;
    }
    
    public void setBackTime(Date backTime) {
        this.backTime = backTime;
    }
    
    public Date getBackTime() {
        return this.backTime;
    }
    
    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }
    
    public Date getCreateOn() {
        return this.createOn;
    }
    
    public void setUpdateOn(Date updateOn) {
        this.updateOn = updateOn;
    }
    
    public Date getUpdateOn() {
        return this.updateOn;
    }
    
    public void setState(Integer state) {
        this.state = state;
    }
    
    public Integer getState() {
        return this.state;
    }
    
    public void setCreditState(Integer creditState) {
        this.creditState = creditState;
    }
    
    public Integer getCreditState() {
        return this.creditState;
    }
    
    public void setDiscountFee(BigDecimal discountFee) {
        this.discountFee = discountFee;
    }
    
    public BigDecimal getDiscountFee() {
        return this.discountFee;
    }
    
    public void setProductType(Integer productType) {
        this.productType = productType;
    }
    
    public Integer getProductType() {
        return this.productType;
    }
    
    public void setApplyInvoice(Boolean applyInvoice) {
        this.applyInvoice = applyInvoice;
    }
    
    public Boolean getApplyInvoice() {
        return this.applyInvoice;
    }
    
    public void setInvoiceEnd(Boolean invoiceEnd) {
        this.invoiceEnd = invoiceEnd;
    }
    
    public Boolean getInvoiceEnd() {
        return this.invoiceEnd;
    }
    
    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }
    
    public String getChannelType() {
        return this.channelType;
    }
    
    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }
    
    public String getCouponId() {
        return this.couponId;
    }

    
    public Long getId() {
      return id;
    }

    
    public void setId(Long id) {
      this.id = id;
    }

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }
    
}
