package org.gz.app.web.controller;

import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.gz.aliOrder.dto.AddOrderReq;
import org.gz.aliOrder.dto.AddOrderResp;
import org.gz.aliOrder.dto.OrderDetailReq;
import org.gz.aliOrder.dto.OrderDetailResp;
import org.gz.aliOrder.dto.SubmitOrderReq;
import org.gz.aliOrder.dto.UpdateCreditAmountReq;
import org.gz.aliOrder.dto.UpdateOrderStateReq;
import org.gz.aliOrder.hystrix.IAliOrderService;
import org.gz.app.feign.RepaymentServiceClient;
import org.gz.app.supports.ZhimaOrderUtils;
import org.gz.app.vo.ZhimaOrderDetailVo;
import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.dto.repayment.ZmRepaymentScheduleResp;
import org.gz.liquidation.common.dto.repayment.ZmStatementDetailResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

/**
 * 小程序
 * 
 * @author yangdx
 *
 */
@RestController
@RequestMapping("/xcxOrder")
@Slf4j
public class XcxController {

	/**
	 * 小程序订单service
	 */
	@Autowired IAliOrderService aliOrderService;
	
	@Autowired
	private RepaymentServiceClient repaymentServiceClient;
	
	/**
	 * 添加订单
	 * @param addOrderRequest
	 * @return
	 */
	@RequestMapping(value="/addOrder", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<AddOrderResp> addOrder(@RequestBody AddOrderReq addOrderRequest) {
		log.info("start execute addOrder...");
		
		ResponseResult<AddOrderResp> result = aliOrderService.addOrder(addOrderRequest);
		
		log.info("execute addOrder success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	/**
	 * 订单创建成功异步通知更新信用免押积分和芝麻单号
	 * @param updateCreditAmountReq
	 * @return
	 */
	/*@RequestMapping(value="/updateCreditAmount", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<String> updateCreditAmount(@RequestBody UpdateCreditAmountReq updateCreditAmountReq) {
		log.info("start execute updateCreditAmount...");
		
		ResponseResult<String> result = aliOrderService.updateCreditAmount(updateCreditAmountReq);
		
		log.info("execute updateCreditAmount success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}*/
	
	/**
	 * 确认提交订单将订单状态改为待支付
	 * @param submitOrderReq
	 * @return
	 */
	@RequestMapping(value="/submitOrder", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<String> submitOrder(@RequestBody SubmitOrderReq submitOrderReq) {
		log.info("start execute submitOrder, params: {}", JSONObject.toJSONString(submitOrderReq));
		
		ResponseResult<String> result = aliOrderService.submitOrder(submitOrderReq);
		
		log.info("execute submitOrder success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	/**
	 * 取消订单
	 * @param updateOrderStateReq
	 * @return
	 */
	@RequestMapping(value="/cancleOrder", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<String> cancleOrder(@RequestBody JSONObject body) {
		log.info("start execute cancleOrder, params: {}", body.toJSONString());
		
		UpdateOrderStateReq updateOrderStateReq = new UpdateOrderStateReq();
		updateOrderStateReq.setRentRecordNo(body.getString("rentRecordNo"));
		updateOrderStateReq.setCallType(1);
		updateOrderStateReq.setRemark(body.getString("remark"));
		
		ResponseResult<String> result = aliOrderService.cancleOrder(updateOrderStateReq);
		
		log.info("execute cancleOrder success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	/**
	 * 确认签收
	 * @param updateOrderStateReq
	 * @return
	 */
	@RequestMapping(value="/signedOrder", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<String> signedOrder(@RequestBody UpdateOrderStateReq updateOrderStateReq) {
		log.info("start execute signedOrder, params: {}", JSONObject.toJSONString(updateOrderStateReq));
		
		ResponseResult<String> result = aliOrderService.signedOrder(updateOrderStateReq);
		
		log.info("execute signedOrder success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	/**
	 * 芝麻订单详情查询
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/zhimaOrderDetail", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<ZhimaOrderDetailVo> zhimaOrderDetail(@RequestBody JSONObject body) {
		log.info("start execute zhimaOrderDetail, params: {}", body.toJSONString());
		ResponseResult<ZhimaOrderDetailVo> result = new ResponseResult<ZhimaOrderDetailVo>();
		
		ZhimaOrderDetailVo vo = ZhimaOrderUtils.queryZhimaOrderDetail(body.getString("orderNo"));
		
		if (vo != null) {
			log.info("execute zhimaOrderDetail success, result: {}", JSONObject.toJSONString(vo));
		}
		result.setData(vo);
		
		return result;
	}
	
	/**
	 * 查询爱租机订单详情
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/queryOrderDetail", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<OrderDetailResp> queryOrderDetail(@RequestBody JSONObject body) {
		log.info("start execute queryOrderDetail, params: {}", body.toJSONString());
		
		ResponseResult<OrderDetailResp> result = aliOrderService.queryOrderDetail((body.getString("orderNo")));
		
		log.info("execute queryOrderDetail success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	/**
	 * 查询爱租机订单详情
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/queryOrderStateList", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<List<OrderDetailResp>> queryOrderStateList(@RequestBody OrderDetailReq orderDetailReq) {
		log.info("start execute queryOrderStateList, params: {}", JSONObject.toJSONString(orderDetailReq));
		
		ResponseResult<List<OrderDetailResp>> result = aliOrderService.queryOrderStateList(orderDetailReq);
		
		log.info("execute queryOrderStateList success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	/**
	 * 生产芝麻订单还款计划
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/zmRepaymentSchedule", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<List<ZmRepaymentScheduleResp>> zmRepaymentSchedule(@RequestBody JSONObject body) {
		log.info("start execute zmRepaymentSchedule, params: {}", body.toJSONString());
		
		ResponseResult<List<ZmRepaymentScheduleResp>> result = repaymentServiceClient.zmRepaymentSchedule(body.getString("orderNo"));
		
		log.info("execute zmRepaymentSchedule success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	/**
	 * 查询芝麻订单账单详情
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/queryZmStatementDetail", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<ZmStatementDetailResp> queryZmStatementDetail(@RequestBody JSONObject body) {
		log.info("start execute queryZmStatementDetail, params: {}", body.toJSONString());
		
		ResponseResult<ZmStatementDetailResp> result = repaymentServiceClient.queryZmStatementDetail(body.getString("orderNo"));
		
		log.info("execute queryZmStatementDetail success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
	
	/**
	 * 查询租赁合同信息
	 * @param rentRecordNo
	 * @return
	 */
	@RequestMapping(value="/lookContract", method=RequestMethod.POST)
	public ResponseResult<Map<String, Object>> lookContract(@RequestBody JSONObject body) {
		log.info("start execute lookContract, params: {}", body.toJSONString());
		
		ResponseResult<Map<String, Object>> result = aliOrderService.lookContract(body.getString("orderNo"));
		
		log.info("execute lookContract success, result: {}", JSONObject.toJSONString(result));
		
		return result;
	}
}
