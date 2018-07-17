/**
 * 
 */
package org.gz.oss.common.feign;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.oss.common.config.FeignFullConfig;
import org.gz.oss.common.entity.MaterielModelQuery;
import org.gz.warehouse.common.vo.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.List;
import java.util.Map;

/**
 * FeignClient注解属性说明：
 * name/value:用于指定带有可选协议前辍的服务ID,可通过http://localhost：7001查看注册服务名
 * configuration：自定义FeignClient的配置类，配置类上必须包含@Configuration注解，其内容是要覆盖的bean定义
 * fallback:用于定义feign client interface的回退类，该回退类必须实现feign client interface中的所有方法，且必须是一个有效的Spring bean(即，增加类似@Component这样的注解)
 */
@FeignClient(value = "MICROSERVICE-LOVERENT-WAREHOUSE",configuration=FeignFullConfig.class,fallback=IProductServiceFallBack.class)
public interface IProductService {

    @PostMapping(value = "/api/product/addProduct", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> addProduct(@RequestBody ProductInfo pInfo);

    @PostMapping(value = "/api/product/updateProduct", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> updateProduct(@RequestBody ProductInfo pInfo);

    @PostMapping(value = "/api/product/updateProductIsEnable", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> updateProductIsEnable(@RequestBody ProductInfo pInfo);

    /**
     * 接口方法名可与服务提供方不同，但方法签名(参数，返回)、请求方法必须服务提供方保持一致，包括@RequestBody.
     */
    @PostMapping(value = "/api/product/queryProductList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<ResultPager<ProductInfo>> queryProductList(@RequestBody ProductInfoQvo qvo);

    @PostMapping(value = "/api/product/queryAllProductLeaseTerm", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> queryAllProductLeaseTerm(@RequestBody ProductInfoQvo qvo);

    @PostMapping(value = "/api/product/getByIdOrPdtNo", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<ProductInfo> getByIdOrPdtNo(@RequestBody ProductInfo pInfo);
    
    @PostMapping(value = "/materielModel/api/queryAllMaterielModel", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> queryAllMaterielModel(@RequestBody ProductInfoQvo qvo);

    @GetMapping(value = "/api/product/queryAllNewConfigs", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> queryAllNewConfigs();

    @PostMapping(value = "/api/product/getProductInfoWithCommodityList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> getProductInfoWithCommodityList(@RequestBody ProductInfoQvo qvo);

    @PostMapping(value = "/api/product/getSaleCommodityInfo", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> getSaleCommodityInfo(@RequestBody List<Map<String,Object>> list);

    @GetMapping(value = "/api/queryMaterielBrandListByClassId/{classId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> queryMaterielBrandListByClassId(@PathVariable("classId") Integer classId);

    @PostMapping(value = "/api/queryMaterielModelPicListByBrandId", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> queryMaterielModelPicListByBrandId(@RequestBody MaterielModelQuery data);



    @PostMapping(value = "/api/product/getSaleCommodityInfo", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<List<ProductInfo>> getSaleCommodityInfo(@RequestBody ProductInfoQvo qvo);

    @PostMapping(value = "/api/product/queryProductInfoListWithMaterielImageByModelIds", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<List<ProductInfo>> queryProductInfoListWithMaterielImageByModelIds(@RequestBody ProductInfoQvo qvo);

    @PostMapping(value = "/api/product/queryProductInfoWithMaterieImageByModelId", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<ProductInfo> queryProductInfoWithMaterieImageByModelId(@RequestBody ProductInfoQvo qvo);




    @PostMapping(value = "/api/product/getSaleCommodityInfoById", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<ProductInfo> getSaleCommodityInfoById(@RequestBody ProductInfo qvo);

    @GetMapping(value = "/api/getAllNewConfigs", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> getAllNewConfigs();

    /**
     * 根据品牌ID，型号ID，成色，分类查询商品列表（售卖）
     * @param vo
     * @return
     */
    @PostMapping(value = "/api/product/queryRentCommodityPage")
    public ResponseResult<?> getSaleCommodityInfoByBrandIdAndModelIdAndConfigid(@RequestBody RentProductReq vo);


    /**
     * 根据品牌ID，型号ID，成色，分类查询商品列表（租赁）
     * @param q
     * @return
     */
    @PostMapping(value = "/api/product/getRentCommodityInfoByBrandIdAndModelIdAndConfigid")
    public ResponseResult<?> getRentCommodityInfoByBrandIdAndModelIdAndConfigid(@RequestBody RentProductReq q);



    /**
     * 补仓业务
     * @param req
     * @return ResponseResult
     */
    @PostMapping(value = "/api/product/queryAvailableSplitProductList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<ResultPager<SaleProductResp>> queryAvailableSplitProductList(@RequestBody SaleProductReq req);

    
    @PostMapping(value = "/getLeasePriceLowestProduct")
    public ResponseResult<ProductInfo> getLeasePriceLowestProduct(@RequestBody ProductInfoQvo qvo);

    /**
     * 查询租金最低的产品
     * @param m
     * @return
     */
    @PostMapping(value = "/api/product/getLeasePriceLowestProduct", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<ProductInfo> getLeasePriceLowestProducts(@RequestBody ProductInfoQvo qvo);

    /**
     * 根据多个型号ID查询最低月租金
     * @param qvo
     * @return ProductInfo
     */
    @PostMapping(value = "/materielModel/getMinAmountByModelId", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<List<ProductInfo>> getMinAmountByModelId(@RequestBody ProductInfoQvo qvo);

    /**
     * 查询售卖和租赁商品列表
     * @param qvo
     * @return
     */
    @PostMapping(value = "/api/product/getSellAndLeaseProductList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<ProductInfo> getSellAndLeaseProductList(@RequestBody ProductInfoQvo qvo);

    /**
     * app首页-根据多个产品ID，imeiNo、snNo批量查询商品详细信息
     * @param qvo
     * @return
     */
    @PostMapping(value = "/api/product/batchQuerySellCommoidtyInfo", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<List<ProductInfo>> batchQuerySellCommoidtyInfo(@RequestBody ProductInfoQvo qvo);
}


@Component
class IProductServiceFallBack implements IProductService {

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
    public ResponseResult<ResultPager<ProductInfo>> queryProductList(ProductInfoQvo qvo) {
        return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
    }
    
    private ResponseResult<?> defaultBkMethod() {
        return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
    }

    @Override
    public ResponseResult<?> queryAllProductLeaseTerm(ProductInfoQvo qvo) {
        return defaultBkMethod();
    }

    @Override
    public ResponseResult<ProductInfo> getByIdOrPdtNo(ProductInfo pInfo) {
        return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
    }

	@Override
    public ResponseResult<?> queryAllMaterielModel(ProductInfoQvo qvo) {
		return defaultBkMethod();
	}

    @Override
    public ResponseResult<?> queryAllNewConfigs() {
        return defaultBkMethod();
    }

    @Override
    public ResponseResult<?> getProductInfoWithCommodityList(ProductInfoQvo qvo) {
        return defaultBkMethod();
    }

    @Override
    public ResponseResult<?> getSaleCommodityInfo(List<Map<String, Object>> list) {
        return defaultBkMethod();
    }

    @Override
    public ResponseResult<?> queryMaterielBrandListByClassId(Integer classId) {
        return defaultBkMethod();
    }

    @Override
    public ResponseResult<?> queryMaterielModelPicListByBrandId(MaterielModelQuery data) {
        return defaultBkMethod();
    }

    @Override
    public ResponseResult<List<ProductInfo>> queryProductInfoListWithMaterielImageByModelIds(ProductInfoQvo qvo) {
        return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
    }

    @Override
    public ResponseResult<ProductInfo> queryProductInfoWithMaterieImageByModelId(ProductInfoQvo qvo) {
        return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
    }

    @Override
    public ResponseResult<ProductInfo> getSaleCommodityInfoById(ProductInfo qvo) {
        return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
    }


    @Override
    public ResponseResult<?> getAllNewConfigs() {
        return defaultBkMethod();
    }

    @Override
    public ResponseResult<?> getSaleCommodityInfoByBrandIdAndModelIdAndConfigid(RentProductReq vo) {
        return defaultBkMethod();
    }

    @Override
    public ResponseResult<?> getRentCommodityInfoByBrandIdAndModelIdAndConfigid(RentProductReq q) {
        return defaultBkMethod();
    }

    @Override
    public ResponseResult<ResultPager<SaleProductResp>> queryAvailableSplitProductList(SaleProductReq req) {
        return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
    }

    @Override
    public ResponseResult<List<ProductInfo>> getSaleCommodityInfo(ProductInfoQvo qvo) {
        return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
    }

	@Override
	public ResponseResult<ProductInfo> getLeasePriceLowestProduct(ProductInfoQvo qvo) {
		 return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
	}

    @Override
    public ResponseResult<ProductInfo> getLeasePriceLowestProducts(ProductInfoQvo qvo) {
        return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
    }

    @Override
    public ResponseResult<List<ProductInfo>> getMinAmountByModelId(ProductInfoQvo qvo) {
        return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
    }

    @Override
    public ResponseResult<ProductInfo> getSellAndLeaseProductList(ProductInfoQvo qvo) {
        return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
    }

    @Override
    public ResponseResult<List<ProductInfo>> batchQuerySellCommoidtyInfo(ProductInfoQvo qvo) {
        return ResponseResult.build(1000, "microservice-loverent-warehouse系统异常，已回退", null);
    }


}