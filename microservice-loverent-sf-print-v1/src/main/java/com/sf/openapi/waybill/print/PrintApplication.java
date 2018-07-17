/**
 * 
 */
package com.sf.openapi.waybill.print;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@Slf4j
public class PrintApplication {
	public static void main(String[] args) {
		log.info("start...");
		SpringApplication.run(PrintApplication.class, args);
		log.info("over...");
	}

}
