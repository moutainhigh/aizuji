package org.gz.app.feign;

import org.gz.app.hystrix.RecommendServiceHystrixImpl;
import org.gz.common.resp.ResponseResult;
import org.gz.oss.common.dto.RecommendDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value="microservice-loverent-oss-server-v1", fallback=RecommendServiceHystrixImpl.class)
public interface RecommendServiceClient {

	@PostMapping(value = "/api/recommend/queryRecommendList")
    public ResponseResult<?> queryRecommendList(@RequestBody RecommendDto dto);
	
	@PostMapping(value = "/api/recommend/queryProductWithCommodityListByRecommendInfo")
    public ResponseResult<?> queryProductWithCommodityListByRecommendInfo(@RequestBody RecommendDto dto);
}
