package org.gz.oss.common.entity;

import lombok.Getter;
import lombok.Setter;

import org.gz.common.entity.BaseEntity;

@Setter
@Getter
public class CouponUseParam extends BaseEntity{
	
	
	private static final long serialVersionUID = -3141225371575983186L;

	/** 优惠券id */
    private Long couponId;

    /** 用户id */
    private Long userId;
    
    /** 用户名 */
    private String userName;
    
    /** 订单id */
    private Long orderId;
    
    /** 订单编号 */
    private String orderNo;
    
}