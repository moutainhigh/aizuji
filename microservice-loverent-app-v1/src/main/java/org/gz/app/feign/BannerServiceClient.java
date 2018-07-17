package org.gz.app.feign;

import java.util.List;
import java.util.Map;

import org.gz.app.hystrix.BannerServiceHystirxImpl;
import org.gz.common.resp.ResponseResult;
import org.gz.oss.common.entity.Banner;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value="microservice-loverent-oss-server-v1", fallback=BannerServiceHystirxImpl.class)
public interface BannerServiceClient {

	@PostMapping(value = "/banner/queryAllList")
	ResponseResult<Map<String, List<Banner>>> queryAllList();
	
	@GetMapping(value = "/api/banner/queryBannerList")
    public ResponseResult<?> queryBannerList();
}
