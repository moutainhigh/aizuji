package org.gz.liquidation.common.dto.coupon;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @Description:优惠券统计	
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年4月4日 	Created
 */
@Setter @Getter
public class CouponStatisticsResp {
	/**
	 * 用券总额
	 */
	private BigDecimal totalAmount;
	/**
	 * 总额
	 */
	private BigDecimal amount;
	/**
	 * 退还总额
	 */
	private BigDecimal returnAmount;
}
