package org.gz.sms.constants;

/**
 * 短信发送频率间隔时间
 * @author dell
 *
 */
public interface SmsSendFrequecyInterval {
	/**短信验证码发送上线 24H*/
	public static final Integer CAPTCHA_SEND_NUMBER = 5;
	
	/**验证码发送频率限制 毫秒*/
	public static final Long CAPTCHA_SEND_INTERVAL = 5 * 60 * 1000L;
}
