package org.gz.liquidation.entity;

import java.util.Date;
/**
 * 
 * @Description:TODO	还款统计实体类
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月30日 	Created
 */
public class RepaymentStatistics {
	private Long id;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 订单号
	 */
	private String orderSN;
	/**
	 * 履约次数
	 */
	private Integer performanceCount;
	/**
	 * 违约次数
	 */
	private Integer breachCount;
	/**
	 * 创建时间
	 */
	private Date createOn;
	/**
	 * 更新时间
	 */
	private Date updateOn;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getOrderSN() {
		return orderSN;
	}
	public void setOrderSN(String orderSN) {
		this.orderSN = orderSN;
	}
	public Integer getPerformanceCount() {
		return performanceCount;
	}
	public void setPerformanceCount(Integer performanceCount) {
		this.performanceCount = performanceCount;
	}
	public Integer getBreachCount() {
		
		return breachCount;
	}
	public void setBreachCount(Integer breachCount) {
		this.breachCount = breachCount;
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
	
}
