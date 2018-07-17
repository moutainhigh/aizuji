package org.gz.order.backend.feign;

import org.gz.common.resp.ResponseResult;
import org.gz.order.backend.config.FeignFullConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(value = "MICROSERVICE-LOVERENT-THIRDPARTY-V1", configuration = FeignFullConfig.class, fallback = IShunFengServiceFallBack.class)
public interface IShunFengService {

    @PostMapping(value = "/routeQuery")
    public ResponseResult<?> routeQuery(@RequestParam("orderId")String orderId,@RequestParam("type")Integer type);

}

@Component
class IShunFengServiceFallBack implements IShunFengService{

    @Override
    public ResponseResult<?> routeQuery(String orderId,Integer type) {
        return defaultBkMethod();
    }

    
    private ResponseResult<?> defaultBkMethod() {
        return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
    }
}
