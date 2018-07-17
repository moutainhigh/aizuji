package org.gz.cache.commom.behavior;

import java.io.Serializable;
import java.util.List;

/**
 * @author ydx
 */
public interface RedisListBehavior extends RedisKeyBehavior, Serializable{
	
	/**
	 * 返回列表 key 的长度，如果 key 不存在，则 key 被解释为一个空列表，返回 0，如果 key 不是列表类型，返回一个错误
	 * @param key
	 * 			key
	 * @return
	 */
	Long llen(String key);
	
	/**
	 * 返回列表 key 中，下标为 index 的元素，0 表示列表的第一个元素，以 1 表示列表的第二个元素，-1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素
	 * @param key
	 * 			key
	 * @param index
	 * 			下标值
	 * @return
	 */
	String lindex(String key, final long index);
	
	/**
	 * 移除并返回列表 key 的头元素。
	 * @param key
	 * @param clazz
	 * @return
	 */
	String lpop(String key);
	
	/**
	 * 将一个值 value 插入到列表 key 的表头
	 * @param key
	 * @param value
	 * @return
	 */
	Long lpush(String key, String value);
	
	/**
	 * 将一个或多个值 value 插入到列表 key的表头
	 * @param key
	 * @param values
	 * @return
	 */
	Long lpush(String key, String[] values);
	
	/**
	 * 将一个或多个值 value 插入到列表 key的表头，并且最后对列表进行修建
	 * @param key
	 * 			key
	 * @param values
	 * 			values
	 * @param trimLen
	 * 			修建保留长度，从左至右修剪（0, trimLen），下表从0开始，trimLen必须大于0
	 */
	boolean lpush(String key, String[] values, int trimLen);
	
	/**
	 * 返回列表 key 中指定区间内的元素，区间以偏移量 begin 和 end 指定，
	 * 下标(index)参数 start 和 stop 都以 0 为底，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推，
	 * 也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推
	 * @param key
	 * @param begin
	 * @param end
	 * @param clazz
	 * @return
	 */
	List<String> lrange(String key, long begin, long end);
	
	/**
	 * 据参数 count 的值，移除列表中与参数 value 相等的元素。
	 * 1、count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count。
	 * 2、count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
	 * 3、count = 0 : 移除表中所有与 value 相等的值
	 * @param key
	 * @param count
	 * @param value
	 * @return
	 */
	Long lRem(String key, long count, String value);
	
	/**
	 * 将列表 key 下标为 index 的元素的值设置为 value，当 index 参数超出范围，或对一个空列表( key 不存在)进行 LSET 时，返回一个错误
	 * @param key
	 * @param index
	 * @param value
	 * @return
	 */
	boolean lSet(String key, long index, String value);
	
	/**
	 * 对一个列表进行修剪(trim)，只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
	 * 例如，执行命令 LTRIM list 0 2 ，表示只保留列表 list 的前三个元素，其余元素全部删除
	 * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
	 * 也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
	 * 
	 * @param key
	 * @param begin
	 * @param end
	 * @return
	 */
	boolean lTrim(String key, long begin, long end);
	
	/**
	 * 移除并返回列表 key 的尾元素
	 * @param key
	 * @param clazz
	 * @return
	 */
	String rpop(String key);
	
	/**
	 * 将一个值 value 插入到列表 key 的表尾(最右边)
	 * @param key
	 * @param value
	 * @return
	 */
	Long rpush(String key, String value);
	
	/**
	 * 将一个或多个值 value 插入到列表 key的表尾
	 * @param key
	 * @param values
	 * @return
	 */
	Long rpush(String key, String[] values);
	
	/**
	 * 将一个或多个值 value 插入到列表 key的表头，并且最后对列表进行修建
	 * @param key
	 * 			key
	 * @param values
	 * 			values
	 * @param trimLen
	 * 			修建保留长度，从右至左修建(-trimLen, -1)，trimLen必须大于0
	 */
	boolean rpush(String key, final String[] values, int trimLen);
	
}
