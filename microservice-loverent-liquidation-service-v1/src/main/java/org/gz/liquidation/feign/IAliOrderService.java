package org.gz.liquidation.feign;

import org.gz.aliOrder.dto.OrderDetailResp;
import org.gz.aliOrder.dto.UpdateOrderStateReq;
import org.gz.common.resp.ResponseResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
/**
 * 
 * @Description:查询阿里订单FeignClient
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月30日 	Created
 */
@FeignClient(value = "microservice-loverent-aliOrder-v1",configuration=org.gz.liquidation.common.config.LiquidationFeignConfig.class)
public interface IAliOrderService {
	/**
	   * 后台查询订单详细信息
	   * 
	   * @param rentRecordNo 订单号
	   * @return
	   * @throws createBy:临时工 createDate:2018年3月28日
	   */
	  @PostMapping(value = "/api/aliOrder/queryBackOrderDetail", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	  ResponseResult<OrderDetailResp> queryBackOrderDetail(@RequestBody String rentRecordNo);
	  /**
	   * 支付成功修改订单状态
	   * 
	   * @param updateOrderStateReq
	   * @return
	   * @throws
	   * createBy:临时工          
	   * createDate:2018年3月28日
	   */
	   @PostMapping(value = "/api/aliOrder/paySuccess", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	   ResponseResult<String> paySuccess(@RequestBody UpdateOrderStateReq updateOrderStateReq);
}
