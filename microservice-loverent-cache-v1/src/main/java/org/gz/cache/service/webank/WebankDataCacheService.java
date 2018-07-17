package org.gz.cache.service.webank;

public interface WebankDataCacheService {

	/**
	 * 获取 access token
	 * @return
	 */
	String getAccessToken();

	/**
	 * 设置 token
	 * @param token
	 * @param ttl
	 * 			second
	 */
	void setAccessToken(String token, int ttl);
	
	/**
	 * 获取token ttl
	 * @return
	 */
	Long getAccessTokenTTL();
	
	
	String acquireRequestLock();

	void releaseRequestLock(String identifier);

	
}
