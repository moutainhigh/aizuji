package org.gz.oss.backend.web.controller;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.StringUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.oss.common.entity.Advertising;
import org.gz.oss.common.entity.AdvertisingVO;
import org.gz.oss.common.service.AdvertisingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * 广告位控制器
 * @author daiqingwen
 * @date 2018/3/13
 */
@RestController
@RequestMapping("/advertising")
@Slf4j
public class AdvertisingController extends BaseController {

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


    /**
     * 新增
     * @param vo
     * @return ResponseResult
     */
    @PostMapping(value = "/insertAdv" )
    public ResponseResult<?> insert( AdvertisingVO vo){
        if(StringUtils.isEmpty(vo)){
            return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), "参数为空", null);
        }
        try {
            return this.advertisingService.insert(vo);
        } catch (Exception e){
            log.error("新增广告位失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), ResponseStatus.DATA_CREATE_ERROR.getMessage(), null);
        }
    }

    /**
     * 修改
     * @param vo
     * @return ResponseResult
     */
    @PostMapping(value = "/updateAdv")
    public ResponseResult<?> update( AdvertisingVO vo){
        if(StringUtils.isEmpty(vo)){
            return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(), "参数为空", null);
        }
        try {
            return this.advertisingService.update(vo);
        } catch (Exception e){
            log.error("修改广告位失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(), ResponseStatus.DATA_UPDATED_ERROR.getMessage(), null);
        }
    }

    /**
     * 刪除
     * @param id
     * @return ResponseResult
     */
    @PostMapping(value = "/delete")
    public ResponseResult<?> delete( int id){
        if(StringUtils.isEmpty(id)){
            return ResponseResult.build(ResponseStatus.DATA_DELETED_ERROR.getCode(), "参数为空", null);
        }
        try {
            return this.advertisingService.delete(id);
        } catch (Exception e){
            log.error("刪除广告位失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_DELETED_ERROR.getCode(), ResponseStatus.DATA_DELETED_ERROR.getMessage(), null);
        }
    }

    /**
     * 广告位上移/下移
     * @param vo
     * @return ResponseResult
     */
    @PostMapping(value = "/move")
    public ResponseResult<?> move( AdvertisingVO vo){
        if(vo.getAttaList().size() <= 0){
            return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(), "参数为空", null);
        }
        try {
            return this.advertisingService.move(vo);
        } catch (Exception e){
            log.error("广告位上移/下移失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(), ResponseStatus.DATA_UPDATED_ERROR.getMessage(), null);
        }

    }

    /**
     * 根据广告位ID，获取广告位信息
     * @param id 广告位ID
     * @return ResponseResult
     */
    @PostMapping(value = "/getAdvertisingById")
    public ResponseResult<Advertising> getAdvertisingById(int id){
        if(StringUtils.isEmpty(id)){
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), "参数为空", null);
        }
        try {
            return this.advertisingService.getAdvertisingById(id);
        }catch (Exception e){
            log.error("广告位上移/下移失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(), ResponseStatus.DATA_UPDATED_ERROR.getMessage(), null);
        }
    }



}
