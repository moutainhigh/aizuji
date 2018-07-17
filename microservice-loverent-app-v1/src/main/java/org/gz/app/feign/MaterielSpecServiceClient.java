package org.gz.app.feign;

import org.gz.app.hystrix.MaterielSpecServiceHystirxImpl;
import org.gz.common.resp.ResponseResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONObject;

@FeignClient(value="microservice-loverent-warehouse", fallback=MaterielSpecServiceHystirxImpl.class)
public interface MaterielSpecServiceClient {

	@PostMapping(value = "/materielSpec/api/queryAllSpecValueByModelId", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseResult<?> queryAllSpecValueByModelId(@RequestParam("modelId") Integer modelId);

	@PostMapping(value = "/materielSpec/getSpecBatchNoBySpecInfo", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseResult<?> getSpecBatchNoBySpecInfo(@RequestBody JSONObject body);
	
}
