package org.gz.app.hystrix;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.feign.AdvertServiceClient;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AdvertServiceHystrixImpl implements AdvertServiceClient {

	@Override
	public ResponseResult<?> queryAdverList() {
		log.error("---> queryAdverList failed, execute fall back...");
		return buildDefaultFallback();
	}

	private ResponseResult<?> buildDefaultFallback() {
		ResponseResult<?> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}
}
