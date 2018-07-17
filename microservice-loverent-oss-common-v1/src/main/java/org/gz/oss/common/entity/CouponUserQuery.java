package org.gz.oss.common.entity;

import lombok.Getter;
import lombok.Setter;

import org.gz.common.entity.QueryPager;

@Setter
@Getter
public class CouponUserQuery extends QueryPager{
	
	private static final long serialVersionUID = 1L;
	
    /** 优惠券id */
    private Long couponId;

    /** 优惠券是否使用  0 未使用  1 使用 */
    private String couponStatus;
    
    /** 用户id */
    private Long userId;
    
    /** 优惠券类型  10 通用  20 售卖 30 租赁-买断 31 租赁-首期款  32 租赁-交租 */
    private Byte couponType;
    
    /** 型号id */
    private Long modelId;
    
    /** 产品id */
    private Long productId;
    
    /** 领取时间状态  0 今天 1 近一周 2 近一月 */
    private Integer hasTimeStatus;
    

}
