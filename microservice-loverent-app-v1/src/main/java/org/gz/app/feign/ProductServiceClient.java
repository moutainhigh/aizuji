package org.gz.app.feign;

import org.gz.app.hystrix.ProductServiceHystirxImpl;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.common.vo.MaterielBasicInfoResp;
import org.gz.warehouse.common.vo.ProductInfo;
import org.gz.warehouse.common.vo.ProductInfoQvo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.alibaba.fastjson.JSONObject;

@FeignClient(value="microservice-loverent-warehouse", fallback=ProductServiceHystirxImpl.class)
public interface ProductServiceClient {

	@PostMapping(value = "/api/product/queryAllProductLeaseTerm", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseResult<?> queryAllProductLeaseTerm(@RequestBody ProductInfoQvo vo);
	
	@PostMapping(value = "/api/product/getLeasePriceLowestProduct", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseResult<ProductInfo> getLeasePriceLowestProduct(@RequestBody JSONObject body);
	
	@PostMapping(value = "/api/order/queryModelList")
	ResponseResult<?> queryModelList(@RequestBody JSONObject body);
	
	@PostMapping(value = "/api/order/queryMainMaterielInfo")
	ResponseResult<MaterielBasicInfoResp> queryMainMaterielInfo(@RequestBody JSONObject body);
	
	@PostMapping(value = "/api/order/queryMainMaterielBasicIntroductionInfo")
	ResponseResult<MaterielBasicInfoResp> queryMainMaterielBasicIntroductionInfo(@RequestBody JSONObject body);
	
	@PostMapping(value = "/api/product/getLeaseProductInfo", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<ProductInfo> getLeaseProductInfo(@RequestBody ProductInfoQvo qvo);
}
