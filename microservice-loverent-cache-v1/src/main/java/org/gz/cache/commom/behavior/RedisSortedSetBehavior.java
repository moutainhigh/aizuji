package org.gz.cache.commom.behavior;

import java.io.Serializable;
import java.util.List;
import java.util.Set;


/**
 * @author ydx
 */
public interface RedisSortedSetBehavior extends RedisKeyBehavior, Serializable{
	
	/**
	 * 将一个member元素及其 score值加入到有序集 key当中
	 * 如果某个 member 已经是有序集的成员，那么更新这个 member 的 score 值，并通过重新插入这个 member 元素，来保证该 member 在正确的位置上
	 * score 值可以是整数值或双精度浮点数
	 * 如果 key 不存在，则创建一个空的有序集并执行 ZADD 操作
	 * 当 key 存在但不是有序集类型时，返回一个错误
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 */
	boolean zadd(String key, double score, String member);
	
	/**
	 * 返回有序集 key的长度
	 * @param key
	 * @return
	 */
	Long zcard(String key);
	
	/**
	 * 返回有序集 key中， score值在 min和 max之间(默认包括 score值等于 min或 max)的成员的数量
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	Long zcount(String key, double min, double max);
	
	/**
	 * 为有序集 key 的成员 member 的 score 值加上增量 increment。
	 * 可以通过传递一个负数值 increment ，让 score 减去相应的值，比如 ZINCRBY key -5 member ，就是让 member 的 score 值减去 5。
	 * 当 key 不存在，或 member 不是 key 的成员时， ZINCRBY key increment member 等同于 ZADD key increment member。
	 * 当 key 不是有序集类型时，返回一个错误。
	 * @param key
	 * @param increment
	 * @param value
	 * @return
	 */
	boolean zincrBy(String key, double increment, String member);
	
	/**
	 * 返回有序集 key 中，指定区间内的成员。其中成员的位置按 score值递增(从小到大)来排序。
	 * 如果你需要成员按 score 值递减(从大到小)来排列，请使用 ZREVRANGE 命令。
	 * 下标参数 start 和 stop 都以 0 为底，也就是说，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，以此类推。
	 * 也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，以此类推。
	 * 超出范围的下标并不会引起错误。比如说，当 start 的值比有序集的最大下标还要大，或是 start > stop 时， ZRANGE 命令只是简单地返回一个空列表。
	 * 另一方面，假如 stop 参数的值比有序集的最大下标还要大，那么 Redis 将 stop 当作最大下标来处理。
	 * @param key
	 * @param begin
	 * @param end
	 * @param calzz
	 * @return
	 */
	Set<String> zrange(String key, long begin, long end);
	
	/**
	 * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列。
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	Set<String> zrangeByScore(String key, double min, double max);
	
	/**
	 * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列。
	 * offset表示从符合条件的第offset个成员开始返回，同时返回count个成员。
	 * @param key
	 * @param min
	 * 			score开始
	 * @param max
	 * 			score结束
	 * @param offset
	 * 			从符合条件集合中第offset个开始获取count个元素
	 * @param count
	 * @return
	 */
	Set<String> zrangeByScore(String key, double min, double max, int offset, int count);
	
	
	/**
	 * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递增(从小到大)顺序排列。
	 * 排名以 0 为底，也就是说， score 值最小的成员排名为 0 。
	 * @param key
	 * @param member
	 * @return
	 */
	Long zrank(String key, String member);
	
	/**
	 * 移除有序集 key 中的一个成员，不存在的成员将被忽略。
	 * @param key
	 * @param members
	 * @return
	 */
	long zrem(String key, String... members);
	
	
	/**
	 * 移除有序集 key 中，所有 score值介于 min和 max之间(包括等于 min 或 max )的成员。
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	long zRemRangeByScore(String key, double min, double max);
	
	/**
	 * 返回有序集 key 中，指定区间内的成员。
	 * 其中成员的位置按 score 值递减(从大到小)来排列。
	 * @param key
	 * @param begin
	 * 			元素开始下标
	 * @param end
	 * 			元素结束下标
	 * @return
	 */
	Set<String> zRevRange(String key, long begin, long end);
	
	/**
	 * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从大到小)次序排列。
	 * @param key
	 * @param max
	 * @param min
	 * @return
	 */
	Set<String> zRevRangeByScore(String key, double max, double min);
	
	/**
	 * 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从大到小)次序排列。
	 * offset表示从符合条件的第offset个成员开始返回，同时返回count个成员。
	 * @param key
	 * @param min
	 * @param max
	 * @param offset
	 * 			从符合条件集合中第offset个开始获取count个元素
	 * @param count
	 * @return
	 */
	Set<String> zRevRangeByScore(String key, double min, double max, int offset, int count);
	
	
	/**
	 * 返回有序集 key 中成员 member 的排名。其中有序集成员按 score 值递增(从大到小)顺序排列。
	 * 排名以 0 为底，也就是说， score 值最大的成员排名为 0 。
	 * @param key
	 * @param member
	 * @return
	 */
	Long zRevRank(String key, String member);
	
	/**
	 * 返回有序集 key中，成员 member的 score值。
	 * @param key
	 * @param member
	 * @return
	 */
	Double zscore(String key, String member);
}
