package org.gz.overdue.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.Page;
import org.gz.liquidation.common.dto.LateFeeRemissionReq;
import org.gz.liquidation.common.dto.RepaymentReq;
import org.gz.liquidation.common.dto.RepaymentScheduleResp;
import org.gz.overdue.config.OverdueFeignConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "microservice-loverent-liquidation-service-v1",configuration=OverdueFeignConfig.class, fallback = ILiquidationServiceFallBack.class)
public interface LiquidationService {
	/**
	 * 分页查询逾期数据(供租后系统调用)
	 * @param page
	 * @return
	 */
	@PostMapping(value = "/repayment/queryPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<ResultPager<RepaymentScheduleResp>> queryPage(@RequestBody Page page);
	
	/**
	 * 
	 * @Description: TODO 根据订单查询还款计划
	 * @param orderSN	订单号
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月3日
	 */
	@PostMapping(value = "/repayment/queryRepaymentList/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<List<RepaymentScheduleResp>> queryRepaymentList(@PathVariable("orderSN") String orderSN);
	
	/**
	 * 查询应还金额
	 * @param repaymentReq
	 * @return
	 */
	@PostMapping(value = "/repayment/queryRepaymentAmount", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<BigDecimal> queryRepaymentAmount(@RequestBody RepaymentReq repaymentReq);
	 
	/**
	 * 查询滞纳金总额和应还滞纳金
	 * @param orderSN
	 * @return
	 */
	@PostMapping(value = "/repayment/lateFees/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<Map<String,BigDecimal>> selectLateFees(@PathVariable("orderSN") String orderSN);
	
	/**
	 * 滞纳金减免
	 * @param lateFeeRemissionReq
	 * @return
	 */
	@PostMapping(value = "/remissionLog/lateFee/remission", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> doLateFeeRemission(@RequestBody LateFeeRemissionReq lateFeeRemissionReq);
}
@Component
class ILiquidationServiceFallBack implements LiquidationService {

	@Override
	public ResponseResult<ResultPager<RepaymentScheduleResp>> queryPage(Page page) {
		return ResponseResult.build(1000, "microservice-loverent-liquidation-v1 /repayment/queryPage 系统异常，已回退", null);
	}
	
	@Override
	public ResponseResult<List<RepaymentScheduleResp>> queryRepaymentList(String orderSN) {
		return ResponseResult.build(1000, "microservice-loverent-liquidation-v1 /repayment/queryRepaymentList/{orderSN} 系统异常，已回退", null);
	}
	
	@Override
	public ResponseResult<BigDecimal> queryRepaymentAmount(RepaymentReq repaymentReq) {
		return ResponseResult.build(1000, "microservice-loverent-liquidation-v1 /repayment/queryRepaymentAmount 系统异常，已回退", null);
	}

	@Override
	public ResponseResult<Map<String, BigDecimal>> selectLateFees(String orderSN) {
		return ResponseResult.build(1000, "microservice-loverent-liquidation-v1 /repayment/lateFees/{orderSN} 系统异常，已回退", null);
	}
	
	@Override
	public ResponseResult<?> doLateFeeRemission(LateFeeRemissionReq lateFeeRemissionReq) {
		return ResponseResult.build(1000, "microservice-loverent-liquidation-v1 /remissionLog/lateFee 系统异常，已回退", null);
	}

}