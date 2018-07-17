package org.gz.cache.service.captche;

import org.gz.cache.commom.behavior.RedisStringBehavior;
import org.gz.common.constants.SmsType;

public interface CaptcheCacheService extends RedisStringBehavior {
	
	/**
	 * 设置手机短信验证码
	 * @param tel
	 * 			手机号
	 * @param captcha
	 * 			验证码
	 * @param smsType
	 * 			短信类型
	 */
	void setTelCaptcha(String tel, String captcha, SmsType smsType, Integer ttl);
	
	/**
	 * 获取手机短信验证码
	 * @param tel
	 * 			手机号
	 * @param smsType
	 * 			短信类型
	 */
	String getTelCaptcha(String tel, SmsType smsType);
	
	/**
	 * 删除手机短信验证码
	 * @param tel
	 * 			手机号
	 * @param smsType
	 * 			短信类型
	 */
	void removeTelCaptcha(String tel, SmsType smsType);

	/**
	 * 获取最后一次短信发送时间
	 * @param phoneNum
	 * @param smsType
	 * @return
	 */
	String getLastSendTime(String tel, SmsType smsType);

	/**
	 * 获取短信验证码发送次数
	 * @param phoneNum
	 * @param smsType
	 * @return
	 */
	String getSmsCaptchaSendNumber(String tel, SmsType smsType);
}
