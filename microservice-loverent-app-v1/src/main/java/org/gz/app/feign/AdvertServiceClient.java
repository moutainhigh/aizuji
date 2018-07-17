package org.gz.app.feign;

import org.gz.app.hystrix.AdvertServiceHystrixImpl;
import org.gz.common.resp.ResponseResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value="microservice-loverent-oss-server-v1", fallback=AdvertServiceHystrixImpl.class)
public interface AdvertServiceClient {

	@GetMapping(value = "/api/advertising/queryAdvertisingList")
    public ResponseResult<?> queryAdverList();
}
