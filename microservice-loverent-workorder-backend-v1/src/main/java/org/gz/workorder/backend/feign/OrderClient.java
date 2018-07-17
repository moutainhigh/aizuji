package org.gz.workorder.backend.feign;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.order.common.dto.AddOrderReq;
import org.gz.order.common.dto.OrderDetailReq;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.order.common.dto.RentRecordQuery;
import org.gz.order.common.dto.SubmitOrderReq;
import org.gz.order.common.dto.UpdateOrderStateReq;
import org.gz.order.common.dto.WorkOrderRsp;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value="microservice-loverent-order-api-v1", fallback=RentRecordServiceHystirxImpl.class)
public interface OrderClient {

	@PostMapping(value = "/api/order/addOrder", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseResult<String> add(@Valid @RequestBody AddOrderReq addOrderRequest);

	@PostMapping(value = "/api/order/submitOrder", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseResult<OrderDetailResp> submitOrder(@Valid @RequestBody SubmitOrderReq submitOrderReq);

	@PostMapping(value = "/api/order/updateOrderState", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseResult<String> updateOrderState(@Valid @RequestBody UpdateOrderStateReq req);

	@PostMapping(value = "/api/order/queryOrderStateList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseResult<List<OrderDetailResp>> queryOrderStateList(
			@Valid @RequestBody OrderDetailReq orderDetailReq);
	
	@PostMapping(value = "/api/order/queryOrderDetailForWork", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<OrderDetailResp> queryOrderDetailForWork(@RequestBody String rentRecordNo);

	@PostMapping(value = "/api/order/lookContract", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<Map<String, Object>> lookContract(@RequestBody String rentRecordNo);
	
	@PostMapping(value = "/api/order/queryPageWokerOrderList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<ResultPager<WorkOrderRsp>> queryPageWokerOrderList(@Valid @RequestBody RentRecordQuery rentRecordQuery);
}
@Component
class RentRecordServiceHystirxImpl implements OrderClient {
	

	@Override
	public ResponseResult<String> add(AddOrderReq addOrderRequest) {
		return ResponseResult.build(ResponseStatus.SERVICE_CALL_OVERTIME.getCode(),ResponseStatus.SERVICE_CALL_OVERTIME.getMessage(), null);
	}

	@Override
	public ResponseResult<OrderDetailResp> submitOrder(
			SubmitOrderReq submitOrderReq) {
		return ResponseResult.build(ResponseStatus.SERVICE_CALL_OVERTIME.getCode(),ResponseStatus.SERVICE_CALL_OVERTIME.getMessage(), null);
	}

	@Override
	public ResponseResult<String> updateOrderState(UpdateOrderStateReq req) {
		return ResponseResult.build(ResponseStatus.SERVICE_CALL_OVERTIME.getCode(),ResponseStatus.SERVICE_CALL_OVERTIME.getMessage(), null);
	}

	@Override
	public ResponseResult<List<OrderDetailResp>> queryOrderStateList(
			OrderDetailReq orderDetailReq) {
		return ResponseResult.build(ResponseStatus.SERVICE_CALL_OVERTIME.getCode(),ResponseStatus.SERVICE_CALL_OVERTIME.getMessage(), null);
	}

	@Override
	public ResponseResult<OrderDetailResp> queryOrderDetailForWork(String rentRecordNo) {
		return ResponseResult.build(ResponseStatus.SERVICE_CALL_OVERTIME.getCode(),ResponseStatus.SERVICE_CALL_OVERTIME.getMessage(), null);
	}

	@Override
	public ResponseResult<Map<String, Object>> lookContract(String rentRecordNo) {
		return ResponseResult.build(ResponseStatus.SERVICE_CALL_OVERTIME.getCode(),ResponseStatus.SERVICE_CALL_OVERTIME.getMessage(), null);
	}

	@Override
	public ResponseResult<ResultPager<WorkOrderRsp>> queryPageWokerOrderList(RentRecordQuery rentRecordQuery) {
		return ResponseResult.build(ResponseStatus.SERVICE_CALL_OVERTIME.getCode(),ResponseStatus.SERVICE_CALL_OVERTIME.getMessage(), null);
	}
}
