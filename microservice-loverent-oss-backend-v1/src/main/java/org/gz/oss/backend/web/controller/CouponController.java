package org.gz.oss.backend.web.controller;


import lombok.extern.slf4j.Slf4j;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.AssertUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.oss.common.entity.Coupon;
import org.gz.oss.common.entity.CouponQuery;
import org.gz.oss.common.entity.CouponRelation;
import org.gz.oss.common.entity.CouponUseDetail;
import org.gz.oss.common.entity.CouponUseDetailQuery;
import org.gz.oss.common.entity.CouponUserQuery;
import org.gz.oss.common.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 优惠券管理Controller
 */
@RestController
@RequestMapping(value = "/coupon")
@Slf4j
public class CouponController extends BaseController {

	@Autowired
	private CouponService couponService;

	/**
	 * 获取优惠劵列表
	 * @param cq
	 * @return
	 */
    @PostMapping(value = "/getCouponList")
    public ResponseResult<ResultPager<Coupon>> getCouponList(CouponQuery cq) {
    	if(cq != null){
    		try {
    			return this.couponService.getCouponList(cq);
    		} catch (Exception e) {
    			log.error("获取优惠券列表失败：{}",e.getLocalizedMessage());
                return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
    		}
    	}
    	return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
    }
    
    /**	
     * 新增优惠劵
     * @param c
     * @return
     */
    @PostMapping(value = "/addCoupon")
    public ResponseResult<?> addCoupon(@RequestBody Coupon c) {
        try {
            return this.couponService.addCoupon(c);
        } catch (Exception e) {
            log.error("优惠券新增失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
        }
    }
    
    /**	
     * 获取优惠劵编辑详情
     * @param couponId
     * @return
     */
    @GetMapping(value = "/toUpdateCoupon")
    public ResponseResult<Coupon> toUpdateCoupon(Long couponId) {
    	//验证数据 
    	if (AssertUtils.isPositiveNumber4Long(couponId)) {
	    	try {
	    		return this.couponService.toUpdateCoupon(couponId);
			} catch (Exception e) {
				log.error("获取优惠券详情失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
    	}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
    }
    
    /**	
     * 修改优惠劵
     * @param c
     * @return
     */
    @PostMapping(value = "/updateCoupon")
    public ResponseResult<?> updateCoupon(@RequestBody Coupon c) {
        try {
            return this.couponService.updateCoupon(c);
        } catch (Exception e) {
            log.error("优惠券修改失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), "", null);
        }
    }
    
    /**
     * 获取优惠券详情
     * @param couponId
     * @return
     */
    @GetMapping(value = "/queryById")
    public ResponseResult<?> queryById(Long couponId) {
    	//验证数据 
    	if (AssertUtils.isPositiveNumber4Long(couponId)) {
	        try {
	            return this.couponService.selectByPrimaryKey(couponId);
	        } catch (Exception e) {
	            log.error("查询优惠券详细失败：{}",e.getLocalizedMessage());
	            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
	        }
    	}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
    }
    
    /**
     * 获取优惠券已领取列表
     * @param crq
     * @return
     */
    @PostMapping(value = "/queryHasCouponList")
    public ResponseResult<ResultPager<CouponRelation>> queryHasCouponList(@RequestBody CouponUserQuery cuq) {
    	if(cuq != null){
    		try {
                return this.couponService.queryHasCouponList(cuq);
            } catch (Exception e) {
                log.error("获取优惠券已领取列表失败：{}",e.getLocalizedMessage());
                return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
            }
    	}
        return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
    }
    
    /**
     * 获取优惠券已使用详情列表
     * @param cudq
     * @return
     */
    @PostMapping(value = "/queryUseCouponList")
    public ResponseResult<ResultPager<CouponUseDetail>> queryUseCouponList(@RequestBody CouponUseDetailQuery cudq) {
        if(cudq != null){
        	try {
                return this.couponService.queryUseCouponList(cudq);
            } catch (Exception e) {
                log.error("获取优惠券已使用列表失败：{}",e.getLocalizedMessage());
                return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
            }
        }
        return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
    }
    
	
}
