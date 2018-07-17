package org.gz.liquidation.common.dto;

import java.util.Date;

import org.gz.common.entity.QueryPager;
/**
 * 
 * @Description:TODO	退款记录查询请求dto
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年1月22日 	Created
 */
public class RefundLogQueryReq extends QueryPager {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2529238755944052984L;
	/**
	 * 订单号
	 */
	private String orderSN;
	private String userRealName;
	/**
	 * 操作人
	 */
	private String operator;
	private Date startDate;
	private Date endDate;
	/**
	 * 退款科目
	 */
	private String accountCode;
	private Long createBy;
	public String getOrderSN() {
		return orderSN;
	}
	public void setOrderSN(String orderSN) {
		this.orderSN = orderSN;
	}
	public String getUserRealName() {
		return userRealName;
	}
	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
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
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	
}
