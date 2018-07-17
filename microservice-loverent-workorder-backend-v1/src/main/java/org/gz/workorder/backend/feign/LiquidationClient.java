package org.gz.workorder.backend.feign;

import java.util.List;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.liquidation.common.dto.RepaymentStatisticsReq;
import org.gz.liquidation.common.dto.RepaymentStatisticsResp;
import org.gz.workorder.backend.config.FeignFullConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "microservice-loverent-liquidation-service-v1", configuration = FeignFullConfig.class, fallback = IRepaymentServiceFallBack.class)
public interface LiquidationClient {

    @RequestMapping(value = "/payment/{orderSN}/downPayment", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)  
    public ResponseResult<?> queryDownPayment(@PathVariable("orderSN") String orderSN);

    @PostMapping(value = "/repayment/repaymentStatistics", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<List<RepaymentStatisticsResp>> queryRepaymentStatistics(@RequestBody RepaymentStatisticsReq repaymentStatisticsReq);
    
    @RequestMapping(value = "/repayment/repaymentSchedule/{orderSN}", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)  
    public ResponseResult<?> repaymentSchedule(@PathVariable("orderSN") String orderSN);

    @RequestMapping(value = "/transactionRecord/api/latest/{orderSN}", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)  
    public ResponseResult<?> latest(@PathVariable("orderSN") String orderSN);
}

@Component
class IRepaymentServiceFallBack implements LiquidationClient{

    @Override
    public ResponseResult<?> queryDownPayment(String orderSN) {
		return ResponseResult.build(ResponseStatus.SERVICE_CALL_OVERTIME.getCode(),ResponseStatus.SERVICE_CALL_OVERTIME.getMessage(), null);
    }

	@Override
    public ResponseResult<List<RepaymentStatisticsResp>> queryRepaymentStatistics(RepaymentStatisticsReq repaymentStatisticsReq) {
		return ResponseResult.build(ResponseStatus.SERVICE_CALL_OVERTIME.getCode(),ResponseStatus.SERVICE_CALL_OVERTIME.getMessage(), null);
    }

	@Override
	public ResponseResult<?> repaymentSchedule(String orderSN) {
		return ResponseResult.build(ResponseStatus.SERVICE_CALL_OVERTIME.getCode(),ResponseStatus.SERVICE_CALL_OVERTIME.getMessage(), null);
	}

	@Override
	public ResponseResult<?> latest(String orderSN) {
		return ResponseResult.build(ResponseStatus.SERVICE_CALL_OVERTIME.getCode(),ResponseStatus.SERVICE_CALL_OVERTIME.getMessage(), null);
	}
}
