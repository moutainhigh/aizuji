package org.gz.cache.service.lianpay.impl;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.gz.cache.commom.behavior.impl.RedisStringOperateBehavior;
import org.gz.cache.commom.constant.RedisCacheConstant;
import org.gz.cache.commom.constant.RedisCacheDB;
import org.gz.cache.service.lianpay.LianPayCityCodeCacheService;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

@Service
@Slf4j
public class LianPayCityCodeCacheServiceImpl extends RedisStringOperateBehavior implements
		LianPayCityCodeCacheService {

	private static final long serialVersionUID = 1L;
	
	public LianPayCityCodeCacheServiceImpl() {
		super(RedisCacheDB.PAY);
	}

	@Override
	public void cacheLianPayCityCode(Map<String, String> data) {
		Jedis conn = getResource();
		try {
			if (conn != null) {
				if (data != null && !data.isEmpty()) {
					Pipeline pipeline = conn.pipelined();
					for (Entry<String, String> entry : data.entrySet()) {
						String key = this.initCacheKey(RedisCacheConstant.FL_APP_AZJ,
								RedisCacheConstant.PAY, RedisCacheConstant.PAY_LIANLIAN, RedisCacheConstant.CITY_CODE, entry.getKey());
						pipeline.set(key, entry.getValue());	
					}
					pipeline.sync();
				}
			}
		} catch (Exception e) {
			log.error("redis cacheLianPayCityCode failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
	}

	@Override
	public String getCodeByKey(String key) {
		Jedis conn = getResource();
		try {
			if (conn != null) {
				return conn.get(this.initCacheKey(RedisCacheConstant.FL_APP_AZJ,
						RedisCacheConstant.PAY, RedisCacheConstant.PAY_LIANLIAN, RedisCacheConstant.CITY_CODE, key));
			}
		} catch (Exception e) {
			log.error("redis getCodeByKey failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}

	@Override
	public boolean checkLianPayCityCodeIsInit() {
		Jedis conn = getResource();
		try {
			if (conn != null) {
				Set<String> keys = conn.keys(this.initCacheKey(RedisCacheConstant.FL_APP_AZJ,
						RedisCacheConstant.PAY, RedisCacheConstant.PAY_LIANLIAN, RedisCacheConstant.CITY_CODE, "*"));
				return keys != null && !keys.isEmpty();
			}
		} catch (Exception e) {
			log.error("redis checkLianPayCityCodeExist failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return false;
	}


}
