package com.gzcn.libs.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

/**
 * redis3.0以下主从版实现类
 *
 */
public class JedisClient  implements IRedisUtil {

	private static Logger logger = LoggerFactory.getLogger(JedisClient.class);
	private  JedisPool jedisPool;
	private static final String SIGN = ":";


	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}



	@Override
	public String set(String key, String value) {
		return set(key, value, 0);
	}
	public Long lpush(String key, String value) {
		Long result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result =jedis.lpush(key, value); 
			
		} catch (Exception e) {
			logger.error("lpush {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;

	}
	public Long rpush(String key, String value) {
		Long result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result =jedis.rpush(key, value); 
		} catch (Exception e) {
			logger.error("rpush {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;

	}
	public String rpoplpush(String key, String value) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.rpoplpush(key, value);  
		} catch (Exception e) {
			logger.error("rpoplpush {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;

	}
	public String rpop(String key) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result =jedis.rpop(key);  
		} catch (Exception e) {
			logger.error("rpop {} ", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;

	}
	  
	@Override
	public String setObject(String key, Object value) {
		return setObject(key, value,0);
	}
	
	@Override
	public Object hgetObject(String key, String field) {
		Object value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))) {
				value = toObject(jedis.hget(getBytesKey(key),getBytesKey(field)));
			}
			
		} catch (Exception e) {
			logger.error("getObject {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}

	@Override
	public Long hsetObject(String key, String field, Object value) {
		return hsetObject( key,  field,  value,0);
	}
	
	@Override
	public Long hsetObject(String key, String field, Object value, int cacheSeconds) {
		Long result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hset(getBytesKey(key),getBytesKey(field), toBytes(value));
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
		} catch (Exception e) {
			logger.error("hsetObject {} = {}= {}= {}", key, field,value,cacheSeconds, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	@Override
	public long setList(String key, List<String> value) {
		return setList(key, value,0);
	}

	@Override
	public long setObjectList(String key, List<Object> value) {
		return setObjectList(key, value,0);
	}

	@Override
	public long setSet(String key, Set<String> value) {
		return setSet(key, value,0);
	}
	public Long zcard(String key){
		Long value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			value = jedis.zcard(key);
		} catch (Exception e) {
			logger.error("zcard {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	@Override
	public Long llen(String key) {
		Long value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				value = jedis.llen(key);
			}
		} catch (Exception e) {
			logger.error("llen {} ", key, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	public Set<String> zrangeByScore(String key, long start, long end){
		Set<String> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				value = jedis.zrangeByScore(key, start, end);
			}
		} catch (Exception e) {
			logger.error("zrangeByScore {} = {}= {}", key, start, end, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	public Long zadd(String key, long score, String value){
		Long ret = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			ret = jedis.zadd(key,new Long(score).doubleValue(), value);
		} catch (Exception e) {
			logger.error("zadd {} = {}= {}", key, score, value, e);
		} finally {
			returnResource(jedis);
		}
		return ret;
	}
	
	




	public Long zadd(String key, Map<String,Double> scoreMembers){
		Long ret = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			ret = jedis.zadd(key,scoreMembers);
		} catch (Exception e) {
			logger.error("zadd {} = {}", key, scoreMembers, e);
		} finally {
			returnResource(jedis);
		}
		return ret;
	}
	public Set<String> zrange(String key, long start, long end){
		Set<String> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				value = jedis.zrange(key, start, end);
			}
		} catch (Exception e) {
			logger.error("zrange {} = {}= {}", key, start, end, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	/**
	 * 获取缓存
	 * @param key 键
	 * @return 值
	 */
	
	public  String get(String key) {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				value = jedis.get(key);
				
				value = value != null && value != "" && !"nil".equalsIgnoreCase(value) ? value : null;
			}
		} catch (Exception e) {
			logger.error("get {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	@Override
	public byte[] get(byte[] key) {
		byte[] value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				value = jedis.get(key);
			}
		} catch (Exception e) {
			logger.error("get {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	
	@Override
	public Map<String, String> hgetAll(String key) {
		Map<String, String> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				value = jedis.hgetAll(key);
			}
		} catch (Exception e) {
			logger.error("hget {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}

	@Override
	public Map<byte[], byte[]> hgetAll(byte[] key) {
		Map<byte[], byte[]> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				value = jedis.hgetAll(key);
			}
		} catch (Exception e) {
			logger.error("hget {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}

	/**
	 * 获取缓存
	 * @param key 键
	 * @param field 
	 * @return 值
	 */
	public  String hget(String key,String field) {
		String value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				value = jedis.hget(key, field);
				
				value = value != null && value != "" && !"nil".equalsIgnoreCase(value) ? value : null;
			}
		} catch (Exception e) {
			logger.error("hget {} = {}", key, field, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	/**
	 * 删除缓存
	 * @param key 键
	 * @param field 
	 * @return 值
	 */
	
	public  Long hdel(String key,String field) {
		Long value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				value = jedis.hdel(key, field);
			}
		} catch (Exception e) {
			logger.error("hdel {} = {}", key, field, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public  Long hset(String key, String field, String value) {
		return hset( key,  field,  value,0) ;
	}
	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public  Long hset(String key, String field, String value, int cacheSeconds) {
		Long result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hset(key, field, value);
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
		} catch (Exception e) {
			logger.error("set {} = {} = {} = {}", key,field, value,cacheSeconds, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	/**
	 * 设置缓存
	 * @param key 键
	 * @param map 值
	 * @return
	 */
	public  String hset(String key, Map<String,String> map) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hmset(key, map);
		} catch (Exception e) {
			logger.error("set {} = {}", key,map, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	/**
	 * 获取缓存
	 * @param key 键
	 * @return 值
	 */
	public  Object getObject(String key) {
		Object value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))) {
				value = toObject(jedis.get(getBytesKey(key)));
			}
		} catch (Exception e) {
			logger.error("getObject {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public  String set(String key, String value, int cacheSeconds) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.set(key, value);
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
		} catch (Exception e) {
			logger.error("set {} = {} = {}", key, value,cacheSeconds, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	@Override
	public String set(byte[] key, byte[] value, int cacheSeconds) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.set(key, value);
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
		} catch (Exception e) {
			logger.error("set {} = {} = {}", key, value,cacheSeconds, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	
	@Override
	public String set(byte[] key, byte[] value) {
		return set( key, value, 0);
	}

	/**
	 * 设置缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public  String setObject(String key, Object value, int cacheSeconds) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.set(getBytesKey(key), toBytes(value));
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
		} catch (Exception e) {
			logger.error("setObject {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 获取List缓存
	 * @param key 键
	 * @return 值
	 */
	public  List<String> getList(String key) {
		List<String> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				value = jedis.lrange(key, 0, -1);
			}
		} catch (Exception e) {
			logger.error("getList {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 获取List缓存
	 * @param key 键
	 * @return 值
	 */
	public  List<Object> getObjectList(String key) {
		List<Object> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))) {
				List<byte[]> list = jedis.lrange(getBytesKey(key), 0, -1);
				value = new ArrayList<Object>();
				for (byte[] bs : list){
					value.add(toObject(bs));
				}
			}
		} catch (Exception e) {
			logger.error("getObjectList {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 设置List缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public  long setList(String key, List<String> value, int cacheSeconds) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				jedis.del(key);
			}
			result = jedis.rpush(key, (String[])value.toArray());
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
		} catch (Exception e) {
			logger.error("setList {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 设置List缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public  long setObjectList(String key, List<Object> value, int cacheSeconds) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))) {
				jedis.del(key);
			}
			List<byte[]> list = new ArrayList<byte[]>();
			for (Object o : value){
				list.add(toBytes(o));
			}
			result = jedis.rpush(getBytesKey(key), (byte[][])list.toArray());
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
		} catch (Exception e) {
			logger.error("setObjectList {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 向List缓存中添加值
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public  long listAdd(String key, String... value) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.rpush(key, value);
			logger.debug("listAdd {} = {}", key, value);
		} catch (Exception e) {
			logger.error("listAdd {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 向List缓存中添加值
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public  long listObjectAdd(String key, Object... value) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			List<byte[]> list = new ArrayList<byte[]>();
			for (Object o : value){
				list.add(toBytes(o));
			}
			result = jedis.rpush(getBytesKey(key), (byte[][])list.toArray());
		} catch (Exception e) {
			logger.error("listObjectAdd {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 获取缓存
	 * @param key 键
	 * @return 值
	 */
	public  Set<String> getSet(String key) {
		Set<String> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				value = jedis.smembers(key);
			}
		} catch (Exception e) {
			logger.error("getSet {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 获取缓存
	 * @param key 键
	 * @return 值
	 */
	public  Set<Object> getObjectSet(String key) {
		Set<Object> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))) {
				value = new HashSet<Object>();new HashSet<Object>();
				Set<byte[]> set = jedis.smembers(getBytesKey(key));
				for (byte[] bs : set){
					value.add(toObject(bs));
				}
				logger.debug("getObjectSet {} = {}", key, value);
			}
		} catch (Exception e) {
			logger.error("getObjectSet {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 设置Set缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public  long setSet(String key, Set<String> value, int cacheSeconds) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				jedis.del(key);
			}
			result = jedis.sadd(key, (String[])value.toArray());
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
			logger.debug("setSet {} = {}", key, value);
		} catch (Exception e) {
			logger.error("setSet {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 设置Set缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public  long setObjectSet(String key, Set<Object> value, int cacheSeconds) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))) {
				jedis.del(key);
			}
			Set<byte[]> set = new HashSet<byte[]>();
			for (Object o : value){
				set.add(toBytes(o));
			}
			result = jedis.sadd(getBytesKey(key), (byte[][])set.toArray());
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
			logger.debug("setObjectSet {} = {}", key, value);
		} catch (Exception e) {
			logger.error("setObjectSet {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 向Set缓存中添加值
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public  long setSetAdd(String key, String... value) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.sadd(key, value);
			logger.debug("setSetAdd {} = {}", key, value);
		} catch (Exception e) {
			logger.error("setSetAdd {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 向Set缓存中添加值
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public  long setSetObjectAdd(String key, Object... value) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			Set<byte[]> set = new HashSet<byte[]>();
			for (Object o : value){
				set.add(toBytes(o));
			}
			result = jedis.rpush(getBytesKey(key), (byte[][])set.toArray());
			logger.debug("setSetObjectAdd {} = {}", key, value);
		} catch (Exception e) {
			logger.error("setSetObjectAdd {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 获取Map缓存
	 * @param key 键
	 * @return 值
	 */
	public  Map<String, String> getMap(String key) {
		Map<String, String> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				value = jedis.hgetAll(key);
				logger.debug("getMap {} = {}", key, value);
			}
		} catch (Exception e) {
			logger.error("getMap {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 获取Map缓存
	 * @param key 键
	 * @return 值
	 */
	public  Map<String, Object> getObjectMap(String key) {
		Map<String, Object> value = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))) {
				value = new HashMap<String, Object>();
				Map<byte[], byte[]> map = jedis.hgetAll(getBytesKey(key));
				for (Map.Entry<byte[], byte[]> e : map.entrySet()){
					value.put(e.getKey().toString(), toObject(e.getValue()));
				}
				logger.debug("getObjectMap {} = {}", key, value);
			}
		} catch (Exception e) {
			logger.error("getObjectMap {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return value;
	}
	
	/**
	 * 设置Map缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public  String setMap(String key, Map<String, String> value, int cacheSeconds) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)) {
				jedis.del(key);
			}
			result = jedis.hmset(key, value);
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
			logger.debug("setMap {} = {}", key, value);
		} catch (Exception e) {
			logger.error("setMap {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 设置Map缓存
	 * @param key 键
	 * @param value 值
	 * @param cacheSeconds 超时时间，0为不超时
	 * @return
	 */
	public  String setObjectMap(String key, Map<String, Object> value, int cacheSeconds) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))) {
				jedis.del(key);
			}
			Map<byte[], byte[]> map = new HashMap<byte[], byte[]>();
			for (Map.Entry<String, Object> e : value.entrySet()){
				map.put(getBytesKey(e.getKey()), toBytes(e.getValue()));
			}
			result = jedis.hmset(getBytesKey(key), (Map<byte[], byte[]>)map);
			if (cacheSeconds != 0) {
				jedis.expire(key, cacheSeconds);
			}
			logger.debug("setObjectMap {} = {}", key, value);
		} catch (Exception e) {
			logger.error("setObjectMap {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 向Map缓存中添加值
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public  String mapPut(String key, Map<String, String> value) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hmset(key, value);
			logger.debug("mapPut {} = {}", key, value);
		} catch (Exception e) {
			logger.error("mapPut {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 向Map缓存中添加值
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public  String mapObjectPut(String key, Map<String, Object> value) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			Map<byte[], byte[]> map = new HashMap<byte[], byte[]>();
			for (Map.Entry<String, Object> e : value.entrySet()){
				map.put(getBytesKey(e.getKey()), toBytes(e.getValue()));
			}
			result = jedis.hmset(getBytesKey(key), (Map<byte[], byte[]>)map);
			logger.debug("mapObjectPut {} = {}", key, value);
		} catch (Exception e) {
			logger.error("mapObjectPut {} = {}", key, value, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 移除Map缓存中的值
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public  long mapRemove(String key, String mapKey) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hdel(key, mapKey);
			logger.debug("mapRemove {}  {}", key, mapKey);
		} catch (Exception e) {
			logger.error("mapRemove {}  {}", key, mapKey, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 移除Map缓存中的值
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public  long mapObjectRemove(String key, String mapKey) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hdel(getBytesKey(key), getBytesKey(mapKey));
			logger.debug("mapObjectRemove {}  {}", key, mapKey);
		} catch (Exception e) {
			logger.error("mapObjectRemove {}  {}", key, mapKey, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 判断Map缓存中的Key是否存在
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public  boolean mapExists(String key, String mapKey) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hexists(key, mapKey);
			logger.debug("mapExists {}  {}", key, mapKey);
		} catch (Exception e) {
			logger.error("mapExists {}  {}", key, mapKey, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 判断Map缓存中的Key是否存在
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public  boolean mapObjectExists(String key, String mapKey) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hexists(getBytesKey(key), getBytesKey(mapKey));
			logger.debug("mapObjectExists {}  {}", key, mapKey);
		} catch (Exception e) {
			logger.error("mapObjectExists {}  {}", key, mapKey, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 删除缓存
	 * @param key 键
	 * @return
	 */
	public  long del(String key) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(key)){
				result = jedis.del(key);
				logger.debug("del {}", key);
			}else{
				logger.debug("del {} not exists", key);
			}
		} catch (Exception e) {
			logger.error("del {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 删除缓存
	 * @param key 键
	 * @return
	 */
	public  long delObject(String key) {
		long result = 0;
		Jedis jedis = null;
		try {
			jedis = getResource();
			if (jedis.exists(getBytesKey(key))){
				result = jedis.del(getBytesKey(key));
				logger.debug("delObject {}", key);
			}else{
				logger.debug("delObject {} not exists", key);
			}
		} catch (Exception e) {
			logger.error("delObject {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	
	/**
	 * 缓存是否存在
	 * @param key 键
	 * @return
	 */
	public  boolean exists(String key) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.exists(key);
		} catch (Exception e) {
			logger.error("exists {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	/**
	 * 缓存是否存在
	 * @param key 键
	 * @return
	 */
	public  boolean hexists(String key,String field) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.hexists(key,field);
		} catch (Exception e) {
			logger.error("hexists {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}
	/**
	 * 缓存是否存在
	 * @param key 键
	 * @return
	 */
	public  boolean existsObject(String key) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = getResource();
			result = jedis.exists(getBytesKey(key));
			logger.debug("existsObject {}", key);
		} catch (Exception e) {
			logger.error("existsObject {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return result;
	}

	/**
	 * 获取资源
	 * @return
	 * @throws JedisException
	 */
	public  Jedis getResource() throws JedisException {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
		} catch (JedisException e) {
			logger.error("getResource.", e);
			returnBrokenResource(jedis);
			throw e;
		}
		return jedis;
	}

	/**
	 * 归还资源
	 * @param jedis
	 * @param isBroken
	 */
	public  void returnBrokenResource(Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnBrokenResource(jedis);
		}
	}
	
	/**
	 * 释放资源
	 * @param jedis
	 * @param isBroken
	 */
	public  void returnResource(Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}

	/**
	 * 获取byte[]类型Key
	 * @param key
	 * @return
	 */
	public static byte[] getBytesKey(Object object){
	
		if(object instanceof String){
			String str = object.toString();
    		return str.getBytes();
    	}else{
    		try {
				return SerializeUtil.serialize(object);
			} catch (Exception e) {
				logger.error("getBytesKey {}", object, e);
			}
    	}
		return null;
	}
	
	/**
	 * Object转换byte[]类型
	 * @param key
	 * @return
	 */
	public static byte[] toBytes(Object object){
    	try {
			return SerializeUtil.serialize(object);
		} catch (Exception e) {
			logger.error("getBytesKey {}", object, e);
		}
		return null;
	}

	/**
	 * byte[]型转换Object
	 * @param key
	 * @return
	 */
	public static Object toObject(byte[] bytes){
		try {
			return SerializeUtil.unserialize(bytes);
		} catch (Exception e) {
			logger.error("getBytesKey {}", bytes, e);
		}
		return null;
	}
	


	public  String createKey(String... key){
		StringBuffer ret = new StringBuffer();
		if(key != null )
		for (String string : key) {
			ret.append(string).append(SIGN);
		}
		return ret.toString();
		
	}

	@Override
	public long incr(String key) {
		Long ret = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			ret=jedis.incr(key);
		} catch (Exception e) {
			logger.error("incr {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return ret;
	}
	@Override
	public long decr(String key) {
		Long ret = null;
		Jedis jedis = null;
		try {
			jedis = getResource();
			ret=jedis.decr(key);
		} catch (Exception e) {
			logger.error("decr {}", key, e);
		} finally {
			returnResource(jedis);
		}
		return ret;
	}
}