package org.gz.app.hystrix;

import org.gz.app.feign.CouponServiceClient;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.gz.oss.common.entity.Coupon;
import org.gz.oss.common.entity.CouponRelationQuery;
import org.gz.oss.common.entity.CouponUserQuery;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

@Component
public class CouponServiceHystrixImpl implements CouponServiceClient {

	@Override
	public ResponseResult<?> queryCouponList(CouponRelationQuery crq) {
		return buildDefaultFallback();
	}

	@Override
	public ResponseResult<?> useRegisterGiveCoupon(JSONObject body) {
		return buildDefaultFallback();
	}
	
	private ResponseResult<?> buildDefaultFallback() {
		ResponseResult<?> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}
	
	@Override
	public ResponseResult<Coupon> queryCouponDetail(CouponUserQuery cuq) {
		ResponseResult<Coupon> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<?> getUserCouponList(
			CouponUserQuery cuq) {
		return buildDefaultFallback();
	}

}
