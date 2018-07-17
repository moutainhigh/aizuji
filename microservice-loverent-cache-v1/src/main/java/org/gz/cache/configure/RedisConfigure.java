package org.gz.cache.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author yangdx
 */
@Configuration
public class RedisConfigure {
	
	@Autowired
	private RedisConfigureProperties redisConfigureProperties;
    
	@Bean
	public JedisPool redisPoolFactory() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(redisConfigureProperties.getMaxTotal());
        jedisPoolConfig.setMaxIdle(redisConfigureProperties.getMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(redisConfigureProperties.getMaxWaitMillis());
        jedisPoolConfig.setTestOnReturn(true);

        JedisPool jedisPool = new JedisPool(jedisPoolConfig, 
        		redisConfigureProperties.getHost(), 
        		redisConfigureProperties.getPort(), 
        		redisConfigureProperties.getTimeout(),
        		redisConfigureProperties.getPassword());
        return jedisPool;
    }
	
}
