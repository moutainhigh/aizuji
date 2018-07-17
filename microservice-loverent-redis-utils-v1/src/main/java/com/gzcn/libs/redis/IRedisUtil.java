package com.gzcn.libs.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IRedisUtil {
	public Set<String> zrangeByScore(String key, long start, long end);
	public Long zadd(String key, long score, String value);
	public Long zadd(String key, Map<String,Double> scoreMembers);
	public Set<String> zrange(String key, long start, long end);
	public Long zcard(String key);
	/**
	 * 查询队列长度
	 * @param key
	 * @return 队列长度
	 */
	public Long llen(String key);
	/**
	 * 插入队列的最左边
	 * @param key
	 * @param value
	 * @return
	 */
	public Long lpush(String key, String value) ;
	/**
	 * 插入队列的最右边
	 * @param key
	 * @param value
	 * @return
	 */
	public Long rpush(String key, String value);
	/**
	 * 将队列的值缓存到另外一个队列中，返回移动的值
	 * @param key
	 * @param value
	 * @return
	 */
	public String rpoplpush(String oldKey, String newKey) ;
	/**
	 * 移除一个队列
	 * @param key
	 * @return
	 */
	public String rpop(String key) ;
	/**
	 * 删除缓存
	 * @param key 键
	 * @param field 
	 * @return 值
	 */
	
	public  Long hdel(String key,String field) ;
	/**
	 * 设置缓存
	 * @param key 键
	 * @param map 值
	 * @return
	 */
	public  String hset(String key, Map<String,String> map) ;
	/**
	 * 获取缓存
	 * @param key 键
	 * @param field 
	 * @return 值
	 */
	
	public  String hget(String key,String field) ;
	/**
	 * 获取缓存
	 * @param key 键
	 * @return Map
	 */
	public  Map<String,String>  hgetAll(String key) ;
	/**
	 * 获取缓存
	 * @param key 键
	 * @return Map
	 */
	public  Map<byte[],byte[]>  hgetAll(byte[] key) ;
	/**
	 * 获取缓存
	 * @param key 键
	 * @param field 
	 * @return 值
	 */
	
	public  Object hgetObject(String key,String field) ;
	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public  Long hset(String key, String field, String value);
	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public  Long hsetObject(String key, String field, Object value);
	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public  Long hsetObject(String key, String field, Object value, int cacheSeconds);
	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public  Long hset(String key, String field, String value, int cacheSeconds) ;
	/**
	 * 获取缓存
	 * @param key 键
	 * @return 值
	 */

	public String get(String key);
	/**
	 * 获取缓存
	 * @param key 键
	 * @return 值
	 */

	public byte[] get(byte[] key);


	/**
	 * 获取缓存
	 * @param key 键
	 * @return 值
	 */
	public Object getObject(String key);

	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public String set(String key, String value, int cacheSeconds);
	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public String set(byte[] key, byte[] value, int cacheSeconds);
	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public String set(byte[] key, byte[] value);
	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public String set(String key, String value);

	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public String setObject(String key, Object value, int cacheSeconds);
	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public String setObject(String key, Object value);

	/**
	 * 获取List缓存
	 * @param key 键
	 * @return 值
	 */
	public List<String> getList(String key);

	/**
	 * 获取List缓存
	 * @param key 键
	 * @return 值
	 */
	public List<Object> getObjectList(String key);

	/**
	 * 设置List缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public long setList(String key, List<String> value, int cacheSeconds);
	/**
	 * 设置List缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public long setList(String key, List<String> value);
	/**
	 * 设置List缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public long setObjectList(String key, List<Object> value, int cacheSeconds);
	/**
	 * 设置List缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public long setObjectList(String key, List<Object> value);
	/**
	 * 设置Set缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public long setSet(String key, Set<String> value);

	/**
	 * 设置Set缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public long setSet(String key, Set<String> value, int cacheSeconds);
	/**
	 * 向List缓存中添加值
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public long listAdd(String key, String... value);

	/**
	 * 向List缓存中添加值
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public long listObjectAdd(String key, Object... value);

	/**
	 * 获取缓存
	 * @param key 键
	 * @return 值
	 */
	public Set<String> getSet(String key);

	/**
	 * 获取缓存
	 * @param key 键
	 * @return 值
	 */
	public Set<Object> getObjectSet(String key);


	/**
	 * 设置Set缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public long setObjectSet(String key, Set<Object> value, int cacheSeconds);

	/**
	 * 向Set缓存中添加值
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public long setSetAdd(String key, String... value);


	/**
	 * 向Set缓存中添加值
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public long setSetObjectAdd(String key, Object... value);

	/**
	 * 获取Map缓存
	 * @param key 键
	 * @return 值
	 */
	public Map<String, String> getMap(String key);

	/**
	 * 获取Map缓存
	 * @param key 键
	 * @return 值
	 */
	public Map<String, Object> getObjectMap(String key);

	/**
	 * 设置Map缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public String setMap(String key, Map<String, String> value, int cacheSeconds);

	/**
	 * 设置Map缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public String setObjectMap(String key, Map<String, Object> value,
			int cacheSeconds);

	/**
	 * 向Map缓存中添加值
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public String mapPut(String key, Map<String, String> value);

	/**
	 * 向Map缓存中添加值
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public String mapObjectPut(String key, Map<String, Object> value);

	/**
	 * 移除Map缓存中的值
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public long mapRemove(String key, String mapKey);

	/**
	 * 移除Map缓存中的值
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public long mapObjectRemove(String key, String mapKey);

	/**
	 * 判断Map缓存中的Key是否存在
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public boolean mapExists(String key, String mapKey);

	/**
	 * 判断Map缓存中的Key是否存在
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public boolean mapObjectExists(String key, String mapKey);

	/**
	 * 删除缓存
	 * @param key 键
	 * @return
	 */
	public long del(String key);

	/**
	 * 删除缓存
	 * @param key 键
	 * @return
	 */
	public long delObject(String key);

	/**
	 * 缓存是否存在
	 * @param key 键
	 * @return
	 */
	public boolean exists(String key);
	/**
	 * 缓存是否存在
	 * @param key 键
	 * @param field 字段
	 * @return
	 */
	public  boolean hexists(String key,String field) ;

	/**
	 * 缓存是否存在
	 * @param key 键
	 * @return
	 */
	public boolean existsObject(String key);
	public  String createKey(String... key);
	/**
	 * 缓存递增
	 * @param key
	 * @return
	 */
	public long incr(String key);
	/**
	 * 缓存递减
	 * @param key
	 * @return
	 */
	public long decr(String key);
}
