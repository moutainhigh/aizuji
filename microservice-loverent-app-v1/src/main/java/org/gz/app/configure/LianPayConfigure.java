package org.gz.app.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LianPayConfigure {

	@Value("${lianpay.sign.notify.url}")
	private String signNotifyUrl;

	@Value("${lianpay.sign.success.url}")
	private String signSuccessUrl;
	
	@Value("${lianpay.sign.failure.url}")
	private String signFailureUrl;
	
	@Value("${lianpay.pay.notify.url}")
	private String payNotifyUrl;

	@Value("${weixin.pay.notify.url}")
	private String wxPayNotifyUrl;

	
	public String getSignNotifyUrl() {
		return signNotifyUrl;
	}

	public void setSignNotifyUrl(String signNotifyUrl) {
		this.signNotifyUrl = signNotifyUrl;
	}

	public String getSignSuccessUrl() {
		return signSuccessUrl;
	}

	public void setSignSuccessUrl(String signSuccessUrl) {
		this.signSuccessUrl = signSuccessUrl;
	}

	public String getSignFailureUrl() {
		return signFailureUrl;
	}

	public void setSignFailureUrl(String signFailureUrl) {
		this.signFailureUrl = signFailureUrl;
	}

	public String getPayNotifyUrl() {
		return payNotifyUrl;
	}

	public void setPayNotifyUrl(String payNotifyUrl) {
		this.payNotifyUrl = payNotifyUrl;
	}

	public String getWxPayNotifyUrl() {
		return wxPayNotifyUrl;
	}

	public void setWxPayNotifyUrl(String wxPayNotifyUrl) {
		this.wxPayNotifyUrl = wxPayNotifyUrl;
	}
	
}
