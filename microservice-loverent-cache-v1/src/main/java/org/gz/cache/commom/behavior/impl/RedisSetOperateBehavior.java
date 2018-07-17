package org.gz.cache.commom.behavior.impl;

import java.util.List;
import java.util.Set;

import org.gz.cache.commom.behavior.RedisSetBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;



/**
 * @author ydx
 */
public abstract class RedisSetOperateBehavior extends RedisKeyOperateBehavior implements RedisSetBehavior{

	private static final long serialVersionUID = 1L;
	
	private Logger LOGGER = LoggerFactory.getLogger(RedisSetOperateBehavior.class);
	
	public RedisSetOperateBehavior(int dbIndex) {
		super(dbIndex);
	}

	@Override
	public Long sadd(String key, String... values) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.sadd(key, values);
			}
		} catch (Exception e) {
			LOGGER.error("execute sadd failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return 0L;
	}



	@Override
	public Long sadd(String key, String value) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.sadd(key, value);
			}
		} catch (Exception e) {
			LOGGER.error("execute sadd failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}



	@Override
	public Long scard(String key) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.scard(key);
			}
		} catch (Exception e) {
			LOGGER.error("execute scard failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}



	@Override
	public Set<String> sdiff(String... keys) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.sdiff(keys);
			}
		} catch (Exception e) {
			LOGGER.error("execute sdiff failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}



	@Override
	public Set<String> sinter(String... keys) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.sinter(keys);
			}
		} catch (Exception e) {
			LOGGER.error("execute sinter failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}



	@Override
	public boolean sIsmember(String key, String member) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.sismember(key, member);
			}
		} catch (Exception e) {
			LOGGER.error("execute sIsmember failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return false;
	}



	@Override
	public Set<String> smembers(String key) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.smembers(key);
			}
		} catch (Exception e) {
			LOGGER.error("execute smembers failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}



	@Override
	public String spop(String key) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.spop(key);
			}
		} catch (Exception e) {
			LOGGER.error("execute spop failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}



	@Override
	public String srandmember(String key) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.srandmember(key);
			}
		} catch (Exception e) {
			LOGGER.error("execute srandmember failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}



	@Override
	public List<String> srandmember(String key, int count) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.srandmember(key, count);
			}
		} catch (Exception e) {
			LOGGER.error("execute srandmember failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}



	@Override
	public boolean srem(String key, String... members) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				conn.srem(key, members);
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("execute srem failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return false;
	}



	@Override
	public Set<String> sunion(String... keys) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.sunion(keys);
			}
		} catch (Exception e) {
			LOGGER.error("execute sunion failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}
	
}
