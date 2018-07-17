package org.gz.oss.common.entity;

import lombok.Getter;
import lombok.Setter;

import org.gz.common.entity.QueryPager;

@Setter
@Getter
public class CouponUseDetailQuery extends QueryPager {
	
	private static final long serialVersionUID = 1L;

	/** 主键 */
    private Long couponId;

    /** 领取时间状态 0 今天 1 近一周 2 近一月*/
    private String hasTimeStatus;

    /** 消费类型 1:租赁 2:以租代购 3:出售*/
    private String consumeType;
	
}
