package org.gz.app.hystrix;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.feign.ShunFengServiceClient;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ShunFengServiceHystirxImpl implements ShunFengServiceClient {

	@Override
	public ResponseResult<?> routeQuery(String orderId, Integer type) {
		log.error("-----------------顺丰第三方服务不可用------------");
		ResponseResult<?> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}
	
}
