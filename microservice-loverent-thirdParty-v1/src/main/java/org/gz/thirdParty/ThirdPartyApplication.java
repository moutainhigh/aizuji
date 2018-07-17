package org.gz.thirdParty;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@Slf4j
public class ThirdPartyApplication {
	public static void main(String[] args) {
		log.info("start...");
		SpringApplication.run(ThirdPartyApplication.class, args);
		log.info("over...");
	}
}