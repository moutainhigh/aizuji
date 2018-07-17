package org.gz.liquidation.common.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 
 * @Description:TODO	计算买断金额请求DTO
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月23日 	Created
 */
public class ReadyBuyoutReq {
	@NotEmpty(message="orderSN:订单号不能为空")
	private String orderSN;
	 /**
     * 买断类型 1 正常 2 逾期
     */
	@Positive(message="type:买断类型只能为1(正常)或者2(逾期)")
	@NotNull(message="type买断类型不能为空")
	@Min(value = 1)
	@Max(value = 2)
    private Integer type;
	public String getOrderSN() {
		return orderSN;
	}

	public void setOrderSN(String orderSN) {
		this.orderSN = orderSN;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
}
