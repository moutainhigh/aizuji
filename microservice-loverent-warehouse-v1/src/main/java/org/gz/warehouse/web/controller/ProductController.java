package org.gz.warehouse.web.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.gz.common.entity.ResultPager;
import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.web.controller.BaseController;
import org.gz.warehouse.common.WarehouseErrorCode;
import org.gz.warehouse.common.vo.ProductInfo;
import org.gz.warehouse.common.vo.ProductInfoQvo;
import org.gz.warehouse.common.vo.RentProductReq;
import org.gz.warehouse.common.vo.SaleProductReq;
import org.gz.warehouse.common.vo.SaleProductResp;
import org.gz.warehouse.entity.MaterielNewConfig;
import org.gz.warehouse.entity.ProductLeaseTerm;
import org.gz.warehouse.mapper.ProductInfoMapper;
import org.gz.warehouse.service.materiel.MaterielNewConfigService;
import org.gz.warehouse.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
/**
 * 产品信息控制器
 * @Description:TODO
 * Author	Version		Date		Changes
 * liuyt 	1.0  		2017年12月14日 	Created
 */
@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductController extends BaseController {

    @Resource
    private ProductService productService;

    @Resource
    private MaterielNewConfigService materielNewConfigService;

    @Resource
    private ProductInfoMapper productInfoMapper;

    @Autowired
    private Validator validator;
    
    /**
     * 新增产品
     * @param pInfo , bindingResult
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/addProduct", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> addProduct(@Valid @RequestBody ProductInfo pInfo, BindingResult bindingResult) {
        // 验证数据
        ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
        if (validateResult == null) {
            try {
            pInfo.setCreateOn(new Date());
            pInfo.setUpdateOn(new Date());
            //检验是否存在该产品
                ProductInfoQvo qvoproduct = new ProductInfoQvo();
                qvoproduct.setMaterielClassId(pInfo.getMaterielClassId());
                qvoproduct.setMaterielModelId(pInfo.getMaterielModelId());
                qvoproduct.setLeaseTermId(pInfo.getLeaseTermId());
                qvoproduct.setProductType(pInfo.getProductType());
                qvoproduct.setSpecBatchNo(pInfo.getSpecBatchNo());
                qvoproduct.setMaterielUnitId(pInfo.getMaterielUnitId());
                qvoproduct.setMaterielNewConfigId(pInfo.getMaterielNewConfigId());
                List<ProductInfo> productInfoList = this.productInfoMapper.queryList(qvoproduct);
                if (productInfoList!=null && productInfoList.size()>0){
                    return ResponseResult.build(WarehouseErrorCode.PRODUCT_APPLY_REPEAT_ERROR.getCode(), WarehouseErrorCode.PRODUCT_APPLY_REPEAT_ERROR.getMessage(), null);
                }
            this.productService.addProduct(pInfo);
            validateResult = ResponseResult.buildSuccessResponse();
            } catch (ServiceException se) {
                return ResponseResult.build(se.getErrorCode(), se.getErrorMsg(), null);
            } catch (Exception e) {
                log.error("新增产品失败：{}", e);
                return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                    ResponseStatus.DATABASE_ERROR.getMessage(),
                    null);
            }
        }
        return validateResult;
    }

    /**
     * 更新产品
     * @param
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/updateProduct", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> updateProduct(@Valid @RequestBody ProductInfo pInfo, BindingResult bindingResult) {
        // 验证数据
        ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
        if (validateResult == null) {
            try {
                pInfo.setUpdateOn(new Date());
                pInfo.setUpdateOn(new Date());

                ProductInfoQvo qvoproduct = new ProductInfoQvo();
                qvoproduct.setMaterielClassId(pInfo.getMaterielClassId());
                qvoproduct.setMaterielModelId(pInfo.getMaterielModelId());
                qvoproduct.setLeaseTermId(pInfo.getLeaseTermId());
                qvoproduct.setProductType(pInfo.getProductType());
                qvoproduct.setSpecBatchNo(pInfo.getSpecBatchNo());
                qvoproduct.setMaterielUnitId(pInfo.getMaterielUnitId());
                qvoproduct.setMaterielNewConfigId(pInfo.getMaterielNewConfigId());
                qvoproduct.setProductId(pInfo.getId());
                List<ProductInfo> productInfoList = this.productInfoMapper.queryList(qvoproduct);
                if (productInfoList!=null && productInfoList.size()>0){
                    return ResponseResult.build(WarehouseErrorCode.PRODUCT_APPLY_REPEAT_ERROR.getCode(), WarehouseErrorCode.PRODUCT_APPLY_REPEAT_ERROR.getMessage(), null);
                }

                this.productService.updateProductInfo(pInfo);
                validateResult = ResponseResult.buildSuccessResponse();
            } catch (ServiceException se) {
                return ResponseResult.build(se.getErrorCode(), se.getErrorMsg(), null);
            } catch (Exception e) {
                log.error("更新产品失败：{}", e);
                return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                    ResponseStatus.DATABASE_ERROR.getMessage(),
                    null);
            }
        }
        return validateResult;
    }

    /**
     * 更新产品上下架
     * @param
     * @return
     */
    @PostMapping(value = "/updateProductIsEnable", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> updateProductIsEnable(@RequestBody ProductInfo pInfo) {
        try {
            pInfo.setUpdateOn(new Date());
            this.productService.updateProductIsEnable(pInfo);
            return ResponseResult.buildSuccessResponse();
        } catch (Exception e) {
            log.error("更新产品上下架失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }

    /**
     * 获取分页列表
     * @param
     * @return
     */
    @PostMapping(value = "/queryProductList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<ResultPager<ProductInfo>> queryProductList(@RequestBody ProductInfoQvo qvo) {
        if (qvo != null) {
            try {
                ResultPager<ProductInfo> page = this.productService.queryProductList(qvo);
                return ResponseResult.buildSuccessResponse(page);
            } catch (Exception e) {
                log.error("获取产品列表失败：{}", e);
                return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                    ResponseStatus.DATABASE_ERROR.getMessage(),
                    null);
            }
        }
        return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(),
            ResponseStatus.PARAMETER_VALIDATION.getMessage(),
            null);
    }
    
    /**
     * 获取所有产品列表
     * @return
     * @throws
     * @createBy:zhangguoliang
     * createDate:2017年12月22日
     */
    @PostMapping(value = "/queryAllProductList")
    public ResponseResult<?> queryAllProductList() {
        try {
        	List<ProductInfo> list = productService.queryAllProductList();
        	return ResponseResult.buildSuccessResponse(list);
        } catch (Exception e) {
            log.error("queryAllProductList失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }

    /**
     * 获取所有租期列表
     * @param
     * @return
     */
    @PostMapping(value = "/queryAllProductLeaseTerm", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<List<ProductLeaseTerm>> queryAllProductLeaseTerm(@RequestBody ProductInfoQvo qvo) {
        try {
            List<ProductLeaseTerm> list = this.productService.queryAllProductLeaseTerm(qvo.getMaterielModelId());
            return ResponseResult.buildSuccessResponse(list);
        } catch (Exception e) {
            log.error("获取所有租期列表失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }

    /**
     * 通过id或者编号获取产品信息
     * @param
     * @return
     */
    @PostMapping(value = "/getByIdOrPdtNo", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<ProductInfo> getByIdOrPdtNo(@RequestBody ProductInfo pInfo) {
        try {
            ProductInfo productInfo = this.productService.getByIdOrPdtNo(pInfo);
            return ResponseResult.buildSuccessResponse(productInfo);
        } catch (Exception e) {
            log.error("通过id或者编号获取产品信息失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }

    /**
     * 查询租金最低的产品
     * @param
     * @return
     */
    @PostMapping(value = "/getLeasePriceLowestProduct", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<ProductInfo> getLeasePriceLowestProduct(@RequestBody ProductInfoQvo qvo) {
        try {
            ProductInfo productInfo = this.productService.getLeasePriceLowestProduct(qvo);
            return ResponseResult.buildSuccessResponse(productInfo);
        } catch (Exception e) {
            log.error("查询租金最低的产品失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }



    /**
     * 获取产品租期列表
     * @param
     * @return
     */
    @PostMapping(value = "/getLeaseProductList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<ProductInfo> getLeaseProductList(@RequestBody ProductInfoQvo qvo) {
        try {
            ProductInfo productInfo = this.productService.getLeaseProductList(qvo);
            return ResponseResult.buildSuccessResponse(productInfo);
        } catch (Exception e) {
            log.error("获取产品租期列表失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                    ResponseStatus.DATABASE_ERROR.getMessage(),
                    null);
        }
    }

    /**
     * 获取所有新旧程度配置
     * @param
     * @return
     */
    @GetMapping(value = "/queryAllNewConfigs")
    public ResponseResult<List<MaterielNewConfig>> queryAllNewConfigs() {
        try {
            List<MaterielNewConfig> list = this.materielNewConfigService.queryAllNewConfigs();
            return ResponseResult.buildSuccessResponse(list);
        } catch (Exception e) {
            log.error("获取所有租期列表失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }

    /**
     * 查询售卖商品列表
     * @param
     * @return
     */
    @PostMapping(value = "/getProductInfoWithCommodityList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<ProductInfo> getProductInfoWithCommodityList(@RequestBody ProductInfoQvo qvo) {
        try {
            ProductInfo productInfo = this.productService.getProductInfoWithCommodityList(qvo);
            return ResponseResult.buildSuccessResponse(productInfo);
        } catch (Exception e) {
            log.error("查询售卖商品列表失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }

    /**
     * 查询租赁商品详情
     * @param
     * @return
     */
    @PostMapping(value = "/getLeaseProductInfo", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<ProductInfo> getLeaseProductInfo(@RequestBody ProductInfoQvo qvo) {
        try {
            ProductInfo productInfo = this.productService.getLeaseProductInfo(qvo);
            return ResponseResult.buildSuccessResponse(productInfo);
        } catch (Exception e) {
            log.error("查询租赁商品详情失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                    ResponseStatus.DATABASE_ERROR.getMessage(),
                    null);
        }
    }


    /**
     * 查询售卖和租赁商品列表
     * @param
     * @return
     */
    @PostMapping(value = "/getSellAndLeaseProductList")
    public ResponseResult<ProductInfo> getSellAndLeaseProductList( ProductInfoQvo qvo) {
        try {
            ProductInfo productInfo = this.productService.getSellAndLeaseProductList(qvo);
            return ResponseResult.buildSuccessResponse(productInfo);
        } catch (Exception e) {
            log.error("查询售卖和租赁商品列表失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                    ResponseStatus.DATABASE_ERROR.getMessage(),
                    null);
        }
    }

    /**
     *  根据多个型号id查询产品信息和对应的主物料主图信息
     * @param
     * @return
     */
    @PostMapping(value = "/queryProductInfoListWithMaterielImageByModelIds", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<List<ProductInfo>> queryProductInfoListWithMaterielImageByModelIds(@RequestBody ProductInfoQvo qvo) {
        try {
            List<ProductInfo> list = this.productService.queryProductInfoListWithMaterielImageByModelIds(qvo);
            return ResponseResult.buildSuccessResponse(list);
        } catch (Exception e) {
            log.error("根据多个型号id查询产品信息和对应的主物料主图信息失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }

    /**
     *  根据单个型号id查询产品信息和对应的主物料主图信息
     * @param
     * @return
     */
    @PostMapping(value = "/queryProductInfoWithMaterieImageByModelId", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<ProductInfo> queryProductInfoWithMaterieImageByModelId(@RequestBody ProductInfoQvo qvo) {
        try {
            ProductInfo productInfo = this.productService.queryProductInfoWithMaterieImageByModelId(qvo);
            return ResponseResult.buildSuccessResponse(productInfo);
        } catch (Exception e) {
            log.error("根据单个型号id查询产品信息和对应的主物料主图信息失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(),
                    ResponseStatus.DATA_REQUERY_ERROR.getMessage(),
                    null);
        }
    }



    /**
     * 获取售卖商品详细信息
     * @param
     * @return ResponseResult
     */
    @PostMapping(value = "/getSaleCommodityInfo", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> getSaleCommodityInfo(@RequestBody ProductInfoQvo qvo) {
        try {
            List<ProductInfo> list = this.productService.getSaleCommodityInfo(qvo);
            return ResponseResult.buildSuccessResponse(list);
        }catch (Exception e){
            log.error("获取售卖详细信息失败列表失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), ResponseStatus.DATA_REQUERY_ERROR.getMessage(),null);
        }
    }

    /**
     * app首页-根据多个产品ID，imeiNo、snNo批量查询商品详细信息
     * @param qvo
     * @return ResponseResult
     */
    @PostMapping(value = "/batchQuerySellCommoidtyInfo", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> batchQuerySellCommoidtyInfo(@RequestBody ProductInfoQvo qvo) {
        try {
            List<ProductInfo> list = this.productService.batchQuerySellCommoidtyInfo(qvo);
            return ResponseResult.buildSuccessResponse(list);
        }catch (Exception e){
            log.error("获取售卖详细信息失败列表失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), ResponseStatus.DATA_REQUERY_ERROR.getMessage(),null);
        }
    }



    /**
     * 根据产品ID、imeiNo、snNo获取售卖商品详细信息
     * @param vo
     * @return ResponseResult
     */
    @PostMapping(value = "/getSaleCommodityInfoById", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<ProductInfo> getSaleCommodityInfoById(@RequestBody ProductInfo vo) {
        try {
            return this.productService.getSaleCommodityInfoById(vo);
        }catch (Exception e){
            log.error("根据产品ID获取售卖商品详情失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), ResponseStatus.DATA_REQUERY_ERROR.getMessage(),null);
        }
    }

    /**
     * 根据品牌ID，型号ID，成色，分类查询商品列表（售卖）
     * @param
     * @return
     */
    @PostMapping(value = "/queryRentCommodityPage")
    public ResponseResult<?> queryRentCommodityPage(@RequestBody RentProductReq q){
        try{
            return ResponseResult.buildSuccessResponse(productService.queryRentCommodityPage(q));
        }catch (Exception e){
            log.error("根据品牌ID，型号ID，成色，分类获取售卖商品列表失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), ResponseStatus.DATA_REQUERY_ERROR.getMessage(),null);
        }
    }


    /**
     * 根据品牌ID，型号ID，成色，分类查询商品列表（租赁）
     * @param q
     * @return
     */
    @PostMapping(value = "/getRentCommodityInfoByBrandIdAndModelIdAndConfigid")
    public ResponseResult<?> getRentCommodityInfoByBrandIdAndModelIdAndConfigid(@RequestBody RentProductReq q){
        try{
            return ResponseResult.buildSuccessResponse(productService.getRentCommodityInfoByBrandIdAndModelIdAndConfigid(q));
        }catch (Exception e){
            log.error("根据品牌ID，型号ID，成色，分类获取租赁商品列表失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), ResponseStatus.DATA_REQUERY_ERROR.getMessage(),null);
        }
    }



    /**
     * 
    * @Description: 根据品牌ID查询推荐的商品列表(推荐规则：先按相同型号,按品牌,再按其它品牌全部推荐)
    * @param req
    * @return ResponseResult<ResultPager<SaleProductResp>>
     */
    @PostMapping(value = "/queryAvailableProductList")
    public ResponseResult<ResultPager<SaleProductResp>> queryAvailableProductList(@RequestBody SaleProductReq req) {
    	ResponseResult<String> vr = super.getValidatedResult(validator, req, Default.class);
    	if(vr==null) {
    		ResponseResult<ResultPager<SaleProductResp>> res = this.productService.queryAvailableProductList(req);
    		return res;
    	}
    	return ResponseResult.build(1000, vr.getErrMsg(), null);
    }
    
    /**
     * 
    * @Description: 根据品牌ID查询推荐的商品列表(推荐规则：先按相同型号推荐，若无再按品牌推荐，若无再按其它品牌推荐)
    * @param req
    * @return ResponseResult<ResultPager<SaleProductResp>>
     */
    @PostMapping(value = "/queryAvailableSplitProductList")
    public ResponseResult<ResultPager<SaleProductResp>> queryAvailableSplitProductList(@RequestBody SaleProductReq req) {
    	ResponseResult<String> vr = super.getValidatedResult(validator, req, Default.class);
    	if(vr==null) {
    		req.setPageSize(Integer.MAX_VALUE);
    		ResponseResult<ResultPager<SaleProductResp>> res = this.productService.queryAvailableSplitProductList(req);
    		return res;
    	}
    	return ResponseResult.build(1000, vr.getErrMsg(), null);
    }
}
