package org.gz.liquidation.backend.feign;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.dto.RefundLogQueryReq;
import org.gz.liquidation.common.dto.RefundLogReq;
import org.gz.liquidation.common.dto.RefundLogResp;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
/**
 * 
 * @Description:TODO	退款记录服务客户端接口
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年2月8日 	Created
 */
@FeignClient(value = "microservice-loverent-liquidation-service-v1",configuration=org.gz.liquidation.common.config.LiquidationFeignConfig.class)
public interface IRefundLogService {
	
	@PostMapping(value = "/refundLog/queryPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResultPager<RefundLogResp> queryPage(@RequestBody RefundLogQueryReq req);
	
	@PostMapping(value = "/refundLog/addRefundLog", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseResult<String> addRefundLog(@RequestBody RefundLogReq refundLogReq);
	/**
	 * 
	 * @Description: 获取退款截图
	 * @param id
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月10日
	 */
	@PostMapping(value = "/refundLog/refundImg/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseResult<String> getRefundImg(@PathVariable("id") Long id);
}
