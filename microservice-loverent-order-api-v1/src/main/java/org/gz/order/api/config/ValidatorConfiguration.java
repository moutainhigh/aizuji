/**
 * 
 */
package org.gz.order.api.config;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Title:验证器配置，此处为了少引入包使用的是Hibernate Validator,也可以使用其它验证器，如：Oval Validator
 * @author hxj
 * @date 2017年12月21日 上午10:37:17
 */
@Configuration
public class ValidatorConfiguration {
	
	/**
	 * 是否启用快速失败模式
	 */
	private static final boolean FAIL_FAST=true;
	
	@Bean
	public Validator validator() {
		ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class).configure().failFast(FAIL_FAST).buildValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		return validator;
	}
}