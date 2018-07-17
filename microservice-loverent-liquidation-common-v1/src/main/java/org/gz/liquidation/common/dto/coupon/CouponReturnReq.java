package org.gz.liquidation.common.dto.coupon;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @Description:退还优惠券请求dto
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月27日 	Created
 */
@Setter @Getter
public class CouponReturnReq {
	@ApiModelProperty(value = "订单号不能为空", required = true)
	@NotEmpty(message="订单号不能为空")
	private String orderSN;
	@ApiModelProperty(value = "优惠券id不能为空", required = true)
	@Positive(message="优惠券id必须是正数")
	private Long couponId;
}
