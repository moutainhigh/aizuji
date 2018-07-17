package org.gz.liquidation.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @Description:滞纳金定时任务执行日志
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月8日 	Created
 */
public class LateFeeTaskLogEntity implements Serializable {
    private Long id;

    /**
     * 订单号
     */
    private String orderSN;
    /**
     * 创建时间
     */
    private Date createOn;
    /**
     * 账单日期
     */
    private Date billDate;
    private Date startDate;
    private Date endDate;
    private static final long serialVersionUID = 1L;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrderSN() {
		return orderSN;
	}
	public void setOrderSN(String orderSN) {
		this.orderSN = orderSN;
	}
	public Date getCreateOn() {
		return createOn;
	}
	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
    
}