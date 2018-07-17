package org.gz.cache.service.sign.impl;

import lombok.extern.slf4j.Slf4j;

import org.gz.cache.commom.behavior.impl.RedisStringOperateBehavior;
import org.gz.cache.commom.constant.RedisCacheConstant;
import org.gz.cache.commom.constant.RedisCacheDB;
import org.gz.cache.service.sign.SignDataCacheService;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

@Service
@Slf4j
public class SignDataCacheServiceImpl extends RedisStringOperateBehavior implements SignDataCacheService {

	private static final long serialVersionUID = 5735565308701304874L;

	public SignDataCacheServiceImpl() {
		super(RedisCacheDB.CA_SIGN_CACHE_DB);
	}

	@Override
	public void cacheSignRecord(Long userId, String signServiceId) {
		Jedis conn = getResource();
		try {
			if (conn != null) {
				conn.sadd(initCacheKey(RedisCacheConstant.CA_SIGN, 
						RedisCacheConstant.CA_RECORD, userId.toString()), signServiceId);
			}
			
		} catch (Exception e) {
			log.error("setSignData failed, userId: {}, signServiceId: {}", userId, signServiceId);
		}
	}

	@Override
	public void removeSignRecord(Long userId, String signServiceId) {
		Jedis conn = getResource();
		try {
			if (conn != null) {
				conn.srem(initCacheKey(RedisCacheConstant.CA_SIGN, 
						RedisCacheConstant.CA_RECORD, userId.toString()), signServiceId);
			}
			
		} catch (Exception e) {
			log.error("removeSignRecord failed, userId: {}, signServiceId: {}", userId, signServiceId);
		}
	}

	@Override
	public String getPersonAccountByUserId(Long userId) {
		Jedis conn = getResource();
		try {
			if (conn != null) {
				return conn.get(initCacheKey(RedisCacheConstant.CA_SIGN, 
						RedisCacheConstant.CA_ACCOUNT, userId.toString()));
			}
		} catch (Exception e) {
			log.error("getPersonAccountByUserId failed, userId: {}", userId);
		}
		return null;
	}

	@Override
	public String setPersonAccountByUserId(Long userId, String accountId) {
		Jedis conn = getResource();
		try {
			if (conn != null) {
				return conn.set(initCacheKey(RedisCacheConstant.CA_SIGN, 
						RedisCacheConstant.CA_ACCOUNT, userId.toString()), accountId);
			}
		} catch (Exception e) {
			log.error("setPersonAccountByUserId failed, userId: {}", userId);
		}
		return null;
	}

	@Override
	public String getOrganizeAccount() {
		Jedis conn = getResource();
		try {
			if (conn != null) {
				return conn.get(initCacheKey(RedisCacheConstant.CA_SIGN, 
						RedisCacheConstant.CA_ACCOUNT, 
						RedisCacheConstant.CA_ACCOUNT_ORGANIZE));
			}
		} catch (Exception e) {
			log.error("getOrganizeAccount failed, e: {}", e);
		}
		return null;
	}

	@Override
	public String setOrganizeAccount(String accountId) {
		Jedis conn = getResource();
		try {
			if (conn != null) {
				return conn.set(initCacheKey(RedisCacheConstant.CA_SIGN, 
						RedisCacheConstant.CA_ACCOUNT, RedisCacheConstant.CA_ACCOUNT_ORGANIZE), accountId);
			}
		} catch (Exception e) {
			log.error("setOrganizeAccount failed, e: {}", e);
		}
		return null;
	}

	@Override
	public String getOrganizeTemplateSeal() {
		Jedis conn = getResource();
		try {
			if (conn != null) {
				return conn.get(initCacheKey(RedisCacheConstant.CA_SIGN, 
						RedisCacheConstant.CA_SEAL_DATA, RedisCacheConstant.CA_ACCOUNT_ORGANIZE));
			}
		} catch (Exception e) {
			log.error("getOrganizeTemplateSeal failed, e: {}", e);
		}
		return null;
	}

	@Override
	public String setOrganizeTemplateSeal(String sealData) {
		Jedis conn = getResource();
		try {
			if (conn != null) {
				return conn.set(initCacheKey(RedisCacheConstant.CA_SIGN, 
						RedisCacheConstant.CA_SEAL_DATA, RedisCacheConstant.CA_ACCOUNT_ORGANIZE), sealData);
			}
		} catch (Exception e) {
			log.error("setOrganizeTemplateSeal failed, e: {}", e);
		}
		return null;
	}

	@Override
	public String getPersonSealDataByUserId(Long userId) {
		Jedis conn = getResource();
		try {
			if (conn != null) {
				return conn.get(initCacheKey(RedisCacheConstant.CA_SIGN, 
						RedisCacheConstant.CA_SEAL_DATA, userId.toString()));
			}
		} catch (Exception e) {
			log.error("getPersonSealDataByUserId failed, userId: {}", userId);
		}
		return null;
	}

	@Override
	public String setPersonSealDataByUserId(Long userId, String sealData) {
		Jedis conn = getResource();
		try {
			if (conn != null) {
				return conn.set(initCacheKey(RedisCacheConstant.CA_SIGN, 
						RedisCacheConstant.CA_SEAL_DATA, userId.toString()), sealData);
			}
		} catch (Exception e) {
			log.error("setPersonSealDataByUserId failed, userId: {}", userId);
		}
		return null;
	}
	
}
