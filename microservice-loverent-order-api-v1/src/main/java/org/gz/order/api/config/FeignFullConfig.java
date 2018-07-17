package org.gz.order.api.config;

/**
 * 
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;

/**
 * 定义Feign日志级别
 */
@Configuration(value = "orderFeignFullConfig")
public class FeignFullConfig {

	@Bean
	Logger.Level feignLoggerLevel(){
		return Logger.Level.FULL;
	}
}