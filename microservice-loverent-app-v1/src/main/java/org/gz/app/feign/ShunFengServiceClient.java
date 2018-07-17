package org.gz.app.feign;

import org.gz.app.hystrix.ShunFengServiceHystirxImpl;
import org.gz.common.resp.ResponseResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="microservice-loverent-thirdParty-v1", fallback=ShunFengServiceHystirxImpl.class)
public interface ShunFengServiceClient {

	@RequestMapping("/routeQuery")
	public ResponseResult<?> routeQuery(@RequestParam("orderId") String orderId,@RequestParam("type") Integer type);
}
