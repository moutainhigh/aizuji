package org.gz.oss.common.entity;

import lombok.Getter;
import lombok.Setter;

import org.gz.common.entity.QueryPager;

@Setter
@Getter
public class CouponRelationQuery extends QueryPager{
	
	private static final long serialVersionUID = 1L;
	
	/** 主键id */
    private Long id;

    /** 优惠券是否使用  0 未使用  1 使用 */
    private String couponStatus;
    
    /** 领取时间 */
    private String hasTimeStatus;
    
    /** 用户id */
    private Long userId;

}
