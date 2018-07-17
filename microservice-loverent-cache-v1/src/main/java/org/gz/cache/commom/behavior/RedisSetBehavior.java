package org.gz.cache.commom.behavior;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author ydx
 */
public interface RedisSetBehavior extends RedisKeyBehavior, Serializable{
	
	/**
	 * 将多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略
	 * @param key
	 * @param value
	 * @return
	 */
	Long sadd(String key, String... values);
	
	/**
	 * 将一个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略
	 * @param key
	 * @param value
	 * @return
	 */
	Long sadd(String key, String value);
	
	/**
	 * 返回集合 key 的基数(集合中元素的数量)
	 * @param key
	 * @return
	 */
	Long scard(String key);
	
	/**
	 * 返回所有给定集合之间的差集
	 * @param keys
	 * @param clazz
	 * @return
	 */
	Set<String> sdiff(String... keys);
	
	/**
	 * 返回所有给定集合的交集
	 * @param keys
	 * @param clazz
	 * @return
	 */
	Set<String> sinter(String... keys);
	
	/**
	 * 判断 member 元素是否集合 key 的成员
	 * @param key
	 * @param value
	 * @return
	 */
	boolean sIsmember(String key, String member);
	
	/**
	 * 返回集合 key 中的所有成员，不存在的 key 被视为空集合
	 * @param key
	 * @return
	 */
	Set<String> smembers(String key);
	
	/**
	 * 移除并返回集合中的一个随机元素
	 * @param key
	 * @return
	 */
	String spop(String key);
	
	/**
	 * 如果命令执行时，只提供了 key 参数，那么返回集合中的一个随机元素。
	 * Redis 2.6 版本开始， SRANDMEMBER 命令接受可选的 count 参数.
	 * 如果 count 为正数，且小于集合基数，那么命令返回一个包含 count 个元素的数组，数组中的元素各不相同。如果 count 大于等于集合基数，那么返回整个集合。
	 * 如果 count 为负数，那么命令返回一个数组，数组中的元素可能会重复出现多次，而数组的长度为 count 的绝对值。
	 * 该操作和 SPOP 相似，但 SPOP 将随机元素从集合中移除并返回，而 SRANDMEMBER 则仅仅返回随机元素，而不对集合进行任何改动。
	 * @param key
	 * @return
	 */
	String srandmember(String key);
	
	/**
	 * 如果命令执行时，只提供了 key 参数，那么返回集合中的一个随机元素。
	 * Redis 2.6 版本开始， SRANDMEMBER 命令接受可选的 count 参数.
	 * 如果 count 为正数，且小于集合基数，那么命令返回一个包含 count 个元素的数组，数组中的元素各不相同。如果 count 大于等于集合基数，那么返回整个集合。
	 * 如果 count 为负数，那么命令返回一个数组，数组中的元素可能会重复出现多次，而数组的长度为 count 的绝对值。
	 * 该操作和 SPOP 相似，但 SPOP 将随机元素从集合中移除并返回，而 SRANDMEMBER 则仅仅返回随机元素，而不对集合进行任何改动。
	 * @param key
	 * @param count
	 * @return
	 */
	List<String> srandmember(String key, int count);
	
	/**
	 * 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。当 key 不是集合类型，返回一个错误。
	 * @param key
	 * @param values
	 * @return
	 */
	boolean srem(String key, String... members);
	
	/**
	 * 返回所有给定集合的并集
	 * @param clazz
	 * @param keys
	 * @return
	 */
	Set<String> sunion(String... keys);
}
