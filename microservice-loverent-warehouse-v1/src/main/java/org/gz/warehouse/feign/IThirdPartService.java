/**
 * 
 */
package org.gz.warehouse.feign;

import org.gz.warehouse.config.FeignFullConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@FeignClient(value = "microservice-loverent-thirdParty-v1", configuration = FeignFullConfig.class, fallbackFactory = IThirdPartServiceFallBackFactory.class)
public interface IThirdPartService {

	@PostMapping(value = "/order2")
	public ResultCode order2(@RequestBody ApplyOrderRequest req);

	@GetMapping("/orderDownload2/{orderId}")
	public ResultCode orderDownload2(@PathVariable("orderId")String orderId);
}

@Component
@Slf4j
class IThirdPartServiceFallBackFactory implements FallbackFactory<IThirdPartService> {

	@Override
	public IThirdPartService create(Throwable cause) {
		return new IThirdPartService() {
			@Override
			public ResultCode order2(ApplyOrderRequest req) {
				log.error("调用microservice-loverent-thirdParty-v1下单失败，原因：{}",cause.getMessage());
				return ResultCode.SERVER_ERROR;
			}

			@Override
			public ResultCode orderDownload2(String orderId) {
				log.error("调用microservice-loverent-thirdParty-v1下载电子运单图片失败，原因：{}",cause.getMessage());
				return ResultCode.SERVER_ERROR;
			}
			
		};
	}

}