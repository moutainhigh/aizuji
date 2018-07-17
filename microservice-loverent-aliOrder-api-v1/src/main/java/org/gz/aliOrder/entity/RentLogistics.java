package org.gz.aliOrder.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * RentLogistics 实体类
 * 
 * @author pk
 * @date 2018-03-08
 */
public class RentLogistics implements Serializable {


  /**
  * 
  */
  private static final long serialVersionUID = -5913394826257443241L;
  /**
   * id
   */
    private Long id;
    /**
     * 租机编号 
     */
    private String rentRecordNo;
    /**
     * 创建人ID 
     */
    private Long createBy;
    /**
     * 创建时间 
     */
    private Date createOn;
    /**
     * 创建人用户名 
     */
    private String createMan;
    /**
     * 物流单号 
     */
    private String logisticsNo;
    /**
     * 商户号（月结卡号） 
     */
    private String businessNo;
    /**
     * 物流状态 0发货中 1已签收 
     */
    private Integer state;
    /**
     * 物流类型 0我司发货 1用户归还 2用户维修 
     */
    private Integer type;

    public void setRentRecordNo(String rentRecordNo) {
        this.rentRecordNo = rentRecordNo;
    }
    
    public String getRentRecordNo() {
        return this.rentRecordNo;
    }
    
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }
    
    public Long getCreateBy() {
        return this.createBy;
    }
    
    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }
    
    public Date getCreateOn() {
        return this.createOn;
    }
    
    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }
    
    public String getCreateMan() {
        return this.createMan;
    }
    
    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }
    
    public String getLogisticsNo() {
        return this.logisticsNo;
    }
    
    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }
    
    public String getBusinessNo() {
        return this.businessNo;
    }
    
    public void setState(Integer state) {
        this.state = state;
    }
    
    public Integer getState() {
        return this.state;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }
    
    public Integer getType() {
        return this.type;
    }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
    
}
