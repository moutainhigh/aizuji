package org.gz.oss.server.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.gz.common.resp.ResponseResult;
import org.gz.common.web.controller.BaseController;
import org.gz.oss.common.feign.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author WenMing.Zhou    先放下，3个参数少一个产品ID
 * @Description: 购物Controller
 * @date 2018/3/22 14:27
 */
@RestController
@RequestMapping("/shopping")
@Slf4j
public class SaleController extends BaseController {

    @Autowired
    private IProductService iProductService;


    /**
     * 获取产品规格信息
     * @param list
     * @return
     */
    @PostMapping(value = "/getSpecValue")
    public ResponseResult<?> querySpecValue(@RequestParam List<Map<String,Object>> list){
        ResponseResult<?> saleCommodityInfo = iProductService.getSaleCommodityInfo(list);
        return saleCommodityInfo;
    }


}
