package org.gz.cache.commom.behavior.impl;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.gz.cache.commom.behavior.RedisKeyBehavior;
import org.gz.cache.commom.constant.RedisCacheConstant;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author ydx
 */
@Slf4j
public abstract class RedisKeyOperateBehavior implements RedisKeyBehavior{
	
	private static final long serialVersionUID = 1L;

	protected int dbIndex;
	
	@Autowired
	protected JedisPool jedisPool;
	
	public RedisKeyOperateBehavior(int dbIndex){
		this.dbIndex = dbIndex;
	}
	
	protected Jedis getResource() {
		if (jedisPool == null) {
			return null;
		}
		
		try {
			Jedis conn = jedisPool.getResource();
			conn.select(dbIndex);
			return conn;
		} catch (Exception e) {
			log.error("get resource from redis pool failed, e: {}", e);
			return null;
		}
	}
	
	protected void closeResource(Jedis jedis) {
	    try {
	        if (jedis != null) {
	        	jedis.close();
	        }
	    } catch (Exception e) {
	        log.error("closeResource failed, e: {}", e);
	    }
	}
	
	@Override
	public boolean del(String... keys) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				conn.del(keys);
			}
		} catch (Exception e) {
			log.error("execute del failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		
		return false;
	}



	@Override
	public boolean exists(String key) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				conn.exists(key);
			}
		} catch (Exception e) {
			log.error("execute exists failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		
		return false;
	}



	@Override
	public boolean expire(String key, int seconds) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				conn.expire(key, seconds);
			}
		} catch (Exception e) {
			log.error("execute expire failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		
		return false;
	}



	@Override
	public boolean expireAt(String key, int unixTime) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				conn.expireAt(key, unixTime);
			}
		} catch (Exception e) {
			log.error("execute expireAt failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		
		return false;
	}



	@Override
	public Long pttl(String key) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.pttl(key);
			}
		} catch (Exception e) {
			log.error("execute pttl failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		
		return 0L;
	}



	@Override
	public Long ttl(String key) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.ttl(key);
			}
		} catch (Exception e) {
			log.error("execute ttl failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		
		return 0L;
	}



	@Override
	public String randomKey() {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.randomKey();
			}
		} catch (Exception e) {
			log.error("execute randomKey failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		
		return null;
	}



	@Override
	public boolean rename(String oldKey, String newKey) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				conn.rename(oldKey, newKey);
				return true;
			}
		} catch (Exception e) {
			log.error("execute rename failed, oldKey: {}, newKey: {}, e: {}", oldKey, newKey, e);
		} finally {
			closeResource(conn);
		}
		
		return false;
	}



	@Override
	public boolean renameNX(String oldKey, String newKey) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				conn.renamenx(oldKey, newKey);
				return true;
			}
		} catch (Exception e) {
			log.error("execute renameNX failed, oldKey: {}, newKey: {}, e: {}", oldKey, newKey, e);
		} finally {
			closeResource(conn);
		}
		
		return false;
	}



	@Override
	public void setLastCacheTime(String fullKey) {
	}



	@Override
	public Date getLastCacheTime(String fullKey) {
		return null;
	}
	
	protected void operationFailed() {
	}
	
	@Override
	public String initCacheKey(String... keyParts) {
		if (keyParts == null || keyParts.length == 0) {
			return RedisCacheConstant.NULL_CACHE_KEY;
		}
		
		StringBuffer buffer = new StringBuffer();
		for (String part : keyParts) {
			buffer.append(part).append(RedisCacheConstant.CACHE_KEY_SEPARATOR);
		}
		return buffer.toString();
	}
	
	/*@SuppressWarnings("unchecked")
	protected T parseObject(String str, Class<T> clazz) {
		if (str == null) return null;
		if (clazz == null) return (T)str;
		if (StringUtils.equalsIgnoreCase(clazz.getSimpleName(), "String")) return (T)str;
		return JSON.parseObject(str, clazz);
	}*/
}
