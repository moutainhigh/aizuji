package org.gz.app.configure;

import java.util.ArrayList;
import java.util.List;

import org.gz.app.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class AppCustomWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

	private static List<String> LOGIN_EXCLUDE_PATTERN;
	
	
	/**
	 * 不做登录校验的请求路径
	 */
	static {
		LOGIN_EXCLUDE_PATTERN = new ArrayList<>();
		LOGIN_EXCLUDE_PATTERN.add("/user/login");
		LOGIN_EXCLUDE_PATTERN.add("/user/register");
		LOGIN_EXCLUDE_PATTERN.add("/user/logout");
		LOGIN_EXCLUDE_PATTERN.add("/user/queryUserByMobile");
		LOGIN_EXCLUDE_PATTERN.add("/sms/sendCaptche");
		LOGIN_EXCLUDE_PATTERN.add("/product/loadProduct");
		LOGIN_EXCLUDE_PATTERN.add("/sms/sendCaptche");
		LOGIN_EXCLUDE_PATTERN.add("/banner/loadBanner");
		LOGIN_EXCLUDE_PATTERN.add("/materielSpec/queryAllSpecValueByModelId");
		LOGIN_EXCLUDE_PATTERN.add("/materielSpec/getSpecBatchNoBySpecInfo");
		LOGIN_EXCLUDE_PATTERN.add("/productColumn/loadProductColumns");
		LOGIN_EXCLUDE_PATTERN.add("/product/queryAllProductLeaseTerm");
		LOGIN_EXCLUDE_PATTERN.add("/product/getLeasePriceLowestProduct");
		LOGIN_EXCLUDE_PATTERN.add("/product/queryProductInfo");
		LOGIN_EXCLUDE_PATTERN.add("/product/queryProductGraphicInfo");
		LOGIN_EXCLUDE_PATTERN.add("/product/getSelectProduct");
		LOGIN_EXCLUDE_PATTERN.add("/lianpay/getSign");
		LOGIN_EXCLUDE_PATTERN.add("/lianpay/getPaySign");
		LOGIN_EXCLUDE_PATTERN.add("/lianpay/bindingCallBack/**");
		LOGIN_EXCLUDE_PATTERN.add("/lianpay/LianPayCallBack");
		LOGIN_EXCLUDE_PATTERN.add("/WX/getSign");
		LOGIN_EXCLUDE_PATTERN.add("/WX/WXCallBack");
		LOGIN_EXCLUDE_PATTERN.add("/order/lookContract");
		LOGIN_EXCLUDE_PATTERN.add("/shunfeng/routeQuery");
		LOGIN_EXCLUDE_PATTERN.add("/alipay/payCallback");
		LOGIN_EXCLUDE_PATTERN.add("/thirdParty/getAlipayLoginSignData");
		LOGIN_EXCLUDE_PATTERN.add("/thirdParty/empower");
		LOGIN_EXCLUDE_PATTERN.add("/thirdParty/xcxEmpower");
		LOGIN_EXCLUDE_PATTERN.add("/thirdParty/bindPhone");
		LOGIN_EXCLUDE_PATTERN.add("/webank/query/fetchResultByUserIdFromWebankAndReturn/*");
		LOGIN_EXCLUDE_PATTERN.add("/webank/query/fetchResultByTelFromWebankAndReturn/*");
		LOGIN_EXCLUDE_PATTERN.add("/card/testLog");
		LOGIN_EXCLUDE_PATTERN.add("/materiel/**");
		LOGIN_EXCLUDE_PATTERN.add("/advert/**");
		LOGIN_EXCLUDE_PATTERN.add("/operation/**");
		LOGIN_EXCLUDE_PATTERN.add("/recommend/**");
		LOGIN_EXCLUDE_PATTERN.add("/banner/**");
		LOGIN_EXCLUDE_PATTERN.add("/xcxOrder/**");
		LOGIN_EXCLUDE_PATTERN.add("/xcxOperation/**");
		LOGIN_EXCLUDE_PATTERN.add("/aizuji/xcxResult/notify");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		String[] arr = new String[LOGIN_EXCLUDE_PATTERN.size()];
		LOGIN_EXCLUDE_PATTERN.toArray(arr);
		registry.addInterceptor(initLoginInterceptor())
			.addPathPatterns("/**")
			.excludePathPatterns(arr);
		
		super.addInterceptors(registry);
	}
	
	@Bean
	public LoginInterceptor initLoginInterceptor() {
		return new LoginInterceptor();
	}
}
