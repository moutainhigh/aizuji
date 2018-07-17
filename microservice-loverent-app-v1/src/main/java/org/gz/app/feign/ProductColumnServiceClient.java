package org.gz.app.feign;

import java.util.List;

import org.gz.app.hystrix.ProductColumnServiceHystirxImpl;
import org.gz.common.resp.ResponseResult;
import org.gz.oss.common.entity.ProductColumn;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value="microservice-loverent-oss-server-v1", fallback=ProductColumnServiceHystirxImpl.class)
public interface ProductColumnServiceClient {

	@PostMapping(value = "/productColumn/queryColumnList")
	ResponseResult<List<ProductColumn>> queryColumnList();
	
}
