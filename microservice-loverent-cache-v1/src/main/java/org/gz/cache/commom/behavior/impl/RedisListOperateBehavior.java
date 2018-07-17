package org.gz.cache.commom.behavior.impl;

import java.util.List;

import org.gz.cache.commom.behavior.RedisListBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;



/**
 * @author ydx
 */
public abstract class RedisListOperateBehavior extends RedisKeyOperateBehavior implements RedisListBehavior {

	private static final long serialVersionUID = 1L;
	
	private Logger LOGGER = LoggerFactory.getLogger(RedisListOperateBehavior.class);
	
	public RedisListOperateBehavior(int dbIndex) {
		super(dbIndex);
	}

	@Override
	public Long llen(String key) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.llen(key);
			}
		} catch (Exception e) {
			LOGGER.error("execute llen failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}


	@Override
	public String lindex(String key, long index) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.lindex(key, index);
			}
		} catch (Exception e) {
			LOGGER.error("execute lindex failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}


	@Override
	public String lpop(String key) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.lpop(key);
			}
		} catch (Exception e) {
			LOGGER.error("execute lpop failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}


	@Override
	public Long lpush(String key, String value) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.lpush(key, value);
			}
		} catch (Exception e) {
			LOGGER.error("execute lpush failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}


	@Override
	public Long lpush(String key, String[] values) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.lpush(key, values);
			}
		} catch (Exception e) {
			LOGGER.error("execute lpush failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}


	@Override
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
			LOGGER.error("execute lpush failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return false;
	}


	@Override
	public List<String> lrange(String key, long begin, long end) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.lrange(key, begin, end);
			}
		} catch (Exception e) {
			LOGGER.error("execute lrange failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}


	@Override
	public Long lRem(String key, long count, String value) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.lrem(key, count, value);
			}
		} catch (Exception e) {
			LOGGER.error("execute lRem failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}


	@Override
	public boolean lSet(String key, long index, String value) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				conn.lset(key, index, value);
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("execute lSet failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return false;
	}


	@Override
	public boolean lTrim(String key, long begin, long end) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				conn.ltrim(key, begin, end);
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("execute lTrim failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return false;
	}


	@Override
	public String rpop(String key) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.rpop(key);
			}
		} catch (Exception e) {
			LOGGER.error("execute rpop failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}


	@Override
	public Long rpush(String key, String value) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				//return the number of elements inside the list after the push operation.
				return conn.rpush(key, value);
			}
		} catch (Exception e) {
			LOGGER.error("execute rpush failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}


	@Override
	public Long rpush(String key, String[] values) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				//return the number of elements inside the list after the push operation.
				return conn.rpush(key, values);
			}
		} catch (Exception e) {
			LOGGER.error("execute rpush failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}


	@Override
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
			LOGGER.error("execute rpush failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return false;
	}

	
}
