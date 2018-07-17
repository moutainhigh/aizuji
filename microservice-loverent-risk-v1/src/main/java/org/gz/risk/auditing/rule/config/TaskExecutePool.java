package org.gz.risk.auditing.rule.config;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration  
@EnableAsync  
public class TaskExecutePool {  
  
    @Autowired  
    private TaskThreadPoolConfig config;  
  
    
	@Bean(name = "ThreadPoolTaskExecutor")
	public Executor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(config.getCorePoolSize());  
        executor.setMaxPoolSize(config.getMaxPoolSize());  
        executor.setQueueCapacity(config.getQueueCapacity());  
        executor.setKeepAliveSeconds(config.getKeepAliveSeconds());
		
		return executor;
	}
}  
