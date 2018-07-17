/**
 * 
 */
package org.gz.warehouse.config;

import org.gz.warehouse.interceptor.SessionAccessInterceptor;
import org.gz.warehouse.interceptor.InterfaceAccessInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2018年1月8日 上午11:28:43
 */
@Configuration
public class CustomWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 多个拦截器组成一个拦截器链
		// addPathPatterns 用于添加拦截规则
		// excludePathPatterns 用户排除拦截
		registry.addInterceptor(new SessionAccessInterceptor()).addPathPatterns("/**").excludePathPatterns("/**/user/login", "/**/api/**");
		registry.addInterceptor(new InterfaceAccessInterceptor()).addPathPatterns("/**/user/login", "/**/api/**");
		super.addInterceptors(registry);
	}
}
