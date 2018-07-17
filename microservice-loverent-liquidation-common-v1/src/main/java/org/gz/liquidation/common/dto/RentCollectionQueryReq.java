package org.gz.liquidation.common.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @Description:TODO	租后收款查询DTO
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年2月8日 	Created
 */
public class RentCollectionQueryReq {
	/**
	 * 订单号
	 */
	@ApiModelProperty(value = "订单号")
	private String orderSN;
	/**
	 * 姓名
	 */
	@ApiModelProperty(value = "姓名")
	private String name;
	/**
	 * 身份证
	 */
	@ApiModelProperty(value = "身份证")
	private String IdNumber;
	
	public String getOrderSN() {
		return orderSN;
	}
	public void setOrderSN(String orderSN) {
		this.orderSN = orderSN;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdNumber() {
		return IdNumber;
	}
	public void setIdNumber(String idNumber) {
		IdNumber = idNumber;
	}
	
}
