package org.gz.liquidation.web.controller.coupon;

import javax.annotation.Resource;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.JsonUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.liquidation.common.Page;
import org.gz.liquidation.common.dto.QueryDto;
import org.gz.liquidation.common.dto.coupon.CouponReturnReq;
import org.gz.liquidation.common.dto.coupon.CouponStatisticsResp;
import org.gz.liquidation.common.dto.coupon.CouponUseLogQueryReq;
import org.gz.liquidation.common.dto.coupon.CouponUseLogResp;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.liquidation.service.coupon.CouponService;
import org.gz.liquidation.service.coupon.CouponUseLogService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @Description:优惠券控制器
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月27日 	Created
 */
@RestController
@RequestMapping("/coupon")
@Slf4j
public class CouponController extends BaseController {

	@Resource
	private CouponService couponService;
	@Resource
	private CouponUseLogService couponUseLogService;
	
	@ApiOperation(value = "优惠券退回()", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 预支付订单号 data:2018011015233388400000",
            response = ResponseResult.class)
	@PostMapping(value = "/returnCoupon", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> returnCoupon(@RequestBody CouponReturnReq couponReturnReq){
		log.info(LiquidationConstant.LOG_PREFIX+"returnCoupon :{}",JsonUtils.toJsonString(couponReturnReq));
		return couponService.returnCoupon(couponReturnReq);
	}
	
	@ApiOperation(value = "优惠券使用记录分页查询(清算后台系统调用)", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/selectPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResultPager<CouponUseLogResp> selectPage(@RequestBody CouponUseLogQueryReq req) {
		log.info(LiquidationConstant.LOG_PREFIX+"selectPage :{}",JsonUtils.toJsonString(req));
		QueryDto queryDto = new QueryDto();
		Page page = new Page();
		page.setStart(req.getCurrPage());
		page.setPageSize(req.getPageSize());
		queryDto.setPage(page);
		queryDto.setQueryConditions(req);
		return couponUseLogService.selectPage(queryDto);
	}
	
	@ApiOperation(value = "优惠券使用统计(清算后台系统调用)", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/statistics")
	public ResponseResult<CouponStatisticsResp> queryCouponStatistics(@RequestParam("usageScenario") Integer usageScenario){
		log.info(LiquidationConstant.LOG_PREFIX+"queryCouponStatistics :{}",usageScenario);
		return couponUseLogService.queryCouponStatistics(usageScenario);
		
	}
}
