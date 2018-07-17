package org.gz.liquidation.common.dto;

import org.gz.common.entity.BaseEntity;

/**
 * 
 * @Description:TODO	还款履约统计
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月27日 	Created
 */
public class RepaymentStatisticsResp extends BaseEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2217736591096954077L;
	/**
	 * 订单编号
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
	
}
