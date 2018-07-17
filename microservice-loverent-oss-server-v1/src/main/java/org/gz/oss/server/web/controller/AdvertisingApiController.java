package org.gz.oss.server.web.controller;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.web.controller.BaseController;
import org.gz.oss.common.service.AdvertisingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * 广告位控制器
 * @author daiqingwen
 * @date 2018/3/13
 */
@RestController
@RequestMapping("/api/advertising")
@Slf4j
public class AdvertisingApiController extends BaseController {

    @Autowired
    private AdvertisingService advertisingService;

    /**
     * 查询
     * @return ResponseResult<Advertising>
     */
    @GetMapping(value = "/queryAdvertisingList")
    public ResponseResult<?> queryAdverList(){
        try {
            return this.advertisingService.queryAdverList();
        } catch (Exception e){
            log.error("获取广告位失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), ResponseStatus.DATA_REQUERY_ERROR.getMessage(), null);
        }
    }

}
