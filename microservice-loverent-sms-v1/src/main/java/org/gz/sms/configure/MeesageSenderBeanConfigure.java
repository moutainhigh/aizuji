package org.gz.sms.configure;

import org.gz.sms.sender.YunTongXunMessageSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MeesageSenderBeanConfigure {

	@Bean
	public YunTongXunMessageSender initYunTongXunMessageSender() {
		return new YunTongXunMessageSender();
	}
}
