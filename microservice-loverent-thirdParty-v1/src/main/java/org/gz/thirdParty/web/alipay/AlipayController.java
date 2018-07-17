package org.gz.thirdParty.web.alipay;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.JsonUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.thirdParty.bean.alipay.ZhimaOrderCreditPayReq;
import org.gz.thirdParty.service.alipay.AlipayService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.ZhimaMerchantOrderCreditPayResponse;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @Description:支付宝控制器
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年4月2日 	Created
 */
@RestController
@RequestMapping("/alipay")
@Slf4j
public class AlipayController extends BaseController {
	
	/**
	 * 
	 * @Description: 芝麻信用支付
	 * @param zhimaOrderCreditPayReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年4月2日
	 */
	@PostMapping(value = "/zhimaOrder/creditPay", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> zhimaOrderCreditPay(@RequestBody ZhimaOrderCreditPayReq zhimaOrderCreditPayReq){
		log.info(JsonUtils.toJsonString(zhimaOrderCreditPayReq));
		try {
			ZhimaMerchantOrderCreditPayResponse response = AlipayService.creditPay(
					zhimaOrderCreditPayReq.getOrderOperateType(), zhimaOrderCreditPayReq.getZmOrderNo(),
					zhimaOrderCreditPayReq.getOrderSN(), zhimaOrderCreditPayReq.getOutTransNo(),
					zhimaOrderCreditPayReq.getPayAmount(), zhimaOrderCreditPayReq.getRemark());
			return ResponseResult.buildSuccessResponse(response);
		} catch (AlipayApiException e) {
			log.error(e.getMessage());
			return ResponseResult.build(ResponseStatus.ALIPAY_CREDIT_PAY_ERROR.getCode(), ResponseStatus.ALIPAY_CREDIT_PAY_ERROR.getMessage(), null);
			
		}
	}
	/**
	 * 
	 * @Description: 查询支付宝交易订单
	 * @param outTradeNo 商户交易流水号
	 * @param tradeNo 支付宝交易流水号
	 * @param transactionSource 交易来源 ALI_APPLET:小程序订单 其他都认为是APP订单
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年4月2日
	 */
	@PostMapping(value = "/queryTrade")
	public ResponseResult<?> queryTrade(String outTradeNo,String tradeNo,String transactionSource){
		try {
			AlipayTradeQueryResponse response = AlipayService.queryTrade(outTradeNo, tradeNo, transactionSource);
			return ResponseResult.buildSuccessResponse(response);
		} catch (AlipayApiException e) {
			log.error(e.getMessage());
			return ResponseResult.build(ResponseStatus.ALIPAY_TRADE_QUERY_ERROR.getCode(),
					ResponseStatus.ALIPAY_TRADE_QUERY_ERROR.getMessage(), null);
		}
		
	}
}
