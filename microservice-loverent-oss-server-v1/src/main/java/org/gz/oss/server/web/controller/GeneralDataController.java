package org.gz.oss.server.web.controller;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.web.controller.BaseController;
import org.gz.oss.common.entity.MaterielModelQuery;
import org.gz.oss.common.feign.IProductService;
import org.gz.warehouse.common.vo.RentProductReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;


/**
 * @author WenMing.Zhou
 * @Description: 通用数据Controller
 * @date 2018/3/22 19:10
 */
@RestController
@Slf4j
public class GeneralDataController extends BaseController{

    @Autowired
    private IProductService productService;


    /**
     * 根据分类ID查询品牌列表
     * @param classId
     * @return
     */
    @GetMapping(value = "/api/queryMaterielBrandListByClassId/{classId}")
    public ResponseResult<?> queryMaterielBrandListByClassId(@PathVariable("classId") Integer classId){
        try {
            return  productService.queryMaterielBrandListByClassId(classId);
        } catch (Exception e) {
            log.error("Get Brand failure：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), ResponseStatus.DATA_REQUERY_ERROR.getMessage(), null);
        }
    }

    /**
     *根据品牌ID查询型号列表
     * @param materielModelQuery
     * @return
     */
    @PostMapping(value = "/api/queryMaterielModelPicListByBrandId")
    public ResponseResult<?> queryMaterielModelPicListByBrandId(@Valid @RequestBody MaterielModelQuery materielModelQuery,BindingResult bindingResult){
        try {
            ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
            if (validateResult==null){
                return productService.queryMaterielModelPicListByBrandId(materielModelQuery);
            }
            return validateResult;
        } catch (Exception e) {
            log.error("Get Model failure：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), ResponseStatus.DATA_REQUERY_ERROR.getMessage(), null);
        }
    }

    /**
     *查询所有的成色配置
     * @return
     */
    @GetMapping(value = "/api/getAllNewConfigs")
    public ResponseResult<?> getAllNewConfigs(){
        try {
            ResponseResult<?> allNewConfigs = productService.getAllNewConfigs();
            return allNewConfigs;
        } catch (Exception e) {
            log.error("getAllNewConfigs failure：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), ResponseStatus.DATA_REQUERY_ERROR.getMessage(), null);
        }
    }

    /**
     * 根据品牌ID，型号ID，成色查询商品列表(售卖的)
     * @param vo
     * @return
     */
    @PostMapping(value = "/api/getSaleCommodityInfoByBrandIdAndModelIdAndConfigid", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> getSaleCommodityInfoByBrandIdAndModelIdAndConfigid(@Valid @RequestBody RentProductReq vo, BindingResult bindingResult){
        try {
            ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
            if (validateResult==null){
                return productService.getSaleCommodityInfoByBrandIdAndModelIdAndConfigid(vo);
            }
            return validateResult;
        } catch (Exception e) {
            log.error("根据品牌ID，型号ID，成色查询售卖商品列表失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), ResponseStatus.DATA_REQUERY_ERROR.getMessage(), null);
        }
    }



    /**
     * 根据品牌ID，型号ID，成色查询商品列表(租赁的)
     * @param vo
     * @return
     */
    @PostMapping(value = "/api/getRentCommodityInfoByBrandIdAndModelIdAndConfigid", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> getRentCommodityInfoByBrandIdAndModelIdAndConfigid(@Valid @RequestBody RentProductReq vo, BindingResult bindingResult){
        try {
            ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
            if (validateResult==null){
                return productService.getRentCommodityInfoByBrandIdAndModelIdAndConfigid(vo);
            }
            return validateResult;
        } catch (Exception e) {
            log.error("根据品牌ID，型号ID，成色查询租赁商品列表失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), ResponseStatus.DATA_REQUERY_ERROR.getMessage(), null);
        }
    }









}
