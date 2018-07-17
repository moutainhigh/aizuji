package org.gz.cache.commom.behavior;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ydx
 */
public interface RedisKeyBehavior extends Serializable{
	/**
	 * 删除key
	 * @param keys
	 * 			keys
	 */
	boolean del(String... keys);
	
	/**
	 * 检查key是否存在
	 * @param key
	 * 			key
	 * @return
	 */
	boolean exists(String key);
	
	/**
	 * 设置key生命时长
	 * @param key
	 * 			key
	 * @param seconds
	 * 			生命时长
	 * @return
	 */
	boolean expire(String key, final int seconds);
	
	/**
	 * 设置key在unixTime时失效
	 * @param key
	 * 			key
	 * @param unixTime
	 * 			失效时间点
	 * @return
	 */
	boolean expireAt(String key, int unixTime);
	
	/**
	 * 以毫秒单位返回key剩余生命时长
	 * @param key
	 * 		key
	 * @return
	 */
	Long pttl(String key);
	
	/**
	 * 以秒单位返回key剩余生命时长
	 * @param key
	 * 		key
	 * @return
	 */
	Long ttl(String key);
	
	/**
	 * 随机获取一个key
	 * @return
	 */
	String randomKey();
	
	/**
	 * 将 key 改名为 newkey。
	 * 当 key 和 newkey 相同，或者 key 不存在时。
	 * 返回一个错误,当 newkey 已经存在时， RENAME 命令将覆盖旧值。
	 * @param oldName
	 * 			旧key名称
	 * @param newName
	 * 			新key名称
	 * @return
	 */
	boolean rename(String oldKey, String newKey);
	
	/**
	 * 当且仅当 newkey 不存在时，将 key 改名为 newkey。
	 * 当 key 不存在时，返回一个错误
	 * @param oldName
	 * 			旧key名称
	 * @param newName
	 * 			新key名称
	 * @return
	 */
	boolean renameNX(String oldKey, String newKey);
	
	/**
	 * 设置缓存数据的key
	 * @param keyParts
	 * 			缓存键(a,b,c) return a:b:c
	 * @return
	 */
	String initCacheKey(String... keyParts);
	
	/**
	 * 记录最后一次缓存数据
	 */
	void setLastCacheTime(String fullKey);
	
	/**
	 * 获取最后一次缓存数据
	 */
	Date getLastCacheTime(String fullKey);
	
}
