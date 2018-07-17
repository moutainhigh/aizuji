package org.gz.workorder.backend.feign;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.workorder.backend.config.FeignFullConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(value = "MICROSERVICE-LOVERENT-THIRDPARTY-V1", configuration = FeignFullConfig.class, fallback = IShunFengServiceFallBack.class)
public interface ThirdPartyClient {

    @PostMapping(value = "/routeQuery")
    public ResponseResult<?> routeQuery(@RequestParam("orderId")String orderId,@RequestParam("type")Integer type);

}

@Component
class IShunFengServiceFallBack implements ThirdPartyClient{

    @Override
    public ResponseResult<?> routeQuery(String orderId,Integer type) {
    	return ResponseResult.build(ResponseStatus.SERVICE_CALL_OVERTIME.getCode(),ResponseStatus.SERVICE_CALL_OVERTIME.getMessage(), null);
    }
}
