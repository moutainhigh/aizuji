package org.gz.cache.service.user.impl;

import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.gz.cache.commom.behavior.impl.RedisStringOperateBehavior;
import org.gz.cache.commom.constant.CacheTTLConst;
import org.gz.cache.commom.constant.RedisCacheConstant;
import org.gz.cache.commom.constant.RedisCacheDB;
import org.gz.cache.commom.constant.UserLoginCacheType;
import org.gz.cache.service.user.UserCacheService;
import org.gz.common.constants.CommonConstant;
import org.gz.common.utils.StringUtils;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

@Slf4j
@Service
public class UserCacheServiceImpl extends RedisStringOperateBehavior implements UserCacheService {

	private static final long serialVersionUID = 1L;
	
	public UserCacheServiceImpl() {
		super(RedisCacheDB.USER_CACHE_DB);
	}

	
	@Override
	public void setLoginUserCache(String token, Map<String, String> fieldMap, Integer loginCacheType, int ttl, String loginType) {
		Jedis conn = getResource();
		log.info("setLoginUserCache, token: {}, fieldMap: {}", token, fieldMap);
		try {
			if (conn != null) {
				String userId = fieldMap.get("userId");
				String phoneNum = fieldMap.get("phoneNum");
				
				//remove tel history token
				//登录手机号对应的token
				String telLoginKey = initCacheKey(RedisCacheConstant.FL_APP_AZJ, RedisCacheConstant.PHONE_LOGIN, phoneNum);
				//获取历史token
				String htoken = conn.get(telLoginKey);
				String htokenKey = null;
				if (!StringUtils.isEmpty(htoken)) {
					//kickit out
					htokenKey = initCacheKey(RedisCacheConstant.FL_APP_AZJ, RedisCacheConstant.LOGIN_TOKEN, htoken);
				}
				
				//用户信息
				String userInfoKey = initCacheKey(RedisCacheConstant.FL_APP_AZJ, RedisCacheConstant.USER_INFO, userId);
				//当前token对应的用户ID
				String tokenKey = initCacheKey(RedisCacheConstant.FL_APP_AZJ, RedisCacheConstant.LOGIN_TOKEN, token);
				
				Pipeline pipeline = conn.pipelined();
				pipeline.hmset(userInfoKey, fieldMap);
				pipeline.setex(telLoginKey, ttl, token);
				pipeline.setex(tokenKey, ttl, userId);
				if (htokenKey != null) {
					pipeline.del(htokenKey);
				}
				if (loginCacheType.equals(UserLoginCacheType.REGISTER)) {
					//已注册手机号
					String registedUserCacheKey = initCacheKey(RedisCacheConstant.FL_APP_AZJ, RedisCacheConstant.REGISTED_PHONE);
					pipeline.sadd(registedUserCacheKey, phoneNum);
				}
				if (CommonConstant.LOGIN_TYPE_THIRD_PARTY.equals(loginType)) {
					//登录方式1-密码 2-第三方
					String loginTypeKey = initCacheKey(RedisCacheConstant.FL_APP_AZJ, RedisCacheConstant.LOGIN_TYPE, token);	
					pipeline.setex(loginTypeKey, 
							CacheTTLConst.LOGIN_THIRDPARTY_CACHE_TTL, 
							loginType);
				}
				pipeline.sync();
			}
		} catch (Exception e) {
			log.error("execute setLoginUserCache failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
	}

	@Override
	public List<String> getLoginUserCacheByToken(String token, String... fields) {
		Jedis conn = getResource();
		try {
			if (conn != null) {
				String tokenKey = initCacheKey(RedisCacheConstant.FL_APP_AZJ, RedisCacheConstant.LOGIN_TOKEN, token);
				if (conn.exists(tokenKey)) {
					String userId = conn.get(tokenKey);
					String userInfoKey = initCacheKey(RedisCacheConstant.FL_APP_AZJ, RedisCacheConstant.USER_INFO, userId);
					return conn.hmget(userInfoKey, fields);
				}
			}
		} catch (Exception e) {
			log.error("execute getLoginUserCacheByToken failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}
	
	@Override
	public Map<String, String> getLoginUserCacheByToken(String token) {
		Jedis conn = getResource();
		try {
			if (conn != null) {
				String tokenKey = initCacheKey(RedisCacheConstant.FL_APP_AZJ, RedisCacheConstant.LOGIN_TOKEN, token);
				if (conn.exists(tokenKey)) {
					String userId = conn.get(tokenKey);
					String userInfoKey = initCacheKey(RedisCacheConstant.FL_APP_AZJ, RedisCacheConstant.USER_INFO, userId);
					return conn.hgetAll(userInfoKey);
				}
			}
		} catch (Exception e) {
			log.error("execute getLoginUserCacheByToken failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}
	
	@Override
	public List<String> getLoginUserCacheByUserId(Long userId, String... fields) {
		Jedis conn = getResource();
		try {
			if (conn != null) {
				String userInfoKey = initCacheKey(RedisCacheConstant.FL_APP_AZJ, RedisCacheConstant.USER_INFO, userId.toString());
				return conn.hmget(userInfoKey, fields);
			}
		} catch (Exception e) {
			log.error("execute getLoginUserCacheByToken failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}


	@Override
	public void removeUserLoginCache(String token) {
		Jedis conn = getResource();
		try {
			if (conn != null) {
				//当前token对应的用户ID
				String tokenKey = initCacheKey(RedisCacheConstant.FL_APP_AZJ, RedisCacheConstant.LOGIN_TOKEN, token);
				if (conn.exists(tokenKey)) {
					//获取对应的用户ID
					String userId = conn.get(tokenKey);
					if (!StringUtils.isEmpty(userId)) {
						//用户信息
						String userInfoKey = initCacheKey(RedisCacheConstant.FL_APP_AZJ, RedisCacheConstant.USER_INFO, userId);
						String phoneNum = conn.hget(userInfoKey, "phoneNum");
						//登录手机号对应的token
						String telLoginKey = initCacheKey(RedisCacheConstant.FL_APP_AZJ, RedisCacheConstant.PHONE_LOGIN, phoneNum);
						Pipeline pipeline = conn.pipelined();
						pipeline.del(tokenKey);
						pipeline.del(telLoginKey);
						pipeline.sync();
					}
				}
			}
		} catch (Exception e) {
			log.error("execute removeUserLoginCache failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
	}


	@Override
	public void updateUserInfoCache(String userId, Map<String, String> fieldMap) {
		Jedis conn = getResource();
		log.info("updateUserInfoCache, userId: {}, fieldMap: {}", userId, fieldMap);
		try {
			if (conn != null) {
				String userInfoKey = initCacheKey(RedisCacheConstant.FL_APP_AZJ, RedisCacheConstant.USER_INFO, userId);
				conn.hmset(userInfoKey, fieldMap);
			}
		} catch (Exception e) {
			log.error("execute updateUserInfoCache failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
	}


	@Override
	public boolean tokenExist(String token) {
		Jedis conn = getResource();
		try {
			if (conn != null) {
				String tokenKey = initCacheKey(RedisCacheConstant.FL_APP_AZJ, RedisCacheConstant.LOGIN_TOKEN, token);
				log.info("===>tokenExist, dbindex: {},  tokenKey: {}", this.dbIndex, tokenKey);
				return conn.exists(tokenKey);
			}
		} catch (Exception e) {
			log.error("execute tokenExist failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return false;
	}


	@Override
	public void updateUserCacheExpireTime(String token) {
		Jedis conn = getResource();
		try {
			if (conn != null) {
				String loginTypeKey = initCacheKey(RedisCacheConstant.FL_APP_AZJ, RedisCacheConstant.LOGIN_TYPE, token);
				String loginType = conn.get(loginTypeKey);
				//如果是密码登录则更新登录失效时间, 第三方登录15天后强制失效
				if (CommonConstant.LOGIN_TYPE_PWD.equals(loginType)) {
					String tokenKey = initCacheKey(RedisCacheConstant.FL_APP_AZJ, RedisCacheConstant.LOGIN_TOKEN, token);
					String userId = conn.get(tokenKey);
					if (!StringUtils.isEmpty(userId)) {
						//用户信息
						String userInfoKey = initCacheKey(RedisCacheConstant.FL_APP_AZJ, RedisCacheConstant.USER_INFO, userId);
						String phoneNum = conn.hget(userInfoKey, "phoneNum");
						//登录手机号对应的token
						String telLoginKey = initCacheKey(RedisCacheConstant.FL_APP_AZJ, RedisCacheConstant.PHONE_LOGIN, phoneNum);
						
						Pipeline pipeline = conn.pipelined();
						pipeline.expire(tokenKey, CacheTTLConst.LOGIN_PWD_CACHE_TTL);
						pipeline.expire(telLoginKey, CacheTTLConst.LOGIN_PWD_CACHE_TTL);
						pipeline.sync();
					}
				}
			}
		} catch (Exception e) {
			log.error("execute updateUserInfoCache failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
	}


	@Override
	public boolean checkPhoneIsRegisted(String phoneNum) {
		Jedis conn = getResource();
		try {
			if (conn != null) {
				String registedUserCacheKey = initCacheKey(RedisCacheConstant.FL_APP_AZJ, RedisCacheConstant.REGISTED_PHONE);
				return conn.sismember(registedUserCacheKey, phoneNum); 
			}
		} catch (Exception e) {
			log.error("execute updateUserInfoCache failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return false;
	}

}
