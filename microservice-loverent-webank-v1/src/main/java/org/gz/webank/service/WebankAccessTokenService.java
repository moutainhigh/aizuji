package org.gz.webank.service;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.gz.cache.service.webank.WebankDataCacheService;
import org.gz.common.http.HttpUtils;
import org.gz.common.utils.StringUtils;
import org.gz.webank.common.ConfigureConst;
import org.gz.webank.configure.WebankConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

/**
 * access token
 * 
 * @author yangdx
 */
@Service
@Slf4j
public class WebankAccessTokenService {
	
	@Autowired
	private WebankConfigure webankConfigure;
	
	@Autowired
	private WebankDataCacheService webankDataCacheService;

	/**
	 * 获取APP token 并 缓存
	 */
	public void requestTokenAndCache() {
	
		String identifier = webankDataCacheService.acquireRequestLock();
		
		if (!StringUtils.isEmpty(identifier)) {
			String resp = HttpUtils.httpGetCall(ConfigureConst.ACCESS_TOKEN_URL, buildAccessTokenRequestParams());
			log.info("WebankAccessTokenService.requestTokenAndCache() --> get access token, resp: {}", resp);
			
			if (!StringUtils.isEmpty(resp)) {
				JSONObject data = JSONObject.parseObject(resp);
				String code = data.getString("code");
				if (ConfigureConst.SUCCESS_CODE.equals(code)) {
					String accessToken = data.getString("access_token");
					webankDataCacheService.setAccessToken(accessToken, 7200);
				} else {
					log.error("WebankAccessTokenService.requestTokenAndCache() --> get access token failed, resp: {}", resp);
				}
			}
		} else {
			log.info("WebankAccessTokenService.requestTokenAndCache() --> token exist or get identifier failed!");
		}
		
		webankDataCacheService.releaseRequestLock(identifier);
	}
	
	/**
	 * 检查token存活时间
	 */
	public void checkTokenTTL() {
		
		 Long ttl = webankDataCacheService.getAccessTokenTTL();
		 
		 log.info("WebankAccessTokenService.checkTokenTTL() --> ttl:{}", ttl);
		 
		 if (ttl <=  900 || ttl == -2) {
			 this.requestTokenAndCache();
		 }
	}
	
	private Map<String, String> buildAccessTokenRequestParams() {
		Map<String, String> pm = new HashMap<String, String>();
		pm.put("app_id", webankConfigure.getAppId());
		pm.put("secret", webankConfigure.getSecret());
		pm.put("grant_type", ConfigureConst.GRANT_TYPE);
		pm.put("version", ConfigureConst.VERSION);
		return pm;
	}
}
