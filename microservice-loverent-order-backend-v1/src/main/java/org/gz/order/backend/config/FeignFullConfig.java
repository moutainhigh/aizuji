/**
 * 
 */
package org.gz.order.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;

/**
 * 定义Feign日志级别
 */
@Configuration(value = "orderBackendFeignFullConfig")
public class FeignFullConfig {

	@Bean
	Logger.Level feignLoggerLevel(){
		return Logger.Level.FULL;
	}
}