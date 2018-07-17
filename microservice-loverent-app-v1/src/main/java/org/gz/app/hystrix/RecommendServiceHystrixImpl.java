package org.gz.app.hystrix;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.feign.RecommendServiceClient;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.gz.oss.common.dto.RecommendDto;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RecommendServiceHystrixImpl implements RecommendServiceClient {

	@Override
	public ResponseResult<?> queryRecommendList(RecommendDto dto) {
		log.error("---> queryRecommendList failed, execute fall back...");
		return buildDefaultFallback();
	}
	
	@Override
	public ResponseResult<?> queryProductWithCommodityListByRecommendInfo(
			RecommendDto dto) {
		log.error("---> queryProductWithCommodityListByRecommendInfo failed, execute fall back...");
		return buildDefaultFallback();
	}
	
	private ResponseResult<?> buildDefaultFallback() {
		ResponseResult<?> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

}
