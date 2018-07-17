package org.gz.cache.commom.behavior;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author ydx
 */
public interface RedisHashBehavior extends RedisKeyBehavior, Serializable{

	/**
	 * 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略
	 * @param key
	 * @param fields
	 * @return
	 */
	long hDel(String key, String... fields);
	
	/**
	 * 查看哈希表 key 中，给定域 field 是否存在
	 * @param key
	 * @param field
	 * @return
	 */
	boolean hExists(String key, String field);
	
	/**
	 * 返回哈希表 key 中给定域 field 的值
	 * @param key
	 * @param field
	 * @return
	 */
	String hGet(String key, String field);
	
	/**
	 * 返回哈希表 key 中，所有的域和值
	 * @param key
	 * @return
	 */
	Map<String, String> hGetAll(String key);
	
	/**
	 * 为哈希表 key 中的域 field 的值加上增量 increment,增量也可以为负数，相当于对给定域进行减法操作.
	 * 如果 key 不存在，一个新的哈希表被创建并执行 HINCRBY 命令,如果域 field 不存在，那么在执行命令前，域的值被初始化为 0.
	 * 对一个储存字符串值的域 field 执行 HINCRBY 命令将造成一个错误.
	 * @param key
	 * @param field
	 * @param delta
	 */
	double hIncrByFloat(String key, String field, double delta);
	
	/**
	 * 为哈希表 key 中的域 field 的值加上增量 increment,增量也可以为负数，相当于对给定域进行减法操作.
	 * 如果 key 不存在，一个新的哈希表被创建并执行 HINCRBY 命令,如果域 field 不存在，那么在执行命令前，域的值被初始化为 0.
	 * 对一个储存字符串值的域 field 执行 HINCRBY 命令将造成一个错误.
	 * @param key
	 * @param field
	 * @param delta
	 */
	long hIncrBy(String key, String field, long delta);
	
	
	/**
	 * 返回哈希表 key 中的所有域。
	 * HMSET website google www.google.com yahoo www.yahoo.com
	 * redis> HKEYS website
	 * 1) "google"
	 * 2) "yahoo"
	 * @param key
	 * @return
	 */
	Set<String> hKeys(String key);
	
	/**
	 * 返回哈希表 key中字段的数量
	 * @param key
	 * @return
	 */
	long hLen(String key);
	
	/**
	 * 返回哈希表 key中，一个或多个给定域的值.
	 * @param key
	 * @param fields
	 * @return
	 */
	List<String> hMGet(String key, String... fields);
	
	/**
	 * 同时将多个 field-value (域-值)对设置到哈希表 key 中, 此命令会覆盖哈希表中已存在的域, 如果 key 不存在，一个空哈希表被创建并执行 HMSET 操作
	 * @param key
	 * @param hashes
	 * @return
	 */
	boolean hMSet(String key, Map<String, String> hashes);
	
	/**
	 * 将哈希表 key 中的域 field 的值设为 value, 如果 key 不存在，一个新的哈希表被创建并进行 HSET操作, 如果域 field 已经存在于哈希表中，旧值将被覆盖
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	boolean hSet(String key, String field, String value);
	
	/**
	 * 将哈希表 key 中的域 field 的值设为 value, 如果 key不存在, 一个新的哈希表被创建并进行 HSET操作, 若域 field 已经存在，该操作无效
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	boolean hSetNX(String key, String field, String value);
	
	/**
	 * 返回哈希表 key 中所有域的值.
	 * HMSET website google www.google.com yahoo www.yahoo.com
	 * redis> HVALS website
	 * 1) "www.google.com"
	 * 2) "www.yahoo.com"
	 * @param key
	 * @return
	 */
	List<String> hVals(String key);
}
