package org.gz.app.feign;

import java.util.List;

import javax.validation.Valid;

import org.gz.app.hystrix.RepaymentServiceHystirxImpl;
import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.dto.DuesDetailResp;
import org.gz.liquidation.common.dto.RepaymentDetailResp;
import org.gz.liquidation.common.dto.SettleDetailResp;
import org.gz.liquidation.common.dto.repayment.ZmRepaymentScheduleResp;
import org.gz.liquidation.common.dto.repayment.ZmStatementDetailResp;
import org.gz.liquidation.common.entity.ReadyBuyoutReq;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value="microservice-loverent-liquidation-service-v1", fallback=RepaymentServiceHystirxImpl.class)
public interface RepaymentServiceClient {

	/**
	 * 查询还款计划
	 * @param orderSN
	 * @return
	 */
	@PostMapping(value = "/repayment/repaymentSchedule/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseResult<?> loadRepayPlanList(@PathVariable("orderSN") String orderSN);
	
	/**
	 * 计算买断支付金额
	 * @param readyBuyoutReq
	 * @param bindingResult
	 * @return
	 */
	@PostMapping(value = "/sign/readyBuyout", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> readyBuyout(@Valid @RequestBody ReadyBuyoutReq readyBuyoutReq);
	
	/**
	 * 查询买断信息
	 * @param orderSN
	 * @return
	 */
	@PostMapping(value = "/repayment/buyout/orderDetail/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<RepaymentDetailResp> queryBuyoutOrderDetail(@PathVariable("orderSN") String orderSN);
	
	/**
	 * 租金计算
	 * @param orderSN
	 * @return
	 */
	@PostMapping(value = "/repayment/repayRent/detail/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<DuesDetailResp> repayRentDetail(@PathVariable("orderSN") String orderSN);
	
	/**
	 * APP结清支付详情查询接口
	 * @param orderSN
	 * @return
	 */
	@PostMapping(value = "/repayment/settle/detail/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<SettleDetailResp> settleDetail(@PathVariable("orderSN") String orderSN);
	
	/**
	 * 查询芝麻订单还款计划
	 * @param req
	 * @return
	 */
	@PostMapping(value = "/repayment/zmOrder/repaymentSchedule/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<List<ZmRepaymentScheduleResp>> zmRepaymentSchedule(@PathVariable("orderSN") String orderSN);
	
	/**
	 * 查询芝麻订单账单详情
	 * @param orderSN
	 * @return
	 */
	@PostMapping(value = "/repayment/zmOrder/statementDetail/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<ZmStatementDetailResp> queryZmStatementDetail(@PathVariable("orderSN") String orderSN);
}
