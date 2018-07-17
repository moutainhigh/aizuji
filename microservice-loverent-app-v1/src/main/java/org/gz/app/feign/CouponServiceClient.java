package org.gz.app.feign;

import org.gz.app.hystrix.CouponServiceHystrixImpl;
import org.gz.common.resp.ResponseResult;
import org.gz.oss.common.entity.Coupon;
import org.gz.oss.common.entity.CouponRelationQuery;
import org.gz.oss.common.entity.CouponUserQuery;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.fastjson.JSONObject;

@FeignClient(value="microservice-loverent-oss-server-v1", fallback=CouponServiceHystrixImpl.class)
public interface CouponServiceClient {

	@PostMapping(value = "/api/coupon/queryCouponList")
    public ResponseResult<?> queryCouponList(@RequestBody CouponRelationQuery crq);
	
	@PostMapping(value = "/api/coupon/useRegisterGiveCoupon")
    public ResponseResult<?> useRegisterGiveCoupon(@RequestBody JSONObject body);
	
	/**
	 * 用户使用优惠券列表
	 * @param userId
	 * @return
	 */
    @PostMapping(value = "/api/coupon/getUserCouponList")
    public ResponseResult<?> getUserCouponList(@RequestBody CouponUserQuery cuq);
    
    /**
	 * 获取用户优惠劵详情
	 * @param userId
	 * @return
	 */
    @PostMapping(value = "/api/coupon/queryCouponDetail")
    public ResponseResult<Coupon> queryCouponDetail(@RequestBody CouponUserQuery cuq);
}	
