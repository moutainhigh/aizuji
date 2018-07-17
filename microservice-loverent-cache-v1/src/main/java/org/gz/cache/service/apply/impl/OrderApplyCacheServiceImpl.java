package org.gz.cache.service.apply.impl;

import org.gz.cache.commom.behavior.impl.RedisStringOperateBehavior;
import org.gz.cache.commom.constant.RedisCacheConstant;
import org.gz.cache.commom.constant.RedisCacheDB;
import org.gz.cache.service.apply.OrderApplyCacheService;
import org.springframework.stereotype.Service;

@Service
public class OrderApplyCacheServiceImpl extends RedisStringOperateBehavior 
	implements OrderApplyCacheService {

	private static final long serialVersionUID = 1L;
	
	public OrderApplyCacheServiceImpl() {
		super(RedisCacheDB.USER_CACHE_DB);
	}

	@Override
	public void cacheOrderApplyData(String userId, String data, int ttl) {
		String cacheKey = this.initCacheKey(RedisCacheConstant.FL_APP_AZJ, 
				RedisCacheConstant.ORDER_APPLY, userId.toString());
		this.setEx(cacheKey, ttl, data);
	}

	@Override
	public String getOrderApplyData(String userId) {
		return this.get(this.initCacheKey(RedisCacheConstant.FL_APP_AZJ, 
				RedisCacheConstant.ORDER_APPLY, userId.toString()));
	}

	@Override
	public void removeOrderApplyData(String userId) {
		this.del(this.initCacheKey(RedisCacheConstant.FL_APP_AZJ, 
				RedisCacheConstant.ORDER_APPLY, userId.toString()));
	}

	
}
