package org.gz.oss.common.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import org.gz.common.entity.QueryPager;

@Setter
@Getter
public class CouponBackageQuery extends QueryPager {
	
	private static final long serialVersionUID = 1L;
	
    /** 主键 */
    private Long id;

    /** 领券资格 10 新注册用户 20 指定发放 30 所有用户 */
    private Byte qualifications;

    /** 有效期开始时间 */
    private Date validityStartTime;

    /** 有效期结束时间 */
    private Date validityEndTime;
    
}