package org.gz.liquidation.common.config;

/**
 * 
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;

/**
 * 定义Feign日志级别
 */
@Configuration
public class LiquidationFeignConfig {

	@Bean
	Logger.Level feignLoggerLevel(){
		return Logger.Level.FULL;
	}
}