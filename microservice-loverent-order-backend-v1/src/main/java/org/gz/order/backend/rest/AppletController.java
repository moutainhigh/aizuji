package org.gz.order.backend.rest;


import javax.validation.Validator;
import javax.validation.groups.Default;

import org.apache.commons.lang.StringUtils;
import org.gz.aliOrder.Enum.BackRentState;
import org.gz.aliOrder.Enum.OrderResultCode;
import org.gz.aliOrder.dto.OrderDetailResp;
import org.gz.aliOrder.dto.RentRecordQuery;
import org.gz.aliOrder.dto.UpdateOrderStateReq;
import org.gz.aliOrder.entity.RentLogistics;
import org.gz.aliOrder.entity.RentState;
import org.gz.aliOrder.hystrix.IAliOrderService;
import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.JsonUtils;
import org.gz.common.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

/**
 * 小程序
 * @author phd
 */
@RestController
@RequestMapping("/applet")
public class AppletController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(AppletController.class);

    @Autowired
    IAliOrderService iAliOrderService;
    
    @Autowired
	private Validator validator;
    
    /**
     * 查询订单列表
     * @param rentRecordQuery
     * @return
     */
    @ApiOperation(value = "查询订单列表", httpMethod = "POST", notes = " ResponseResult<ResultPager<OrderDetailResp>>", response = ResponseResult.class)
    @PostMapping(value = "/queryRentRecordList")
    public ResponseResult<ResultPager<OrderDetailResp>> queryRentRecordList(RentRecordQuery rentRecordQuery) {
      ResponseResult<String> validateResult = super.getValidatedResult(this.validator,
        rentRecordQuery,
        Default.class);
        logger.info("queryPageRentRecordList,req={}", JsonUtils.toJsonString(rentRecordQuery));
      if (validateResult == null) {
        try {
          ResponseResult<ResultPager<OrderDetailResp>> result = new ResponseResult<>();
          result = iAliOrderService.queryRentRecordList(rentRecordQuery);
          logger.info("queryPageRentRecordList,rsp={}", JsonUtils.toJsonString(result));
          return result;
        } catch (Exception e) {
          logger.error("queryRentRecordList exception {}", e);
          return ResponseResult.build(1000, e.getLocalizedMessage(), null);
        }
      }
      return ResponseResult.build(validateResult.getErrCode(), validateResult.getErrMsg(), null);
    }
    /**
     * 订单详情
     * @param rentRecordNo
     * @return
     */
    @ApiOperation(value = "订单详情", httpMethod = "GET", notes = " ResponseResult<OrderDetailResp>", response = ResponseResult.class)
    @GetMapping(value = "/detail")
    public ResponseResult<?> detail(String rentRecordNo) {
		try {
			ResponseResult<OrderDetailResp> result= iAliOrderService.queryBackOrderDetail(rentRecordNo);
			logger.info("detail,rsp={}", JsonUtils.toJsonString(JsonUtils.toJsonString(result)));
			return result;
		} catch (Exception e) {
			logger.error("detail exception{}",e);
			return ResponseResult.build(1000, e.getLocalizedMessage(), null);
		}
    }
    
    /**
     * 订单流程信息
     * @param rentRecordNo
     * @return
     */
    @ApiOperation(value = "订单流程信息", httpMethod = "POST", notes = "ResponseResult<ResultPager<RentState>>", response = ResponseResult.class)
    @PostMapping(value = "/rentState")
    public ResponseResult<?> rentState(String rentRecordNo) {
		try {
			ResponseResult<ResultPager<RentState>> result= iAliOrderService.selectRentState(rentRecordNo);
			logger.info("rentState,rsp={}", JsonUtils.toJsonString(JsonUtils.toJsonString(result)));
			return result;
		} catch (Exception e) {
			logger.error("rentState exception{}",e);
			return ResponseResult.build(1000, e.getLocalizedMessage(), null);
		}
    }
    
    /**
     * 订单物流信息
     * @param rentRecordNo
     * @return
     */
    @ApiOperation(value = "订单物流信息", httpMethod = "POST", notes = "ResponseResult<ResultPager<RentLogistics>>", response = ResponseResult.class)
    @PostMapping(value = "/logistics")
    public ResponseResult<?> logistics(String rentRecordNo) {
		try {
			ResponseResult<ResultPager<RentLogistics>> result= iAliOrderService.selectLogistics(rentRecordNo);
			logger.info("logistics,rsp={}", JsonUtils.toJsonString(JsonUtils.toJsonString(result)));
			return result;
		} catch (Exception e) {
			logger.error("logistics exception{}",e);
			return ResponseResult.build(1000, e.getLocalizedMessage(), null);
		}
    }
    
    /**取消订单
     * @param updateOrderStateReq
     * @return
     */
    @ApiOperation(value = "取消订单", httpMethod = "POST", notes = "ResponseResult<String>", response = ResponseResult.class)
    @PostMapping(value = "/cancelOrder")
    public ResponseResult<String> cancleOrder(UpdateOrderStateReq updateOrderStateReq) {
      logger.info("cancleOrder,req={}", updateOrderStateReq);
      try {
        if (StringUtils.isEmpty(updateOrderStateReq.getRentRecordNo())) {
          ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号不能为空", null);
        }
      updateOrderStateReq.setCallType(1);
        updateOrderStateReq.setCreateBy(getAuthUser().getId()); 
        updateOrderStateReq.setCreateMan(getAuthUser().getUserName()); 
        ResponseResult<String> result = iAliOrderService.cancleOrder(updateOrderStateReq);
        logger.info("cancleOrder,rsp={}", JsonUtils.toJsonString(result));
        return result;
      } catch (Exception e) {
        logger.error("cancleOrder exception {}", e);
        return ResponseResult.build(1000, e.getLocalizedMessage(), null);
      }
    }

    /**
     * 确认签收
     * @param updateOrderStateReq
     * @return
     */
    @ApiOperation(value = "确认签收", httpMethod = "POST", notes = " ResponseResult<String>", response = ResponseResult.class)
    @PostMapping(value = "/signedOrder")
    public ResponseResult<String> signedOrder(UpdateOrderStateReq updateOrderStateReq) {
      logger.info("signedOrder,req={}", JsonUtils.toJsonString(updateOrderStateReq));
      try {
        if (StringUtils.isEmpty(updateOrderStateReq.getRentRecordNo())) {
          ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号不能为空", null);
        }
        updateOrderStateReq.setCreateBy(getAuthUser().getId()); 
        updateOrderStateReq.setCreateMan(getAuthUser().getUserName()); 
        updateOrderStateReq.setState(BackRentState.NormalPerformance.getCode());
        ResponseResult<String> result = iAliOrderService.signedOrder(updateOrderStateReq);
        logger.info("signedOrder,rsp={}", JsonUtils.toJsonString(result));
        return result;
      } catch (Exception e) {
        logger.error("signedOrder exception {}", e);
        return ResponseResult.build(1000, e.getLocalizedMessage(), null);
      }
    }
}
