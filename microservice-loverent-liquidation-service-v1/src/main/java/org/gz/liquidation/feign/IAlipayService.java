package org.gz.liquidation.feign;

import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.dto.alipay.ZhimaOrderCreditPayReq;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.alipay.api.response.ZhimaMerchantOrderCreditPayResponse;
/**
 * 
 * @Description:支付宝服务FeignClient
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年4月3日 	Created
 */
@FeignClient(value = "microservice-loverent-thirdParty-v1")
public interface IAlipayService {
	/**
	 * 
	 * @Description: 芝麻信用支付
	 * @param zhimaOrderCreditPayReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年4月3日
	 */
	@PostMapping(value = "/alipay/zhimaOrder/creditPay", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<ZhimaMerchantOrderCreditPayResponse> zhimaOrderCreditPay(@RequestBody ZhimaOrderCreditPayReq zhimaOrderCreditPayReq);
	/**
	 * 
	 * @Description: 支付宝交易订单查询
	 * @param outTradeNo
	 * @param tradeNo
	 * @param transactionSource
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年4月3日
	 */
	@PostMapping(value = "/alipay/queryTrade")
	public ResponseResult<?> queryTrade(@RequestParam("outTradeNo") String outTradeNo,@RequestParam("tradeNo") String tradeNo,@RequestParam("transactionSource") String transactionSource);
}
