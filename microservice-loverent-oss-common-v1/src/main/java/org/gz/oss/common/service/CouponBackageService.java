package org.gz.oss.common.service;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.oss.common.entity.CouponBackage;
import org.gz.oss.common.entity.CouponBackageQuery;

/**
 * 优惠券礼包业务接口
 *
 */
public interface CouponBackageService {

	/**
	 * 获取优惠券礼包列表
	 * @param c
	 * @return
	 */
	ResponseResult<ResultPager<CouponBackage>> getCouponBackageList(CouponBackageQuery c);
	
	/**
	 * 新建优惠券礼包
	 * @param c
	 * @return
	 */
	ResponseResult<?> addCouponBackage(CouponBackage c);
	
	/**
	 * 更新优惠券礼包
	 * @param c
	 * @return
	 */
	ResponseResult<?> updateCouponBackage(CouponBackage c);

	/**
	 * 跳转优惠券礼包编辑页面
	 * @param c
	 * @return
	 */
	ResponseResult<CouponBackage> toUpdateCouponBackage(Long backageId);
	

}
