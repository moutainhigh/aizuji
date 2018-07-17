package org.gz.oss.server.web.controller;


import lombok.extern.slf4j.Slf4j;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.JsonUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.oss.common.entity.Coupon;
import org.gz.oss.common.entity.CouponQuery;
import org.gz.oss.common.entity.CouponUseParam;
import org.gz.oss.common.entity.CouponUserQuery;
import org.gz.oss.common.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;


/**
 * 优惠券管理Controller
 */
@RestController
@RequestMapping(value = "/api/coupon")
@Slf4j
public class CouponApiController extends BaseController {

	@Autowired
	private CouponService couponService;
	
	
	/**	
     * 优惠劵用户使用
     * @param cud
     * @return
     */
    @PostMapping(value = "/useCoupon")
    public ResponseResult<?> useCoupon(@RequestBody CouponUseParam cup) {
        try {
        	log.info(">>>>>>>>>>>>>>>>>> useCoupon cup:{}",JsonUtils.toJsonString(cup));
            return this.couponService.useCoupon(cup);
        } catch (Exception e) {
            log.error("优惠劵用户使用异常：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
        }
    }
    
    /**	
     * 新注册用户送优惠劵
     * @param userId
     * @return
     */
    @PostMapping(value = "/useRegisterGiveCoupon")
    public ResponseResult<?> useRegisterGiveCoupon(@RequestBody JSONObject body) {
        try {
        	CouponUseParam cup = new CouponUseParam();
        	cup.setUserId(body.getLong("userId"));
        	cup.setUserName(body.getString("phoneNum"));
        	
            return this.couponService.useRegisterGiveCoupon(cup);
        } catch (Exception e) {
            log.error("新注册用户送优惠劵异常：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
        }
    }
    
    /**
	 * 我的优惠券使用情况列表
	 * @param userId
	 * @return
	 */
    @PostMapping(value = "/queryCouponList")
    public ResponseResult<ResultPager<Coupon>> queryCouponList(@RequestBody CouponUserQuery cuq) {
		try {
			return this.couponService.queryCouponList(cuq);
		} catch (Exception e) {
			log.error("获取用户优惠券列表失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
		}
    }
    
    /**
	 * 获取用户优惠劵详情
	 * @param cq
	 * @return
	 */
    @PostMapping(value = "/queryCouponDetail")
    public ResponseResult<Coupon> queryCouponDetail(@RequestBody CouponQuery cq) {
		try {
			return this.couponService.queryCouponDetail(cq);
		} catch (Exception e) {
			log.error("获取用户优惠劵详情失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
		}
    }
    
    /**
	 * 用户使用优惠券列表
	 * @param userId
	 * @return
	 */
    @PostMapping(value = "/getUserCouponList")
    public ResponseResult<ResultPager<Coupon>> getUserCouponList(@RequestBody CouponUserQuery cuq) {
		try {
			return this.couponService.getUserCouponList(cuq);
		} catch (Exception e) {
			log.error("获取用户优惠券列表失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
		}
    }
	
    /**	
     * 用户优惠劵退还
     * @param cup
     * @return
     */
    @PostMapping(value = "/useReturnCoupon")
    public ResponseResult<?> useReturnCoupon(@RequestBody CouponUseParam cup) {
        try {
            return this.couponService.useReturnCoupon(cup);
        } catch (Exception e) {
            log.error("优惠劵用户使用异常：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
        }
    }
}
