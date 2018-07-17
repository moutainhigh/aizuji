package org.gz.app.supports;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.configure.AlipayConfigure;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.ZhimaCreditScoreBriefGetRequest;
import com.alipay.api.request.ZhimaCreditWatchlistBriefGetRequest;
import com.alipay.api.response.ZhimaCreditScoreBriefGetResponse;
import com.alipay.api.response.ZhimaCreditWatchlistBriefGetResponse;

/**
 * 芝麻分
 * 
 * @author yangdaoxiong
 *
 */
@Slf4j
public class AlipayZhimaScoreUtils {

	/**
	 * 获取芝麻信用分
	 * @param name
	 * @param idcard
	 * @param minScore
	 * @return
	 */
	public static Integer queryZhimaScore(String name, String idcard, Integer minScore) {
		AlipayClient alipayClient = new DefaultAlipayClient(
				AlipayConfigure.GATE_WAY, 
				AlipayConfigure.APPID,
				AlipayConfigure.RSA_PRIVATE_KEY, 
				AlipayConfigure.FORMAT,
				AlipayConfigure.CHARSET, 
				AlipayConfigure.ALIPAY_PUBLIC_KEY,
				AlipayConfigure.SIGNTYPE);
		ZhimaCreditScoreBriefGetRequest request = new ZhimaCreditScoreBriefGetRequest();
		request.setBizContent("{"
				+ "    \"transaction_id\":\""+buildTransactionId()+"\","
				+ "    \"product_code\":\"w1010100000000002733\","
				+ "    \"cert_type\":\"IDENTITY_CARD\","
				+ "    \"cert_no\":\""+idcard+"\","
				+ "    \"name\":\""+name+"\"," + "\"admittance_score\":"+minScore+""
				+ "  }");
		try {
			ZhimaCreditScoreBriefGetResponse response = alipayClient
					.execute(request);
			if (response.isSuccess()) {
				String body = response.getBody();
				log.info("---> queryZhimaScore resp body: {}", body);
				JSONObject jsonBody = JSONObject.parseObject(body);
				JSONObject responseBody = jsonBody.getJSONObject("zhima_credit_score_brief_get_response");
				String isAdmittance = responseBody.getString("is_admittance");
				if ("Y".equals(isAdmittance)) {
					// >=minScore
					return minScore;
				} else {
					return 0;
				}
			} else {
				return 0;
			}
		} catch (Exception e) {
			log.error("---> queryZhimaScore failed, e: {}", e);
			return 0;
		}
	}
	
	/**
	 * 获取行业黑名单逾期情况
	 * @param name
	 * @param idcard
	 * @param minScore
	 * @return
	 */
	public static Integer queryWatchListValue(String name, String idcard) {
		AlipayClient alipayClient = new DefaultAlipayClient(
				AlipayConfigure.GATE_WAY, 
				AlipayConfigure.APPID,
				AlipayConfigure.RSA_PRIVATE_KEY, 
				AlipayConfigure.FORMAT,
				AlipayConfigure.CHARSET, 
				AlipayConfigure.ALIPAY_PUBLIC_KEY,
				AlipayConfigure.SIGNTYPE);
		ZhimaCreditWatchlistBriefGetRequest request = new ZhimaCreditWatchlistBriefGetRequest();
		request.setBizContent("{" +
		"    \"transaction_id\":\""+buildTransactionId()+"\"," +
		"    \"product_code\":\"w1010100000000002977\"," +
		"    \"cert_type\":\"IDENTITY_CARD\"," +
		"    \"cert_no\":\""+idcard+"\"," +
		"    \"name\":\""+name+"\"" +
		"  }");
		try {
			ZhimaCreditWatchlistBriefGetResponse response = alipayClient
					.execute(request);
			if (response.isSuccess()) {
				String body = response.getBody();
				log.info("---> queryWatchListValue resp body: {}", body);
				JSONObject jsonBody = JSONObject.parseObject(body);
				JSONObject responseBody = jsonBody.getJSONObject("zhima_credit_watchlist_brief_get_response");
				String level = responseBody.getString("level");
				return Integer.valueOf(level);
			} else {
				return -1;	//	查询失败
			}
		} catch (Exception e) {
			log.error("---> queryWatchListValue failed, e: {}", e);
			return -1;	//	查询失败
		}
	}
	
	/**
	 * transcation id
	 * @return
	 */
	private static String buildTransactionId() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
