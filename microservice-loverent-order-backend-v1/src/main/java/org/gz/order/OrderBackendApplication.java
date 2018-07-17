package org.gz.order;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
@ComponentScan
@EnableFeignClients(basePackages = { "org.gz" })
@EnableCircuitBreaker
@ComponentScan(basePackages={"org.gz"})
public class OrderBackendApplication {
	public static void main(String[] args) {
		log.info("start...");
		SpringApplication.run(OrderBackendApplication.class, args);
	}
}