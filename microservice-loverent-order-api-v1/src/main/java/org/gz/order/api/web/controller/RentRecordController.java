/**
 * 
 */
package org.gz.order.api.web.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Validator;
import javax.validation.groups.Default;

import org.apache.commons.collections.CollectionUtils;
import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.JsonUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.order.api.service.IRentRecordService;
import org.gz.order.common.Enum.BackRentState;
import org.gz.order.common.Enum.OrderResultCode;
import org.gz.order.common.dto.AddOrderReq;
import org.gz.order.common.dto.OrderDetailReq;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.order.common.dto.OrderDetailRespForWare;
import org.gz.order.common.dto.QueryInvoiceReq;
import org.gz.order.common.dto.QueryInvoiceRsp;
import org.gz.order.common.dto.RentRecordQuery;
import org.gz.order.common.dto.RentRecordReq;
import org.gz.order.common.dto.SaleOrderReq;
import org.gz.order.common.dto.SubmitOrderReq;
import org.gz.order.common.dto.UpdateOrderStateReq;
import org.gz.order.common.dto.WorkOrderRsp;
import org.gz.order.common.entity.RentCoordinate;
import org.gz.order.common.entity.RentRecord;
import org.gz.order.common.entity.RentRecordExtends;
import org.gz.order.common.entity.UserHistory;
import org.gz.order.server.service.RentRecordExtendsService;
import org.gz.order.server.service.RentRecordService;
import org.gz.risk.api.controller.IRiskController;
import org.gz.risk.common.request.CreditNode;
import org.gz.risk.common.request.RiskReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
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
  private IRentRecordService irentRecordService;
	@Autowired
	private RentRecordExtendsService rentRecordExtendsService;
  @Autowired
  private RentRecordService rentRecordService;

	@Autowired
	private Validator validator;

    @Autowired
    private IRiskController          iRiskController;
	/**
	 * 查询指定订单的订单拓展信息
	 * 
	 * @param rentRecordNo 订单号
	 * @return @throws
	 */
	@PostMapping(value = "/api/recordExtends/getByRentRecordNo", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<RentRecordExtends> getByRentRecordNo(@RequestBody String rentRecordNo) {
		return ResponseResult.buildSuccessResponse(rentRecordExtendsService.getByRentRecordNo(rentRecordNo));
	}
	/**
	 * 查询指定订单状态的列表
	 * 
	 * @param state 订单状态 @return @throws
	 */
	@PostMapping(value = "/api/order/queryRentRecordByState", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<List<RentRecord>> queryRentRecordByState(@RequestBody Integer state) {
    return irentRecordService.queryRentRecordByState(state);
	}

	/**
	 * 添加订单接口
	 * 
	 * @param admin
	 * @param bindingResult
	 * @return
	 * @throws createBy:临时工
	 *             createDate:2017年12月13日
	 */
	@PostMapping(value = "/api/order/addOrder", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<String> addOrder(@RequestBody AddOrderReq addOrderRequest) {
		logger.info("addOrder,req={}", JsonUtils.toJsonString(addOrderRequest));
		try {
			if (null == addOrderRequest.getUserId()) {
				ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "userId为空", null);
			}
      ResponseResult<String> result = irentRecordService.add(addOrderRequest);
			logger.info("addOrder,rsp={}", JsonUtils.toJsonString(result));
			return result;
		} catch (Exception e) {
			logger.error("addOrder exception {}", e);
			return ResponseResult.build(1000, e.getLocalizedMessage(), null);
		}
	}

	/**
	 * 确认提交订单接口
	 * 
	 * @param admin
	 * @param bindingResult
	 * @return
	 * @throws createBy:临时工
	 *             createDate:2017年12月13日
	 */
	@PostMapping(value = "/api/order/submitOrder", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<OrderDetailResp> submitOrder(@RequestBody SubmitOrderReq submitOrderReq) {
		logger.info("submitOrder,req={}", JsonUtils.toJsonString(submitOrderReq));
		try {

			if (StringUtils.isEmpty(submitOrderReq.getRentRecordNo()) || StringUtils.isEmpty(submitOrderReq.getLat())
					|| StringUtils.isEmpty(submitOrderReq.getLng())) {
				ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号或者经纬度不能为空", null);
			}

      ResponseResult<OrderDetailResp> result = irentRecordService.submitOrder(submitOrderReq);
			
			//如果没异常  推送消息到风控系统
            if (result.isSuccess()) {
                // 6 .推送订单给风控系统
                RiskReq riskReq = new RiskReq();
                riskReq.setLoanRecordId(submitOrderReq.getRentRecordNo());
                riskReq.setPhase(CreditNode.NODE_FIRST_CREDIT);
                ResponseResult<String> processAutoCredit = iRiskController.processAutoCredit(riskReq);
                if (!processAutoCredit.isSuccess()) {
                    logger.error("submitOrder 推送风控异常,rentRecordNo={},getErrCode={},getErrMsg={}",
                        submitOrderReq.getRentRecordNo(),
                        processAutoCredit.getErrCode(),
                        processAutoCredit.getErrMsg());
                }
            }
			logger.info("submitOrder,rsp={}", JsonUtils.toJsonString(result));
			return result;
		} catch (Exception e) {
			logger.error("submitOrder exception {}", e);
			return ResponseResult.build(1000, e.getLocalizedMessage(), null);
		}
	}

	/**
	 * 修改订单状态接口
	 * 
	 * @param admin
	 * @param bindingResult
	 * @return
	 * @throws createBy:临时工
	 *             createDate:2017年12月15日
	 */
	@PostMapping(value = "/api/order/updateOrderState", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<String> updateOrderState(@RequestBody UpdateOrderStateReq updateOrderState) {
		logger.info("updateOrderState,req={}", JsonUtils.toJsonString(updateOrderState));
		try {
			if (StringUtils.isEmpty(updateOrderState.getRentRecordNo()) || updateOrderState.getState() == null) {
				ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号和状态不能为空", null);
			}

      ResponseResult<String> result = irentRecordService.updateOrderState(updateOrderState);
			logger.info("updateOrderState,rsp={}", JsonUtils.toJsonString(result));
			return result;
		} catch (Exception e) {
			logger.error(" updateOrderState exception {}", e);
			return ResponseResult.build(1000, e.getLocalizedMessage(), null);
		}
	}

	/**
	 * 申请签约
	 * 
	 * @param UpdateOrderStateReq
	 * @param bindingResult
	 * @return pdf对应的oss路径
	 * @throws createBy:临时工
	 *             createDate:2017年12月24日
	 */
	@PostMapping(value = "/api/order/applySign", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<String> applySign(@RequestBody UpdateOrderStateReq updateOrderState) {
		logger.info("applySign,req={}", JsonUtils.toJsonString(updateOrderState));
		try {
			if (StringUtils.isEmpty(updateOrderState.getRentRecordNo())) {
				ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号不能为空", null);
			}

      ResponseResult<String> result = irentRecordService.applySign(updateOrderState);
			logger.info("applySign,rsp={}", JsonUtils.toJsonString(result));
			return result;
		} catch (Exception e) {
			logger.error(" applySign exception {}", e);
			return ResponseResult.build(1000, e.getLocalizedMessage(), null);
		}
	}

	/**
	 * 确认签约 签约完成需要更新订单表的 sealAgreementUrl 和evid和SignServiceId
	 * 
	 * @param UpdateOrderStateReq
	 *            updateOrderState
	 * @param bindingResult
	 * @return
	 * @throws createBy:临时工
	 *             createDate:2017年12月29日
	 */
	@PostMapping(value = "/api/order/sureSign", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<String> sureSign(@RequestBody UpdateOrderStateReq updateOrderState) {
		logger.info("sureSign,req={}", JsonUtils.toJsonString(updateOrderState));
		try {
			if (StringUtils.isEmpty(updateOrderState.getRentRecordNo())) {
				ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号不能为空", null);
			}

      ResponseResult<String> result = irentRecordService.sureSign(updateOrderState);
			logger.info("sureSign,rsp={}", JsonUtils.toJsonString(result));
			return result;
		} catch (Exception e) {
			logger.error(" sureSign exception {}", e);
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
	@PostMapping(value = "/api/order/buyOut", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<String> buyOut(@RequestBody UpdateOrderStateReq updateOrderState) {
		logger.info("buyOut,req={}", JsonUtils.toJsonString(updateOrderState));
		try {
			if (StringUtils.isEmpty(updateOrderState.getRentRecordNo())) {
				ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号不能为空", null);
			}

      ResponseResult<String> result = irentRecordService.buyOut(updateOrderState);
			logger.info("buyOut,rsp={}", JsonUtils.toJsonString(result));
			return result;
		} catch (Exception e) {
			logger.error(" buyOut exception {}", e);
			return ResponseResult.build(1000, e.getLocalizedMessage(), null);
		}
	}

  /**
   * 仓库系统确认发货
   * 
   * @param rentRecordReq
   * @param bindingResult
   * @return
   * @throws createBy:临时工 createDate:2017年12月19日
   */
	@PostMapping(value = "/api/order/SendOut", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<String> SendOut(@RequestBody RentRecordReq rentRecordReq) {
		logger.info("SendOut,req={}", JsonUtils.toJsonString(rentRecordReq));
		try {
			if (StringUtils.isEmpty(rentRecordReq.getRentRecordNo())
					|| (StringUtils.isEmpty(rentRecordReq.getLogisticsNo())
							&& StringUtils.isEmpty(rentRecordReq.getReturnLogisticsNo()))) {
				return ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号和运单号不能为空", null);
			}
      ResponseResult<String> result = irentRecordService.SendOut(rentRecordReq);
			logger.info("SendOut,rsp={}", JsonUtils.toJsonString(result));
			return result;
		} catch (Exception e) {
			logger.error("SendOut exception {}", e);
			return ResponseResult.build(1000, e.getLocalizedMessage(), null);
		}
	}

	/**
	 * 查询订单详细信息
	 * 
	 * @param rentRecordNo
	 * @param bindingResult
	 * @return
	 * @throws createBy:临时工
	 *             createDate:2017年12月19日
	 */
	@PostMapping(value = "/api/order/queryOrderDetail", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<OrderDetailResp> queryOrderDetail(@RequestBody String rentRecordNo) {

		logger.info("orderDetail,req={}", rentRecordNo);
		try {
			if (StringUtils.isEmpty(rentRecordNo)) {
				return ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号不能为空", null);
			}
      ResponseResult<OrderDetailResp> queryOrderDetail = irentRecordService.queryOrderDetail(rentRecordNo);
			logger.info("orderDetail,rsp={}", JsonUtils.toJsonString(queryOrderDetail));
			return queryOrderDetail;
		} catch (Exception e) {
			logger.error("orderDetail exception {}", e);
			return ResponseResult.build(1000, e.toString(), null);
		}
	}

	/**
	 * 通过前端返回的三种订单状态查询对应的订单列表信息
	 * 
	 * @param orderDetailReq
	 * @param 前端三种状态
	 *            1、进行中的订单 2、已完成的订单 3、已取消的订单
	 * @return
	 * @throws createBy:临时工
	 *             createDate:2017年12月19日
	 */
	@PostMapping(value = "/api/order/queryOrderStateList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<List<OrderDetailResp>> queryOrderStateList(@RequestBody OrderDetailReq orderDetailReq) {
		ResponseResult<String> validateResult = super.getValidatedResult(this.validator, orderDetailReq, Default.class);
		logger.info("queryOrderStateList,req={}", JsonUtils.toJsonString(orderDetailReq));
		if (validateResult == null) {
			try {
        ResponseResult<List<OrderDetailResp>> result = irentRecordService.queryOrderStateList(orderDetailReq);
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
	 * 按条件查询订单数据 分页 出库订单(包括待拣货，待发货，已发货)分页列表
	 *
	 * * 返回结果 订单状态，申请日期，销售单号，单据状态，物料名称，
	 * 物料编码，规格值，拣货数量，单位，IMEI号，SN号，拣货日期，拣货人，填单日期，填单人,收货人，
	 * 收货地址，联系电话，申请时GPS定位，签约时GPS定位，运单号
	 * 
	 * @param rentRecordQuery
	 * @param bindingResult
	 * @return
	 * @throws createBy:临时工
	 *             createDate:2017年12月19日
	 */
	@PostMapping(value = "/api/order/queryPageRentRecordList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<ResultPager<OrderDetailRespForWare>> queryRentRecordList(
			@RequestBody RentRecordQuery rentRecordQuery) {
		ResponseResult<String> validateResult = super.getValidatedResult(this.validator, rentRecordQuery,
				Default.class);
		logger.info("queryPageRentRecordList,req={}", JsonUtils.toJsonString(rentRecordQuery));
		if (validateResult == null) {
			try {
				ResponseResult<ResultPager<OrderDetailRespForWare>> result = new ResponseResult<>();
        result = irentRecordService.queryPageWareOrderList(rentRecordQuery);
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
	 * 查询订单经纬度列表信息 {"rentRecordNo":"SO1712220000000005","state":""}
	 * 
	 * @param rentCoordinate
	 * @return
	 * @throws createBy:临时工
	 *             createDate:2017年12月27日
	 */
	@PostMapping(value = "/api/order/queryOrderCoordinate", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<List<RentCoordinate>> queryOrderCoordinate(@RequestBody RentCoordinate rentCoordinate) {
		logger.info("queryOrderCoordinate,req={}", JsonUtils.toJsonString(rentCoordinate));
		try {
			if (StringUtils.isEmpty(rentCoordinate.getRentRecordNo())) {
				return ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号不能为空", null);
			}
      ResponseResult<List<RentCoordinate>> queryOrderCoordinate = irentRecordService.queryList(rentCoordinate);
			logger.info("queryOrderCoordinate,rsp={}", JsonUtils.toJsonString(queryOrderCoordinate));
			return queryOrderCoordinate;
		} catch (Exception e) {
			logger.error("queryOrderCoordinate exception {}", e);
			return ResponseResult.build(1000, e.getLocalizedMessage(), null);
		}
	}

	/**
	 * 后端查询订单详细信息
	 * 
	 * @param rentRecordNo
	 * @param
	 * @return
	 * @throws createBy:临时工
	 *             createDate:2017年12月19日
	 */
	@PostMapping(value = "/api/order/queryBackOrderDetail", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<OrderDetailResp> queryBackOrderDetail(@RequestBody String rentRecordNo) {

		logger.info("queryBackOrderDetail,req={}", rentRecordNo);
		try {
			if (StringUtils.isEmpty(rentRecordNo)) {
				return ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号不能为空", null);
			}
      ResponseResult<OrderDetailResp> queryOrderDetail = irentRecordService.queryBackOrderDetail(rentRecordNo);
			logger.info("queryBackOrderDetail,rsp={}", JsonUtils.toJsonString(queryOrderDetail));
			return queryOrderDetail;
		} catch (Exception e) {
			logger.error("queryBackOrderDetail exception {}", e);
			return ResponseResult.build(1000, e.getLocalizedMessage(), null);
		}
	}

	/**
	 * 查询租赁合同信息
	 * 
	 * @param rentRecordNo
	 * @param bindingResult
	 * @return
	 * @throws createBy:临时工
	 *             createDate:2017年12月19日
	 */
	@PostMapping(value = "/api/order/lookContract", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<Map<String, Object>> lookContract(@RequestBody String rentRecordNo) {

		logger.info("lookContract,req={}", rentRecordNo);
		try {
			if (StringUtils.isEmpty(rentRecordNo)) {
				return ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号不能为空", null);
			}
      ResponseResult<Map<String, Object>> queryOrderDetail = irentRecordService.lookContract(rentRecordNo);
			logger.info("lookContract,rsp={}", JsonUtils.toJsonString(queryOrderDetail));
			return queryOrderDetail;
		} catch (Exception e) {
			logger.error("lookContract exception {}", e);
			return ResponseResult.build(1000, e.getLocalizedMessage(), null);
		}
	}

	/**
	 * 提供给库存系统通过imei串查询订单列表
	 * 
	 * @param imeis
	 * @param
	 * @return
	 * @throws createBy:临时工
	 *             createDate:2017年12月19日
	 */
	@PostMapping(value = "/api/order/queryRentRecordNoByImei", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<List<String>> queryRentRecordNoByImei(@RequestBody String imeis) {

		logger.info("queryRentRecordNoByImei,req={}", imeis);
		try {
			if (StringUtils.isEmpty(imeis)) {
				return ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "imeis不能为空", null);
			}
			List<String> imeilist = Arrays.asList(imeis.split(","));
      ResponseResult<List<String>> queryRentRecordNoByImei = irentRecordService.queryRentRecordNoByImei(imeilist);
			logger.info("queryRentRecordNoByImei,rsp={}", JsonUtils.toJsonString(queryRentRecordNoByImei));
			return queryRentRecordNoByImei;
		} catch (Exception e) {
			logger.error("queryRentRecordNoByImei exception {}", e);
			return ResponseResult.build(1000, e.getLocalizedMessage(), null);
		}
	}

	/**
	 * 按条件查询订单数据 分页 工单系统（不包含申请中0订单）分页
	 * 
	 * @param rentRecordQuery
	 * @return
	 * @throws createBy:临时工
	 *             createDate:2018年1月24日
	 */
	@PostMapping(value = "/api/order/queryPageWokerOrderList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<ResultPager<WorkOrderRsp>> queryPageWokerOrderList(
			@RequestBody RentRecordQuery rentRecordQuery) {
		ResponseResult<String> validateResult = super.getValidatedResult(this.validator, rentRecordQuery,
				Default.class);
		logger.info("queryPageWokerOrderList,req={}", JsonUtils.toJsonString(rentRecordQuery));
		if (validateResult == null) {
			try {
				ResponseResult<ResultPager<WorkOrderRsp>> result = new ResponseResult<>();
        result = irentRecordService.queryPageWokerOrderList(rentRecordQuery);
				logger.info("queryPageWokerOrderList,rsp={}", JsonUtils.toJsonString(result));
				return result;
			} catch (Exception e) {
				logger.error("queryPageWokerOrderList exception {}", e);
				return ResponseResult.build(1000, e.getLocalizedMessage(), null);
			}
		}
		return ResponseResult.build(validateResult.getErrCode(), validateResult.getErrMsg(), null);
	}

  /**
   * 信审
   * {"rentRecordNo":"SO1712220000000005","state":3,"createBy":22,"createMan":"shenpirenyuan","remark":"1111","lng":"","lat":"","sealAgreementUrl":"","evid":""}
   * 
   * @param 3 审核通过 BackRentState.WaitPayment.getCode()
   * @param 2 拒绝 BackRentState.Refuse.getCode()
   * @return
   * @throws createBy:临时工 createDate:2017年12月15日
   */
  @PostMapping(value = "/api/order/audit", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<String> audit(@RequestBody UpdateOrderStateReq updateOrderState) {
    logger.info("audit,req={}", JsonUtils.toJsonString(updateOrderState));
    try {
      if (StringUtils.isEmpty(updateOrderState.getRentRecordNo()) || updateOrderState.getState() == null) {
        ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号和状态不能为空", null);
      }
      ResponseResult<String> result = irentRecordService.audit(updateOrderState);
      logger.info("audit,rsp={}", JsonUtils.toJsonString(result));
      return result;
    } catch (Exception e) {
      logger.error(" audit exception {}", e);
      return ResponseResult.build(1000, e.getLocalizedMessage(), null);
    }
  }

  /**
   * 更新订单信审状态 {"rentRecordNo":"SO1712220000000005","creditState":2}
   * 
   * @param HashMap
   * @param rentRecordNo String 订单编号
   * @param creditState int 1进入一审 2进入二审 3进入三审
   * @return
   * @throws createBy:临时工 createDate:2017年12月15日
   */
  @PostMapping(value = "/api/order/updateCreditState", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<String> updateCreditState(@RequestBody HashMap<String, Object> map) {
    logger.info("updateCreditState,req={}", JsonUtils.toJsonString(map));
    try {
      String rentRecordNo = (String) map.get("rentRecordNo");
      Integer creditState = (Integer) map.get("creditState");
      if (StringUtils.isEmpty(rentRecordNo) || creditState == null) {
        ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号和状态不能为空", null);
      }
      ResponseResult<String> result = irentRecordService.updateCreditState(map);
      logger.info("updateCreditState,rsp={}", JsonUtils.toJsonString(result));
      return result;
    } catch (Exception e) {
      logger.error(" updateCreditState exception {}", e);
      return ResponseResult.build(1000, e.getLocalizedMessage(), null);
    }
  }

  /**
   * 通过订单编号查询历史用户信息
   * 
   * @param rentRecordNo
   * @return
   * @throws createDate:2018年1月28日
   */
  @PostMapping(value = "/api/order/queryUserHistory", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<UserHistory> queryUserHistory(@RequestBody String rentRecordNo) {

    logger.info("queryUserHistory,req={}", rentRecordNo);
    try {
      if (StringUtils.isEmpty(rentRecordNo)) {
        return ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号不能为空", null);
      }
      ResponseResult<UserHistory> queryUserHistory = irentRecordService.queryUserHistory(rentRecordNo);
      logger.info("queryUserHistory,rsp={}", JsonUtils.toJsonString(queryUserHistory));
      return queryUserHistory;
    } catch (Exception e) {
      logger.error("queryUserHistory exception {}", e);
      return ResponseResult.build(1000, e.getLocalizedMessage(), null);
    }

  }

  /**
   * 通过申请时间查询订单总数
   * 
   * @param rentRecordQuery
   * @param {"startApplyTime":"2017-08-11"}
   * @return
   * @throws createBy:临时工 createDate:2018年1月29日
   */
  @PostMapping(value = "/api/order/countByapplyTime", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<Integer> countByapplyTime(@RequestBody RentRecordQuery rentRecordQuery) {
    ResponseResult<String> validateResult = super.getValidatedResult(this.validator,
      rentRecordQuery,
      Default.class);
    logger.info("countByapplyTime,req={}", JsonUtils.toJsonString(rentRecordQuery));
    if (validateResult == null) {
      try {
        ResponseResult<Integer> result = new ResponseResult<>();
        result = irentRecordService.countByapplyTime(rentRecordQuery);
        logger.info("countByapplyTime,rsp={}", JsonUtils.toJsonString(result));
        return result;
      } catch (Exception e) {
        logger.error("countByapplyTime exception {}", e);
        return ResponseResult.build(1000, e.getLocalizedMessage(), null);
      }
    }
    return ResponseResult.build(validateResult.getErrCode(), validateResult.getErrMsg(), null);
  }

  /**
   * 通过申请时间查询历史用户信息
   * 
   * @param rentRecordQuery
   * @param {"currPage":1,"pageSize":10,"startIndex":0,"endIndex":100,"startApplyTime":"2017-08-11"}
   * @return
   * @throws createBy:临时工 createDate:2018年1月29日
   */
  @PostMapping(value = "/api/order/queryUserHistoryList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<List<UserHistory>> queryUserHistoryList(@RequestBody RentRecordQuery rentRecordQuery) {
    ResponseResult<String> validateResult = super.getValidatedResult(this.validator,
      rentRecordQuery,
      Default.class);
    logger.info("queryUserHistoryList,req={}", JsonUtils.toJsonString(rentRecordQuery));
    if (validateResult == null) {
      try {
        ResponseResult<List<UserHistory>> result = new ResponseResult<>();
        result = irentRecordService.queryUserHistoryList(rentRecordQuery);
        logger.info("queryUserHistoryList,rsp={}", JsonUtils.toJsonString(result));
        return result;
      } catch (Exception e) {
        logger.error("queryUserHistoryList exception {}", e);
        return ResponseResult.build(1000, e.getLocalizedMessage(), null);
      }
    }
    return ResponseResult.build(validateResult.getErrCode(), validateResult.getErrMsg(), null);
  }

  /**
   * 通过申请时间查询当前时间之前所有签约成功的订单
   * 
   * @param rentRecordQuery
   * @param {"endApplyTime":"2017-08-11","greateZero":7 ,"startApplyTime":"2017-08-11"}
   * @return
   * @throws createBy:临时工 createDate:2018年1月30日
   */
  @PostMapping(value = "/api/order/queryOrderNoList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<List<String>> queryOrderNoList(@RequestBody RentRecordQuery rentRecordQuery) {
    ResponseResult<String> validateResult = super.getValidatedResult(this.validator,
      rentRecordQuery,
      Default.class);
    logger.info("queryOrderNoList,req={}", JsonUtils.toJsonString(rentRecordQuery));
    if (validateResult == null) {
      try {
        ResponseResult<List<String>> result = new ResponseResult<>();
        result = irentRecordService.queryOrderNoList(rentRecordQuery);
        logger.info("queryOrderNoList,rsp={}", JsonUtils.toJsonString(result));
        return result;
      } catch (Exception e) {
        logger.error("queryOrderNoList exception {}", e);
        return ResponseResult.build(1000, e.getLocalizedMessage(), null);
      }
    }
    return ResponseResult.build(validateResult.getErrCode(), validateResult.getErrMsg(), null);
  }

  /**
   * 查询订单列表信息 不分页
   * 
   * @param rentRecordQuery
   * @param {"userId":22,"orderBy":[{"cloumnName":"rr.id","sequence":"desc"}],"notZero":5，"greateZero",1}
   * @return
   */
  @ApiOperation(value = "查询订单列表信息 不分页", httpMethod = "POST", notes = "操作成功返回 List<OrderDetailResp>", response = ResponseResult.class)
  @PostMapping(value = "/api/order/queryOrderList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<List<OrderDetailResp>> queryOrderList(@RequestBody RentRecordQuery rentRecordQuery) {
    ResponseResult<String> validateResult = super.getValidatedResult(this.validator,
      rentRecordQuery,
      Default.class);
    logger.info("queryOrderList,req={}", JsonUtils.toJsonString(rentRecordQuery));
    if (validateResult == null) {
      try {
        ResponseResult<List<OrderDetailResp>> result = new ResponseResult<>();
        result = irentRecordService.queryOrderList(rentRecordQuery);
        logger.info("queryOrderList,rsp={}", JsonUtils.toJsonString(result));
        return result;
      } catch (Exception e) {
        logger.error("queryOrderList exception {}", e);
        return ResponseResult.build(1000, e.getLocalizedMessage(), null);
      }
    }
    return ResponseResult.build(validateResult.getErrCode(), validateResult.getErrMsg(), null);
  }


  /**
   * 传入订单编号list批量更新订单状态为逾期
   * 
   * @param List<String> rentRecordNos
   * @return
   */
  @ApiOperation(value = "传入订单编号list批量更新订单状态为逾期", httpMethod = "POST", notes = "", response = ResponseResult.class)
  @PostMapping(value = "/api/order/batchUpdateOverDue", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<String> batchUpdateOverDue(@RequestBody List<String> rentRecordNos) {
    logger.info("batchUpdateOverDue,req={}", JsonUtils.toJsonString(rentRecordNos));
    try {
      if (CollectionUtils.isEmpty(rentRecordNos)) {
        return ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号list不能为空", null);
      }
      ResponseResult<String> batchUpdateOverDue = irentRecordService.batchUpdateOverDue(rentRecordNos);
      logger.info("batchUpdateOverDue,rsp={}", JsonUtils.toJsonString(batchUpdateOverDue));
      return batchUpdateOverDue;
    } catch (Exception e) {
      logger.error("batchUpdateOverDue exception {}", e);
      return ResponseResult.build(1000, e.getLocalizedMessage(), null);
    }
  }

  /**
   * 按条件查询订单数据提供给清算 （不包含申请中0订单）分页
   * 
   * @param rentRecordQuery
   * @return
   * @throws createBy:临时工 createDate:2018年2月11日
   */
  @ApiOperation(value = "按条件查询订单数据提供给清算", httpMethod = "POST", notes = " ResponseResult<ResultPager<OrderDetailResp>", response = ResponseResult.class)
  @PostMapping(value = "/api/order/queryPageOrderForLiquation", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<ResultPager<OrderDetailResp>> queryPageOrderForLiquation(@RequestBody RentRecordQuery rentRecordQuery) {
    ResponseResult<String> validateResult = super.getValidatedResult(this.validator,
      rentRecordQuery,
      Default.class);
    logger.info("queryPageOrderForLiquation,req={}", JsonUtils.toJsonString(rentRecordQuery));
    if (validateResult == null) {
      try {
        ResponseResult<ResultPager<OrderDetailResp>> result = new ResponseResult<>();
        result = irentRecordService.queryPageOrderForLiquation(rentRecordQuery);
        logger.info("queryPageOrderForLiquation,rsp={}", JsonUtils.toJsonString(result));
        return result;
      } catch (Exception e) {
        logger.error("queryPageOrderForLiquation exception {}", e);
        return ResponseResult.build(1000, e.getLocalizedMessage(), null);
      }
    }
    return ResponseResult.build(validateResult.getErrCode(), validateResult.getErrMsg(), null);
  }

  /**
   * 查询订单详细信息提供给工单系统
   * 
   * @param rentRecordNo
   * @return
   * @throws createBy:临时工 createDate:2018年2月27日
   */
  @PostMapping(value = "/api/order/queryOrderDetailForWork", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<OrderDetailResp> queryOrderDetailForWork(@RequestBody String rentRecordNo) {

    logger.info("queryOrderDetailForWork,req={}", rentRecordNo);
    try {
      if (StringUtils.isEmpty(rentRecordNo)) {
        return ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号不能为空", null);
      }
      ResponseResult<OrderDetailResp> queryOrderDetail = irentRecordService.queryOrderDetailForWork(rentRecordNo);
      logger.info("queryOrderDetailForWork,rsp={}", JsonUtils.toJsonString(queryOrderDetail));
      return queryOrderDetail;
    } catch (Exception e) {
      logger.error("queryOrderDetailForWork exception {}", e);
      return ResponseResult.build(1000, e.toString(), null);
    }
  }

  /**
   * 分页查询订单还机列表信息返回给库存系统（归还中”、“提前解约中”、“强制归还中”、“退货中）
   * {"currPage":1,"pageSize":10,"startIndex":0,"endIndex":10,"rentRecordNo":"","snCode":"","imei":"","startApplyTime":"","endApplyTime":"","returnLogisticsNo":"1111","orderBy":null,"sortId":null}
   * 
   * @param rentRecordQuery
   * @return
   * @throws createBy:临时工 createDate:2018年3月7日
   */
  @ApiOperation(value = "分页查询订单还机列表信息返回给库存系统", httpMethod = "POST", notes = " ResponseResult<ResultPager<OrderDetailResp>", response = ResponseResult.class)
  @PostMapping(value = "/api/order/queryPageReturnOrderList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<ResultPager<OrderDetailResp>> queryPageReturnOrderList(@RequestBody RentRecordQuery rentRecordQuery) {
    ResponseResult<String> validateResult = super.getValidatedResult(this.validator,
      rentRecordQuery,
      Default.class);
    logger.info("queryPageReturnOrderList,req={}", JsonUtils.toJsonString(rentRecordQuery));
    if (validateResult == null) {
      try {
        ResponseResult<ResultPager<OrderDetailResp>> result = new ResponseResult<>();
        result = irentRecordService.queryPageReturnOrderList(rentRecordQuery);
        logger.info("queryPageReturnOrderList,rsp={}", JsonUtils.toJsonString(result));
        return result;
      } catch (Exception e) {
        logger.error("queryPageReturnOrderList exception {}", e);
        return ResponseResult.build(1000, e.getLocalizedMessage(), null);
      }
    }
    return ResponseResult.build(validateResult.getErrCode(), validateResult.getErrMsg(), null);
  }

  /**
   * 多种状态下还机入库 库存系统调用通知订单修改订单状态
   * 
   * @description 从（归还中”、“提前解约中”、“强制归还中”、“退货中 16，11，30，33）变为（27定损完成”、“12
   * 提前解约”、“31 强制定损完成”、“34已退货 ）
   * @param updateOrderState
   * @return
   * @throws createBy:临时工 createDate:2018年3月8日
   */
  @PostMapping(value = "/api/order/sureReturned", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<String> sureReturned(@RequestBody UpdateOrderStateReq updateOrderState) {
    logger.info("sureReturned,req={}", JsonUtils.toJsonString(updateOrderState));
    try {
      if (StringUtils.isEmpty(updateOrderState.getRentRecordNo()) || updateOrderState.getState() == null) {
        ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号和状态不能为空", null);
      }
      ResponseResult<String> result = ResponseResult.buildSuccessResponse("");
      result = rentRecordService.sureReturned(updateOrderState);
      logger.info("sureReturned,rsp={}", JsonUtils.toJsonString(result));
      return result;
    } catch (Exception e) {
      logger.error(" sureReturned exception {}", e);
      return ResponseResult.build(1000, e.getLocalizedMessage(), null);
    }
  }

  /**
   * @description 订单归还中接口（16 归还中 30 强制归还中 33退货中）
   * @param updateOrderState
   * @return
   * @throws createBy:临时工 createDate:2018年3月12日
   */
  @PostMapping(value = "/api/order/returnIng", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<String> returnIng(@RequestBody UpdateOrderStateReq updateOrderState) {
    logger.info("returnIng,req={}", JsonUtils.toJsonString(updateOrderState));
    try {
      if (StringUtils.isEmpty(updateOrderState.getRentRecordNo()) || updateOrderState.getState() == null) {
        ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号和状态不能为空", null);
      }
      ResponseResult<String> result = ResponseResult.buildSuccessResponse("");
      result = irentRecordService.returnIng(updateOrderState);
      logger.info("returnIng,rsp={}", JsonUtils.toJsonString(result));
      return result;
    } catch (Exception e) {
      logger.error(" returnIng exception {}", e);
      return ResponseResult.build(1000, e.getLocalizedMessage(), null);
    }
  }

  @ApiOperation(value = "添加售卖订单接口", httpMethod = "POST", notes = " ResponseResult<String>", response = ResponseResult.class)
  @PostMapping(value = "/api/order/addSaleOrder", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<String> addSaleOrder(@RequestBody SaleOrderReq saleOrderReq) {
    ResponseResult<String> validateResult = super.getValidatedResult(this.validator, saleOrderReq, Default.class);
    logger.info("addSaleOrder,req={}", JsonUtils.toJsonString(saleOrderReq));
    if (validateResult == null) {
      try {
        ResponseResult<String> result = irentRecordService.addSaleOrder(saleOrderReq);
        logger.info("addSaleOrder,rsp={}", JsonUtils.toJsonString(result));
        return result;
      } catch (Exception e) {
        logger.error("addSaleOrder exception {}", e);
        return ResponseResult.build(1000, e.getLocalizedMessage(), null);
      }
    }
    return ResponseResult.build(validateResult.getErrCode(), validateResult.getErrMsg(), null);
  }

  /**
   * @description 售卖订单支付成功，提供给清算系统调用订单系统修改状态
   * @param updateOrderState
   * @return
   * @throws createBy:临时工 createDate:2018年3月13日
   */
  @PostMapping(value = "/api/order/paySuccess", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<String> paySuccess(@RequestBody UpdateOrderStateReq updateOrderState) {
    logger.info("paySuccess,req={}", JsonUtils.toJsonString(updateOrderState));
    try {
      if (StringUtils.isEmpty(updateOrderState.getRentRecordNo())) {
        ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号不能为空", null);
      }
      ResponseResult<String> result = ResponseResult.buildSuccessResponse("");
      result = irentRecordService.paySuccess(updateOrderState);
      logger.info("paySuccess,rsp={}", JsonUtils.toJsonString(result));
      return result;
    } catch (Exception e) {
      logger.error(" paySuccess exception {}", e);
      return ResponseResult.build(1000, e.getLocalizedMessage(), null);
    }
  }

  /**
   * 清算系统支付定损费完成调用通知订单修改订单状态
   * 
   * @description 从 ForceDamageCheck(31, " 强制定损完成"),DamageCheck(27, "定损完成"),变成
   * ForceRecycleEnd(32, " 强制归还完成"), Recycle(20, "已归还"),
   * @param updateOrderState
   * @return
   * @throws createBy:临时工 createDate:2018年3月8日
   */
  @PostMapping(value = "/api/order/alreadReturned", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<String> alreadReturned(@RequestBody UpdateOrderStateReq updateOrderState) {
    logger.info("alreadReturned,req={}", JsonUtils.toJsonString(updateOrderState));
    try {
      if (StringUtils.isEmpty(updateOrderState.getRentRecordNo()) || updateOrderState.getState() == null) {
        ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号和状态不能为空", null);
      }
      ResponseResult<String> result = ResponseResult.buildSuccessResponse("");
      result = rentRecordService.alreadReturned(updateOrderState);
      logger.info("alreadReturned,rsp={}", JsonUtils.toJsonString(result));
      return result;
    } catch (Exception e) {
      logger.error(" alreadReturned exception {}", e);
      return ResponseResult.build(1000, e.getLocalizedMessage(), null);
    }
  }

  /**
   * 租赁和以租代购订单确认签收调用接口
   * 
   * @param updateOrderStateReq
   * @return
   * @throws createBy:临时工 createDate:2018年3月28日
   */
  @ApiOperation(value = "确认签收", httpMethod = "POST", notes = " ResponseResult<String>", response = ResponseResult.class)
  @PostMapping(value = "/api/order/signedOrder", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<String> signedOrder(@RequestBody UpdateOrderStateReq updateOrderStateReq) {
    logger.info("signedOrder,req={}", JsonUtils.toJsonString(updateOrderStateReq));
    try {
      if (StringUtils.isEmpty(updateOrderStateReq.getRentRecordNo())) {
        ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号不能为空", null);
      }
      updateOrderStateReq.setState(BackRentState.NormalPerformance.getCode());
      ResponseResult<String> result = irentRecordService.signedOrder(updateOrderStateReq);
      logger.info("signedOrder,rsp={}", JsonUtils.toJsonString(result));
      return result;
    } catch (Exception e) {
      logger.error("signedOrder exception {}", e);
      return ResponseResult.build(1000, e.getLocalizedMessage(), null);
    }
  }

  @ApiOperation(value = "分页查询开票信息 提供给清算", httpMethod = "POST", notes = " ResponseResult<ResultPager<QueryInvoiceRsp>>", response = ResponseResult.class)
  @PostMapping(value = "/api/order/queryPageInvoice", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<ResultPager<QueryInvoiceRsp>> queryPageInvoice(@RequestBody QueryInvoiceReq queryInvoiceReq) {
    ResponseResult<String> validateResult = super.getValidatedResult(this.validator,
      queryInvoiceReq,
      Default.class);
    logger.info("queryPageInvoice,req={}", JsonUtils.toJsonString(queryInvoiceReq));
    if (validateResult == null) {
      try {
        ResponseResult<ResultPager<QueryInvoiceRsp>> result = new ResponseResult<>();
        result = irentRecordService.queryPageInvoice(queryInvoiceReq);
        logger.info("queryPageInvoice,rsp={}", JsonUtils.toJsonString(result));
        return result;
      } catch (Exception e) {
        logger.error("queryPageInvoice exception {}", e);
        return ResponseResult.build(1000, e.getLocalizedMessage(), null);
      }
    }
    return ResponseResult.build(validateResult.getErrCode(), validateResult.getErrMsg(), null);
  }

  /**
   * 清算系统调用确认开票
   * 
   * @param rentRecordNo
   * @param bindingResult
   * @return
   * @throws createBy:临时工 createDate:2018年3月30日
   */
  @PostMapping(value = "/api/order/invoiceEnd", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseResult<String> invoiceEnd(@RequestBody String rentRecordNo) {

    logger.info("invoiceEnd,req={}", rentRecordNo);
    try {
      if (StringUtils.isEmpty(rentRecordNo)) {
        return ResponseResult.build(OrderResultCode.REQ_PARA_ERROR.getCode(), "订单编号不能为空", null);
      }
      ResponseResult<String> invoiceEndrsp = irentRecordService.invoiceEnd(rentRecordNo);
      logger.info("invoiceEnd,rsp={}", JsonUtils.toJsonString(invoiceEndrsp));
      return invoiceEndrsp;
    } catch (Exception e) {
      logger.error("invoiceEnd exception {}", e);
      return ResponseResult.build(1000, e.toString(), null);
    }
  }

}
