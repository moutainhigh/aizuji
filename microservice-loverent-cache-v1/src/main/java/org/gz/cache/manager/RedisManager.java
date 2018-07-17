package org.gz.cache.manager;


import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.gz.cache.commom.behavior.impl.RedisStringOperateBehavior;
import org.gz.cache.commom.constant.RedisCacheDB;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

/**
 * 缓存操作通用类
 * 
 * @author yangdx
 *
 */
@Slf4j
@Component
public class RedisManager extends RedisStringOperateBehavior {
	
	private static final long serialVersionUID = 1L;
	
	public RedisManager() {
		super(RedisCacheDB.DEFAULT_DB);
	}
	
	public Long llen(String key) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.llen(key);
			}
		} catch (Exception e) {
			log.error("execute llen failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}
	
	public String lindex(String key, long index) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.lindex(key, index);
			}
		} catch (Exception e) {
			log.error("execute lindex failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}

	public String lpop(String key) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.lpop(key);
			}
		} catch (Exception e) {
			log.error("execute lpop failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}
	
	public Long lpush(String key, String value) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.lpush(key, value);
			}
		} catch (Exception e) {
			log.error("execute lpush failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}

	public Long lpush(String key, String[] values) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.lpush(key, values);
			}
		} catch (Exception e) {
			log.error("execute lpush failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}

	public boolean lpush(String key, String[] values, int trimLen) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				Transaction trans = conn.multi();
				trans.lpush(key, values);
				trans.ltrim(key, 0, trimLen);
				trans.exec();
			}
		} catch (Exception e) {
			log.error("execute lpush failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return false;
	}

	public List<String> lrange(String key, long begin, long end) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.lrange(key, begin, end);
			}
		} catch (Exception e) {
			log.error("execute lrange failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}
	
	public Long lRem(String key, long count, String value) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.lrem(key, count, value);
			}
		} catch (Exception e) {
			log.error("execute lRem failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}
	
	public boolean lSet(String key, long index, String value) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				conn.lset(key, index, value);
				return true;
			}
		} catch (Exception e) {
			log.error("execute lSet failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return false;
	}
	
	public boolean lTrim(String key, long begin, long end) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				conn.ltrim(key, begin, end);
				return true;
			}
		} catch (Exception e) {
			log.error("execute lTrim failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return false;
	}

	public String rpop(String key) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.rpop(key);
			}
		} catch (Exception e) {
			log.error("execute rpop failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}

	public Long rpush(String key, String value) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				//return the number of elements inside the list after the push operation.
				return conn.rpush(key, value);
			}
		} catch (Exception e) {
			log.error("execute rpush failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}

	public Long rpush(String key, String[] values) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				//return the number of elements inside the list after the push operation.
				return conn.rpush(key, values);
			}
		} catch (Exception e) {
			log.error("execute rpush failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}

	public boolean rpush(String key, String[] values, int trimLen) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				Transaction trans = conn.multi();
				trans.rpush(key, values);
				trans.ltrim(key, -trimLen, -1);
				trans.exec();
			}
		} catch (Exception e) {
			log.error("execute rpush failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return false;
	}
	
	public long hDel(String key, String... fields) {
		Jedis conn = getResource();
		try {
			if (conn != null) {
				return conn.hdel(key, fields);
			}
			
		} catch (JedisException e) {
			log.error("execute hDel failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		
		return 0;
	}
	
	public boolean hExists(String key, String field) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.hexists(key, field);
			}
		} catch (Exception e) {
			log.error("execute hExists failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		
		return false;
	}
	
	public String hGet(String key, String field) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.hget(key, field);
			}
		} catch (Exception e) {
			log.error("execute hGet failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}
	
	public Map<String, String> hGetAll(String key) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.hgetAll(key);
			}
		} catch (Exception e) {
			log.error("execute hGetAll failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}
	
	public double hIncrByFloat(String key, String field, double delta) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.hincrByFloat(key, field, delta);
			}
		} catch (Exception e) {
			log.error("execute hIncrBy failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return 0;
	}
	
	public long hIncrBy(String key, String field, long delta) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.hincrBy(key, field, delta);
			}
		} catch (Exception e) {
			log.error("execute hIncrBy failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return 0;
	}

	public Set<String> hKeys(String key) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.hkeys(key);
			}
		} catch (Exception e) {
			log.error("execute hKeys failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}

	public long hLen(String key) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.hlen(key);
			}
		} catch (Exception e) {
			log.error("execute hLen failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return 0;
	}

	public List<String> hMGet(String key, String... fields) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.hmget(key, fields);
			}
		} catch (Exception e) {
			log.error("execute hMGet failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}

	public boolean hMSet(String key, Map<String, String> hash) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				conn.hmset(key, hash);
				return true;
			}
		} catch (Exception e) {
			log.error("execute hMGet failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return false;
	}

	public boolean hSet(String key, String field, String value) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				conn.hset(key, field, value);
				return true;
			}
		} catch (Exception e) {
			log.error("execute hSet failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return false;
	}

	public boolean hSetNX(String key, String field, String value) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				conn.hsetnx(key, field, value);
				return true;
			}
		} catch (Exception e) {
			log.error("execute hSetNX failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return false;
	}
	
	public List<String> hVals(String key) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.hvals(key);
			}
		} catch (Exception e) {
			log.error("execute hVals failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}
	
	public Long sadd(String key, String... values) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.sadd(key, values);
			}
		} catch (Exception e) {
			log.error("execute sadd failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return 0L;
	}

	public Long sadd(String key, String value) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.sadd(key, value);
			}
		} catch (Exception e) {
			log.error("execute sadd failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}

	public Long scard(String key) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.scard(key);
			}
		} catch (Exception e) {
			log.error("execute scard failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}

	public Set<String> sdiff(String... keys) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.sdiff(keys);
			}
		} catch (Exception e) {
			log.error("execute sdiff failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}

	public Set<String> sinter(String... keys) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.sinter(keys);
			}
		} catch (Exception e) {
			log.error("execute sinter failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}

	public boolean sIsmember(String key, String member) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.sismember(key, member);
			}
		} catch (Exception e) {
			log.error("execute sIsmember failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return false;
	}

	public Set<String> smembers(String key) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.smembers(key);
			}
		} catch (Exception e) {
			log.error("execute smembers failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}

	public String spop(String key) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.spop(key);
			}
		} catch (Exception e) {
			log.error("execute spop failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}

	public String srandmember(String key) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.srandmember(key);
			}
		} catch (Exception e) {
			log.error("execute srandmember failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}

	public List<String> srandmember(String key, int count) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.srandmember(key, count);
			}
		} catch (Exception e) {
			log.error("execute srandmember failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}

	public boolean srem(String key, String... members) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				conn.srem(key, members);
				return true;
			}
		} catch (Exception e) {
			log.error("execute srem failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return false;
	}

	public Set<String> sunion(String... keys) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.sunion(keys);
			}
		} catch (Exception e) {
			log.error("execute sunion failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}
	
}