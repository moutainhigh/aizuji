package org.gz.thirdParty.service.weixin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.HttpClientUtil;
import org.gz.common.utils.JsonUtils;
import org.gz.common.utils.WXPayConstants;
import org.gz.common.utils.WXPayUtil;
import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WeiXinService {
	/**
	 * 微信商户号
	 */
	private static final String APP_ID = "wx9bec1c39b13a71e7";
	private static final String MCH_ID = "1495341962";
	private static final String API_KEY = "Gzhlwkj1234567891011121314151617";
	private static final String URL_HEAD = "https://api.mch.weixin.qq.com/";
	// private static final String URL_HEAD =
	// "https://api.mch.weixin.qq.com/sandboxnew/";
	private String charset = "utf-8";
	private HttpClientUtil httpClientUtil = null;

	public WeiXinService() {
		httpClientUtil = new HttpClientUtil();
	}

	public static Map<String, String> applyOrder(Map<String, String> map) throws Exception {
		String url = URL_HEAD + "pay/unifiedorder";// 测试URL
		Map<String, String> createMap = new HashMap<String, String>();
		Map<String, String> payMap = new HashMap<String, String>();
		createMap.put("appid", APP_ID);//
		createMap.put("mch_id", MCH_ID);//
		createMap.put("nonce_str", UUID.randomUUID().toString());//
		createMap.put("body", "爱租机-手机租赁");//
		createMap.put("out_trade_no", map.get("order_id"));//
		createMap.put("total_fee", map.get("total_fee"));//
		createMap.put("sign_type", "MD5");//
		createMap.put("spbill_create_ip", map.get("ip"));//
		createMap.put("notify_url", "http://113.106.166.42:58005/WX/WXCallBack");//
		createMap.put("trade_type", "APP");//
		createMap.put("sign", WXPayUtil.generateSignature(createMap, API_KEY));//
		try {
			String resp = HttpClientUtil.postParameters(url, WXPayUtil.mapToXml(createMap));
			Map<String, String> respMap = WXPayUtil.xmlToMap(resp);
			if (WXPayConstants.FAIL.equals(respMap.get("return_code"))) {// 申请请求失败
				payMap.put("return_code", WXPayConstants.FAIL);
				payMap.put("return_msg", respMap.get("return_msg"));
				return payMap;
			}
			if (WXPayConstants.FAIL.equals(respMap.get("result_code"))) {// 业务处理失败
				payMap.put("return_code", WXPayConstants.FAIL);
				payMap.put("return_msg", respMap.get("err_code_des"));
				return payMap;
			}
			String prepay_id = respMap.get("prepay_id");
			payMap.put("appid", APP_ID);
			payMap.put("partnerid", MCH_ID);
			payMap.put("prepayid", prepay_id);
			payMap.put("package", "Sign=WXPay");
			payMap.put("noncestr", UUID.randomUUID().toString());
			String time = Long.toString(new Date().getTime());
			payMap.put("timestamp", time);
			payMap.put("sign", WXPayUtil.generateSignature(payMap, API_KEY));
			return payMap;
		} catch (Exception e) {
			e.printStackTrace();
			payMap.put("return_code", WXPayConstants.FAIL);
			payMap.put("return_msg", "业务处理异常");
			return payMap;
		}
	}

	public static void getsignkey() {
		String url = URL_HEAD + "pay/getsignkey";// 测试URL
		Map<String, String> createMap = new HashMap<String, String>();
		createMap.put("mch_id", MCH_ID);//
		createMap.put("nonce_str", UUID.randomUUID().toString());//
		try {
			createMap.put("sign", WXPayUtil.generateSignature(createMap, API_KEY));//
			String res = HttpClientUtil.postParameters(url, WXPayUtil.mapToXml(createMap));
			System.out.println(res);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Description: 查询订单
	 * @param transactionSN
	 *            交易流水号
	 * @return
	 * @throws createBy:liaoqingji
	 *             createDate:2018年3月22日
	 */
	public static ResponseResult<?> queryOrder(String transactionSN) {

		String url = URL_HEAD + "pay/orderquery";

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("appid", APP_ID);
		paramMap.put("mch_id", MCH_ID);
		paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
		paramMap.put("out_trade_no", transactionSN);

		try {

			// 参数签名
			String sign = WXPayUtil.generateSignedXml(paramMap, API_KEY);
			String resp = HttpClientUtil.postParameters(url, sign);
			Map<String, String> respMap = WXPayUtil.xmlToMap(resp);
			log.info("queryOrder is end.{}", JSON.toJSONString(respMap));
			
			if (WXPayConstants.SUCCESS.equals(respMap.get("return_code"))) {// 此字段是通信标识，非交易标识，交易是否成功需要查看trade_state来判断
				return ResponseResult.buildSuccessResponse(respMap);
			}else{
				return ResponseResult.build(ResponseStatus.WEI_XIN_ORDER_QUERY_FAILED.getCode(), respMap.get("return_msg"), respMap);
			}
			
		} catch (Exception e) {
			log.error("queryOrder 查询订单异常:{}", e.getMessage());
			return ResponseResult.build(ResponseStatus.WEI_XIN_ORDER_QUERY_ERROR.getCode(),
					ResponseStatus.WEI_XIN_ORDER_QUERY_ERROR.getMessage(), null);
		} 

	}

	public static void main(String[] args) throws Exception {
		// test();
		// unifiedOrder();//85dc059cdb30223bb24d07b2df0bd7c8
		// getsignkey();
		
		/*String transactionSN = "2018021014511698800148";
		ResponseResult<?> responseResult = queryOrder(transactionSN);
		
		log.info(JsonUtils.toJsonString(responseResult.getData()));*/
	}

}
