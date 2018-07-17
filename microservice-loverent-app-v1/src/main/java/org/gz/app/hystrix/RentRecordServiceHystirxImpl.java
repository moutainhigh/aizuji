package org.gz.app.hystrix;

import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.feign.RentRecordServiceClient;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.gz.order.common.dto.AddOrderReq;
import org.gz.order.common.dto.OrderDetailReq;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.order.common.dto.SubmitOrderReq;
import org.gz.order.common.dto.UpdateOrderStateReq;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RentRecordServiceHystirxImpl implements RentRecordServiceClient {

	@Override
	public ResponseResult<String> add(AddOrderReq addOrderRequest) {
		log.error("-----------------订单服务不可用------------");
		ResponseResult<String> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<OrderDetailResp> submitOrder(
			SubmitOrderReq submitOrderReq) {
		log.error("-----------------订单服务不可用------------");
		ResponseResult<OrderDetailResp> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<String> updateOrderState(UpdateOrderStateReq req) {
		log.error("-----------------订单服务不可用------------");
		ResponseResult<String> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<List<OrderDetailResp>> queryOrderStateList(
			OrderDetailReq orderDetailReq) {
		log.error("-----------------订单服务不可用------------");
		ResponseResult<List<OrderDetailResp>> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<OrderDetailResp> queryOrderDetail(String rentRecordNo) {
		log.error("-----------------订单服务不可用------------");
		ResponseResult<OrderDetailResp> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<Map<String, Object>> lookContract(String rentRecordNo) {
		log.error("-----------------订单服务不可用------------");
		ResponseResult<Map<String, Object>> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<String> sureSign(UpdateOrderStateReq updateOrderState) {
		log.error("-----------------订单服务不可用------------");
		ResponseResult<String> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<String> applySign(UpdateOrderStateReq updateOrderState) {
		log.error("-----------------订单服务不可用------------");
		ResponseResult<String> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

}
