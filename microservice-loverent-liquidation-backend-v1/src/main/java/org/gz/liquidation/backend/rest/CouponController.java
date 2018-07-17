package org.gz.liquidation.backend.rest;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.JsonUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.liquidation.backend.feign.ICouponService;
import org.gz.liquidation.common.dto.coupon.CouponStatisticsResp;
import org.gz.liquidation.common.dto.coupon.CouponUseLogQueryReq;
import org.gz.liquidation.common.dto.coupon.CouponUseLogResp;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/coupon")
@Slf4j
public class CouponController extends BaseController {

	@Autowired
	private ICouponService iCouponService;
	
	@PostMapping(value = "/selectPage")
	public ResponseResult<?> selectPage(CouponUseLogQueryReq req) {
		log.info(LiquidationConstant.LOG_PREFIX+"selectPage :{}",JsonUtils.toJsonString(req));
		ResultPager<CouponUseLogResp> resultPager = iCouponService.selectPage(req);
		return ResponseResult.buildSuccessResponse(resultPager);
	}
	
	@PostMapping(value = "/statistics")
	public ResponseResult<CouponStatisticsResp> queryCouponStatistics(Integer usageScenario){
		log.info(LiquidationConstant.LOG_PREFIX+"queryCouponStatistics :{}",usageScenario);
		return iCouponService.queryCouponStatistics(usageScenario);
	}
}
