package org.gz.liquidation;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages={"org.gz"}) //启用声明式FeignClient
@EnableCircuitBreaker//启用断路器
@EnableTransactionManagement
@Slf4j
@ComponentScan(basePackages = ("org.gz.loverent.timed.task.config,org.gz.liquidation,org.gz.sms"))
public class LiquidationApplication {
	public static void main(String[] args) {
		log.info("start...");
		SpringApplication.run(LiquidationApplication.class, args);
	}
	
}