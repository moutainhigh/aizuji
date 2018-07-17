package org.gz.app.hystrix;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.feign.SaleOrderServiceClient;
import org.gz.common.resp.ResponseResult;
import org.gz.order.common.dto.SaleOrderReq;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SaleOrderServiceHystrixImpl implements SaleOrderServiceClient {

	@Override
	public ResponseResult<String> addSaleOrder(SaleOrderReq saleOrderReq) {
		log.error("----->>>订单服务不可用，添加售卖订单失败");
		return buildFallbackResp();
	}

	private ResponseResult<String> buildFallbackResp() {
		return ResponseResult.build(-1, "订单服务不可用", null);
	}

}
