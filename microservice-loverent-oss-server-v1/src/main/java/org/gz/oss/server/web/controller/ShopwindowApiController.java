package org.gz.oss.server.web.controller;

import lombok.extern.slf4j.Slf4j;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.oss.common.service.ShopwindowService;
import org.gz.warehouse.common.vo.ProductInfo;
import org.gz.warehouse.common.vo.ProductInfoQvo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 橱窗位控制器
 * @author daiqingwen
 * @date 2018/3/19 下午 16:16
 */

@RestController
@Slf4j
@RequestMapping("/api/shopwindow")
public class ShopwindowApiController {

    @Autowired
    public ShopwindowService shopwindowService;


    /**
     * 获取首页信息
     * @return ResponseResult
     */
    @PostMapping("/findIndexInfo")
    public ResponseResult<?> findIndexInfo(){
        try {
            return this.shopwindowService.findIndexInfo();
        }catch (Exception e){
            log.error("app获取首页信息失败：{}",e);
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), ResponseStatus.DATA_REQUERY_ERROR.getMessage(), null);
        }
    }

    /**
     * 根据产品ID、imeiNo、snNo获取售卖商品详情
     * @return ResponseResult
     */
    @PostMapping("/getSaleCommodityById")
    public ResponseResult<?> getSaleCommodityById(@RequestBody ProductInfo param){
        try {
           return this.shopwindowService.getSaleCommodityById(param);
        }catch (Exception e){
            log.error("app根据产品ID获取售卖商品详情失败：{}",e);
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), ResponseStatus.DATA_REQUERY_ERROR.getMessage(), null);
        }
    }


}
