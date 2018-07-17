package org.gz.app.feign;

import org.gz.app.hystrix.SaleOrderServiceHystrixImpl;
import org.gz.common.resp.ResponseResult;
import org.gz.order.common.dto.SaleOrderReq;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value="microservice-loverent-order-api-v1", fallback=SaleOrderServiceHystrixImpl.class)
public interface SaleOrderServiceClient {

	@PostMapping(value = "/api/order/addSaleOrder", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)	
	public ResponseResult<String> addSaleOrder(@RequestBody SaleOrderReq saleOrderReq);
	
	
}
