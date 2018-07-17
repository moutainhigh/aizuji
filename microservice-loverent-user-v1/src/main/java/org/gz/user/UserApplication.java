package org.gz.user;

import org.gz.sms.service.SmsSendService;
import org.gz.user.feign.CouponServiceClient;
import org.gz.user.service.outside.IUploadAliService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagement
@EnableFeignClients(clients={SmsSendService.class, IUploadAliService.class, CouponServiceClient.class})
//@EnableHystrixDashboard
//@EnableHystrix
@ComponentScan(basePackages={"org.gz"})
public class UserApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

}
