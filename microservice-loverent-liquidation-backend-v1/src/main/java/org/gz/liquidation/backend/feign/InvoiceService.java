package org.gz.liquidation.backend.feign;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.dto.invoice.InvoiceReq;
import org.gz.liquidation.common.dto.invoice.InvoiceResp;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
/**
 * 
 * @Description: 发票FeignClient 
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月30日 	Created
 */
@FeignClient(value = "microservice-loverent-liquidation-service-v1",
configuration=org.gz.liquidation.common.config.LiquidationFeignConfig.class)
public interface InvoiceService {
	/**
	 * 
	 * @Description: 分页查询订单发票信息
	 * @param invoiceReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月30日
	 */
	@PostMapping(value = "/invoice/query/page", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseResult<ResultPager<InvoiceResp>> queryPageInvoice(@RequestBody InvoiceReq invoiceReq);
	/**
	 * 
	 * @Description: 确认开票
	 * @param orderSN
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月30日
	 */
	@PostMapping(value = "/invoice/{orderSN}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseResult<?> invoice(@PathVariable("orderSN") String orderSN);
}
