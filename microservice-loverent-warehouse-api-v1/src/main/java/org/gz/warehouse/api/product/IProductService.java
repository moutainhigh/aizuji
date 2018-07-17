package org.gz.warehouse.api.product;

import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.common.vo.ProductInfo;
import org.gz.warehouse.common.vo.ProductInfoQvo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "MICROSERVICE-LOVERENT-WAREHOUSE")
public interface IProductService {

    @RequestMapping(value = "/api/product/addProduct", method = RequestMethod.POST)
    public ResponseResult<?> addProduct(ProductInfo pInfo);

    @RequestMapping(value = "/api/product/updateProduct", method = RequestMethod.POST)
    public ResponseResult<?> updateProduct(ProductInfo pInfo);

    @RequestMapping(value = "/api/product/updateProductIsEnable", method = RequestMethod.POST)
    public ResponseResult<?> updateProductIsEnable(ProductInfo pInfo);

    @PostMapping(value = "/api/product/queryProductList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> queryProductList(@RequestBody ProductInfoQvo qvo);
}
/*
@Component
class Fallback implements IProductService {

    @Override
    public ResponseResult<?> addProduct(ProductInfo pInfo) {
        return defaultBkMethod();
    }

    @Override
    public ResponseResult<?> updateProduct(ProductInfo pInfo) {
        return defaultBkMethod();
    }

    @Override
    public ResponseResult<?> updateProductIsEnable(ProductInfo pInfo) {
        return defaultBkMethod();
    }

    @Override
    public ResponseResult<?> queryProductList(ProductInfoQvo qvo) {
        return defaultBkMethod();
    }
    
    private ResponseResult<?> defaultBkMethod() {
        return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
    }

}
*/