package org.gz.oss.common.feign;

import org.gz.common.resp.ResponseResult;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.oss.common.config.FeignFullConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "microservice-loverent-order-api-v1",configuration=FeignFullConfig.class)
public interface IOrderService {
	 /**
	  * 
	  * @Description: 查询订单详情
	  * @param rentRecordNo	订单号
	  * @return
	  * @throws
	  */
	 @PostMapping(value = "/api/order/queryBackOrderDetail", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 public ResponseResult<OrderDetailResp> queryBackOrderDetail(@RequestBody String rentRecordNo);
	
}
