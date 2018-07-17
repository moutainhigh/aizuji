package org.gz.oss.backend.web.controller;

import javax.servlet.http.HttpSession;

import org.gz.common.entity.AuthUser;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.web.controller.BaseController;
import org.gz.oss.common.feign.IProductService;
import org.gz.oss.common.service.ProductService;
import org.gz.warehouse.common.vo.ProductInfo;
import org.gz.warehouse.common.vo.ProductInfoQvo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ProductController extends BaseController {

    @Autowired
    private IProductService iPdoductService;


    @Autowired
    private ProductService productService;

    /**
     * 获取产品分页列表
     * @param
     * @return
     */
    @RequestMapping(value = "/product/queryProductList")
    public ResponseResult<?> queryProductList(ProductInfoQvo qvo) {
        try {
            return iPdoductService.queryProductList(qvo);
        } catch (Exception e) {
            log.error("queryProductList失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }
    
    /**
     * 获取所有产品列表
     * @return
     * @throws
     * @createBy:zhangguoliang
     * createDate:2017年12月22日
     */
    @PostMapping(value = "/product/queryAllMaterielModel")
    public ResponseResult<?> queryAllProductList(@RequestBody ProductInfoQvo qvo) {
        try {
            return iPdoductService.queryAllMaterielModel(qvo);
        } catch (Exception e) {
            log.error("queryAllProductList失败：{}", e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }

    /**
     * 获取租期列表
     * @param
     * @return
     */
    @PostMapping(value = "/product/queryAllProductLeaseTerm")
    public ResponseResult<?> queryAllProductLeaseTerm(ProductInfoQvo qvo) {
        try {
            return iPdoductService.queryAllProductLeaseTerm(qvo);
        } catch (Exception e) {
            log.error("queryAllProductLeaseTerm失败：{}", e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }

    /**
     * 添加产品
     * @param
     * @return
     */
    @PostMapping(value = "/product/addProduct")
    public ResponseResult<?> addProduct(ProductInfo pInfo) {
        try {
            Long userId = this.getCurrentUserId().longValue();
            pInfo.setCreateBy(userId);
            pInfo.setUpdateBy(userId);
            ResponseResult<?> result = iPdoductService.addProduct(pInfo);
            return result;
        } catch (Exception e) {
            log.error("addProduct失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(),
                ResponseStatus.DATA_CREATE_ERROR.getMessage(),
                null);
        }
    }

    /**
     * 更新产品上下架
     * @param
     * @return
     */
    @PostMapping(value = "/product/updateProductIsEnable")
    public ResponseResult<?> updateProductIsEnable(ProductInfo pInfo) {
        try {
            Long userId = this.getCurrentUserId().longValue();
            pInfo.setUpdateBy(userId);
            //先判断此产品是否存在售卖橱窗、租赁橱窗、抢购、banner位置
            int isHasConfig = productService.queryProductIsHasConfig(pInfo.getId());
            if (isHasConfig>0){
             return ResponseResult.build(ResponseStatus.PRODUCT_HAS_CONFIG_ERROR.getCode(),
                     ResponseStatus.PRODUCT_HAS_CONFIG_ERROR.getMessage(),
                     null);
            }
            ResponseResult<?> result = iPdoductService.updateProductIsEnable(pInfo);
            return result;
        } catch (Exception e) {
            log.error("updateProductIsEnable失败：{}", e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }

    /**
     * 更新产品信息
     * @param
     * @return
     */
    @PostMapping(value = "/product/updateProduct")
    public ResponseResult<?> updateProduct(ProductInfo pInfo) {
        try {
            Long userId = this.getCurrentUserId().longValue();
            pInfo.setUpdateBy(userId);
          //  pInfo.setUpdateOn(new Date());
            ResponseResult<?> result = iPdoductService.updateProduct(pInfo);
            return result;
        } catch (Exception e) {
            log.error("updateProduct失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }

    /**
     * 根据产品id或编号获取产品信息
     * @param
     * @return
     */
    @PostMapping(value = "/product/getByIdOrPdtNo")
    public ResponseResult<?> getByIdOrPdtNo(ProductInfo pInfo) {
        try {
            ResponseResult<?> result = iPdoductService.getByIdOrPdtNo(pInfo);
            return result;
        } catch (Exception e) {
            log.error("getByIdOrPdtNo失败：{}", e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }

    private Long getCurrentUserId() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpSession session = attrs.getRequest().getSession(true);
        Object obj = session.getAttribute("admin");
        return obj == null ? 0 : ((AuthUser) obj).getId();
    }

    /**
     * 获取租期列表
     * @param
     * @return
     */
    @PostMapping(value = "/product/queryAllNewConfigs")
    public ResponseResult<?> queryAllNewConfigs() {
        try {
            return iPdoductService.queryAllNewConfigs();
        } catch (Exception e) {
            log.error("queryAllNewConfigs失败：{}", e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }

    /**
     * 查询售卖和租赁商品列表
     * @param qvo
     * @return
     */
    @PostMapping(value = "/product/getProductInfoWithCommodityList")
    public ResponseResult<?> getProductInfoWithCommodityList(ProductInfoQvo qvo) {
        try {
            return iPdoductService.getProductInfoWithCommodityList(qvo);
        } catch (Exception e) {
            log.error("getProductInfoWithCommodityList失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }







}
