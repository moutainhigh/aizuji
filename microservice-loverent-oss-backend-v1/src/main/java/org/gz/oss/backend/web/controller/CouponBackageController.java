package org.gz.oss.backend.web.controller;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.AssertUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.oss.common.entity.CouponBackage;
import org.gz.oss.common.entity.CouponBackageQuery;
import org.gz.oss.common.service.CouponBackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 优惠券礼包管理Controller
 */
@RestController
@RequestMapping(value = "/couponBackage")
@Slf4j
public class CouponBackageController extends BaseController {

	@Autowired
	private CouponBackageService couponBackageService;
	
	/**
	 * 获取优惠劵礼包列表
	 * @param c
	 * @return
	 */
    @PostMapping(value = "/getCouponBackageList")
    public ResponseResult<ResultPager<CouponBackage>> getCouponBackageList(CouponBackageQuery c) {
    	if(c != null){
    		try {
    			return this.couponBackageService.getCouponBackageList(c);
    		} catch (Exception e) {
    			log.error("获取优惠券礼包列表失败：{}",e.getLocalizedMessage());
                return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
    		}    		
    	}
    	return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
    }
    
    /**	
     * 新增优惠劵优惠券礼包
     * @param c
     * @return
     */
    @PostMapping(value = "/addCoupoBackage")
    public ResponseResult<?> addCouponBackage(CouponBackage c) {
        try {
            return this.couponBackageService.addCouponBackage(c);
        } catch (Exception e) {
            log.error("优惠券礼包新增失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
        }
    }
    
    /**	
     * 获取优惠券礼包编辑详情
     * @param couponId
     * @return
     */
    @GetMapping(value = "/toUpdateCouponBackage/{backageId}")
    public ResponseResult<CouponBackage> toUpdateCoupon(@PathVariable Long backageId) {
    	//验证数据 
    	if (AssertUtils.isPositiveNumber4Long(backageId)) {
    		try {
        		return this.couponBackageService.toUpdateCouponBackage(backageId);
    		} catch (Exception e) {
    			log.error("获取优惠券礼包详情失败：{}",e.getLocalizedMessage());
    			return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
    		}
    	}
    	return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);    	 
    }
    
    /**	
     * 更新优惠券礼包
     * @param c
     * @return
     */
    @PostMapping(value = "/updateCouponBackage")
    public ResponseResult<?> updateCouponBackage(@Valid @RequestBody CouponBackage c) {
        try {
            return this.couponBackageService.updateCouponBackage(c);
        } catch (Exception e) {
            log.error("优惠券礼包修改失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
        }
    }
    
}
	