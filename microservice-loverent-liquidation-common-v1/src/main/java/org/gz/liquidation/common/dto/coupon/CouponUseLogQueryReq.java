package org.gz.liquidation.common.dto.coupon;

import java.util.Date;

import org.gz.common.entity.QueryPager;

import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @Description:优惠券场景请求DTO
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年4月3日 	Created
 */
@Setter @Getter
public class CouponUseLogQueryReq extends QueryPager{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orderSN;
	private String phone;
	/**
	 * 开始时间
	 */
	private Date startDate;
	/**
	 * 结束时间
	 */
	private Date endDate;
	/**
	 * 使用场景
	 */
	private Byte usageScenario;
}
 