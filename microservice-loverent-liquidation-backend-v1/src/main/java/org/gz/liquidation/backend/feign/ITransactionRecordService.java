package org.gz.liquidation.backend.feign;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.dto.DownpaymentStatisticsReq;
import org.gz.liquidation.common.dto.DownpaymentStatisticsResp;
import org.gz.liquidation.common.dto.TransactionRecordQueryReq;
import org.gz.liquidation.common.dto.TransactionRecordReq;
import org.gz.liquidation.common.dto.TransactionRecordResp;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "microservice-loverent-liquidation-service-v1",configuration=org.gz.liquidation.common.config.LiquidationFeignConfig.class)
public interface ITransactionRecordService {
	
	 @PostMapping(value = "/transactionRecord/queryPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 public ResultPager<TransactionRecordResp> queryPage(@RequestBody TransactionRecordQueryReq req);
	 
	 @PostMapping(value = "/transactionRecord/detail/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 public ResponseResult<TransactionRecordResp> transactionRecordDetail(@PathVariable("id") Long id);
	 
	 @PostMapping(value = "/payment/downpaymentStatistics", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 public ResponseResult<DownpaymentStatisticsResp> queryDownpaymentStatistics(@RequestBody DownpaymentStatisticsReq downpaymentStatisticsReq);
	 /**
	  * 
	  * @Description: TODO 手动完成支付
	  * @param req
	  * @return
	  * @throws
	  * createBy:liaoqingji            
	  * createDate:2018年1月9日
	  */
	 @PostMapping(value = "/payment/payCorrected", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 public ResponseResult<?> payCorrected(@RequestBody TransactionRecordReq req);
	 /**
	  * 
	  * @Description: 交易统计(交易记录数和交易金额)
	  * @param req
	  * @return
	  * @throws
	  * createBy:liaoqingji            
	  * createDate:2018年3月22日
	  */
	 @PostMapping(value = "/transactionRecord/statistics", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	 public ResponseResult<?> queryStatistics(@RequestBody TransactionRecordQueryReq req);
}
