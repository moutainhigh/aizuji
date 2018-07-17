package org.gz.oss.common.service;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.oss.common.entity.Coupon;
import org.gz.oss.common.entity.CouponQuery;
import org.gz.oss.common.entity.CouponRelation;
import org.gz.oss.common.entity.CouponRelationQuery;
import org.gz.oss.common.entity.CouponUseDetail;
import org.gz.oss.common.entity.CouponUseDetailQuery;
import org.gz.oss.common.entity.CouponUseParam;
import org.gz.oss.common.entity.CouponUserQuery;


/**
 * 优惠券业务接口
 *
 */
public interface CouponService {

	/**
	 * 获取优惠券列表
	 * @param c
	 * @return
	 */
	ResponseResult<ResultPager<Coupon>> getCouponList(CouponQuery c);

	/**
	 * 获取优惠券详细
	 * @param couponId
	 * @return
	 */
	ResponseResult<?> selectByPrimaryKey(Long couponId);

	/**
     * 获取优惠券已领取列表
     * @param cr
     * @return
     */
	ResponseResult<ResultPager<CouponRelation>> queryHasCouponList(CouponUserQuery crq);

	/**
     * 获取优惠券已使用详情列表
     * @param cud
     * @return
     */
	ResponseResult<ResultPager<CouponUseDetail>> queryUseCouponList(CouponUseDetailQuery cudq);

	/**	
     * 优惠劵使用
     * @param cup
     * @return
     */
	ResponseResult<?> useCoupon(CouponUseParam cup);

	/**
	 * 新建优惠券
	 * @param c
	 * @return
	 */
	ResponseResult<?> addCoupon(Coupon c);
	
	/**
	 * 更新优惠券
	 * @param c
	 * @return
	 */
	ResponseResult<?> updateCoupon(Coupon c);

	/**
	 * 跳转优惠券编辑页面
	 * @param c
	 * @return
	 */
	ResponseResult<Coupon> toUpdateCoupon(Long couponId);

	/**	
     * 新注册用户送优惠劵
     * @param cup
     * @return
     */
	ResponseResult<?> useRegisterGiveCoupon(CouponUseParam cup);

	/**
	 * 我的优惠券使用情况列表
	 * @param userId
	 * @return
	 */
	ResponseResult<ResultPager<Coupon>> queryCouponList(CouponUserQuery cuq);

	/**
	 * 获取用户优惠劵详情
	 * @param cq
	 * @return
	 */
	ResponseResult<Coupon> queryCouponDetail(CouponQuery cq);

	/**
	 * 用户使用优惠券列表
	 * @param cq
	 * @return
	 */
	ResponseResult<ResultPager<Coupon>> getUserCouponList(CouponUserQuery cuq);

	/**	
     * 用户优惠劵退还
     * @param cup
     * @return
     */
	ResponseResult<?> useReturnCoupon(CouponUseParam cup);

}
