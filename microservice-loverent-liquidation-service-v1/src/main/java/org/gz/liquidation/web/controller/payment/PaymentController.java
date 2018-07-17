package org.gz.liquidation.web.controller.payment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.DateUtils;
import org.gz.common.utils.JsonUtils;
import org.gz.common.utils.UUIDUtils;
import org.gz.common.utils.WXPayUtil;
import org.gz.common.web.controller.BaseController;
import org.gz.liquidation.common.Enum.SourceTypeEnum;
import org.gz.liquidation.common.Enum.TransactionSourceEnum;
import org.gz.liquidation.common.Enum.TransactionTypeEnum;
import org.gz.liquidation.common.Enum.TransactionWayEnum;
import org.gz.liquidation.common.dto.DownpaymentStatisticsReq;
import org.gz.liquidation.common.dto.DownpaymentStatisticsResp;
import org.gz.liquidation.common.dto.PayReq;
import org.gz.liquidation.common.dto.PreparePayResp;
import org.gz.liquidation.common.dto.TransactionRecordReq;
import org.gz.liquidation.common.dto.alipay.ZhimaOrderCreditPayReq;
import org.gz.liquidation.common.dto.alipay.ZhimaOrderCreditPayResponse;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.liquidation.config.AlipayConfigure;
import org.gz.liquidation.entity.LianPayCallBack;
import org.gz.liquidation.entity.TransactionRecord;
import org.gz.liquidation.service.account.AccountRecordService;
import org.gz.liquidation.service.repayment.ReceivablesService;
import org.gz.liquidation.service.repayment.RepaymentService;
import org.gz.liquidation.service.transactionrecord.TransactionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.internal.util.AlipaySignature;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @Description:	支付控制器
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月27日 	Created
 */
@RestController
@RequestMapping("/payment")
@Slf4j
public class PaymentController extends BaseController{
	
	public static final SimpleDateFormat FMT_yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
	
	@Autowired
	private TransactionRecordService transactionRecordService;
	
	@Autowired
	private AccountRecordService accountRecordService;
	
	@Autowired
	private ReceivablesService receivablesService;
	
	@Autowired
	private Validator validator;
	
	@Resource
	private RepaymentService repaymentService;
	/**
	 * 微信支付回调入口(测试使用)
	 * @param lianPayCallBack
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@PostMapping(value = "/WXCallBack")
	public String WXCallBack(@RequestBody String xml) throws Exception{
		log.info(LiquidationConstant.LOG_PREFIX+" WXCallBack is begin.{}"+JSON.toJSONString(xml));
		Map<String, String> respMap = WXPayUtil.xmlToMap(xml);
		// SUCCESS/FAIL 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
		String return_code = respMap.get("return_code");
		log.info(LiquidationConstant.LOG_PREFIX+" WXCallBack return_code:{}",return_code);
		
		TransactionRecord param = new TransactionRecord();
		Date finishTime = new Date();
		if(LiquidationConstant.SUCCESS.equalsIgnoreCase(return_code)){// 成功
			/* 以下字段在return_code为SUCCESS的时候有返回 */
			// 业务结果 SUCCESS/FAIL
			String result_code = respMap.get("result_code");
			// 总金额	total_fee	是	Int	100	订单总金额，单位为分
			String total_fee = respMap.get("total_fee");
			int totalFee = Integer.valueOf(total_fee);
			// 现金支付金额	cash_fee	是	Int	100	现金支付金额订单现金支付金额，详见支付金额(https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_2)
			String cash_fee = respMap.get("cash_fee");
			int cashFee = Integer.valueOf(cash_fee);
			// 商户订单号 out_trade_no	  是	String(32) 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
			String tradeNo = respMap.get("out_trade_no");
			// 商家数据包	attach	否	String(128)	123456	商家数据包，原样返回
			String attach = respMap.get("attach");
			// 支付完成时间	time_end	是	String(14)	20141030133525	支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
			String time_end = respMap.get("time_end");
			finishTime = DateUtils.getDate(time_end,FMT_yyyyMMddHHmmss);
			
			log.info(LiquidationConstant.LOG_PREFIX+" WXCallBack result_code:{} total_fee:{} cash_fee:{} out_trade_no:{} attach:{} time_end:{}",result_code,totalFee,cashFee,tradeNo,attach,time_end);
			param.setTransactionSN(tradeNo);
		}
		
		try {
			// 查询支付对应的流水记录
			List<TransactionRecord> list = transactionRecordService.selectByTransactionRecord(param);
			
			if(CollectionUtils.isNotEmpty(list)){
				log.info(LiquidationConstant.LOG_PREFIX+"	执行微信回调业务处理payCallBackHandler");
				TransactionRecord transactionRecord = list.get(0);
				int state = transactionRecord.getState();
				
				if(LiquidationConstant.STATE_SUCCESS != state && LiquidationConstant.STATE_FAILURE != state){// 如果交易状态已经是成功或者失败则不进行处理
					transactionRecord.setFinishTime(finishTime);
					receivablesService.payCallBackHandler(transactionRecord,LiquidationConstant.SUCCESS);
				}else{
					log.info(LiquidationConstant.LOG_PREFIX+" 对应的流水记录的交易状态为成功或者失败，无需处理！");
				}
				
			}else{
				log.info(LiquidationConstant.LOG_PREFIX+" 流水号：{}对应的记录不存在！",param.getTransactionSN());
			}
			
		} catch (Exception e) {
			
			log.error(LiquidationConstant.LOG_PREFIX+" 处理微信支付回调业务处理异常！>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			log.error(e.getLocalizedMessage());
			return null;
		}

		//respMap.forEach((k,v)->System.out.println(k+":"+v));
		
        //微信要求返回格式
        Map<String, String> map = new HashMap<>();
        map.put("return_code", "SUCCESS");
        map.put("return_msg", "OK");
        String resp = WXPayUtil.mapToXml(map);
        log.info(LiquidationConstant.LOG_PREFIX+" WXCallBack is end.{}",resp);
		return resp;
	}
	
	/**
	 * 连连支付回调入口
	 * @param orderQuery
	 * @param bindingResult
	 * @return
	 */
	@PostMapping(value = "/LianPayCallBack")
	public Map<String, String> lianPayCallback(@RequestBody String object){
		//{"bank_code":"01030000","dt_order":"20180112105432","money_order":"0.01","no_order":"SO1801110000000046","oid_partner":"201701051001392503","oid_paybill":"2018011253201968","pay_type":"D","result_pay":"SUCCESS","settle_date":"20180112","sign":"APUurtxhHa8MvkBNylUlLBn93VqHySNLzYQ08HKLiu1gdnqOt3WFi13e0SgXQ/qw+rx2MsPUIz1SNEA7Kedo5HuML4xEBcfXewqjWF8PCPovmV78gSzYlXcX0QVAW1xAUlHnWivhHm7nLxW+OtEkYOYnEyl/ABrEh/cYLkueozU=","sign_type":"RSA"}
		log.info("lianPayCallback is begin.{}"+JSON.toJSONString(object));
		JSONObject jsonObject = JSON.parseObject(object);
		LianPayCallBack lianPayCallBack = JSON.toJavaObject(jsonObject,LianPayCallBack.class);
		String transactionSN = lianPayCallBack.getNo_order();
		String result_pay = lianPayCallBack.getResult_pay();
		log.info(LiquidationConstant.LOG_PREFIX+"lianPayCallback 交易流水号:{}  result_pay:{}",transactionSN,result_pay);
		
		if(LiquidationConstant.SUCCESS.equalsIgnoreCase(result_pay) || LiquidationConstant.FAILURE.equalsIgnoreCase(result_pay)){// 只处理支付成功或失败
			
			TransactionRecord param = new TransactionRecord();
			param.setTransactionSN(transactionSN);
			
			try {
				// 查询支付对应的流水记录
				List<TransactionRecord> list = transactionRecordService.selectByTransactionRecord(param);
				
				if(CollectionUtils.isNotEmpty(list)){
					
					TransactionRecord transactionRecord = list.get(0);
					int state = transactionRecord.getState();
					if(LiquidationConstant.STATE_SUCCESS != state && LiquidationConstant.STATE_FAILURE != state){// 如果交易状态已经是成功或者失败则不进行处理
						
						log.info(LiquidationConstant.LOG_PREFIX+"	执行回调业务处理 payCallBackHandler 开始...");
						receivablesService.payCallBackHandler(transactionRecord,result_pay);
						log.info(LiquidationConstant.LOG_PREFIX+"	执行回调业务处理payCallBackHandler 结束...");
						
					}else{
						log.info(LiquidationConstant.LOG_PREFIX+" 对应的流水记录的交易状态为成功或者失败，无需处理！");
					}
					
				}else{
					log.info(LiquidationConstant.LOG_PREFIX+" 流水号：{}对应的记录不存在！",transactionSN);
				}
			} catch (Exception e) {
				
				log.error(LiquidationConstant.LOG_PREFIX+" 处理支付回调业务处理异常！>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				log.error(e.getLocalizedMessage());
				return null;
			}
			
		}else{
			return null;
		}
        //连连要求返回格式
        Map<String, String> map = new HashMap<>();
        map.put("ret_code", "0000");
        map.put("ret_msg", "交易成功");
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> lianPayCallback is end.{}",JSON.toJSONString(map));
		return map;
	}
	/**
	 * 
	 * @Description: 支付宝回调
	 * @param request
	 * @param response
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月6日
	 */
	@RequestMapping("/alipayCallback")
	@ResponseBody
	public String alipayCallback(HttpServletRequest request, HttpServletResponse response) {
		log.info(LiquidationConstant.LOG_PREFIX+"--【支付宝支付回调】--- alipayCallback start....");
		Map<String, String> notifyData = parseAlipayNotifyData(request);
		log.info(LiquidationConstant.LOG_PREFIX+"--【支付宝支付回调】--- alipayCallback notifyData: {}", JSONObject.toJSONString(notifyData));
		
		String ackMessage = "";
		boolean signCheckResult = false;
		boolean checkReceiverPass = checkReceiver(notifyData);
		
		if(checkReceiverPass) {
			try {
				signCheckResult = alipaySignCheck(notifyData);
			} catch (Exception e) {
				log.error("--->【支付通知】校验支付宝签名异常: {}", e);
			}	
		}
		
		if(!signCheckResult || !checkReceiverPass) {
			ackMessage = "error";
		}else {
			ackMessage = "success";
		}
		
		if(signCheckResult && checkReceiverPass) {
			// 判断交易状态
			String tradeStatus = notifyData.get("trade_status");
			if(tradeStatus.equalsIgnoreCase(LiquidationConstant.ALIPAY_TRADE_SUCCESS)){//交易支付成功
				// 获取交易流水号
				String tradeNo = notifyData.get("out_trade_no");
				
				TransactionRecord param = new TransactionRecord();
				param.setTransactionSN(tradeNo);
				
				try {
					// 查询支付对应的流水记录
					List<TransactionRecord> list = transactionRecordService.selectByTransactionRecord(param);
					
					if(CollectionUtils.isNotEmpty(list)){
						
						TransactionRecord transactionRecord = list.get(0);
						int state = transactionRecord.getState();
						
						if(LiquidationConstant.STATE_SUCCESS != state && LiquidationConstant.STATE_FAILURE != state){// 如果交易状态已经是成功或者失败则不进行处理
							log.info(LiquidationConstant.LOG_PREFIX+"	执行回调业务处理 payCallBackHandler 开始...");
							receivablesService.payCallBackHandler(transactionRecord,LiquidationConstant.SUCCESS);
							log.info(LiquidationConstant.LOG_PREFIX+"	执行回调业务处理payCallBackHandler 结束...");
						}else{
							log.info(LiquidationConstant.LOG_PREFIX+" 对应的流水记录的交易状态为成功或者失败，无需处理！");
						}
						
					}else{
						log.info(LiquidationConstant.LOG_PREFIX+" 流水号：{}对应的记录不存在！",tradeNo);
					}
					
				} catch (Exception e) {
					
					log.error(LiquidationConstant.LOG_PREFIX+" 处理支付回调业务处理异常！>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
					log.error(e.getLocalizedMessage());
					return null;
				}
			}else {
				ackMessage = "error";
			}
		}
		
		if (!StringUtils.isEmpty(ackMessage)) {
			try {
				response.getWriter().print(ackMessage);
			} catch (Exception e) {
				log.error("--->【支付通知】通知结果失败: {}", e);
			}
		} else {
			log.error("---> 【支付通知】 ---ackMessage error, ackMessage is null");
		}
		
		return null;
	}
	/**
	 * 
	 * @Description: 支付宝商户id校验
	 * @param notifyData
	 * @return true 通过 false 失败
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月6日
	 */
	private boolean checkReceiver(Map<String, String> notifyData) {
		String sellerId = notifyData.get("seller_id");
		if(StringUtils.isEmpty(sellerId)) {
			log.error("---->【支付宝支付通知】 支付通知参数校验失败, sellerId = null");
			return false;
		}
		if(!sellerId.equals(AlipayConfigure.SELLER_ID)) {
			log.error("---->【支付宝支付通知】 支付通知参数校验失败, sellerId与商户ID不一致");
			return false;
		}
		return true;
	}

	/**
	 * 校验签名
	 * @param notifyData
	 * @return
	 * @throws Exception
	 */
	private boolean alipaySignCheck(Map<String, String> notifyData) throws Exception{		
		return AlipaySignature.rsaCheckV1(notifyData, AlipayConfigure.ALIPAY_PUBLIC_KEY, AlipayConfigure.CHARSET, "RSA2");
	}
	/**
	 * 
	 * @Description: 支付宝回调参数转换成map
	 * @param request
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月6日
	 */
	private Map<String, String> parseAlipayNotifyData(HttpServletRequest request) {
		Map<String, String> notifyData = new HashMap<String, String>();
		
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			notifyData.put(name, valueStr);
		}
		return notifyData;
	}
	/**
	 * 
	 * @Description: 查询交易状态
	 * @param transactionRecordReq
	 * @param bindingResult
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2017年12月30日
	 */
	@ApiOperation(value = "查询交易状态", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/transaction/state", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> querytransactionState(@RequestBody TransactionRecordReq transactionRecordReq, BindingResult bindingResult){
		//验证数据 
		ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
		if (validateResult == null) {
			try {
				TransactionRecord transactionRecord =  new TransactionRecord();
				transactionRecord.setTransactionSN(transactionRecordReq.getTransactionSN());
				List<TransactionRecord> list = transactionRecordService.selectByTransactionRecord(transactionRecord);
				return ResponseResult.buildSuccessResponse(list);
			} catch (Exception e) {
				log.error(e.getMessage());
				return ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(), null);
			}
		}
		return validateResult;
		
	}
	/**
	 * 
	 * @Description: 查询首期支付是否成功
	 * @param orderSN
	 * @return true 是 false 否
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月2日
	 */
	@ApiOperation(value = "查询首期支付是否成功", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/{orderSN}/downPayment", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<Boolean> queryDownPayment(@PathVariable("orderSN") String orderSN){
		if(StringUtils.isBlank(orderSN)){
			return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), 
					ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
		}
		
		Boolean flag = false;
		
		TransactionRecord transactionRecord =  new TransactionRecord();
		transactionRecord.setOrderSN(orderSN);
		transactionRecord.setTransactionType(TransactionTypeEnum.FIRST_RENT.getCode());
		List<TransactionRecord> list = transactionRecordService.selectByTransactionRecord(transactionRecord);
		if(CollectionUtils.isNotEmpty(list)){
			TransactionRecord tr = list.get(0);
			if(tr.getState() == LiquidationConstant.STATE_SUCCESS){
				flag = true;
			}
		}
		return ResponseResult.buildSuccessResponse(flag);
		
	}
	/**
	 * 
	 * @Description: 首期支付统计
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月4日
	 */
	@PostMapping(value = "/downpaymentStatistics", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<DownpaymentStatisticsResp> queryDownpaymentStatistics(@RequestBody DownpaymentStatisticsReq downpaymentStatisticsReq){
		
		return accountRecordService.queryDownpaymentStatistics(downpaymentStatisticsReq);
		
	}
	/**
	 * 
	 * @Description: 手动完成支付
	 * @param req
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月8日
	 */
	@PostMapping(value = "/payCorrected", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> payCorrected(@RequestBody @Valid TransactionRecordReq req, BindingResult bindingResult){
		log.info(LiquidationConstant.LOG_PREFIX+" payCorrected 参数req:{}",JsonUtils.toJsonString(req));
	    ResponseResult<String> validateResult = super.getValidatedResult(this.validator,req,Default.class);
		
	    if(validateResult == null){
			try {
				return transactionRecordService.payCorrected(req);
			} catch (Exception e) {
				log.error(e.getMessage());
				return ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
						ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(), req);
			}
		}
	    
		return ResponseResult.build(validateResult.getErrCode(), validateResult.getErrMsg(), null);
		
	}
	/**
	 * 
	 * @Description: 生成预支付订单
	 * @param payReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月10日
	 */
	@ApiOperation(value = "生成预支付订单", httpMethod = "POST",
	            notes = "操作成功返回 errorCode=0 预支付订单号 data:2018011015233388400000",
	            response = ResponseResult.class)
	@PostMapping(value = "/preparePay", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<PreparePayResp> preparePay(@RequestBody PayReq payReq) {
		log.info(LiquidationConstant.LOG_PREFIX+" preparePay 参数payReq:{}",JsonUtils.toJsonString(payReq));
	    ResponseResult<String> validateResult = super.getValidatedResult(this.validator,payReq,Default.class);
		if(validateResult == null){
			try {
				return transactionRecordService.preparePay(payReq);
			} catch (Exception e) {
				log.error(e.getMessage());
				return ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
						ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(), null);
			}
		}
	    
		return ResponseResult.build(validateResult.getErrCode(), validateResult.getErrMsg(), null);
		
	}
	
	@ApiOperation(value = "芝麻订单信用支付", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/zhimaOrder/creditPay", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> zhimaOrderCreditPay(@RequestBody ZhimaOrderCreditPayReq zhimaOrderCreditPayReq){
		log.info(LiquidationConstant.LOG_PREFIX+" zhimaOrderCreditPayCallback 参数req:{}",JsonUtils.toJsonString(zhimaOrderCreditPayReq));
		
		ResponseResult<String> validateResult = super.getValidatedResult(this.validator,zhimaOrderCreditPayReq,Default.class);
		if(validateResult == null) {
			String transactionSN = UUIDUtils.genDateUUID("");
			
			TransactionRecord transactionRecord = new TransactionRecord();
			transactionRecord.setTransactionSN(transactionSN);
			transactionRecord.setOrderSN(zhimaOrderCreditPayReq.getOrderSN());
			transactionRecord.setCreateBy(Long.valueOf(zhimaOrderCreditPayReq.getUserId()));
			transactionRecord.setTransactionAmount(zhimaOrderCreditPayReq.getPayAmount());
			transactionRecord.setTransactionType(TransactionTypeEnum.FIRST_RENT.getCode());
			transactionRecord.setRealName(zhimaOrderCreditPayReq.getRealName());
			transactionRecord.setPhone(zhimaOrderCreditPayReq.getPhone());
			transactionRecord.setTransactionWay(TransactionWayEnum.ALIPAY.getCode());
			transactionRecord.setTransactionSource(TransactionSourceEnum.ALI_APPLET.getCode());
			transactionRecord.setSourceType(SourceTypeEnum.SALES_ORDER.getCode());
			transactionRecord.setState(LiquidationConstant.STATE_TRADING);
			transactionRecord.setRemark(LiquidationConstant.REMARK_FIRST_INSTALLMENT);

			transactionRecord.setVersion(UUIDUtils.genDateUUID(""));
			Date date = new Date();
			transactionRecord.setCreateOn(date);
			transactionRecord.setUpdateOn(date);
			// 新增交易流水记录
			transactionRecordService.addTransactionRecord(transactionRecord);
			
			return repaymentService.zhimaOrderCreditPay(zhimaOrderCreditPayReq, transactionSN);
		}
		
		return ResponseResult.build(validateResult.getErrCode(), validateResult.getErrMsg(), null);
	}
	
	@ApiOperation(value = "芝麻订单信用支付回调", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/zhimaOrder/creditPay/callback", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> zhimaOrderCreditPayCallback(@RequestBody ZhimaOrderCreditPayResponse req) {
		log.info(LiquidationConstant.LOG_PREFIX+" zhimaOrderCreditPayCallback 参数req:{}",JsonUtils.toJsonString(req));
		ResponseResult<String> validateResult = super.getValidatedResult(this.validator,req,Default.class);
		if(validateResult == null) {
			return repaymentService.zhimaOrderCreditPayCallback(req);
		}
		
		return ResponseResult.build(validateResult.getErrCode(), validateResult.getErrMsg(), null);
	}
}
