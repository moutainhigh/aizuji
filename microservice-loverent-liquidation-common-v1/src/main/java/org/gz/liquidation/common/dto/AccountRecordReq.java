package org.gz.liquidation.common.dto;

import java.util.Date;

import org.gz.common.entity.QueryPager;
/**
 * 
 * @Description:TODO	清算记录请求DTO
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年1月30日 	Created
 */
public class AccountRecordReq extends QueryPager {
	 /**
	 * 
	 */
	private static final long serialVersionUID = -1892615070529382066L;
	/**
     * 订单号
     */
    private String orderSN;
    /**
     * 开始时间
     */
    private Date startDate;
    /**
     * 结束时间
     */
    private Date endDate;
    /**
     * 清算类型（科目编码）
     */
    private String accountCode;
	public String getOrderSN() {
		return orderSN;
	}
	public void setOrderSN(String orderSN) {
		this.orderSN = orderSN;
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
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
    
}
