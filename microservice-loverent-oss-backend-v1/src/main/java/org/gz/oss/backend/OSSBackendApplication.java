package org.gz.oss.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import lombok.extern.slf4j.Slf4j;

@EnableDiscoveryClient//启用服务发现
@EnableFeignClients(basePackages= {"org.gz.oss.common.feign"}) //启用声明式FeignClient
@EnableCircuitBreaker//启用断路器
@EnableScheduling//启用定时器
@SpringBootApplication(scanBasePackages = { "org.gz.oss","org.gz.loverent.timed.task.config"})
@MapperScan("org.gz.oss.common.dao")
@Slf4j
public class OSSBackendApplication {

    public static void main(String[] args) {
        log.info("start...");
        SpringApplication.run(OSSBackendApplication.class, args);
        log.info("end...");
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

    /**
    * 跨域过滤器
    * @return
    */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4
        return new CorsFilter(source);
    }
}
