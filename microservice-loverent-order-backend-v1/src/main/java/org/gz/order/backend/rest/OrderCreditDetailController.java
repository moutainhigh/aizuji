package org.gz.order.backend.rest;

import java.util.Date;
import java.util.List;

import org.gz.common.entity.AuthUser;
import org.gz.common.entity.ResultPager;
import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.JsonUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.order.backend.service.OrderCreditDetailService;
import org.gz.order.common.dto.OrderCreditDetailQvo;
import org.gz.order.common.entity.OrderCreditDetail;
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
@RequestMapping("/orderCreditDetail")
public class OrderCreditDetailController extends BaseController {

    private Logger    logger = LoggerFactory.getLogger(OrderCreditDetailController.class);

    @Autowired
    private OrderCreditDetailService orderCreditDetailService;

    /**
     * 根据id查询
     * RentRecord RentState 
     */
    @PostMapping(value = "/getById")
    public ResponseResult<?> getById(Long id) {
        ResponseResult<?> result = null;
        try {
            logger.info("orderCreditDetail getById begin.. the param:{}", id);
            OrderCreditDetail detail = orderCreditDetailService.getById(id);
            result = ResponseResult.buildSuccessResponse(detail);
        } catch (Exception e) {
            logger.error("orderCreditDetail getById error：{}", e);
            result = ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
        logger.info("orderCreditDetail getById end.. the returnVal:{}", JsonUtils.toJsonString(result));
        return result;
    }

    /**
     * 插入
     * RentRecord RentState 
     */
    @PostMapping(value = "/add")
    public ResponseResult<?> add(OrderCreditDetail orderCreditDetail) {
        ResponseResult<?> result = null;
        try {
            logger.info("orderCreditDetail add begin.. the param:{}", JsonUtils.toJsonString(orderCreditDetail));
            Long userId = super.getAuthUserId();
            orderCreditDetail.setCreateBy(userId);
            orderCreditDetail.setCreateOn(new Date());
            orderCreditDetail.setCreditOn(new Date());
            orderCreditDetail.setUpdateBy(userId);
            orderCreditDetailService.add(orderCreditDetail);
            result = ResponseResult.buildSuccessResponse();
        } catch (Exception e) {
            logger.error("orderCreditDetail add error：{}", e);
            result = ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
        logger.info("orderCreditDetail add end.. the returnVal:{}", JsonUtils.toJsonString(result));
        return result;
    }

    /**
     * 更新审核记录
     * RentRecord RentState 
     */
    @PostMapping(value = "/update")
    public ResponseResult<?> update(OrderCreditDetail orderCreditDetail) {
        ResponseResult<?> result = null;
        try {
            logger.info("orderCreditDetail update begin.. the param:{}", JsonUtils.toJsonString(orderCreditDetail));
            Long userId = super.getAuthUserId();
            orderCreditDetail.setCreditOn(new Date());
            orderCreditDetail.setUpdateBy(userId);
            orderCreditDetailService.update(orderCreditDetail);
            result = ResponseResult.buildSuccessResponse();
        } catch (Exception e) {
            logger.error("orderCreditDetail update error：{}", e);
            result = ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
        logger.info("orderCreditDetail update end.. the returnVal:{}", JsonUtils.toJsonString(result));
        return result;
    }

    /**
     * 查询审核记录
     * RentRecord RentState 
     */
    @PostMapping(value = "/queryList")
    public ResponseResult<?> queryList(OrderCreditDetailQvo qvo) {
        ResponseResult<?> result = null;
        try {
            logger.info("orderCreditDetail queryList begin.. the param:{}", JsonUtils.toJsonString(qvo));
            ResultPager<OrderCreditDetail> list = orderCreditDetailService.queryList(qvo);
            result = ResponseResult.buildSuccessResponse(list);
        } catch (Exception e) {
            logger.error("orderCreditDetail queryList error：{}", e);
            result = ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
        logger.info("orderCreditDetail queryList end.. the returnVal:{}", JsonUtils.toJsonString(result));
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
            logger.info("orderCreditDetail delete begin.. the param:{}", id);
            orderCreditDetailService.delete(id);
            result = ResponseResult.buildSuccessResponse();
        } catch (Exception e) {
            logger.error("orderCreditDetail delete error：{}", e);
            result = ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
        logger.info("orderCreditDetail delete end.. the returnVal:{}", JsonUtils.toJsonString(result));
        return result;
    }

    /**
     * 订单审核
     * RentRecord RentState 
     */
    @PostMapping(value = "/orderAudit")
    public ResponseResult<?> orderAudit(OrderCreditDetail orderCreditDetail) {
        ResponseResult<?> result = null;
        try {
            logger.info("orderCreditDetail orderAudit begin.. the param:{}", JsonUtils.toJsonString(orderCreditDetail));
            AuthUser user = super.getAuthUser();
            Long userId = user.getId();
            String userName = user.getUserName();
            orderCreditDetail.setCreateBy(userId);
            orderCreditDetail.setCreateOn(new Date());
            orderCreditDetail.setCreditOn(new Date());
            orderCreditDetail.setUpdateBy(userId);
            orderCreditDetail.setUpdateName(userName);
            orderCreditDetailService.orderAudit(orderCreditDetail);
            result = ResponseResult.buildSuccessResponse();
        } catch (ServiceException se) {
            result = ResponseResult.build(se.getErrorCode(), se.getErrorMsg(), null);
        } catch (Exception e) {
            logger.error("orderCreditDetail orderAudit error：{}", e);
            result = ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
        logger.info("orderCreditDetail orderAudit end.. the returnVal:{}", JsonUtils.toJsonString(result));
        return result;
    }

    /**
     * 根据用户id查询对应审核通过的记录数
     * RentRecord RentState 
     */
    @PostMapping(value = "/queryCountByCustomerCreditList")
    public ResponseResult<?> queryCountByCustomerCreditList(OrderCreditDetailQvo qvo) {
        ResponseResult<?> result = null;
        try {
            logger.info("orderCreditDetail queryCountByCustomerCreditList begin.. the param:{}",
                JsonUtils.toJsonString(qvo));
            Integer count = orderCreditDetailService.queryCountByCustomerCreditList(qvo);
            result = ResponseResult.buildSuccessResponse(count);
        } catch (Exception e) {
            logger.error("orderCreditDetail queryCountByCustomerCreditList error：{}", e);
            result = ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
        logger.info("orderCreditDetail queryCountByCustomerCreditList end.. the returnVal:{}",
            JsonUtils.toJsonString(result));
        return result;
    }

    /**
     * 分页查询审核记录
     * RentRecord RentState 
     */
    @PostMapping(value = "/queryCustomerCreditList")
    public ResponseResult<?> queryCustomerCreditList(OrderCreditDetailQvo qvo) {
        ResponseResult<?> result = null;
        try {
            logger.info("orderCreditDetail queryCustomerCreditList begin.. the param:{}", JsonUtils.toJsonString(qvo));
            ResultPager<OrderCreditDetail> list = orderCreditDetailService.queryCustomerCreditList(qvo);
            result = ResponseResult.buildSuccessResponse(list);
        } catch (Exception e) {
            logger.error("orderCreditDetail queryCustomerCreditList error：{}", e);
            result = ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
        logger.info("orderCreditDetail queryCustomerCreditList end.. the returnVal:{}", JsonUtils.toJsonString(result));
        return result;
    }
    
    /**
     * 审核记录
     * RentRecord RentState 
     */
    @PostMapping(value = "/customerCreditList")
    public ResponseResult<?> queryCustomerCreditList(String orderNo) {
        ResponseResult<?> result = null;
        try {
            logger.info("orderCreditDetail customerCreditList begin.. the param:{}", orderNo);
            ResultPager<OrderCreditDetail> rp = orderCreditDetailService.customerCreditList(orderNo);
            result = ResponseResult.buildSuccessResponse(rp);
        } catch (Exception e) {
            logger.error("orderCreditDetail customerCreditList error：{}", e);
            result = ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
        logger.info("orderCreditDetail customerCreditList end.. the returnVal:{}", JsonUtils.toJsonString(result));
        return result;
    }

}
