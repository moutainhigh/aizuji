package org.gz.liquidation.common.dto;

import org.gz.common.entity.QueryPager;

/**
 * 
 * @Description:TODO	订单查询dto
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年1月19日 	Created
 */
public class AfterRentOrderDetailReq extends QueryPager {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8865029490716069652L;
	/**
	 * 订单号
	 */
	private String orderSN;
	/**
	 * 姓名
	 */
	private String realName;
	/**
	 * 身份证
	 */
	private String identityCard;
	
	public String getOrderSN() {
		return orderSN;
	}
	public void setOrderSN(String orderSN) {
		this.orderSN = orderSN;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	
}
