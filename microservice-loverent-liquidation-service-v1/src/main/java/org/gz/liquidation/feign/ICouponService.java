package org.gz.liquidation.feign;

import org.gz.common.resp.ResponseResult;
import org.gz.oss.common.entity.Coupon;
import org.gz.oss.common.entity.CouponQuery;
import org.gz.oss.common.entity.CouponUseParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 
 * @Description:优惠券服务FeignClient
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月26日 	Created
 */
@FeignClient(value = "microservice-loverent-oss-server-v1",fallback=ICouponServiceFallBack.class)
public interface ICouponService {

   /**
    * 
    * @Description: 确认使用优惠
    * @param couponUseParam
    * @return
    * @throws
    * createBy:liaoqingji            
    * createDate:2018年3月26日
    */
   @PostMapping(value="/api/coupon/useCoupon" ,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   ResponseResult<String>  useCoupon(@RequestBody CouponUseParam couponUseParam);
   /**
    * 
    * @Description: 查询优惠券信息
    * @param cq
    * @return
    * @throws
    * createBy:liaoqingji            
    * createDate:2018年3月26日
    */
   @PostMapping(value = "/api/coupon/queryCouponDetail",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   ResponseResult<Coupon> queryCouponDetail(@RequestBody CouponQuery cq);
   /**
    * 
    * @Description: 退回优惠券
    * @param cup
    * @return
    * @throws
    * createBy:liaoqingji            
    * createDate:2018年3月28日
    */
   @PostMapping(value = "/useReturnCoupon",consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
   ResponseResult<?> useReturnCoupon(@RequestBody CouponUseParam cup);
}


@Component
class ICouponServiceFallBack implements ICouponService {
	@Override
	public ResponseResult<String> useCoupon(CouponUseParam couponUseParam) {
		return defaultBkMethod();
	}

	private ResponseResult<String> defaultBkMethod() {
        return ResponseResult.build(1000, "microservice-loverent-oss-server-v1", null);
    }

	@Override
	public ResponseResult<Coupon> queryCouponDetail(CouponQuery cq) {
		return ResponseResult.build(1000, "microservice-loverent-oss-server-v1", null);
	}

	@Override
	public ResponseResult<?> useReturnCoupon(CouponUseParam cup) {
		return ResponseResult.build(1000, "microservice-loverent-oss-server-v1", null);
	}

}