package org.gz.oss.backend.web.controller;

import org.apache.commons.collections.CollectionUtils;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.StringUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.oss.common.entity.*;
import org.gz.oss.common.service.ShopwindowService;
import org.gz.warehouse.common.vo.ProductInfoQvo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;


/**
 * 橱窗位控制器
 * @author daiqingwen
 * @date 2018/3/13 下午 19:36
 */
@RestController
@Slf4j
@RequestMapping("/shopwindow")
public class ShopwindowController extends BaseController {

    @Autowired
    public ShopwindowService shopwindowService;

    /**
     * 修改橱窗位
     * @param sho
     * @return ResponseResult
     */
    @PostMapping(value = "/updateShopwindow")
    public ResponseResult<?> update( ShopwindowVO sho ){
        if(StringUtils.isEmpty(sho)){
            return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(), "参数为空", null);
        }
        try {
            return this.shopwindowService.update(sho);
        } catch (Exception e){
            log.error("修改橱窗位失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(), ResponseStatus.DATA_UPDATED_ERROR.getMessage(), null);
        }
    }

    /**
     * 新增活动时间
     * @param at
     * @return ResponseResult
     */
    @PostMapping(value = "/insertActivityTime")
    public ResponseResult<?> insertActivity( ActivityCommodity at){
        if(StringUtils.isEmpty(at)){
            return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), "参数为空", null);
        }
        try {
            return this.shopwindowService.insertActivityTime(at);
        } catch (Exception e){
            log.error("新增活动时间失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), ResponseStatus.DATA_CREATE_ERROR.getMessage(), null);
        }
    }

    /**
     * 修改活动时间
     * @param at
     * @return ResponseResult
     */
    @PostMapping(value = "/updateActivityTime")
    public ResponseResult<?> updateActivity( ActivityCommodity at){
        if(StringUtils.isEmpty(at)){
            return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(), "参数为空", null);
        }
        try {
            return this.shopwindowService.updateActivityTime(at);
        } catch (Exception e){
            log.error("修改活动时间失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(), ResponseStatus.DATA_UPDATED_ERROR.getMessage(), null);
        }

    }

    /**
     * 删除活动
     * @param id 活动ID
     * @return ResponseResult
     */
    @PostMapping(value = "/deleteActivity")
    public ResponseResult<?> deleteActivity( Long id){
        if(StringUtils.isEmpty(id)){
            return ResponseResult.build(ResponseStatus.DATA_DELETED_ERROR.getCode(), "参数为空", null);
        }
        try {
            return this.shopwindowService.deleteActivity(id);
        }catch (Exception e){
            log.error("删除活动失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_DELETED_ERROR.getCode(), ResponseStatus.DATA_DELETED_ERROR.getMessage(), null);
        }

    }

    /**
     * 新增活动时间和商品
     * @param ac
     * @return ResponseResult
     */
    @PostMapping(value = "/insertActivityCommodity")
    public ResponseResult<?> insertActivityCommodity( ActivityCommodity ac){
        if(StringUtils.isEmpty(ac)){
            return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), "参数为空", null);
        }
        try {
            return this.shopwindowService.insertActivityCommodity(ac);
        }catch (Exception e){
            log.error("新增活动商品失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), ResponseStatus.DATA_CREATE_ERROR.getMessage(), null);
        }

    }

    /**
     * 新增售卖和租赁商品
     * @param sh
     * @return ResponseResult
     */
    @PostMapping(value = "/insertSaleAndLeaseCommodity")
    public ResponseResult<?> insertSaleAndLeaseCommodity(ActivityCommodity sh ){
        if(StringUtils.isEmpty(sh)){
            return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), "参数为空", null);
        }
        try {
            return this.shopwindowService.insertSaleAndLeaseCommodity(sh);
        }catch (Exception e){
            log.error("新增售卖和销售商品失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), ResponseStatus.DATA_CREATE_ERROR.getMessage(), null);
        }
    }


    /**
     * 根据活动商品Id和活动ID，修改活动商品
     * @param sh
     * @return ResponseResult
     */
    @PostMapping(value = "/updateActivityCommodity")
    public ResponseResult<?> updateActivityCommodity( ActivityCommodity sh){
        if(StringUtils.isEmpty(sh)){
            return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(), "参数为空", null);
        }
        try {
            return this.shopwindowService.updateActivityCommodity(sh);
        }catch (Exception e){
            log.error("修改活动商品失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), ResponseStatus.DATA_CREATE_ERROR.getMessage(), null);
        }
    }

    /**
     * 根据商品ID，修改售卖和租赁的商品
     * @param ac
     * @return ResponseResult
     */
    @PostMapping(value = "/updateSaleAndLeaseCommodity")
    public ResponseResult<?> updateSaleAndLeaseCommodity(ActivityCommodity ac){
        if(StringUtils.isEmpty(ac)){
            return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(), "参数为空", null);
        }
        try {
            return this.shopwindowService.updateSaleAndLeaseCommodity(ac);
        }catch (Exception e){
            log.error("修改活动商品失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), ResponseStatus.DATA_CREATE_ERROR.getMessage(), null);
        }
    }

    /**
     * 获取活动列表
     * @return ResponseResult
     */
    @GetMapping(value = "/queryActivityList")
    public ResponseResult<?> queryActivityList(){
        try {
            return this.shopwindowService.queryActivityList();
        } catch (Exception e){
            log.error("获取活动列表失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), ResponseStatus.DATA_REQUERY_ERROR.getMessage(), null);
        }
    }

    /**
     * 根据活动ID，获取活动对应商品
     * @param id
     * @return ResponseResult
     */
    @PostMapping(value = "/queryActivityCommodity")
    public ResponseResult<?> queryActivityCommodity( int id){
        if(StringUtils.isEmpty(id)){
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), "参数为空", null);
        }
        try {
            return this.shopwindowService.queryActivityCommodity(id);
        }catch (Exception e){
            log.error("根据橱窗位ID，获取活动对应商品失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), ResponseStatus.DATA_REQUERY_ERROR.getMessage(), null);
        }
    }

    /**
     * 根据橱窗位ID获取橱窗位对应的商品
     * @param id
     * @return ResponseResult
     */
    @PostMapping(value = "/queryShopwindowCommodityById")
    public ResponseResult<?> queryShopwindowCommodityById( int id){
        if(StringUtils.isEmpty(id)){
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), "参数为空", null);
        }
        try {
            return this.shopwindowService.queryShopwindowCommodityById(id);
        }catch (Exception e){
            log.error("根据橱窗位ID，获取活动对应商品失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), ResponseStatus.DATA_REQUERY_ERROR.getMessage(), null);
        }
    }

    /**
     * 获取所有橱窗位名称和对应商品数量
     * @return ResponseResult
     */
    @PostMapping(value = "/findShopwindowCount")
    public ResponseResult<?> findShopwindowCount(){
            try {
            return this.shopwindowService.findShopwindowCount();
        }catch (Exception e){
            log.error("根据橱窗位ID，获取活动对应商品失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), ResponseStatus.DATA_REQUERY_ERROR.getMessage(), null);
        }
    }

    /**
     * 根据多个型号ID，获取最低月租金
     * @param vo
     * @return
     */
    @PostMapping("/getAmountByModelId")
    public ResponseResult<?> getAmountByModelId(@RequestBody ProductInfoQvo vo){
        if(CollectionUtils.isEmpty(vo.getMaterielModelIdList())){
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(),"参数为空",null);
        }
        try {
            return this.shopwindowService.getAmountByModelId(vo);
        }catch (Exception e){
            log.error("根据型号ID查询最低月租金失败：{}" + e);
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(),ResponseStatus.DATA_REQUERY_ERROR.getMessage(),null);
        }
    }


}
