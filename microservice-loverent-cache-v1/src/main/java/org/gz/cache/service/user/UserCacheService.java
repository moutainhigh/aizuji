package org.gz.cache.service.user;

import java.util.List;
import java.util.Map;

import org.gz.cache.commom.behavior.RedisStringBehavior;

public interface UserCacheService extends RedisStringBehavior {
	
	/**
	 * 登录缓存
	 * @param token
	 * @param fieldMap
	 * @param loginCacheType 
	 * @param ttl
	 * 			缓存时间-秒 
	 * @param loginType
	 * 			登录方式1-密码 2-第三方
	 * 			
	 */
	void setLoginUserCache(String token, Map<String, String> fieldMap, Integer loginCacheType, int ttl, String loginType);
	
	/**
	 * 获取用户信息
	 * @param token
	 * 			登录token
	 * @param fields
	 * 			用户属性字段
	 * @return
	 */
	List<String> getLoginUserCacheByToken(String token, String... fields);
	
	/**
	 * 获取用户信息
	 * @param token
	 * 			登录token
	 * @param fields
	 * 			用户属性字段
	 * @return
	 */
	Map<String, String> getLoginUserCacheByToken(String token);
	
	/**
	 * 获取用户信息
	 * @param tel
	 * 			用户手机号
	 * @param fields
	 * 			用户属性字段
	 * @return
	 */
	List<String> getLoginUserCacheByUserId(Long userId, String... fields);

	/**
	 * 移除用户登录缓存
	 * @param token
	 */
	void removeUserLoginCache(String token);
	
	/**
	 * 更新用户缓存信息
	 * @param userId
	 * @param fieldMap
	 */
	void updateUserInfoCache(String userId, Map<String, String> fieldMap);

	/**
	 * 检查token
	 * @param token
	 * @return
	 */
	boolean tokenExist(String token);

	/**
	 * 更新token缓存时间
	 * @param token
	 */
	void updateUserCacheExpireTime(String token);

	/**
	 * 检查手机号是否已注册
	 * @param phoneNum
	 * @return
	 */
	boolean checkPhoneIsRegisted(String phoneNum);
}
