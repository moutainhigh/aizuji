/**
 * 
 */
package org.gz.aliOrder.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Validator;
import javax.validation.groups.Default;

import org.apache.commons.lang.StringUtils;
import org.gz.aliOrder.Enum.BackRentState;
import org.gz.aliOrder.Enum.OrderResultCode;
import org.gz.aliOrder.dto.AddOrderReq;
import org.gz.aliOrder.dto.AddOrderResp;
import org.gz.aliOrder.dto.OrderDetailReq;
import org.gz.aliOrder.dto.OrderDetailResp;
import org.gz.aliOrder.dto.OrderDetailRespForWare;
import org.gz.aliOrder.dto.RentLogisticsDto;
import org.gz.aliOrder.dto.RentRecordQuery;
import org.gz.aliOrder.dto.SubmitOrderReq;
import org.gz.aliOrder.dto.UpdateCreditAmountReq;
import org.gz.aliOrder.dto.UpdateOrderStateReq;
import org.gz.aliOrder.entity.RentState;
import org.gz.aliOrder.service.AliRentRecordService;
import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.JsonUtils;
import org.gz.common.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

/**
 * Author Version Date Changes 临时工 1.0 2017年12月13日 Created
 */
@RestController
public class RentRecordController extends BaseController {

  Logger logger = LoggerFactory.getLogger(RentRecordController.class);

  @Autowired
  private AliRentRecordService rentRecordService;

	@Autowired
	private Validator validator;

	/**
   * 添加订单接口
   * 
   * @param AddOrderReq
   * @param bindingResult
   * @return
   * @throws createBy:临时工 createDate:2018年3月26日
   */
  @ApiOperation(value = "添加订单接口", httpMethod = "POST", notes = " ResponseResult<AddOrderResp>", response = ResponseResult.class)
	@PostMapping(value = "/api/aliOrder/addOrder", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<AddOrderResp> addOrder(@RequestBody AddOrderReq addOrderRequest) {
    ResponseResult<String> validateResult = super.getValidatedResult(this.validator, addOrderRequest, Default.class);
    logger.info("addOrder,req={}", JsonUtils.toJsonString(addOrderRequest));
    if (validateResult == null) {
      try {
        ResponseResult<AddOrderResp> result = rentRecordService.add(addOrderRequest);
        logger.info("addOrder,rsp={}", JsonUtils.toJsonString(result));
        return result;
      } catch (Exception e) {
        logger.error("addOrder exception {}", e);
        return ResponseResult.build(1000, e.getLocalizedMessage(), null);
      }
    }
    return ResponseResult.build(validateResult.getErrCode(), validateResult.getErrMsg(), null);

	}

  /**
   * 确认提交订单成功异步通知更新信用免押积分和芝麻单号
   * 
   * @param UpdateCreditAmountReq
   * @param bindingResult
   * @return
   * @throws createBy:临时工 createDate:2018年3月26日
   */
  @ApiOperation(value = "订单创建成功回调接口将订单状态改为待支付", httpMethod = "POST", notes = " ResponseResult<UpdateCreditAmountReq>", response = ResponseResult.class)
  @PostMapping(value = "/api/aliOrder/updateCreditAmount", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<String> updateCreditAmount(@RequestBody UpdateCreditAmountReq updateCreditAmountReq) {
    ResponseResult<String> validateResult = super.getValidatedResult(this.validator,
      updateCreditAmountReq,
      Default.class);
    logger.info("updateCreditAmount,req={}", JsonUtils.toJsonString(updateCreditAmountReq));
    if (validateResult == null) {
      try {
        ResponseResult<String> result = rentRecordService.updateCreditAmount(updateCreditAmountReq);
        logger.info("updateCreditAmount,rsp={}", JsonUtils.toJsonString(result));
        return result;
      } catch (Exception e) {
        logger.error("updateCreditAmount exception {}", e);
        return ResponseResult.build(1000, e.getLocalizedMessage(), null);
      }
    }
    return ResponseResult.build(validateResult.getErrCode(), validateResult.getErrMsg(), null);

  }

  @ApiOperation(value = "确认提交订单修改订单信息", httpMethod = "POST", notes = " ResponseResult<String>", response = ResponseResult.class)
  @PostMapping(value = "/api/aliOrder/submitOrder", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<String> submitOrder(@RequestBody SubmitOrderReq submitOrderReq) {
    logger.info("submitOrder,req={}", JsonUtils.toJsonString(submitOrderReq));
    try {
      if (StringUtils.isEmpty(submitOrderReq.getRentRecordNo())) {
        ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号不能为空", null);
      }
      ResponseResult<String> result = rentRecordService.submitOrder(submitOrderReq);
      logger.info("submitOrder,rsp={}", JsonUtils.toJsonString(result));
      return result;
    } catch (Exception e) {
      logger.error("submitOrder exception {}", e);
      return ResponseResult.build(1000, e.getLocalizedMessage(), null);
    }
  }

  @ApiOperation(value = "取消订单", httpMethod = "POST", notes = "UpdateOrderStateReq", response = ResponseResult.class)
  @PostMapping(value = "/api/aliOrder/cancleOrder", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<String> cancleOrder(@RequestBody UpdateOrderStateReq updateOrderStateReq) {
    logger.info("cancleOrder,req={}", updateOrderStateReq);
    try {
      if (StringUtils.isEmpty(updateOrderStateReq.getRentRecordNo())) {
        ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号不能为空", null);
      }
      ResponseResult<String> result = rentRecordService.cancleOrder(updateOrderStateReq);
      logger.info("cancleOrder,rsp={}", JsonUtils.toJsonString(result));
      return result;
    } catch (Exception e) {
      logger.error("cancleOrder exception {}", e);
      return ResponseResult.build(1000, e.getLocalizedMessage(), null);
    }
  }

  @ApiOperation(value = "确认签收", httpMethod = "POST", notes = " ResponseResult<String>", response = ResponseResult.class)
  @PostMapping(value = "/api/aliOrder/signedOrder", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<String> signedOrder(@RequestBody UpdateOrderStateReq updateOrderStateReq) {
    logger.info("signedOrder,req={}", JsonUtils.toJsonString(updateOrderStateReq));
    try {
      if (StringUtils.isEmpty(updateOrderStateReq.getRentRecordNo())) {
        ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号不能为空", null);
      }
      updateOrderStateReq.setState(BackRentState.NormalPerformance.getCode());
      ResponseResult<String> result = rentRecordService.signedOrder(updateOrderStateReq);
      logger.info("signedOrder,rsp={}", JsonUtils.toJsonString(result));
      return result;
    } catch (Exception e) {
      logger.error("signedOrder exception {}", e);
      return ResponseResult.build(1000, e.getLocalizedMessage(), null);
    }
  }

  /**
   * @description 库存系统发起 待发货传入状态 BackRentState.WaitSend.getCode()
   * @param updateOrderStateReq
   * @return
   * @throws createBy:临时工 createDate:2018年3月27日
   */
  @ApiOperation(value = "更新订单状态", httpMethod = "POST", notes = " ResponseResult<String>", response = ResponseResult.class)
  @PostMapping(value = "/api/aliOrder/updateOrderState", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<String> updateOrder(@RequestBody UpdateOrderStateReq updateOrderStateReq) {
    logger.info("updateOrderState,req={}", JsonUtils.toJsonString(updateOrderStateReq));
    try {
      if (StringUtils.isEmpty(updateOrderStateReq.getRentRecordNo()) || updateOrderStateReq.getState() == null) {
        ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号与状态不能为空", null);
      }
      ResponseResult<String> result = rentRecordService.updateOrderState(updateOrderStateReq);
      logger.info("updateOrderState,rsp={}", JsonUtils.toJsonString(result));
      return result;
    } catch (Exception e) {
      logger.error("updateOrderState exception {}", e);
      return ResponseResult.build(1000, e.getLocalizedMessage(), null);
    }
  }

  /**
   * @description 库存系统发起 确认发货 传入状态 BackRentState.SendOut.getCode()
   * @param updateOrderStateReq
   * @return
   * @throws createBy:临时工 createDate:2018年3月27日
   */
  @ApiOperation(value = "确认发货", httpMethod = "POST", notes = " ResponseResult<String>", response = ResponseResult.class)
  @PostMapping(value = "/api/aliOrder/SendOut", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<String> SendOut(@RequestBody UpdateOrderStateReq updateOrderStateReq) {
    logger.info("SendOut,req={}", JsonUtils.toJsonString(updateOrderStateReq));
    try {
      if (StringUtils.isEmpty(updateOrderStateReq.getRentRecordNo()) || updateOrderStateReq.getState() == null
          || StringUtils.isEmpty(updateOrderStateReq.getReturnLogisticsNo())) {
        ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号与状态、物流单号不能为空", null);
      }
      ResponseResult<String> result = rentRecordService.SendOut(updateOrderStateReq);
      logger.info("SendOut,rsp={}", JsonUtils.toJsonString(result));
      return result;
    } catch (Exception e) {
      logger.error("SendOut exception {}", e);
      return ResponseResult.build(1000, e.getLocalizedMessage(), null);
    }
  }
	
  @ApiOperation(value = "支付首期款成功", httpMethod = "POST", notes = " ResponseResult<String>", response = ResponseResult.class)
  @PostMapping(value = "/api/aliOrder/paySuccess", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<String> paySuccess(@RequestBody UpdateOrderStateReq updateOrderStateReq) {
    logger.info("paySuccess,req={}", JsonUtils.toJsonString(updateOrderStateReq));
    try {
      if (StringUtils.isEmpty(updateOrderStateReq.getRentRecordNo())) {
        ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号不能为空", null);
      }
      updateOrderStateReq.setState(BackRentState.WaitPick.getCode());
      ResponseResult<String> result = rentRecordService.paySuccess(updateOrderStateReq);
      logger.info("paySuccess,rsp={}", JsonUtils.toJsonString(result));
      return result;
    } catch (Exception e) {
      logger.error("paySuccess exception {}", e);
      return ResponseResult.build(1000, e.getLocalizedMessage(), null);
    }
  }

  /**
   * 更新买断状态 EarlyBuyout(17,"提前买断"), NormalBuyout(19,"正常买断") ,ForceBuyout(26,
   * "强制买断") ;
   * 
   * @param updateOrderState
   * @return
   * @throws createBy:临时工 createDate:2018年1月2日
   */
  @ApiOperation(value = "更新买断状态", httpMethod = "POST", notes = " ResponseResult<String>", response = ResponseResult.class)
  @PostMapping(value = "/api/aliOrder/buyOut", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<String> buyOut(@RequestBody UpdateOrderStateReq updateOrderState) {
    logger.info("buyOut,req={}", JsonUtils.toJsonString(updateOrderState));
    try {
      if (StringUtils.isEmpty(updateOrderState.getRentRecordNo()) || updateOrderState.getState() == null) {
        ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号和状态不能为空", null);
      }
      ResponseResult<String> result = rentRecordService.buyOut(updateOrderState);
      logger.info("buyOut,rsp={}", JsonUtils.toJsonString(result));
      return result;
    } catch (Exception e) {
      logger.error(" buyOut exception {}", e);
      return ResponseResult.build(1000, e.getLocalizedMessage(), null);
    }
  }

  /**
   * 查询订单详细信息
   * 
   * @param rentRecordNo
   * @param bindingResult
   * @return
   * @throws createBy:临时工 createDate:2017年12月19日
   */
  @ApiOperation(value = "查询订单详细信息", httpMethod = "POST", notes = " ResponseResult<OrderDetailResp>", response = ResponseResult.class)
  @PostMapping(value = "/api/aliOrder/queryOrderDetail", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<OrderDetailResp> queryOrderDetail(@RequestBody String rentRecordNo) {

    logger.info("orderDetail,req={}", rentRecordNo);
    try {
      if (StringUtils.isEmpty(rentRecordNo)) {
        return ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号不能为空", null);
      }
      ResponseResult<OrderDetailResp> queryOrderDetail = rentRecordService.queryOrderDetail(rentRecordNo);
      logger.info("orderDetail,rsp={}", JsonUtils.toJsonString(queryOrderDetail));
      return queryOrderDetail;
    } catch (Exception e) {
      logger.error("orderDetail exception {}", e);
      return ResponseResult.build(1000, e.toString(), null);
    }
  }

  /**
   * 按条件查询订单数据 分页 出库订单(包括待拣货，待发货，已发货)分页列表 * 返回结果 订单状态，申请日期，销售单号，单据状态，物料名称，
   * 物料编码，规格值，拣货数量，单位，IMEI号，SN号，拣货日期，拣货人，填单日期，填单人,收货人，
   * 收货地址，联系电话，申请时GPS定位，签约时GPS定位，运单号
   * 
   * @param rentRecordQuery
   * @param bindingResult
   * @return
   * @throws createBy:临时工 createDate:2017年12月19日
   */
  @ApiOperation(value = "库存系统查询订单列表", httpMethod = "POST", notes = " ResponseResult<OrderDetailRespForWare>", response = ResponseResult.class)
  @PostMapping(value = "/api/aliOrder/queryPageRentRecordList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<ResultPager<OrderDetailRespForWare>> queryPageRentRecordList(@RequestBody RentRecordQuery rentRecordQuery) {
    ResponseResult<String> validateResult = super.getValidatedResult(this.validator,
      rentRecordQuery,
      Default.class);
    logger.info("queryPageRentRecordList,req={}", JsonUtils.toJsonString(rentRecordQuery));
    if (validateResult == null) {
      try {
        ResponseResult<ResultPager<OrderDetailRespForWare>> result = new ResponseResult<>();
        result = rentRecordService.queryPageWareOrderList(rentRecordQuery);
        logger.info("queryPageRentRecordList,rsp={}", JsonUtils.toJsonString(result));
        return result;
      } catch (Exception e) {
        logger.error("queryPageRentRecordList exception {}", e);
        return ResponseResult.build(1000, e.getLocalizedMessage(), null);
      }
    }
    return ResponseResult.build(validateResult.getErrCode(), validateResult.getErrMsg(), null);
  }

  /**
   * 清算系统调用履约完成
   * 
   * @param updateOrderStateReq
   * @return
   * @throws createBy:临时工 createDate:2018年3月28日
   */
  @ApiOperation(value = "履约完成", httpMethod = "POST", notes = " ResponseResult<String>", response = ResponseResult.class)
  @PostMapping(value = "/api/aliOrder/endPerformance", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<String> endPerformance(@RequestBody UpdateOrderStateReq updateOrderStateReq) {
    logger.info("endPerformance,req={}", JsonUtils.toJsonString(updateOrderStateReq));
    try {
      if (StringUtils.isEmpty(updateOrderStateReq.getRentRecordNo())) {
        ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号不能为空", null);
      }
      updateOrderStateReq.setState(BackRentState.EndPerformance.getCode());
      ResponseResult<String> result = rentRecordService.endPerformance(updateOrderStateReq);
      logger.info("endPerformance,rsp={}", JsonUtils.toJsonString(result));
      return result;
    } catch (Exception e) {
      logger.error("endPerformance exception {}", e);
      return ResponseResult.build(1000, e.getLocalizedMessage(), null);
    }
  }

  /**
   * 后台查询订单详细信息
   * 
   * @param rentRecordNo
   * @param bindingResult
   * @return
   * @throws createBy:临时工 createDate:2018年3月28日
   */
  @ApiOperation(value = "后台查询订单详细信息", httpMethod = "POST", notes = " ResponseResult<OrderDetailResp>", response = ResponseResult.class)
  @PostMapping(value = "/api/aliOrder/queryBackOrderDetail", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<OrderDetailResp> queryBackOrderDetail(@RequestBody String rentRecordNo) {

    logger.info("queryBackOrderDetail,req={}", rentRecordNo);
    try {
      if (StringUtils.isEmpty(rentRecordNo)) {
        return ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号不能为空", null);
      }
      ResponseResult<OrderDetailResp> queryOrderDetail = rentRecordService.queryBackOrderDetail(rentRecordNo);
      logger.info("queryBackOrderDetail,rsp={}", JsonUtils.toJsonString(queryOrderDetail));
      return queryOrderDetail;
    } catch (Exception e) {
      logger.error("queryBackOrderDetail exception {}", e);
      return ResponseResult.build(1000, e.toString(), null);
    }
  }

  /**
   * 小程序查询用户订单列表信息
   * 
   * @param orderDetailReq
   * @return
   * @throws createBy:临时工 createDate:2018年3月28日
   */
  @ApiOperation(value = "小程序查询用户订单列表信息", httpMethod = "POST", notes = " ResponseResult<List<OrderDetailResp>>", response = ResponseResult.class)
  @PostMapping(value = "/api/aliOrder/queryOrderStateList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<List<OrderDetailResp>> queryOrderStateList(@RequestBody OrderDetailReq orderDetailReq) {
    ResponseResult<String> validateResult = super.getValidatedResult(this.validator, orderDetailReq, Default.class);
    logger.info("queryOrderStateList,req={}", JsonUtils.toJsonString(orderDetailReq));
    if (validateResult == null) {
      try {
        ResponseResult<List<OrderDetailResp>> result = rentRecordService.queryOrderStateList(orderDetailReq);
        logger.info("queryOrderStateList,rsp={}", JsonUtils.toJsonString(result));
        return result;
      } catch (Exception e) {
        logger.error("queryOrderStateList exception {}", e);
        return ResponseResult.build(1000, e.getLocalizedMessage(), null);
      }
    }
    return ResponseResult.build(validateResult.getErrCode(), validateResult.getErrMsg(), null);
  }
  
  
  /**
   * 后台查询订单列表
   * @param rentRecordQuery
   * @return
   */
  @ApiOperation(value = "后台查询订单列表", httpMethod = "POST", notes = " ResponseResult<ResultPager<OrderDetailResp>>", response = ResponseResult.class)
  @PostMapping(value = "/backend/aliOrder/queryRentRecordList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<ResultPager<OrderDetailResp>> queryRentRecordList(@RequestBody RentRecordQuery rentRecordQuery) {
    ResponseResult<String> validateResult = super.getValidatedResult(this.validator,
      rentRecordQuery,
      Default.class);
      logger.info("queryPageRentRecordList,req={}", JsonUtils.toJsonString(rentRecordQuery));
    if (validateResult == null) {
      try {
        ResponseResult<ResultPager<OrderDetailResp>> result = new ResponseResult<>();
        result = rentRecordService.queryRentRecordList(rentRecordQuery);
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
   * 订单流程信息
   * @param rentRecordNo
   * @return
   */
  @ApiOperation(value = "订单流程信息", httpMethod = "POST", notes = "ResponseResult<ResultPager<RentState>>", response = ResponseResult.class)
  @PostMapping(value = "/backend/aliOrder/selectRentState", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<?> rentState(@RequestBody String rentRecordNo) {
		try {
			ResponseResult<ResultPager<RentState>> result= rentRecordService.selectRentState(rentRecordNo);
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
  @PostMapping(value = "/backend/aliOrder/selectLogistics", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<?> logistics(@RequestBody String rentRecordNo) {
		try {
			ResponseResult<ResultPager<RentLogisticsDto>> result= rentRecordService.selectLogistics(rentRecordNo);
			logger.info("logistics,rsp={}", JsonUtils.toJsonString(JsonUtils.toJsonString(result)));
			return result;
		} catch (Exception e) {
			logger.error("logistics exception{}",e);
			return ResponseResult.build(1000, e.getLocalizedMessage(), null);
		}
  }
  
  /**
   * 查询租赁合同信息
   * 
   * @param rentRecordNo
   * @param bindingResult
   * @return
   * @throws createBy:临时工 createDate:2017年12月19日
   */
  @PostMapping(value = "/api/aliOrder/lookContract", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<Map<String, Object>> lookContract(@RequestBody String rentRecordNo) {

    logger.info("lookContract,req={}", rentRecordNo);
    try {
      if (StringUtils.isEmpty(rentRecordNo)) {
        return ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号不能为空", null);
      }
      ResponseResult<Map<String, Object>> queryOrderDetail = rentRecordService.lookContract(rentRecordNo);
      logger.info("lookContract,rsp={}", JsonUtils.toJsonString(queryOrderDetail));
      return queryOrderDetail;
    } catch (Exception e) {
      logger.error("lookContract exception {}", e);
      return ResponseResult.build(1000, e.getLocalizedMessage(), null);
    }
  }

}
