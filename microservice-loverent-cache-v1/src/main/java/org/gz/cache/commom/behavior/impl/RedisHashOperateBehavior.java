package org.gz.cache.commom.behavior.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.gz.cache.commom.behavior.RedisHashBehavior;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;



/**
 * @author ydx
 */
@Slf4j
public abstract class RedisHashOperateBehavior extends RedisKeyOperateBehavior implements RedisHashBehavior {

	private static final long serialVersionUID = 1L;
	

	public RedisHashOperateBehavior(int dbIndex) {
		super(dbIndex);
	}

	@Override
	public long hDel(String key, String... fields) {
		Jedis conn = getResource();
		boolean broken = false;
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

	@Override
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

	@Override
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

	@Override
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

	@Override
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

	@Override
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

	@Override
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

	@Override
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

	@Override
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

	@Override
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

	@Override
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

	@Override
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

	@Override
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

}
