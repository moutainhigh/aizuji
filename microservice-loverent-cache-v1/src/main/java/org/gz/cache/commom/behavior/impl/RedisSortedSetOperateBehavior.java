package org.gz.cache.commom.behavior.impl;

import java.util.Set;

import org.gz.cache.commom.behavior.RedisSortedSetBehavior;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;



/**
 * @author ydx
 */
public abstract class RedisSortedSetOperateBehavior extends RedisKeyOperateBehavior implements RedisSortedSetBehavior {

	private static final long serialVersionUID = 1L;
	
	private Logger LOGGER = LoggerFactory.getLogger(RedisSortedSetOperateBehavior.class);
	
	public RedisSortedSetOperateBehavior(int dbIndex) {
		super(dbIndex);
	}

	@Override
	public boolean zadd(String key, double score, String member) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				conn.zadd(key, score, member);
			}
		} catch (Exception e) {
			LOGGER.error("execute zadd failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return false;
	}



	@Override
	public Long zcard(String key) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.zcard(key);
			}
		} catch (Exception e) {
			LOGGER.error("execute zcard failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return 0L;
	}



	@Override
	public Long zcount(String key, double min, double max) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.zcount(key, min, max);
			}
		} catch (Exception e) {
			LOGGER.error("execute zcount failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}



	@Override
	public boolean zincrBy(String key, double increment, String member) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				conn.zincrby(key, increment, member);
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("execute zincrBy failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return false;
	}



	@Override
	public Set<String> zrange(String key, long begin, long end) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.zrange(key, begin, end);
			}
		} catch (Exception e) {
			LOGGER.error("execute zrange failed, e: {}", e);
		} finally {
			closeResource(conn);
		}	
		return null;
	}



	@Override
	public Set<String> zrangeByScore(String key, double min, double max) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.zrangeByScore(key, min, max);
			}
		} catch (Exception e) {
			LOGGER.error("execute zrangeByScore failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}



	@Override
	public Set<String> zrangeByScore(String key, double min, double max,
			int offset, int count) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.zrangeByScore(key, min, max, offset, count);
			}
		} catch (Exception e) {
			LOGGER.error("execute zrangeByScore failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}



	@Override
	public Long zrank(String key, String member) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.zrank(key, member);
			}
		} catch (Exception e) {
			LOGGER.error("execute zrank failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}



	@Override
	public long zrem(String key, String... members) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.zrem(key, members);
			}
		} catch (Exception e) {
			LOGGER.error("execute zrem failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return 0;
	}


	@Override
	public long zRemRangeByScore(String key, double min, double max) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.zremrangeByScore(key, min, max);
			}
		} catch (Exception e) {
			LOGGER.error("execute zRemRangeByScore failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return 0;
	}



	@Override
	public Set<String> zRevRange(String key, long begin, long end) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.zrevrange(key, begin, end);
			}
		} catch (Exception e) {
			LOGGER.error("execute zRevRange failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}



	@Override
	public Set<String> zRevRangeByScore(String key, double max, double min) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.zrevrangeByScore(key, max, min);
			}
		} catch (Exception e) {
			LOGGER.error("execute zRevRangeByScore failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}



	@Override
	public Set<String> zRevRangeByScore(String key, double min, double max,
			int offset, int count) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.zrevrangeByScore(key, max, min, offset, count);
			}
		} catch (Exception e) {
			LOGGER.error("execute zRevRangeByScore failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}



	@Override
	public Long zRevRank(String key, String member) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.zrevrank(key, member);
			}
		} catch (Exception e) {
			LOGGER.error("execute zRevRank failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return 0L;
	}



	@Override
	public Double zscore(String key, String member) {
		Jedis conn = getResource();
		
		try {
			if (conn != null) {
				return conn.zscore(key, member);
			}
		} catch (Exception e) {
			LOGGER.error("execute zscore failed, e: {}", e);
		} finally {
			closeResource(conn);
		}
		return null;
	}
	
}
