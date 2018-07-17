package org.gz.liquidation.backend.feign;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.dto.coupon.CouponStatisticsResp;
import org.gz.liquidation.common.dto.coupon.CouponUseLogQueryReq;
import org.gz.liquidation.common.dto.coupon.CouponUseLogResp;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * 
 * @Description:优惠券服务FeignClient
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年4月4日 	Created
 */
@FeignClient(value = "microservice-loverent-liquidation-service-v1",
configuration=org.gz.liquidation.common.config.LiquidationFeignConfig.class)
public interface ICouponService {
	/**
	 * 
	 * @Description: 优惠券使用记录分页查询
	 * @param req
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年4月4日
	 */
	@PostMapping(value = "/coupon/selectPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResultPager<CouponUseLogResp> selectPage(@RequestBody CouponUseLogQueryReq req);
	/**
	 * 
	 * @Description: 优惠券使用统计
	 * @param usageScenario
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年4月4日
	 */
	@PostMapping(value = "/coupon/statistics")
	public ResponseResult<CouponStatisticsResp> queryCouponStatistics(@RequestParam("usageScenario") Integer usageScenario);
}
