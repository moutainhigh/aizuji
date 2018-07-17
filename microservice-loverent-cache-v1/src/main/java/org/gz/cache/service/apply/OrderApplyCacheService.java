package org.gz.cache.service.apply;

public interface OrderApplyCacheService {
	/**
	 * 缓存用户订单申请数据
	 * @param userId
	 * @param data
	 * 			申请数据
	 * @param ttl
	 * 			秒
	 */
	public void cacheOrderApplyData(String userId, String data, int ttl);
	
	/**
	 * 获取用户订单申请数据
	 * @param userId
	 * @return
	 */
	public String getOrderApplyData(String userId);
	
	/**
	 * 移除用户订单申请数据
	 * @param userId
	 */
	public void removeOrderApplyData(String userId);
}
