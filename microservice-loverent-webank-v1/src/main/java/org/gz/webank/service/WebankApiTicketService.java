package org.gz.webank.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.Charsets;
import org.gz.cache.service.webank.WebankDataCacheService;
import org.gz.common.http.HttpUtils;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.HttpClientUtil;
import org.gz.common.utils.ResultUtil;
import org.gz.common.utils.StringUtils;
import org.gz.webank.common.ConfigureConst;
import org.gz.webank.configure.WebankConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.hash.Hashing;

/**
 * api ticket
 * 
 * @author yangdx
 */
@Service
@Slf4j
public class WebankApiTicketService {
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	
	@Autowired
	private WebankConfigure webankConfigure;
	
	@Autowired
	private WebankAccessTokenService accessTokenService;
	
	@Autowired
	private WebankDataCacheService webankDataCacheService;
	
	private static final String GET_H5_FACEID_URL = "https://idasc.webank.com/api/server/h5/geth5faceid";

	public ResponseResult<JSONObject> signData(Long userId) {
		ResponseResult<JSONObject> result = new ResponseResult<>();
		try {
			String orderNo = buildOrderNo();
			String nonceTicket = requestNonceTicket(userId);
			String appId = webankConfigure.getAppId();
			String version = ConfigureConst.VERSION;
			String nonce = buildNonceStr();
			
			List<String> eles = new ArrayList<>();
			eles.add(userId.toString());
			eles.add(nonceTicket);
//			eles.add(orderNo);
			eles.add(appId);
			eles.add(version);
			eles.add(nonce);
			
			String sign = sign(eles);
			
			JSONObject data = new JSONObject();
			data.put("orderNo", orderNo);
			data.put("openApiUserId", userId.toString());
			data.put("openApiSign", sign);
			data.put("openApiNonce", nonce);
			data.put("openApiAppVersion", version);
			data.put("openApiAppId", appId);
			data.put("tiket", nonceTicket);
			
			log.info("get signData success, data: {}", JSONObject.toJSONString(data));
			
			result.setData(data);
		} catch (Exception e) {
			ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}
	
	/**
	 * 获取nonce ticket
	 */
	public String requestNonceTicket(Long userId) {
		String accessToken = webankDataCacheService.getAccessToken();
		
		if (StringUtils.isEmpty(accessToken)) {
			accessTokenService.requestTokenAndCache();
			accessToken = webankDataCacheService.getAccessToken();
		}
		
		String resp = HttpUtils.httpGetCall(ConfigureConst.TICKET_URL, buildNonceTicketRequestParams(accessToken, userId));
		
		log.info("WebankApiTicketService.requestNonceTicket() --> nonce ticket resp: {}", resp);
		if (!StringUtils.isEmpty(resp)) {
			JSONObject data = JSONObject.parseObject(resp);
			
			String code = data.getString("code");
			if (ConfigureConst.SUCCESS_CODE.equals(code)) {
				JSONArray tickets = data.getJSONArray("tickets");
				if (tickets != null && tickets.size() > 0) {
					return tickets.getJSONObject(0).getString("value");
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取sign ticket
	 */
	public String requestSignTicket() {
		String accessToken = webankDataCacheService.getAccessToken();
		
		if (StringUtils.isEmpty(accessToken)) {
			accessTokenService.requestTokenAndCache();
			accessToken = webankDataCacheService.getAccessToken();
		}
		
		String resp = HttpUtils.httpGetCall(ConfigureConst.TICKET_URL, buildSignTicketRequestParams(accessToken));
		
		log.info("WebankApiTicketService.requestSignTicket() --> sign ticket resp: {}", resp);
		if (!StringUtils.isEmpty(resp)) {
			JSONObject data = JSONObject.parseObject(resp);
			
			String code = data.getString("code");
			if (ConfigureConst.SUCCESS_CODE.equals(code)) {
				JSONArray tickets = data.getJSONArray("tickets");
				if (tickets != null && tickets.size() > 0) {
					return tickets.getJSONObject(0).getString("value");
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取nonce tiket
	 * @param accessToken
	 * @param userId
	 * @return
	 */
	private Map<String, String> buildNonceTicketRequestParams(String accessToken, Long userId) {
		Map<String, String> pm = new HashMap<String, String>();
		pm.put("app_id", webankConfigure.getAppId());
		pm.put("access_token", accessToken);
		pm.put("type", ConfigureConst.NONCE_TYPE);
		pm.put("version", ConfigureConst.VERSION);
		pm.put("user_id", ""+userId);
		return pm;
	}
	
	/**
	 * 获取sign tiket
	 * @param accessToken
	 * @return
	 */
	private Map<String, String> buildSignTicketRequestParams(String accessToken) {
		Map<String, String> pm = new HashMap<String, String>();
		pm.put("app_id", webankConfigure.getAppId());
		pm.put("access_token", accessToken);
		pm.put("type", ConfigureConst.SIGN_TYPE);
		pm.put("version", ConfigureConst.VERSION);
		return pm;
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
	 * @param eles
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

	/**
	 * OCR api 签名
	 * @return
	 */
	public ResponseResult<JSONObject> getOCRApiSign() {
		ResponseResult<JSONObject> result = new ResponseResult<>();
		try {
			String orderNo = buildOrderNo();
			String signTicket = requestSignTicket();
			String appId = webankConfigure.getAppId();
			String version = ConfigureConst.VERSION;
			String nonce = buildNonceStr();
			
			List<String> eles = new ArrayList<>();
			eles.add(signTicket);
			eles.add(orderNo);
			eles.add(appId);
			eles.add(version);
			eles.add(nonce);
			
			String sign = sign(eles);
			
			JSONObject data = new JSONObject();
			data.put("orderNo", orderNo);
			data.put("sign", sign);
			data.put("nonce", nonce);
			data.put("version", version);
			data.put("webankAppId", appId);
			data.put("tiket", signTicket);
			
			log.info("get getOCRApiSign success, data: {}", JSONObject.toJSONString(data));
			
			result.setData(data);
		} catch (Exception e) {
			ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

	/**
	 * H5上送身份信息签名
	 * @param userId
	 * @return
	 */
	public ResponseResult<JSONObject> getH5Faceid(JSONObject body) {
		ResponseResult<JSONObject> result = new ResponseResult<>();
		try {
			String orderNo = buildOrderNo();
			String signTicket = requestSignTicket();
			String appId = webankConfigure.getAppId();
			String version = ConfigureConst.VERSION;
			
			List<String> eles = new ArrayList<>();
			eles.add(appId);
			eles.add(orderNo);
			eles.add(body.getString("name"));
			eles.add(body.getString("idNo"));
			eles.add(body.getString("userId"));
			eles.add(version);
			eles.add(signTicket);
			
			String sign = sign(eles);
			
			JSONObject data = new JSONObject();
			data.put("webankAppId", appId);
			data.put("orderNo", orderNo);
			data.put("name", body.getString("name"));
			data.put("idNo", body.getString("idNo"));
			data.put("userId", body.getString("userId"));
			data.put("sourcePhotoType", "1");
			data.put("version", version);
			data.put("sign", sign);
			log.info("---> getH5FaceidSignData success, data: {}", JSONObject.toJSONString(data));

			//请求获取faceid
			String resp = HttpClientUtil.postParametersJson(GET_H5_FACEID_URL, data.toJSONString());
			/*{
				"code": "0",
				"msg": "请求成功",
				"bizSeqNo": "18022720001015100000000001144364",
				"result": {
					"bizSeqNo": "18022720001015100000000001144364",
					"transactionTime": "20180227160355",
					"orderNo": "20180227160355543",
					"h5faceId": "d449bba50f3d7db9f14cac79abe6b4c6",
					"success": false
				},
				"transactionTime": "20180227160355"
			}*/
			log.info("---> getH5Faceid request result: {}", resp);
			
			String h5Faceid = null;
			if (!StringUtils.isEmpty(resp)) {
				JSONObject respData = JSONObject.parseObject(resp);
				if ("0".equals(respData.getString("code"))) {
					JSONObject childEle = respData.getJSONObject("result");
					if (childEle != null) {
						h5Faceid = childEle.getString("h5faceId");
					}
				}
			}
			
			if (!StringUtils.isEmpty(h5Faceid)) {
				return getH5FaceLoginSign(h5Faceid, body.getLong("userId"), orderNo);
			}
			return ResponseResult.build(-1, "获取faceid失败", null);
		} catch (Exception e) {
			log.error("---> getH5Faceid failed, e:{}", e);
			ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}

	/**
	 * 获取启动H5人脸识别sign
	 * @param faceid
	 * @param userId 
	 * @return
	 */
	public ResponseResult<JSONObject> getH5FaceLoginSign(String h5faceId, Long userId, String orderNo) {
		ResponseResult<JSONObject> result = new ResponseResult<>();
		try {
			String nonceTicket = requestNonceTicket(userId);
			String appId = webankConfigure.getAppId();
			String version = ConfigureConst.VERSION;
			String nonce = buildNonceStr();
			
			List<String> eles = new ArrayList<>();
			eles.add(nonceTicket);
			eles.add(orderNo);
			eles.add(appId);
			eles.add(version);
			eles.add(nonce);
			eles.add(h5faceId);
			eles.add(userId.toString());
			
			String sign = sign(eles);
			
			JSONObject data = new JSONObject();
			data.put("webankAppId", appId);
			data.put("version", version);
			data.put("nonce", nonce);
			data.put("orderNo", orderNo);
			data.put("h5faceId", h5faceId);
			data.put("url", "");
			data.put("userId", userId.toString());
			data.put("sign", sign);
			log.info("get getH5FaceLoginSign success, data: {}", JSONObject.toJSONString(data));
			
			result.setData(data);
		} catch (Exception e) {
			ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
		}
		return result;
	}
}
