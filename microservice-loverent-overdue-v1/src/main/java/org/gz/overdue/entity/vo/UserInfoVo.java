package org.gz.overdue.entity.vo;

import javax.validation.constraints.NotNull;

import org.gz.common.entity.QueryPager;

public class UserInfoVo extends QueryPager{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7959988904676829980L;
	/**
	 * 订单编号
	 */
	@NotNull(message = "用户ID不能为null")
	private Long userId;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
