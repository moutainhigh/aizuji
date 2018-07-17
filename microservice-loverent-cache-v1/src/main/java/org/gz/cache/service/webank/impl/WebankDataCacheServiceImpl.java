package org.gz.cache.service.webank.impl;

import lombok.extern.slf4j.Slf4j;

import org.gz.cache.commom.behavior.impl.RedisStringOperateBehavior;
import org.gz.cache.commom.constant.RedisCacheConstant;
import org.gz.cache.commom.constant.RedisCacheDB;
import org.gz.cache.service.webank.WebankDataCacheService;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

@Service
@Slf4j
public class WebankDataCacheServiceImpl extends RedisStringOperateBehavior
		implements WebankDataCacheService {

	private static final long serialVersionUID = 1L;
	
	public WebankDataCacheServiceImpl() {
		super(RedisCacheDB.USER_CACHE_DB);
	}

	@Override
	public String getAccessToken() {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				String key = initCacheKey(RedisCacheConstant.FL_APP_AZJ,
						RedisCacheConstant.WEBANK_CACHE,
						RedisCacheConstant.WEBANK_ACCESS_TOKEN);
				return conn.get(key);
			}
		} catch (Exception e) {
			log.error("execute getAccessToken failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}
	
	@Override
	public void setAccessToken(String token, int ttl) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				String key = initCacheKey(RedisCacheConstant.FL_APP_AZJ,
						RedisCacheConstant.WEBANK_CACHE,
						RedisCacheConstant.WEBANK_ACCESS_TOKEN);
				conn.setex(key, ttl, token);
			}
		} catch (Exception e) {
			log.error("execute setAccessToken failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
	}

	@Override
	public Long getAccessTokenTTL() {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				String key = initCacheKey(RedisCacheConstant.FL_APP_AZJ,
						RedisCacheConstant.WEBANK_CACHE,
						RedisCacheConstant.WEBANK_ACCESS_TOKEN);
				return conn.ttl(key);
			}
		} catch (Exception e) {
			log.error("execute setAccessToken failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return -1L;
	}
	
	@Override
	public String acquireRequestLock() {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return acquireLockWithTimeout(conn, 
						initCacheKey(RedisCacheConstant.FL_APP_AZJ, RedisCacheConstant.WEBANK_CACHE, 
								RedisCacheConstant.WEBANK_TOKEN_REQ_LOCK), 100, 30000);
			}
		} catch (Exception e) {
			log.error("execute acquireRefreshLock failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}

	@Override
	public void releaseRequestLock(String identifier) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				releaseLock(conn, initCacheKey(RedisCacheConstant.FL_APP_AZJ, RedisCacheConstant.WEBANK_CACHE, 
						RedisCacheConstant.WEBANK_TOKEN_REQ_LOCK), identifier);
			}
		} catch (Exception e) {
			log.error("execute acquireRefreshLock failed, e: {}", e);
		} finally {
			closeResource(conn);
		}		
	}


}
