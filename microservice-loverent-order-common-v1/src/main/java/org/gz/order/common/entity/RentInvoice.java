package org.gz.order.common.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * RentInvoice 实体类
 * 
 * @author pk
 * @date 2018-03-12
 */
public class RentInvoice implements Serializable {


    /**
  * 
  */
  private static final long serialVersionUID = 8740674885159117771L;
  /**
   * id
   */
    private Long id;
    /**
     * 租机编号 
     */
    private String rentRecordNo;
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
   * 创建时间
   */
    private Date createOn;
  /**
   * 开票类型 1纸质发票 2电子发票
   */
  private Integer invoiceType;

    public void setRentRecordNo(String rentRecordNo) {
        this.rentRecordNo = rentRecordNo;
    }
    
    public String getRentRecordNo() {
        return this.rentRecordNo;
    }
    
    public void setTitleType(Integer titleType) {
        this.titleType = titleType;
    }
    
    public Integer getTitleType() {
        return this.titleType;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
    
    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getContent() {
        return this.content;
    }
    
    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
    
    public BigDecimal getFee() {
        return this.fee;
    }
    
    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }
    
    public Date getCreateOn() {
        return this.createOn;
    }

    
    public Long getId() {
      return id;
    }

    
    public void setId(Long id) {
      this.id = id;
    }

  public Integer getInvoiceType() {
    return invoiceType;
  }

  public void setInvoiceType(Integer invoiceType) {
    this.invoiceType = invoiceType;
  }

  public BigDecimal getFeeTwo() {
    return feeTwo;
  }

  public void setFeeTwo(BigDecimal feeTwo) {
    this.feeTwo = feeTwo;
  }
    
}
