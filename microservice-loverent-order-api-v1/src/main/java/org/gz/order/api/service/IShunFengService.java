package org.gz.order.api.service;

import org.gz.order.api.config.FeignFullConfig;
import org.gz.order.common.dto.ResultCode;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
@FeignClient(value = "MICROSERVICE-LOVERENT-THIRDPARTY-V1", configuration = FeignFullConfig.class, fallback = IShunFengServiceFallBack.class)
public interface IShunFengService {

    @PostMapping(value = "/routeQuery")
  public ResultCode routeQuery(@RequestParam("orderId") String orderId, @RequestParam("type") Integer type);

}

@Component
class IShunFengServiceFallBack implements IShunFengService{

    @Override
  public ResultCode routeQuery(String orderId, Integer type) {
        return defaultBkMethod();
    }

    
  private ResultCode defaultBkMethod() {
    return new ResultCode(9996, "内部处理异常");
    }
}
