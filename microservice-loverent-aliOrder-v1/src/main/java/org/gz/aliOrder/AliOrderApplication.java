package org.gz.aliOrder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages={"org.gz"}) //启用声明式FeignClient
@ComponentScan(basePackages = { "org.gz"})

public class AliOrderApplication {
	public static void main(String[] args) {
		SpringApplication.run(AliOrderApplication.class, args);
	}

}
