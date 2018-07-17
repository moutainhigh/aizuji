package org.gz.cache.commom.behavior.impl;

import java.util.List;
import java.util.UUID;

import org.gz.cache.commom.behavior.RedisStringBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.ZParams;



/**
 * @author ydx
 */
public abstract class RedisStringOperateBehavior extends RedisKeyOperateBehavior implements RedisStringBehavior {

	private Logger LOGGER = LoggerFactory.getLogger(RedisStringOperateBehavior.class);
	
	private static final long serialVersionUID = 1L;
	
	public RedisStringOperateBehavior(int dbIndex) {
		super(dbIndex);
	}

	@Override
	public Long decr(String key) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.decr(key);
			}
			
		} catch (Exception e) {
			LOGGER.error("execute decr failed, key : {}, e: {}", key, e);
		} finally {
			closeResource(conn);
		}
		return null;
	}



	@Override
	public Long decrBy(String key, long decrement) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.decrBy(key, decrement);
			}
			
		} catch (Exception e) {
			LOGGER.error("execute decrBy failed, key : {}, e: {}", key, e);
		} finally {
			closeResource(conn);
		}
		return null;
	}



	@Override
	public String get(String key) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.get(key);
			}
			
		} catch (Exception e) {
			LOGGER.error("execute get failed, key : {}, e: {}", key, e);
		} finally {
			closeResource(conn);
		}
		
		return null;
	}



	@Override
	public List<String> mget(String... keys) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return  conn.mget(keys);
			}
		} catch (Exception e) {
			LOGGER.error("execute mget failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}



	@Override
	public Long incr(String key) {
		Jedis conn = getResource();

		try {
			if (conn != null) {
				return conn.incr(key);
			}
		} catch (Exception e) {
			LOGGER.error("redis incr failed, key: {}, e: {}", key, e);
		} finally {
			closeResource(conn);
		}
		return 0L;
	}



	@Override
	public Long incrBy(String key, long increment) {
		Jedis conn = getResource();

		try {
			if (conn != null) {
				return conn.incrBy(key, increment);
			}
		} catch (Exception e) {
			LOGGER.error("redis incrBy failed, key: {}, e: {}", key, e);
		} finally {
			closeResource(conn);
		}
		return 0L;
	}



	@Override
	public Double incrByFloat(String key, Double increment) {
		Jedis conn = getResource();

		try {
			if (conn != null) {
				return conn.incrByFloat(key, increment);
			}
		} catch (Exception e) {
			LOGGER.error("redis incrByFloat failed, key: {}, e: {}", key, e);
		} finally {
			closeResource(conn);
		}
		return 0.0;
	}



	@Override
	public boolean set(String key, String value) {
		Jedis conn = getResource();

		try {
			if (conn != null) {
				conn.set(key, value);
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("redis set failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return false;
	}



	@Override
	public boolean setEx(String key, int seconds, String value) {
		Jedis conn = getResource();

		try {
			if (conn != null) {
				conn.setex(key, seconds, value);
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("redis setEx failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return false;
	}



	@Override
	public boolean setNX(String key, String value) {
		Jedis conn = getResource();

		try {
			if (conn != null) {
				conn.setnx(key, value);
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("redis setNX failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return false;
	}



	@Override
	public Long strLen(String key) {
		Jedis conn = getResource();

		try {
			if (conn != null) {
				return conn.strlen(key);
			}
		} catch (Exception e) {
			LOGGER.error("redis setNX failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return 0L;
	}
	
	public String acquireLockWithTimeout(Jedis conn, String lockName,
			long acquireTimeout, long lockTimeout) {
		String identifier = UUID.randomUUID().toString();
		String lockKey = "lock:" + lockName;
		int lockExpire = (int) (lockTimeout / 1000);

		long end = System.currentTimeMillis() + acquireTimeout;
		while (System.currentTimeMillis() < end) {
			conn.select(dbIndex);
			if (conn.setnx(lockKey, identifier) == 1) {
				conn.expire(lockKey, lockExpire);
				return identifier;
			}
			if (conn.ttl(lockKey) == -1) {
				conn.expire(lockKey, lockExpire);
			}

			try {
				Thread.sleep(1);
			} catch (InterruptedException ie) {
				Thread.currentThread().interrupt();
			}
		}

		return null;
	}

	public boolean releaseLock(Jedis conn, String lockName, String identifier) {
		String lockKey = "lock:" + lockName;
		conn.select(dbIndex);

		while (true) {
			conn.watch(lockKey);
			if (identifier.equals(conn.get(lockKey))) {
				Transaction trans = conn.multi();
				trans.del(lockKey);
				List<Object> results = trans.exec();
				if (results == null) {
					continue;
				}
				return true;
			}

			conn.unwatch();
			break;
		}

		return false;
	}

	private String zsetCommon(Transaction trans, String method, int ttl,
			ZParams params, String... sets) {
		String[] keys = new String[sets.length];
		for (int i = 0; i < sets.length; i++) {
			keys[i] = sets[i];
		}

		String id = UUID.randomUUID().toString();
		try {
			trans.getClass()
					.getDeclaredMethod(method, String.class, ZParams.class,
							String[].class)
					.invoke(trans, "idx:" + id, params, keys);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		trans.expire("idx:" + id, ttl);
		return id;
	}

	/**
	 * 
	 * @param trans
	 * @param ttl
	 * 			秒
	 * @param params
	 * @param sets
	 * @return
	 */
	public String zintersect(Transaction trans, int ttl, ZParams params,
			String... sets) {
		return zsetCommon(trans, "zinterstore", ttl, params, sets);
	}

	public String zunion(Transaction trans, int ttl, ZParams params,
			String... sets) {
		return zsetCommon(trans, "zunionstore", ttl, params, sets);
	}
	
}
