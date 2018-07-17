package org.gz.app.hystrix;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.feign.PaymentServiceClient;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.gz.liquidation.common.dto.PayReq;
import org.gz.liquidation.common.dto.PreparePayResp;
import org.gz.liquidation.common.dto.TransactionRecordUpdateReq;
import org.gz.liquidation.common.dto.alipay.ZhimaOrderCreditPayReq;
import org.gz.liquidation.common.dto.alipay.ZhimaOrderCreditPayResponse;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentServiceHystirxImpl implements PaymentServiceClient {

	@Override
	public ResponseResult<PreparePayResp> preparePay(PayReq payReq) {
		log.error("-----------------清算服务不可用------------");
		ResponseResult<PreparePayResp> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<String> updateState(TransactionRecordUpdateReq req) {
		log.error("-----------------清算服务不可用------------");
		ResponseResult<String> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<?> zhimaOrderCreditPay(
			ZhimaOrderCreditPayReq zhimaOrderCreditPayReq) {
		log.error("-----------------清算服务不可用------------");
		ResponseResult<?> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<?> zhimaOrderCreditPayCallback(
			ZhimaOrderCreditPayResponse req) {
		log.error("-----------------清算服务不可用------------");
		ResponseResult<?> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

}
