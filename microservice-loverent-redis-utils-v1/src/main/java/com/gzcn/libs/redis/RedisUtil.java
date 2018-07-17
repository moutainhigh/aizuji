package com.gzcn.libs.redis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisCluster;

/**
 * 
 * redis集群实现类，redis3.0集群版可用
 *
 */

public class RedisUtil implements IRedisUtil {

	// 日志
	private final static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

	private static final String SIGN = ":";
	
	private  JedisCluster jedisCluster ;
	
	
	
	
	@Override
	public Long hdel(String key, String field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hexists(String key, String field) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Long hsetObject(String key, String field, Object value, int cacheSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long llen(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object hgetObject(String key, String field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hsetObject(String key, String field, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long lpush(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long rpush(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String rpoplpush(String oldKey, String newKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String rpop(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] get(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<byte[], byte[]> hgetAll(byte[] key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String hset(String key, Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String hget(String key, String field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String set(byte[] key, byte[] value, int cacheSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String set(byte[] key, byte[] value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hset(String key, String field, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hset(String key, String field, String value, int cacheSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long decr(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Long zcard(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrangeByScore(String key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zadd(String key, long score, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zadd(String key, Map<String, Double> scoreMembers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zrange(String key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String set(String key, String value) {
		return set(key, value, 0);
	}

	@Override
	public String setObject(String key, Object value) {
		return setObject(key, value,0);
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

	@Override
	public String get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObject(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String set(String key, String value, int cacheSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setObject(String key, Object value, int cacheSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getList(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> getObjectList(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long setList(String key, List<String> value, int cacheSeconds) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long setObjectList(String key, List<Object> value, int cacheSeconds) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long setSet(String key, Set<String> value, int cacheSeconds) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long listAdd(String key, String... value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long listObjectAdd(String key, Object... value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Set<String> getSet(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Object> getObjectSet(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long setObjectSet(String key, Set<Object> value, int cacheSeconds) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long setSetAdd(String key, String... value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long setSetObjectAdd(String key, Object... value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<String, String> getMap(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getObjectMap(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setMap(String key, Map<String, String> value, int cacheSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setObjectMap(String key, Map<String, Object> value, int cacheSeconds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String mapPut(String key, Map<String, String> value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String mapObjectPut(String key, Map<String, Object> value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long mapRemove(String key, String mapKey) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long mapObjectRemove(String key, String mapKey) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean mapExists(String key, String mapKey) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mapObjectExists(String key, String mapKey) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long del(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long delObject(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean exists(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existsObject(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String createKey(String... key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long incr(String key) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	

}