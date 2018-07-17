package org.gz.app.feign;

import org.gz.app.hystrix.PaymentServiceHystirxImpl;
import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.dto.PayReq;
import org.gz.liquidation.common.dto.PreparePayResp;
import org.gz.liquidation.common.dto.TransactionRecordUpdateReq;
import org.gz.liquidation.common.dto.alipay.ZhimaOrderCreditPayReq;
import org.gz.liquidation.common.dto.alipay.ZhimaOrderCreditPayResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value="microservice-loverent-liquidation-service-v1", fallback=PaymentServiceHystirxImpl.class)
public interface PaymentServiceClient {

	/**
	 * 生成预支付流水号
	 * @param payReq
	 * @return
	 */
	@PostMapping(value = "/payment/preparePay", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseResult<PreparePayResp> preparePay(@RequestBody PayReq payReq);

	/**
	 * 取消预支付流水号
	 * @param payReq
	 * @return
	 */
	@PostMapping(value = "/transactionRecord/update/state", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseResult<String> updateState(@RequestBody TransactionRecordUpdateReq req);

	/**
	 * 芝麻扣款成
	 * @param zhimaOrderCreditPayReq
	 * @return
	 */
	@PostMapping(value = "/payment/zhimaOrder/creditPay", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> zhimaOrderCreditPay(@RequestBody ZhimaOrderCreditPayReq zhimaOrderCreditPayReq);
	
	/**
	 * 芝麻扣款成功回调
	 * @param req
	 * @return
	 */
	@PostMapping(value = "/zhimaOrder/creditPay/callback", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> zhimaOrderCreditPayCallback(@RequestBody ZhimaOrderCreditPayResponse req);
}
