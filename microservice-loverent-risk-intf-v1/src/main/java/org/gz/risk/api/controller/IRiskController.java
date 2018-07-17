package org.gz.risk.api.controller;

import org.gz.common.resp.ResponseResult;
import org.gz.risk.common.request.FeignFullConfig;
import org.gz.risk.common.request.RiskReq;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * FeignClient注解属性说明：
 * name/value:用于指定带有可选协议前辍的服务ID,可通过http://localhost：7001查看注册服务名
 * configuration：自定义FeignClient的配置类，配置类上必须包含@Configuration注解，其内容是要覆盖的bean定义
 * fallback:用于定义feign client interface的回退类，该回退类必须实现feign client interface中的所有方法，且必须是一个有效的Spring bean(即，增加类似@Component这样的注解)
 */
@FeignClient(value = "microservice-loverent-risk-v1",configuration=FeignFullConfig.class,fallback=IRiskControllerFallBack.class)
public interface IRiskController {


	@RequestMapping(value = "/api/risk/processAutoCredit", method = RequestMethod.POST)
	@ResponseBody
	public ResponseResult<String> processAutoCredit(@RequestBody RiskReq riskReq) ;
}


@Component
class IRiskControllerFallBack implements IRiskController {
	

	@Override
	public ResponseResult<String> processAutoCredit(RiskReq riskReq) {
		return defaultBkMethod();
	}

	private ResponseResult<String> defaultBkMethod() {
        return ResponseResult.build(1000, "microservice-loverent-risk-v1系统异常，已回退", null);
    }


}