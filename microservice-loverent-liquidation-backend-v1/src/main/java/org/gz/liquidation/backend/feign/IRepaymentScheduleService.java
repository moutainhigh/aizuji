package org.gz.liquidation.backend.feign;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.dto.RepaymentScheduleResp;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 
 * @Description:还款计划服务FeignClient
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月2日 	Created
 */
@FeignClient(value = "microservice-loverent-liquidation-service-v1",configuration=org.gz.liquidation.common.config.LiquidationFeignConfig.class)
public interface IRepaymentScheduleService {

	/**
	 * 
	 * @Description: 查询还款计划
	 * @param orderSN 订单号
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月2日
	 */
	@PostMapping(value = "/repayment/rentSchedule/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<List<RepaymentScheduleResp>> rentRepaymentSchedule(@PathVariable("orderSN") String orderSN);
	
	/**
	 * 
	 * @Description: 查询已还滞纳金和未还滞纳金
	 * @param orderSN
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月5日
	 */
	@PostMapping(value = "/repayment/lateFees/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<Map<String,BigDecimal>> selectLateFees(@PathVariable("orderSN") String orderSN);
}
