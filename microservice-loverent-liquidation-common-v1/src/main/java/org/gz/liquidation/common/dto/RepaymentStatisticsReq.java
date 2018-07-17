package org.gz.liquidation.common.dto;

import java.util.List;
/**
 * 
 * @Description:TODO	还款统计请求
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月30日 	Created
 */
public class RepaymentStatisticsReq {
	private List<String> orderSN;

	public List<String> getOrderSN() {
		return orderSN;
	}

	public void setOrderSN(List<String> orderSN) {
		this.orderSN = orderSN;
	}
	
}
