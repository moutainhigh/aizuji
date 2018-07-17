package org.gz.aliOrder.service.outside;

import org.gz.aliOrder.config.FeignFullConfig;
import org.gz.aliOrder.dto.ResultCode;
import org.gz.aliOrder.dto.ZhimaOrderCreditPayReq;
import org.gz.common.resp.ResponseResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
@FeignClient(value = "MICROSERVICE-LOVERENT-THIRDPARTY-V1", configuration = FeignFullConfig.class, fallback = IShunFengServiceFall.class)
public interface IShunFengService {

    @PostMapping(value = "/routeQuery")
  public ResultCode routeQuery(@RequestParam("orderId") String orderId, @RequestParam("type") Integer type);

  @PostMapping(value = "/alipay/zhimaOrder/creditPay", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<?> zhimaOrderCreditPay(@RequestBody ZhimaOrderCreditPayReq zhimaOrderCreditPayReq);
}

@Slf4j
@Component
class IShunFengServiceFall implements IShunFengService{

    @Override
  public ResultCode routeQuery(String orderId, Integer type) {
        return defaultBkMethod();
    }

    
  private ResultCode defaultBkMethod() {
    return new ResultCode(9996, "内部处理异常");
    }

  @Override
  public ResponseResult<?> zhimaOrderCreditPay(ZhimaOrderCreditPayReq zhimaOrderCreditPayReq) {
    log.error("MICROSERVICE-LOVERENT-THIRDPARTY-V1 系统异常zhimaOrderCreditPay，已回退}");
    return ResponseResult.build(1000, "MICROSERVICE-LOVERENT-THIRDPARTY-V1 系统异常，已回退", null);
  }
}
