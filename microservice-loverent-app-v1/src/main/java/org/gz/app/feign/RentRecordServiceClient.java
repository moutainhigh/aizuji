package org.gz.app.feign;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.gz.app.hystrix.RentRecordServiceHystirxImpl;
import org.gz.common.resp.ResponseResult;
import org.gz.order.common.dto.AddOrderReq;
import org.gz.order.common.dto.OrderDetailReq;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.order.common.dto.SubmitOrderReq;
import org.gz.order.common.dto.UpdateOrderStateReq;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value="microservice-loverent-order-api-v1", fallback=RentRecordServiceHystirxImpl.class)
public interface RentRecordServiceClient {

	@PostMapping(value = "/api/order/addOrder", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseResult<String> add(@Valid @RequestBody AddOrderReq addOrderRequest);

	@PostMapping(value = "/api/order/submitOrder", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseResult<OrderDetailResp> submitOrder(@Valid @RequestBody SubmitOrderReq submitOrderReq);

	@PostMapping(value = "/api/order/updateOrderState", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseResult<String> updateOrderState(@Valid @RequestBody UpdateOrderStateReq req);

	@PostMapping(value = "/api/order/queryOrderStateList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseResult<List<OrderDetailResp>> queryOrderStateList(
			@Valid @RequestBody OrderDetailReq orderDetailReq);
	
	@PostMapping(value = "/api/order/queryOrderDetail", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<OrderDetailResp> queryOrderDetail(@RequestBody String rentRecordNo);

	@PostMapping(value = "/api/order/lookContract", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<Map<String, Object>> lookContract(@RequestBody String rentRecordNo);
	
	@PostMapping(value = "/api/order/sureSign", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<String> sureSign(@RequestBody UpdateOrderStateReq updateOrderState);
	
	@PostMapping(value = "/api/order/applySign", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<String> applySign(@RequestBody UpdateOrderStateReq updateOrderState);
}
