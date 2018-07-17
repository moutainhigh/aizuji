package org.gz.app.web.controller;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.feign.RepaymentServiceClient;
import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.entity.ReadyBuyoutReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/repayment")
@Slf4j
public class RepaymentController extends AppBaseController {

	@Autowired
	private RepaymentServiceClient repaymentServiceClient;
	
	/**
	 * 获取订单还款计划
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/loadRepayPlanList", method=RequestMethod.POST)
	@ResponseBody
    public ResponseResult<?> loadRepayPlanList(@RequestBody JSONObject body) {
		log.info("start execute loadRepayPlanList");
		
		ResponseResult<?> result = repaymentServiceClient.loadRepayPlanList(body.getString("orderNo"));
		
		log.info("execute loadRepayPlanList success");
		
		return result;
	}
	
	/**
	 * 计算买断金额
	 */
	@RequestMapping(value="/readyBuyout", method=RequestMethod.POST)
	@ResponseBody
    public ResponseResult<?> readyBuyout(@RequestBody JSONObject body) {
		log.info("start execute readyBuyout, params: {}", body.toJSONString());
		
		ReadyBuyoutReq readyBuyoutReq = new ReadyBuyoutReq();
		readyBuyoutReq.setOrderSN(body.getString("orderNo"));
		readyBuyoutReq.setType(body.getInteger("type"));
		ResponseResult<?> result = repaymentServiceClient.readyBuyout(readyBuyoutReq);
		
		log.info("execute loadRepayPlanList success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	/**
	 * 查询订单还款情况
	 */
	@RequestMapping(value="/queryOrderRePayDetail", method=RequestMethod.POST)
	@ResponseBody
    public ResponseResult<?> queryOrderRePayDetail(@RequestBody JSONObject body) {
		log.info("start execute queryOrderRePayDetail, params: {}", body.toJSONString());
		
		ResponseResult<?> result = repaymentServiceClient.queryBuyoutOrderDetail(body.getString("orderNo"));
		
		log.info("execute queryOrderRePayDetail success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	/**
	 * 偿还租金详情
	 */
	@RequestMapping(value="/queryRepayRentDetail", method=RequestMethod.POST)
	@ResponseBody
    public ResponseResult<?> queryRepayRentDetail(@RequestBody JSONObject body) {
		log.info("start execute queryRepayRentDetail, params: {}", body.toJSONString());
		
		ResponseResult<?> result = repaymentServiceClient.repayRentDetail(body.getString("orderNo"));
		
		log.info("execute queryRepayRentDetail success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	/**
	 * APP结清支付详情查询接口
	 */
	@RequestMapping(value="/settleDetail", method=RequestMethod.POST)
	@ResponseBody
    public ResponseResult<?> settleDetail(@RequestBody JSONObject body) {
		log.info("start execute settleDetail, params: {}", body.toJSONString());
		
		ResponseResult<?> result = repaymentServiceClient.settleDetail(body.getString("orderNo"));
		
		log.info("execute settleDetail success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
}
