package org.gz.app.configure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 支付宝支付信息
 * 
 * @author yangdx
 *
 */
@Configuration
public class AlipayConfigure {
	
	// 商户appId
	public static String APPID = "2017120800456351";
	// 支付宝私钥
	public static String RSA_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC0tYX/3l+D7waZkX0cJL7NcWnEBe7i+NUt4+L1+hQ8nv1zzyzwBrP12b516Ksi7bu2nGssUfJbYkMLdgFBhS5B2nz+hlyGrvK+MgR6jb/ES5hwloNorGism9giJAJi3hgQ3QkWHTrRmeeRtDw2pPiQ2MXgwcKUUxab8A2mcTeolm5Dcr2xizbp1pkXxR1lzCc43rcZf8JwQ1+qGfy1I+dCPTM1UZmxKvQdTil+JTQ6afN2exNaP9vhY4cQzRctH2byReuaRxatqnBGU1V2WRLUIFWLt6l/hFqiriFwviOjaAj8yDrosx58sfap9gVmEGf8jZVHUdH160eXMJEWoBzxAgMBAAECggEBAKFboQ5K6vDswvDnN1ak/PaHOZC+V4O1+uyiGuvW5LEIqnn5WT/uTEzPpEC2g/GJY974Jr6JYaX1xLX8Jbzyhc5poMjJAt+EYd4w3UztO3m0oqaNz0LtB/5wnfpGOxc7BzQvmkhzyyyTh3HUBRwqDIWR+obpnKxC0OJm5FllqPRP84LNZ1amaqSIfefbN8DcFWfGrquSuBQkVAaPClutnZBifPG/EX0qzkGpI3/BawNDhxDYbq1QEqYiMuMX3HesZuuRC5Tiottd/1uwXR3LPlEIPdIkMJ9yD+yK9pkPg6OhPJKR8bUjwUjYpHc7KsfcnKdBQstbuma7H4QcnwPISG0CgYEA87JqoH7FFZrljDgyZjlvmw7o824iNb2F8rUnEBvl2ENqiUL+QneyvlFB4qgWAoXqg+yp0xtPbO8Eju2Gxbe3Gw+4kz6ZwucJrSe1V8kBTfszoWqdm3JF/SIQpToNrRy+ABncGfQJdK49tEQMjMwh8aGS78RXtEDH4Q3GUGe3W3MCgYEAvdUKU7cHhEWYxbj4fk+wAGLCkxr2AxBLXD3mdzBMkpW1adyBN3RryMdfxUFUmDaUd7IIBg0Qn0kkjNbrseZW6dGR5OX56TSAlEBBSeMdTAJ33J+tgx1RRDf6oSzHvebzY06Nz2PZ5FYsbAKipl5lK6Zt04+S3vx6TUSNmWmWVQsCgYEA5ab9VMIieHnAWKOc2zjFNBqY7M6c7ofr8w1EAqpHr8XYYAICGiEzEKcCr9Y256nLlwcwAwF5l+6nHQo6N5kptRbbmeckf8e6FrOU8WU7FYHk5fjTDepiimnPxeUvfjPX2cCE6vBO+OY6v+Zg7U5xroPVNJ6TCAsEUdHpUVxsLoECgYA6S9hWCRk315+o6Lr3H0WveKTERD1Uh4Z9F01VQPHi+jKyBdNw0kHfcMrKatutuuUxNO0d3plCMVYWIlJTWadLOt6Q6scqhMMPxAPIywROOVvR1v+UCkbQqlg/ct8MRIZt8su96q4ZXxVZPh3w9YAgPYXwuGCkNejhHbJWcISILQKBgBDhCQDesaLKLMI/jmK8rqNqQEvyvd7sAa7Q6Yp/GfIOuiJ7VYUSwngLdBvXYUs3lAah9bvPMG8T9VqQG9mP37UKiyg6F/glRkVk74X0z0EvB8/kR098kBF72mRU/Y4eGy72+EdjMTbNIarida1OrP6U2z2vq4vNkSWlHzroZ/hD";
	// 请求网关地址
	public static String GATE_WAY = "https://openapi.alipay.com/gateway.do";
	// 编码
	public static String CHARSET = "UTF-8";
	// 返回格式
	public static String FORMAT = "json";
	// 支付宝公钥
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlm9CmqVNdVtUtTe4cyRFfXZXy3t2d4ox9bKKTk7wKLN4KuYIeoKkxs6aksyacox1Kf9vrNX8fHA7L0BTQderuv0d8tsn7R5DumoNB8lxd2ST+PyYdTkhUX46r7CuTQZkndHsgwUE82SqFqeZbhbHHR0sBhb/AxhWWsrSG7lSKmUBIxpdv4hIeUi48BPvkVOuZjJM5g5O/h02Lsoo/q0U5l/MNoUutUm65NWD+Ye/8YqX6yniiNiQW10vwJzeNwuC9OJ6XO4UyqZbPlrXYBcz3a9ClCZMCSpI09yMciBatFQHNgge2eMOGZrGdxAmFCQ5ntp5MBMjigqiZoNDiUUg5wIDAQAB";
	// RSA2
	public static String SIGNTYPE = "RSA2";
	// 商户seller_id--收款方
	public static String SELLER_ID = "2088821976006061";
	//商户PID-商户中心
	public static String PID = "2088821976006061";
	
	//服务器异步通知地址
	@Value("${alipay.pay.notify.url}")
	private String alipayNotifyUrl;

	//页面跳转同步通知页面路径
	@Value("${alipay.pay.return.url}")
	private String alipayReturnUrl;

	public String getAlipayNotifyUrl() {
		return alipayNotifyUrl;
	}

	public void setAlipayNotifyUrl(String alipayNotifyUrl) {
		this.alipayNotifyUrl = alipayNotifyUrl;
	}

	public String getAlipayReturnUrl() {
		return alipayReturnUrl;
	}

	public void setAlipayReturnUrl(String alipayReturnUrl) {
		this.alipayReturnUrl = alipayReturnUrl;
	}
	
}
