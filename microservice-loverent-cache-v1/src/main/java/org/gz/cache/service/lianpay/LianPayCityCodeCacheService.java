package org.gz.cache.service.lianpay;

import java.util.Map;

import org.gz.cache.commom.behavior.RedisStringBehavior;

public interface LianPayCityCodeCacheService extends RedisStringBehavior {

	/**
	 * 缓存连连支付城市编码
	 */
	public void cacheLianPayCityCode(Map<String, String> data);
	
	/**
	 * 根据key获取对应code
	 * @param key
	 * @return
	 */
	public String getCodeByKey(String key);
	
	/**
	 * 检查是否已加载
	 * @return
	 */
	public boolean checkLianPayCityCodeIsInit();
}
