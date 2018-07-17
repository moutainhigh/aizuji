package org.gz.oss.common.entity;

import lombok.Getter;
import lombok.Setter;

import org.gz.common.entity.QueryPager;

@Setter
@Getter
public class CouponQuery extends QueryPager{
	
	
	private static final long serialVersionUID = -3141225371575983186L;

	/** 优惠券id */
    private Long couponId;

    /** 优惠券名称 */
    private String name;

    /** 优惠券礼包中的优惠券是否使用  0 未使用  1 使用 */
    private Byte status;

    /** 优惠券类型  10 通用  20 售卖 30 租赁-买断 31 租赁-首期款 32 租赁-交租 */
    private Byte couponType;
    
    /** 优惠券状态  10 待生效  20 已生效   30 已过期*/ 
    private Byte couponStatus;
    
    /** 用户id */
    private Long userId;
    
}