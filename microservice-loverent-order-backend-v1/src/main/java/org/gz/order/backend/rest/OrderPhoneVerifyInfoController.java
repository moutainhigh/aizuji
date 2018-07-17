package org.gz.order.backend.rest;

import java.util.Date;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.JsonUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.order.backend.service.OrderPhoneVerifyInfoService;
import org.gz.order.common.dto.OrderPhoneVerifyInfoQvo;
import org.gz.order.common.entity.OrderPhoneVerifyInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单审核controller
 * @Description:TODO
 * Author	Version		Date		Changes
 * liuyt 	1.0  		2017年12月26日 	Created
 */
@RestController
@RequestMapping("/orderPhoneVerifyInfo")
public class OrderPhoneVerifyInfoController extends BaseController {

    private Logger    logger = LoggerFactory.getLogger(OrderPhoneVerifyInfoController.class);

    @Autowired
    private OrderPhoneVerifyInfoService orderPhoneVerifyInfoService;

    /**
     * 根据id查询
     * RentRecord RentState 
     */
    @PostMapping(value = "/getById")
    public ResponseResult<?> getById(Long id) {
        ResponseResult<?> result = null;
        try {
            logger.info("orderPhoneVerifyInfo getById begin.. the param:{}", id);
            OrderPhoneVerifyInfo info = orderPhoneVerifyInfoService.getById(id);
            result = ResponseResult.buildSuccessResponse(info);
        } catch (Exception e) {
            logger.error("orderPhoneVerifyInfo getById error：{}", e);
            result = ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
        logger.info("orderPhoneVerifyInfo getById end.. the returnVal:{}", JsonUtils.toJsonString(result));
        return result;
    }

    /**
     * 插入
     * RentRecord RentState 
     */
    @PostMapping(value = "/add")
    public ResponseResult<?> add(OrderPhoneVerifyInfo orderPhoneVerifyInfo) {
        ResponseResult<?> result = null;
        try {
            logger.info("orderPhoneVerifyInfo add begin.. the param:{}", JsonUtils.toJsonString(orderPhoneVerifyInfo));
            Long userId = super.getAuthUserId();
            orderPhoneVerifyInfo.setCreateBy(userId);
            orderPhoneVerifyInfo.setCreateOn(new Date());
            orderPhoneVerifyInfo.setUpdateOn(new Date());
            orderPhoneVerifyInfo.setUpdateBy(userId);
            orderPhoneVerifyInfoService.add(orderPhoneVerifyInfo);
            result = ResponseResult.buildSuccessResponse();
        } catch (Exception e) {
            logger.error("orderPhoneVerifyInfo add error：{}", e);
            result = ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
        logger.info("orderPhoneVerifyInfo add end.. the returnVal:{}", JsonUtils.toJsonString(result));
        return result;
    }

    /**
     * 更新审核记录
     * RentRecord RentState 
     */
    @PostMapping(value = "/update")
    public ResponseResult<?> update(OrderPhoneVerifyInfo orderPhoneVerifyInfo) {
        ResponseResult<?> result = null;
        try {
            logger.info("orderPhoneVerifyInfo update begin.. the param:{}",
                JsonUtils.toJsonString(orderPhoneVerifyInfo));
            Long userId = super.getAuthUserId();
            orderPhoneVerifyInfo.setUpdateOn(new Date());
            orderPhoneVerifyInfo.setUpdateBy(userId);
            orderPhoneVerifyInfoService.update(orderPhoneVerifyInfo);
            result = ResponseResult.buildSuccessResponse();
        } catch (Exception e) {
            logger.error("orderPhoneVerifyInfo update error：{}", e);
            return ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
        logger.info("orderPhoneVerifyInfo update end.. the returnVal:{}", JsonUtils.toJsonString(result));
        return result;
    }

    /**
     * 查询审核记录
     * RentRecord RentState 
     */
    @PostMapping(value = "/queryList")
    public ResponseResult<?> queryList(OrderPhoneVerifyInfoQvo qvo) {
        ResponseResult<?> result = null;
        try {
            logger.info("orderPhoneVerifyInfo queryList begin.. the param:{}", JsonUtils.toJsonString(qvo));
            ResultPager<OrderPhoneVerifyInfo> list = orderPhoneVerifyInfoService.queryList(qvo);
            result = ResponseResult.buildSuccessResponse(list);
        } catch (Exception e) {
            logger.error("orderPhoneVerifyInfo queryList error：{}", e);
            result = ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
        logger.info("orderPhoneVerifyInfo queryList end.. the returnVal:{}", JsonUtils.toJsonString(result));
        return result;
    }

    /**
     * 删除审核记录
     * RentRecord RentState 
     */
    @PostMapping(value = "/delete")
    public ResponseResult<?> delete(Long id) {
        ResponseResult<?> result = null;
        try {
            logger.info("orderPhoneVerifyInfo delete begin.. the param:{}", id);
            orderPhoneVerifyInfoService.delete(id);
            result = ResponseResult.buildSuccessResponse();
        } catch (Exception e) {
            logger.error("orderPhoneVerifyInfo delete error：{}", e);
            result = ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
        logger.info("orderPhoneVerifyInfo delete end.. the returnVal:{}", JsonUtils.toJsonString(result));
        return result;
    }
}
