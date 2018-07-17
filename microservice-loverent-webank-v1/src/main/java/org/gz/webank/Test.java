package org.gz.webank;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.commons.codec.Charsets;
import org.gz.common.http.HttpUtils;
import org.gz.webank.common.ConfigureConst;

import com.google.common.hash.Hashing;


public class Test {
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	public static void main(String[] args) {
		
		Test test = new Test();
		
		test.ocrLogin(11L);
		
	}
	
	public JSONObject ocrLogin(Long userId) {
		String orderNo = buildOrderNo();
		
		String nonceTicket = "bPkgalJ4yziZTMa4OFZrANkQolVfIM0luCmtAC33Fmtu8XTGYisaSjisr4RiO35p";
		
		String appId = "TIDADa0S";
		String version = ConfigureConst.VERSION;
		String nonce = buildNonceStr();
		
		List<String> eles = new ArrayList<>();
		eles.add(userId.toString());
		eles.add(nonceTicket);
		eles.add(orderNo);
		eles.add(appId);
		eles.add(version);
		eles.add(nonce);
		
		String sign = sign(eles);
		System.out.println(sign);
		
		System.out.println("--------------login--------");
		
		Map<String, String> params = new HashMap<>();
		params.put("webankAppId", appId);
		params.put("version", version);
		params.put("nonce", nonce);
		params.put("orderNo", orderNo);
		params.put("url", "www.baidu.com");
		params.put("userId", userId.toString());
		params.put("sign", sign);
		
		String aString = HttpUtils.httpGetCall("https://ida.webank.com/api/h5/ocrlogin", params);
		System.out.println(aString);
		return null;
	}
	
	/**
	 * 查询订单标识
	 * @return
	 */
	public String buildOrderNo() {
		return format.format(new Date());
	}
	
	/**
	 * 生成签名
	 * @param userId
	 * 			用户ID
	 * @param nonceTicket
	 * 			ticket
	 * @param orderNo
	 * 			订单标识
	 * @return
	 */
	private String sign(List<String> eles) {
		eles.removeAll(Collections.singleton(null));
		Collections.sort(eles);
		
		StringBuffer sb = new StringBuffer();
		for (String el : eles) {
			sb.append(el);
		}
		
		return Hashing.sha1().hashString(sb, Charsets.UTF_8).toString().toUpperCase();
	}

	private String buildNonceStr() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
