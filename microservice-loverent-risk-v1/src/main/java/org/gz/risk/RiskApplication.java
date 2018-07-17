package org.gz.risk;

import org.gz.risk.auditing.rule.config.TaskThreadPoolConfig;
import org.gz.risk.auditing.service.outside.IRentRecordService;
import org.gz.user.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(clients={
		UserService.class,
		IRentRecordService.class
})
@ComponentScan(basePackages = { "org.gz" })
@MapperScan(basePackages = { "org.gz.risk.dao" })
@EnableConfigurationProperties({ TaskThreadPoolConfig.class }) // 开启配置属性支持
@EnableScheduling //开启任务调度支持
@Slf4j
public class RiskApplication {
	public static void main(String[] args) {
		log.info("start...");
		SpringApplication.run(RiskApplication.class, args);
	}


}