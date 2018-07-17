package org.gz.gateway.config;

import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Title:zuul路由规则匹配模式配置
 * @author hxj
 */
@Configuration
public class RoutePatternConfig {
	@Bean
	public PatternServiceRouteMapper serviceRouteMapper() {
		// 调用构造函数PatternServiceRouteMapper(String servicePattern, String routePattern)
		// servicePattern指定微服务名匹配正则表达式
		// routePattern指定输出路由正则表达式
		//当服务名称匹配servicePattern时，其路由将会使用routePattern进行输出
		return new PatternServiceRouteMapper("(?<name>^.+)-(?<version>v.+$)", "${version}/${name}");
	}
}
