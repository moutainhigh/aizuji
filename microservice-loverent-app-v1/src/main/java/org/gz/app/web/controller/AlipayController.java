package org.gz.app.web.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.gz.app.configure.AlipayConfigure;
import org.gz.app.dto.H5AlipayApplyDto;
import org.gz.app.feign.CouponServiceClient;
import org.gz.app.feign.PaymentServiceClient;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.StringUtils;
import org.gz.liquidation.common.Enum.TransactionTypeEnum;
import org.gz.liquidation.common.Enum.TransactionWayEnum;
import org.gz.liquidation.common.dto.PayReq;
import org.gz.liquidation.common.dto.PreparePayResp;
import org.gz.oss.common.entity.Coupon;
import org.gz.oss.common.entity.CouponUserQuery;
import org.gz.user.entity.AppUser;
import org.gz.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 支付宝支付
 * 
 * @author yangdx
 *
 */
@RequestMapping("/alipay")
@RestController
@Slf4j
public class AlipayController extends AppBaseController {

	@Autowired
	private AlipayConfigure alipayConfigure;
	
	@Autowired
	private PaymentServiceClient paymentServiceClient;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CouponServiceClient couponServiceClient;
	
	/**
	 * App申请支付宝支付接口
	 * @param body
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/appTradeApply", method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Map<String, String>> appTradeApply(@RequestBody JSONObject body, HttpServletRequest request) {
		log.info("--【支付宝APP支付申请】 start execute appTradeApply, params: {}", body.toJSONString());
		ResponseResult<Map<String, String>> appResult = new ResponseResult<>();
		Map<String, String> dataMap = new HashMap<>(); 
		
		List<String> list = this.getUserFields(request, "userId", "phoneNum");
		//用户ID
		Long userId = Long.valueOf(list.get(0));
		
		log.info("---> 【支付宝APP支付申请】 start query user info, userId: {}", userId);
    	ResponseResult<AppUser> result = userService.queryUserById(userId);
    	log.info("---> 【支付宝APP支付申请】  query user info end, result: {}", JSONObject.toJSONString(result));
    	if(result.getData()==null){
    		return ResponseResult.build(result.getErrCode(), result.getErrMsg(), null);
    	}
    	AppUser user = result.getData();
		
    	//检查优惠券是否已使用
    	Long couponId = body.getLong("couponId");
		if (couponId != null) {
			try {
				checkCoupon(couponId, userId);
			} catch (Exception e) {
				appResult.setErrCode(-1);
				appResult.setErrMsg(e.getMessage());
				return appResult;
			}
		}
    	
		PayReq payReq = buildPayReq(body, user);
		log.info("---> 【支付宝APP支付申请】 start execute preparePay, userId: {}", userId);
		ResponseResult<PreparePayResp> prePayResult = paymentServiceClient.preparePay(payReq);
		log.info("---> 【支付宝APP支付申请】  execute preparePay end, result: {}", JSONObject.toJSONString(prePayResult));
		if (prePayResult.getErrCode() != 0) {
			return ResponseResult.build(prePayResult.getErrCode(), prePayResult.getErrMsg(), null);
		}
		PreparePayResp preparePay = prePayResult.getData();
		
		//实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient(
				AlipayConfigure.GATE_WAY, 
				AlipayConfigure.APPID, 
				AlipayConfigure.RSA_PRIVATE_KEY, 
				AlipayConfigure.FORMAT, 
				AlipayConfigure.CHARSET, 
				AlipayConfigure.ALIPAY_PUBLIC_KEY, 
				AlipayConfigure.SIGNTYPE);
		
		AlipayTradeAppPayRequest payRequest = new AlipayTradeAppPayRequest();
		
		//交易金额
		BigDecimal totalAmountDecimal = new BigDecimal(body.getString("total_amount"))
			.setScale(2, RoundingMode.UP);
		
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody("loverent orderno " + body.getString("order_no"));
		model.setSubject(TransactionTypeEnum.getDesc(body.getString("transactionType")));
		model.setOutTradeNo(preparePay.getTransactionSN());
		model.setTimeoutExpress("30m");
		model.setTotalAmount(totalAmountDecimal.toString());
		model.setProductCode("QUICK_MSECURITY_PAY");
		payRequest.setBizModel(model);
		payRequest.setNotifyUrl(alipayConfigure.getAlipayNotifyUrl());
		
		try {
	        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(payRequest);
	        String appySignData = response.getBody();
	        dataMap.put("applySignData", appySignData);
	        dataMap.put("outTransNo", preparePay.getTransactionSN());
	        appResult.setData(dataMap);
		} catch (AlipayApiException e) {
			log.error("【支付宝APP支付申请】--- sdkExecute failed, e: {}", e);
			appResult.setErrCode(-1);
			appResult.setErrMsg("支付宝申请支付失败");
		}
		return appResult;
	}
	
	private void checkCoupon(Long couponId, Long userId) {
		CouponUserQuery cuq = new CouponUserQuery();
		cuq.setCouponId(couponId);
		cuq.setUserId(userId);
		log.info("--->【优惠券校验】--start query coupon detail, params: {}", JSONObject.toJSONString(cuq));
		ResponseResult<Coupon> rst = couponServiceClient.queryCouponDetail(cuq);
		log.info("--->【优惠券校验】--query coupon detail success, rst:{}", JSONObject.toJSONString(rst));
		if (rst.getErrCode() == 0) {
			Coupon coupon = rst.getData();
			if (coupon != null) {
				byte used = 1;
				byte status = coupon.getStatus();
				if (status == used) {
					throw new RuntimeException("优惠券已使用");	
				}
				
				Date validEndTime = coupon.getValidityEndTime(); //有效结束时间
				if (validEndTime.getTime() < System.currentTimeMillis()) {
					throw new RuntimeException("优惠券已过期");
				}
			} else {
				throw new RuntimeException("获取优惠券信息失败");
			}
		} else {
			throw new RuntimeException("获取优惠券信息失败");
		}
	}

	/**
	 * H5申请支付宝支付接口
	 * @param body
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/h5TradeApply", method=RequestMethod.POST)
	public ResponseResult<String> h5TradeApply(@Valid H5AlipayApplyDto dto, BindingResult bindingResult, HttpServletRequest request, 
			HttpServletResponse response) {
		ResponseResult<String> result = new ResponseResult<>();
		log.info("--【支付宝H5支付申请】 start execute h5TradeApply, params: {}", JSONObject.toJSONString(dto));
		ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
		if (validateResult != null) {
			log.error("--【支付宝H5支付申请】 --参数验证失败, validateResult: {}", JSONObject.toJSONString(validateResult));
			return validateResult;
		}
		
		List<String> list = this.getUserFields(request, "userId", "phoneNum");
		//用户ID
		Long userId = Long.valueOf(list.get(0));
		
		log.info("---> 【支付宝支付】 start query user info, userId: {}", userId);
    	ResponseResult<AppUser> userResult = userService.queryUserById(userId);
    	log.info("---> 【支付宝支付】  query user info end, result: {}", JSONObject.toJSONString(userResult));
    	if(userResult.getData()==null){
    		return ResponseResult.build(-1, userResult.getErrMsg(), null);
    	}
    	AppUser user = userResult.getData();
		
		PayReq payReq = buildPayReq(dto, user);
		ResponseResult<PreparePayResp> prePayResult = paymentServiceClient.preparePay(payReq);
		if (prePayResult.getErrCode() != 0) {
			log.error("--【支付宝H5支付申请】 --清算生成预支付失败, prePayResult: {}", JSONObject.toJSONString(prePayResult));
			return ResponseResult.build(-1, prePayResult.getErrMsg(), null);
		}
		PreparePayResp preparePay = prePayResult.getData();
		
		//实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient(
				AlipayConfigure.GATE_WAY, 
				AlipayConfigure.APPID, 
				AlipayConfigure.RSA_PRIVATE_KEY, 
				AlipayConfigure.FORMAT, 
				AlipayConfigure.CHARSET, 
				AlipayConfigure.ALIPAY_PUBLIC_KEY, 
				AlipayConfigure.SIGNTYPE);
		
		AlipayTradeWapPayRequest payRequest = new AlipayTradeWapPayRequest();
		
		//交易金额
		BigDecimal totalAmountDecimal = new BigDecimal(dto.getTotal_amount())
			.setScale(2, RoundingMode.UP);
		
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody("loverent orderno " + dto.getOrder_no());
		model.setSubject(TransactionTypeEnum.getDesc(dto.getTransactionType()));
		model.setOutTradeNo(preparePay.getTransactionSN());
		model.setTimeoutExpress("30m");
		model.setTotalAmount(totalAmountDecimal.toString());
		model.setProductCode("QUICK_MSECURITY_PAY");
		payRequest.setBizModel(model);
		payRequest.setNotifyUrl(alipayConfigure.getAlipayNotifyUrl());
		payRequest.setReturnUrl(alipayConfigure.getAlipayReturnUrl());
		
		// form表单生产
		String form = "";
		try {
			form = alipayClient.pageExecute(payRequest).getBody();
			result.setData(form);
			/*response.setContentType("text/html;charset=" + AlipayConfigure.CHARSET);
			response.getWriter().write(form);
			response.getWriter().flush();
			response.getWriter().close();*/
		} catch (AlipayApiException e) {
			log.error("AlipayController.tradeApply sdkExecute failed, e: {}", e);
		} catch (Exception e) {
			log.error("---> 【支付宝支付】 支付请求发送失败: {}", e);
		}
		return result;
	}
	
	private PayReq buildPayReq(@Valid H5AlipayApplyDto dto, AppUser user) {
		PayReq payReq = new PayReq();
		payReq.setAmount(new BigDecimal(dto.getTotal_amount()).setScale(2, RoundingMode.UP));	//交易金额
		payReq.setOrderSN(dto.getOrder_no());		//app订单编号
		payReq.setPhone(user.getPhoneNum());	//手机号
		payReq.setRealName(user.getRealName());	//付款人姓名
		payReq.setSourceType(dto.getSourceType());	//销售订单
		payReq.setTransactionSource(dto.getTransactionSource());	//交易入口
		payReq.setTransactionType(dto.getTransactionType());	//交易类型 首期款交易/租金/回收/买断/未收货违约
		payReq.setTransactionWay("Alipay");		//交易方式  连连
		payReq.setUserId(user.getUserId());	//APP用户ID
		payReq.setUsername(user.getPhoneNum());//用户名
		return payReq;
	}

	/**
	 * 获取支付流水号实体
	 * @param body
	 * @param userId
	 * @param phoneNum
	 * @param user 
	 * @return
	 */
	private PayReq buildPayReq(JSONObject body, AppUser user) {
		PayReq payReq = new PayReq();
		payReq.setAmount(new BigDecimal(body.getString("total_amount")).setScale(2, RoundingMode.UP));	//交易金额
		payReq.setOrderSN(body.getString("order_no"));		//app订单编号
		payReq.setPhone(user.getPhoneNum());	//手机号
		payReq.setRealName(user.getRealName());	//付款人姓名
		payReq.setSourceType(body.getString("sourceType"));	//销售订单
		payReq.setTransactionSource(body.getString("transactionSource"));	//交易入口
		payReq.setTransactionType(body.getString("transactionType"));	//交易类型 首期款交易/租金/回收/买断/未收货违约
		payReq.setTransactionWay(TransactionWayEnum.ALIPAY.getCode());		//交易方式  连连
		payReq.setUserId(user.getUserId());	//APP用户ID
		payReq.setUsername(user.getPhoneNum());//用户名
		
		Long couponId = body.getLong("couponId");
		if (couponId != null) {
			payReq.setCouponId(couponId);	//优惠券ID
			payReq.setCouponFee(new BigDecimal(body.getString("couponFee")).setScale(2, RoundingMode.UP));	//优惠券金额
		}
		return payReq;
	}
	
	@RequestMapping("/payCallback")
	@ResponseBody
	public String alipayCallback(HttpServletRequest request, HttpServletResponse response) {
		log.info("--【支付宝支付回调】- enter alipayCallback....");
		Map<String, String> notifyData = parseAlipayNotifyData(request);
		log.info("--【支付宝支付回调】---notifyData: {}", JSONObject.toJSONString(notifyData));
		
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
			System.out.println(JSONObject.toJSONString(notifyData));
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
	
	private Map<String, String> parseAlipayNotifyData(HttpServletRequest request) {
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
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			notifyData.put(name, valueStr);
		}
		return notifyData;
	}
}
