package org.gz.cache.configure;

import org.gz.cache.manager.RedisManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 缓存操作类
 * 
 * @author yangdx
 *
 */
@Configuration
@Import({RedisConfigureProperties.class ,RedisConfigure.class})
public class RedisManagerBeanConfigure {

	@Bean
	public RedisManager getRedisManager() {
		return new RedisManager();
	}
}
