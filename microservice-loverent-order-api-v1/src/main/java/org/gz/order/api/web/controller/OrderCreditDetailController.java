package org.gz.order.api.web.controller;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.JsonUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.order.api.service.OrderCreditDetailService;
import org.gz.order.common.dto.OrderCreditDetailQvo;
import org.gz.order.common.entity.OrderCreditDetail;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderCreditDetailController extends BaseController {

    @Resource
    private OrderCreditDetailService orderCreditDetailService;

    /**
     * 插入
     * RentRecord RentState 
     */
    @PostMapping(value = "/api/orderCreditDetail/addOrderCreditDetail", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> add(@RequestBody OrderCreditDetail orderCreditDetail) {
        ResponseResult<?> result = null;
        try {
            log.info("OrderCreditDetailController addOrderCreditDetail begin.. the param:{}",
                JsonUtils.toJsonString(orderCreditDetail));
            orderCreditDetailService.add(orderCreditDetail);
            result = ResponseResult.buildSuccessResponse();
        } catch (Exception e) {
            log.error("OrderCreditDetailController addOrderCreditDetail error：{}", e);
            result = ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
        log.info("OrderCreditDetailController addOrderCreditDetail end..the returnVal:{}",
            JsonUtils.toJsonString(result));
        return result;
    }

    /**
     * 根据用户id查询历史审核订单（排除当前订单）
     * RentRecord RentState 
     */
    @PostMapping(value = "/api/orderCreditDetail/queryHistoryCreditListByUserId", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> queryHistoryCreditListByUserId(@RequestBody OrderCreditDetailQvo qvo) {
        ResponseResult<?> result = null;
        try {
            log.info("OrderCreditDetailController queryHistoryCreditListByUserId begin.. the param:{}",
                JsonUtils.toJsonString(qvo));
            List<OrderCreditDetail> list = orderCreditDetailService.queryHistoryCreditListByUserId(qvo);
            result = ResponseResult.buildSuccessResponse(list);
        } catch (Exception e) {
            log.error("OrderCreditDetailController queryHistoryCreditListByUserId error：{}", e);
            result = ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
        log.info("OrderCreditDetailController queryHistoryCreditListByUserId end..the returnVal:{}",
            JsonUtils.toJsonString(result));
        return result;
    }
}
