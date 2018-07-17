package org.gz.user.feign;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.fastjson.JSONObject;

@FeignClient(value="microservice-loverent-oss-server-v1", fallback=CouponServiceHystrixImpl.class)
public interface CouponServiceClient {

	@PostMapping(value = "/api/coupon/useRegisterGiveCoupon")
    public ResponseResult<?> useRegisterGiveCoupon(@RequestBody JSONObject body);
}	

@Component
class CouponServiceHystrixImpl implements CouponServiceClient {

	
	private ResponseResult<?> buildDefaultFallback() {
		ResponseResult<?> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<?> useRegisterGiveCoupon(JSONObject body) {
		return buildDefaultFallback();
	}
}