package org.gz.app.web.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.configure.LianPayConfigure;
import org.gz.app.feign.CouponServiceClient;
import org.gz.app.feign.PaymentServiceClient;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.HttpClientUtil;
import org.gz.common.utils.UUIDUtils;
import org.gz.common.utils.WXPayConstants;
import org.gz.common.utils.WXPayUtil;
import org.gz.liquidation.common.Enum.TransactionWayEnum;
import org.gz.liquidation.common.dto.PayReq;
import org.gz.liquidation.common.dto.PreparePayResp;
import org.gz.oss.common.entity.Coupon;
import org.gz.oss.common.entity.CouponUserQuery;
import org.gz.user.entity.AppUser;
import org.gz.user.service.CardInfoService;
import org.gz.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/WX")
@Slf4j
public class WeiXinController extends AppBaseController {
	/**
	 * APP ID
	 */
	private static final String APP_ID = "wx9bec1c39b13a71e7";
	/**
	 * 商户ID
	 */
	private static final String MCH_ID = "1495341962";
	/**
	 * 商户后台设置API_key
	 */
	private static final String API_KEY = "Gzhlwkj1234567891011121314151617";
	/**
	 * 微信网关地址
	 */
	private static final String URL_HEAD = "https://api.mch.weixin.qq.com/";

	@Autowired
	private UserService userService;
	
	@Autowired
	private CardInfoService cardInfoService;
	
	@Autowired
	private LianPayConfigure payConfigure;
	
	@Autowired
	private PaymentServiceClient paymentServiceClient;
	
	@Autowired
	private CouponServiceClient couponServiceClient;
	
	/**
	 * 微信支付回调入口(测试使用)
	 * @param lianPayCallBack
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@PostMapping(value = "/WXCallBack")
	public String WXCallBack(@RequestBody String xml) throws Exception{
		log.info("WXCallBack is begin.{}"+JSON.toJSONString(xml));
		Map<String, String> respMap = WXPayUtil.xmlToMap(xml);
		String return_code = respMap.get("return_code");
		String result_code = respMap.get("result_code");
		String total_fee = respMap.get("total_fee");
		String cash_fee = respMap.get("cash_fee");
		String out_trade_no = respMap.get("out_trade_no");
		String attach = respMap.get("attach");
		String time_end = respMap.get("time_end");
		respMap.forEach((k,v)->System.out.println(k+":"+v));
        //连连要求返回格式
        Map<String, String> map = new HashMap<>();
        map.put("return_code", "SUCCESS");
        map.put("return_msg", "OK");
        String resp = WXPayUtil.mapToXml(map);
        log.info("WXCallBack is end.{}",resp);
		return resp;
	}
	
	/**
	 * 判断是否为正整数
	 * @param string
	 * @return
	 */
	public static boolean isInt(String string) {
        if (string == null)
            return false;
//        String regEx1 = "[\\-|\\+]?\\d+";
        String regEx1 = "[\\+]?\\d+";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(string);
        if (m.matches())
            return true;
        else
            return false;
    }
	
	/**
	 * 
	 * @param map 订单号:order_id 金额:total_fee 请求ip地址:ip  必传
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSign", method = RequestMethod.POST)
	public ResponseResult<?> applyOrder(@RequestBody JSONObject map, HttpServletRequest request){
		log.info("WX getSign is begin.{}",JSON.toJSONString(map));
		/*if(!isInt(map.getString("total_fee"))){
			return ResponseResult.build(9528, "total_fee必须为正整数", map);
		}*/
		List<String> list = getUserFields(request, "userId", "phoneNum");
		Long userId = Long.valueOf(list.get(0));
		
		log.info("lianpay getPaySign start query user info, userId: {}", userId);
    	ResponseResult<AppUser> result = userService.queryUserById(userId);
    	log.info("lianpay getPaySign query user info end, result: {}", JSONObject.toJSONString(result));
    	if(result.getData()==null){
    		return result;
    	}
    	AppUser user = result.getData();
    	
    	//检查优惠券是否已使用
    	Long couponId = map.getLong("couponId");
		if (couponId != null) {
			try {
				checkCoupon(couponId, userId);
			} catch (Exception e) {
				ResponseResult<?> errRst = new ResponseResult<>();
				errRst.setErrCode(-1);
				errRst.setErrMsg(e.getMessage());
				return errRst;
			}
		}
		
		PayReq req = buildPayReq(map, userId, list.get(1), user);
		log.info("WX getPaySign start call preparePay");
    	ResponseResult<PreparePayResp> payResult = paymentServiceClient.preparePay(req);
    	log.info("WX getPaySign call preparePay end, result: {}", JSONObject.toJSONString(payResult));
    	if (payResult.getErrCode() != 0) {
    		return payResult;
    	}
    	PreparePayResp preparePayResp = payResult.getData(); //获取支付流水号
    	
    	BigDecimal payMoney = new BigDecimal(map.getString("total_fee")).multiply(new BigDecimal("100")).setScale(0, RoundingMode.UP);
		
		String url = URL_HEAD+"pay/unifiedorder";// 测试URL
		Map<String, String> createMap = new HashMap<String, String>();
		Map<String, String> payMap = new HashMap<String, String>();
		createMap.put("appid", APP_ID);//
		createMap.put("mch_id", MCH_ID);//
		createMap.put("nonce_str", UUIDUtils.genDateUUID("WX"));//
		createMap.put("body", "爱租机-手机租赁");//
		createMap.put("out_trade_no", preparePayResp.getTransactionSN());//
		createMap.put("total_fee", payMoney.toString());//
		createMap.put("sign_type", "MD5");//
		createMap.put("spbill_create_ip", map.getString("ip"));//
		createMap.put("notify_url", payConfigure.getWxPayNotifyUrl());//
		createMap.put("trade_type", "APP");//
		try {
			createMap.put("sign", WXPayUtil.generateSignature(createMap, API_KEY));//
			String resp = HttpClientUtil.postParameters(url, WXPayUtil.mapToXml(createMap));
			Map<String, String> respMap = WXPayUtil.xmlToMap(resp);
			if(WXPayConstants.FAIL.equals(respMap.get("return_code"))){//申请请求失败
				payMap.put("return_code", WXPayConstants.FAIL);
				payMap.put("return_msg", respMap.get("return_msg"));
				return ResponseResult.build(9528, respMap.get("err_code_des"), respMap);
			}
			if(WXPayConstants.FAIL.equals(respMap.get("result_code"))){//业务处理失败
				payMap.put("return_code", WXPayConstants.FAIL);
				payMap.put("return_msg", respMap.get("err_code_des"));
				return ResponseResult.build(9529, respMap.get("err_code_des"), respMap);
			}
			String prepay_id = respMap.get("prepay_id");
			payMap.put("appid", APP_ID);
			payMap.put("partnerid", MCH_ID);
			payMap.put("prepayid", prepay_id);
			payMap.put("package", "Sign=WXPay");
			payMap.put("noncestr", UUIDUtils.genDateUUID("WX"));
			String time = Long.toString(new Date().getTime()/1000);
			payMap.put("timestamp", time);
			payMap.put("sign", WXPayUtil.generateSignature(payMap, API_KEY));
			payMap.put("outTransNo", preparePayResp.getTransactionSN());
			log.info("WX getSign is end.{}",JSON.toJSONString(payMap));
			return ResponseResult.buildSuccessResponse(payMap);
		} catch (Exception e) {
			log.error("WX getSign exception, e: {}", e);
			payMap.put("return_code", WXPayConstants.FAIL);
			payMap.put("return_msg", "业务处理异常");
			return ResponseResult.build(9530, "业务处理异常", null);
		}
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
	 * 获取支付流水号实体
	 * @param body
	 * @param userId
	 * @param phoneNum
	 * @param user 
	 * @return
	 */
	private PayReq buildPayReq(JSONObject body, Long userId, String phoneNum, AppUser user) {
		PayReq payReq = new PayReq();
		payReq.setAmount(new BigDecimal(body.getString("total_fee")));	//交易金额
		payReq.setOrderSN(body.getString("order_no"));		//app订单编号
		payReq.setPhone(phoneNum);	//手机号
		payReq.setRealName(user.getRealName());	//付款人姓名
		payReq.setSourceType(body.getString("sourceType"));	//销售订单
		payReq.setTransactionSource(body.getString("transactionSource"));	//交易入口
		payReq.setTransactionType(body.getString("transactionType"));	//交易类型 首期款交易/租金/回收/买断/未收货违约
		payReq.setTransactionWay(TransactionWayEnum.WECHAT.getCode());		//交易方式  连连
		payReq.setUserId(userId);	//APP用户ID
		payReq.setUsername(phoneNum);//用户名
		
		Long couponId = body.getLong("couponId");
		if (couponId != null) {
			payReq.setCouponId(couponId);	//优惠券ID
			payReq.setCouponFee(new BigDecimal(body.getString("couponFee")).setScale(2, RoundingMode.UP));	//优惠券金额
		}
		return payReq;
	}
	
	
	public ResponseResult<?> queryOrder(String transaction_id,String out_trade_no){
		log.info("WX queryOrder is begin.transaction_id:{},out_trade_no:{}",transaction_id,out_trade_no);
		String url = URL_HEAD+"pay/orderquery";
		Map<String, String> createMap = new HashMap<String, String>();
		Map<String, String> queryMap = new HashMap<String, String>();
		createMap.put("appid", APP_ID);//
		createMap.put("mch_id", MCH_ID);//
		createMap.put("transaction_id", transaction_id);//
		createMap.put("out_trade_no", out_trade_no);//
		createMap.put("nonce_str", UUIDUtils.genDateUUID("WX"));//
		try {
			createMap.put("sign", WXPayUtil.generateSignature(createMap, API_KEY));
			String resp = HttpClientUtil.postParameters(url, WXPayUtil.mapToXml(createMap));
			Map<String, String> respMap = WXPayUtil.xmlToMap(resp);
			if(WXPayConstants.FAIL.equals(respMap.get("return_code"))){//申请请求失败
				queryMap.put("return_code", WXPayConstants.FAIL);
				queryMap.put("return_msg", respMap.get("return_msg"));
				return ResponseResult.build(9528, respMap.get("err_code_des"), respMap);
			}
			if(WXPayConstants.FAIL.equals(respMap.get("result_code"))){//业务处理失败
				queryMap.put("return_code", WXPayConstants.FAIL);
				queryMap.put("return_msg", respMap.get("err_code_des"));
				return ResponseResult.build(9529, respMap.get("err_code_des"), respMap);
			}
			log.info("WX queryOrder is end.{}",JSON.toJSONString(respMap));
			return ResponseResult.buildSuccessResponse(respMap);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}//
		
	}
}
