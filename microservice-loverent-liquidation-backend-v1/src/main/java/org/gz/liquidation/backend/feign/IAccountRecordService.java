package org.gz.liquidation.backend.feign;

import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.dto.AccountRecordReq;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
/**
 * 
 * @Description:科目记录流水FeignClient
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月6日 	Created
 */
@FeignClient(value = "microservice-loverent-liquidation-service-v1",
configuration=org.gz.liquidation.common.config.LiquidationFeignConfig.class)
public interface IAccountRecordService {

	/**
	 * 
	 * @Description: 分页查询清算记录
	 * @param accountRecordReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月6日
	 */
	@PostMapping(value = "/accountRecord/queryPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> queryPage(@RequestBody AccountRecordReq accountRecordReq);
	
}
