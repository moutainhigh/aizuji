package org.gz.cache.service.captche.impl;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.gz.cache.commom.behavior.impl.RedisStringOperateBehavior;
import org.gz.cache.commom.constant.CacheTTLConst;
import org.gz.cache.commom.constant.RedisCacheConstant;
import org.gz.cache.commom.constant.RedisCacheDB;
import org.gz.cache.service.captche.CaptcheCacheService;
import org.gz.common.constants.SmsType;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

@Slf4j
@Service
public class CaptcheCacheServiceImpl extends RedisStringOperateBehavior implements CaptcheCacheService {

	private static final long serialVersionUID = 1L;
	
	public CaptcheCacheServiceImpl() {
		super(RedisCacheDB.CAPTCHA_CACHE_DB);
	}

	@Override
	public void setTelCaptcha(String tel, String captcha, SmsType smsType, Integer ttl) {
		Jedis conn = getResource();
		try {
			if (conn != null) {
				Long lastSendTime = new Date().getTime();
				String stimeKey = this.initCacheKey(RedisCacheConstant.FL_APP_AZJ,
						RedisCacheConstant.SMS_CAPTCHE, RedisCacheConstant.SMS_FREQUENCY, smsType.getCacheKey(), "ti",tel);
				String stotalKey = this.initCacheKey(RedisCacheConstant.FL_APP_AZJ,
						RedisCacheConstant.SMS_CAPTCHE, RedisCacheConstant.SMS_FREQUENCY, smsType.getCacheKey(), "nm", tel);
				
				boolean exist = conn.exists(stotalKey);
				
				Pipeline pipeline = conn.pipelined();
				pipeline.setex(this.initCacheKey(RedisCacheConstant.FL_APP_AZJ,
						RedisCacheConstant.SMS_CAPTCHE, smsType.getCacheKey(), tel), ttl, captcha);
				pipeline.set(stimeKey, lastSendTime.toString());
				if (!exist) {
					pipeline.setex(stotalKey, CacheTTLConst.SMS_CPT_NUMBER_INTERVAL, "1");
				} else {
					pipeline.incr(stotalKey);
				}
				pipeline.sync();
			}
		} catch (Exception e) {
			log.error("redis setTelCaptcha failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
	}

	@Override
	public String getTelCaptcha(String tel, SmsType smsType) {
		return get(this.initCacheKey(RedisCacheConstant.FL_APP_AZJ,
				RedisCacheConstant.SMS_CAPTCHE, smsType.getCacheKey(), tel));
	}

	@Override
	public void removeTelCaptcha(String tel, SmsType smsType) {
		del(this.initCacheKey(RedisCacheConstant.FL_APP_AZJ,
				RedisCacheConstant.SMS_CAPTCHE, smsType.getCacheKey(), tel));
	}

	@Override
	public String getLastSendTime(String tel, SmsType smsType) {
		Jedis conn = getResource();
		try {
			if (conn != null) {
				String stimeKey = this.initCacheKey(RedisCacheConstant.FL_APP_AZJ,
						RedisCacheConstant.SMS_CAPTCHE, RedisCacheConstant.SMS_FREQUENCY, smsType.getCacheKey(), "ti",tel);
				return conn.get(stimeKey);
			}
		} catch (Exception e) {
			log.error("redis getLastSendTime failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}

	@Override
	public String getSmsCaptchaSendNumber(String tel, SmsType smsType) {
		Jedis conn = getResource();
		try {
			if (conn != null) {
				String stotalKey = this.initCacheKey(RedisCacheConstant.FL_APP_AZJ,
						RedisCacheConstant.SMS_CAPTCHE, RedisCacheConstant.SMS_FREQUENCY, smsType.getCacheKey(), "nm", tel);
				return conn.get(stotalKey);
			}
		} catch (Exception e) {
			log.error("redis getSmsCaptchaSendNumber failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}
	

}
