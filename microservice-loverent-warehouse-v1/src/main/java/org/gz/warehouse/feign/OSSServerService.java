/**
 * 
 */
package org.gz.warehouse.feign;

import java.util.List;

import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.config.FeignFullConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.fastjson.JSONObject;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2018年4月2日 下午2:03:05
 */
@FeignClient(value = "microservice-loverent-oss-server-v1", configuration = FeignFullConfig.class, fallbackFactory = OSSServerServiceFallBackFactory.class)
public interface OSSServerService {

	@PostMapping("/api/querySaleProductCount")
	public ResponseResult<List<JSONObject>> querySaleProductCountList(@RequestBody JSONObject req);
}

@Component
@Slf4j
class OSSServerServiceFallBackFactory implements FallbackFactory<OSSServerService> {
	@Override
	public OSSServerService create(Throwable cause) {
		return new OSSServerService() {
			@Override
			public ResponseResult<List<JSONObject>> querySaleProductCountList(JSONObject req) {
				log.error("调用microservice-loverent-oss-server-v1系统异常：{}", cause.getLocalizedMessage());
				return ResponseResult.build(1000, cause.getLocalizedMessage(), null);
			}
		};
	}

}