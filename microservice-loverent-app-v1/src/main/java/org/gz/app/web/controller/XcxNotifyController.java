package org.gz.app.web.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.gz.aliOrder.dto.OrderDetailResp;
import org.gz.aliOrder.dto.UpdateCreditAmountReq;
import org.gz.aliOrder.dto.UpdateOrderStateReq;
import org.gz.aliOrder.hystrix.IAliOrderService;
import org.gz.app.feign.PaymentServiceClient;
import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.dto.alipay.ZhimaOrderCreditPayReq;
import org.gz.liquidation.common.dto.alipay.ZhimaOrderCreditPayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

/**
 * 小程序回调
 * 
 * @author yangdx
 *
 */
@RestController
@RequestMapping("/aizuji")
@Slf4j
public class XcxNotifyController {

	/**
	 * 小程序订单service
	 */
	@Autowired 
	private IAliOrderService aliOrderService;
	
	@Autowired
	private PaymentServiceClient paymentServiceClient;
	
	@RequestMapping(value="/xcxResult/notify")
	@ResponseBody
	public void xcxResultNotify(HttpServletRequest request) {
		log.info("============>>>>>start execute xcxResultNotify");
		
		Map<String, String> dataMap = parseNotifyData(request);
		log.info("dataMap: {}", JSONObject.toJSON(dataMap));
		log.info("============>>>>>execute xcxResultNotify success, result: {}", "");
		
		//通知类型
		String notifyType = dataMap.get("notify_type");
		if ("ZM_RENT_ORDER_CREATE".equals(notifyType)) {
			//订单创建成功
			orderCreateNotify(notifyType, dataMap);
		} else if ("ZM_RENT_ORDER_FINISH".equals(notifyType) || "ZM_RENT_ORDER_INSTALLMENT".equals(notifyType)) {
			String pay_status = dataMap.get("pay_status");
			if ("PAY_SUCCESS".equals(pay_status)) {
				//扣款完成
				orderPaymentNotify(notifyType, dataMap);
			}
		} else if ("ZM_RENT_ORDER_CANCEL".equals(notifyType)) {
			//取消订单
		//	orderCancelNotify(notifyType, dataMap);
		}
		
	}
	
	private void orderCancelNotify(String notifyType,
			Map<String, String> dataMap) {
		//芝麻订单号
		String zm_order_no = dataMap.get("zm_order_no");
		
		//外部订单号
		String out_order_no = dataMap.get("out_order_no");
		
		UpdateOrderStateReq updateOrderStateReq = new UpdateOrderStateReq();
		updateOrderStateReq.setRentRecordNo(out_order_no);
		
		aliOrderService.cancleOrder(updateOrderStateReq);
	}

	/**
	 * 扣款完成通知
	 * @param notifyType
	 * @param dataMap
	 */
	private void orderPaymentNotify(String notifyType,
			Map<String, String> dataMap) {
		//芝麻订单号
		String zm_order_no = dataMap.get("zm_order_no");
		//外部订单号
		String out_order_no = dataMap.get("out_order_no");
		//商户资金交易号
		String out_trans_no = dataMap.get("out_trans_no");
		//资金流水号
		String alipay_fund_order_no = dataMap.get("alipay_fund_order_no");
		//本次处理支付金额
		String pay_amount = dataMap.get("pay_amount");
		//支付结果状态  成功-PAY_SUCCESS
		String pay_status = dataMap.get("pay_status");
		//订单完结时间
		String pay_time = dataMap.get("pay_time");
		String channel = dataMap.get("channel");
		String notify_app_id = dataMap.get("notify_app_id");
	
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");	//2018-03-31 15:01:33
		
		String orderOperateType = null;
		if ("ZM_RENT_ORDER_FINISH".equals(notifyType)) {
			orderOperateType = "FINISH";
		} else {
			orderOperateType = "INSTALLMENT";
		}
		ZhimaOrderCreditPayResponse req = new ZhimaOrderCreditPayResponse();
		req.setAlipayFundOrderNo(alipay_fund_order_no);
		req.setChannel(channel);
		req.setNotifyAppId(notify_app_id);
		req.setNotifyType(orderOperateType);
		req.setOutOrderNo(out_order_no);
		req.setOutTransNo(out_trans_no);
		req.setPayAmount(pay_amount);
		req.setPayStatus(pay_status);
		
		Date payTime = null;
		try {
			format.parse(pay_time);
		} catch (Exception e) {
		}
		req.setPayTime(payTime);
		req.setZmOrderNo(zm_order_no);
		
		log.info("--->【芝麻订单】--pay success, call zhimaOrderCreditPayCallback, params: {}", JSONObject.toJSONString(req));
		ResponseResult<?> result = paymentServiceClient.zhimaOrderCreditPayCallback(req);
		log.info("--->【芝麻订单】--pay success, call zhimaOrderCreditPayCallback success, result: {}", JSONObject.toJSONString(result));
	}

	private void orderCreateNotify(String notifyType,
			Map<String, String> dataMap) {
		String fundType = dataMap.get("fund_type");

		//芝麻订单号
		String zm_order_no = dataMap.get("zm_order_no");
		
		//订单来源
		String channel = dataMap.get("channel");
		
		//外部订单号
		String out_order_no = dataMap.get("out_order_no");
		
		//用户信用权益金额
		String credit_privilege_amount = dataMap.get("credit_privilege_amount");
		
		UpdateCreditAmountReq updateCreditAmountReq = new UpdateCreditAmountReq();
		updateCreditAmountReq.setCreditAmount(new BigDecimal(credit_privilege_amount).setScale(2, RoundingMode.UNNECESSARY));
		updateCreditAmountReq.setFundType(fundType);
		updateCreditAmountReq.setRentRecordNo(out_order_no);
		updateCreditAmountReq.setZmOrderNo(zm_order_no);
		
		log.info("---->【芝麻订单】--zhima order create success, start update order info...");
		aliOrderService.updateCreditAmount(updateCreditAmountReq);
		log.info("---->【芝麻订单】--zhima order create success, update order info end...");
		
		//发起首期款扣款
		log.info("---->【芝麻订单】--zhima order create success, start call credit pay...");
		ResponseResult<OrderDetailResp> orderDetail = aliOrderService.queryBackOrderDetail(out_order_no);
		if (orderDetail.getErrCode() == 0) {
			OrderDetailResp resp = orderDetail.getData();
			BigDecimal leaseAmount = resp.getLeaseAmount();
			
			ZhimaOrderCreditPayReq zhimaOrderCreditPayReq = new ZhimaOrderCreditPayReq();
			zhimaOrderCreditPayReq.setOrderOperateType("INSTALLMENT");
			zhimaOrderCreditPayReq.setOrderSN(out_order_no);
			zhimaOrderCreditPayReq.setPayAmount(leaseAmount);
			zhimaOrderCreditPayReq.setPhone(resp.getPhoneNum());
			zhimaOrderCreditPayReq.setRealName(resp.getRealName());
			zhimaOrderCreditPayReq.setUserId(resp.getZmUserId());
			zhimaOrderCreditPayReq.setZmOrderNo(zm_order_no);
			
			ResponseResult<?> result = paymentServiceClient.zhimaOrderCreditPay(zhimaOrderCreditPayReq);
			if (result.getErrCode() == 0) {
				log.info("---->【芝麻订单】--zhima order create success, call credit pay success...");
			} else {
				log.error("---->【芝麻订单】--zhima order create success, call credit pay error...");
			}
		}
		
		
	}

	private Map<String, String> parseNotifyData(HttpServletRequest request) {
		Map<String, String> notifyData = new HashMap<String, String>();
		
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			notifyData.put(name, valueStr);
		}
		return notifyData;
	}
}
