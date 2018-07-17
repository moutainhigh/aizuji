/**
 * 
 */
package org.gz.risk.common.request;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;

/**
 * 定义Feign日志级别
 */
@Configuration
public class FeignFullConfig {

	@Bean
	Logger.Level feignLoggerLevel(){
		return Logger.Level.FULL;
	}
}