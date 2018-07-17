package org.gz.liquidation.service.product;

/**
 * 
 */

import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.common.vo.ProductInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * FeignClient注解属性说明：
 * name/value:用于指定带有可选协议前辍的服务ID,可通过http://localhost：7001查看注册服务名
 * configuration：自定义FeignClient的配置类，配置类上必须包含@Configuration注解，其内容是要覆盖的bean定义
 * fallback:用于定义feign client interface的回退类，该回退类必须实现feign client interface中的所有方法，且必须是一个有效的Spring bean(即，增加类似@Component这样的注解)
 */
@FeignClient(value = "MICROSERVICE-LOVERENT-WAREHOUSE",configuration=org.gz.liquidation.common.config.LiquidationFeignConfig.class)
public interface IProductService {


    @PostMapping(value = "/api/product/getByIdOrPdtNo", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<ProductInfo> getByIdOrPdtNo(@RequestBody ProductInfo pInfo);
}


@Component
class IProductServiceFallBack implements IProductService {

    private ResponseResult<?> defaultBkMethod() {
        return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
    }


    @Override
    public ResponseResult<ProductInfo> getByIdOrPdtNo(ProductInfo pInfo) {
        return ResponseResult.buildSuccessResponse(null);
    }


}