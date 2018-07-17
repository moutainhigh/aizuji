package org.gz.cache.commom.constant;

/**
 * 缓存时间常量
 * @author dell
 *
 */
public interface CacheTTLConst {
	/**登录验证码缓存时间*/
	public static final Integer LOGIN_SMS_CAPTCHE_TTL = 600;
	
	/**注册验证码缓存时间*/
	public static final Integer REGISTER_SMS_CAPTCHE_TTL = 600;

	/**修改密码验证码缓存时间*/
	public static final Integer MODIFY_PWD_SMS_CAPTCHE_TTL = 600;
	
	/**登录失效时间*/
	public static final Integer LOGIN_PWD_CACHE_TTL = Integer.MAX_VALUE;
	
	/**三方授权登录失效时间	- 15天*/
	public static final Integer LOGIN_THIRDPARTY_CACHE_TTL = 15 * 24 * 60 * 60;
	
	/**短信发送上限限制时间*/
	public static final Integer SMS_CPT_NUMBER_INTERVAL = 24 * 60 * 60;
	
	/**订单申请数据*/
	public static final Integer ORDER_APPLY_DATA_TTL = 600;
}
