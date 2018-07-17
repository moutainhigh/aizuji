package org.gz.cache.commom.behavior;

import java.io.Serializable;
import java.util.List;

/**
 * @author ydx
 */
public interface RedisStringBehavior extends RedisKeyBehavior, Serializable{
	
	/**
	 * 将 key中储存的数字值减一，如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 DECR 操作，如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错
	 * @param key
	 * 			key
	 * @return
	 */
	Long decr(String key);
	
	/**
	 * 将 key 所储存的值减去减量 decrement
	 * @param key
	 * 			key
	 * @param decrement
	 * 			decrement
	 * @return
	 */
	Long decrBy(String key, long decrement);
	
	/**
	 * 获取key 所关联的字符串值
	 * @param key
	 * 			key
	 * @return
	 */
	String get(String key);
	
	/**
	 * 返回所有(一个或多个)给定 key 的值
	 * @param keys
	 * @param clazz
	 * @return
	 */
	List<String> mget(String... keys);
	
	/**
	 * 将 key 中储存的数字值增一，如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作，如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
	 * @param key
	 * 			key
	 * @return
	 */
	Long incr(String key);
	
	/**
	 * 将 key中储存的数字值增increment ，如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作，如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
	 * @param key
	 * 			key
	 * @param increment
	 * 			increment
	 * @return
	 */
	Long incrBy(String key, long increment);
	
	/**
	 * 为 key 中所储存的值加上浮点数增量 increment，如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作，如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
	 * @param key
	 * 			key
	 * @param increment
	 * 			increment
	 * @return
	 */
	Double incrByFloat(String key, Double increment);
	
	/**
	 * 将value 关联到 key，如果 key 已经持有其他值， SET 就覆写旧值，无视类型，对于某个原本带有生存时间（TTL）的键来说， 当 SET 命令成功在这个键上执行时， 这个键原有的 TTL 将被清除
	 * @param key
	 */
	boolean set(String key, String value);
	
	/**
	 * 将值 value 关联到 key ，并将 key的生存时间设为 seconds(以秒为单位), 如果 key已经存在， SETEX命令将覆写旧值
	 * @param key
	 * 			key
	 * @param seconds
	 * 			生命时长-秒
	 * @param value
	 * 			换成值
	 * @return
	 */
	boolean setEx(String key, int seconds, String value);
	
	/**
	 * 将 key 的值设为 value ，当且仅当 key不存在, 若给定的 key已经存在，则 SETNX 不做任何动作
	 * @param key
	 * @param value
	 * @return
	 */
	boolean setNX(String key, String value);
	
	/**
	 * 返回 key 所储存的字符串值的长度。当 key储存的不是字符串值时，返回一个错误。
	 * @param key
	 * @return
	 */
	Long strLen(String key);
	
}
