package org.gz.app;

import lombok.extern.slf4j.Slf4j;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
/*@EnableFeignClients(clients={
		UserService.class, 
		UserSmsCaptcheService.class,
		RentRecordServiceClient.class,
		AddressInfoService.class,
		BannerServiceClient.class,
		ProductColumnServiceClient.class,
		ShunFengServiceClient.class,
		MaterielSpecServiceClient.class,
		ProductServiceClient.class,
		CardInfoService.class,
		RepaymentServiceClient.class,
		PaymentServiceClient.class,
		UploadAliService.class,
		UserInfoService.class,
		MaterielServiceClient.class,
		AdvertServiceClient.class
})*/
@EnableFeignClients(basePackages={"org.gz"})
//@EnableHystrix
//@EnableHystrixDashboard
@ComponentScan(basePackages={"org.gz"})
@MapperScan(basePackages={"org.gz.**.mapper","org.gz.**.dao"})
@Slf4j
public class AppApplication {
	public static void main(String[] args) {
		log.info("start...");
		SpringApplication.run(AppApplication.class, args);
	}
	
}