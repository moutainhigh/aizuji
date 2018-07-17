package org.gz.cache.commom.constant;

/**
 * 
 * @author yangdx
 *
 */
public interface RedisCacheDB
{
	/**
	 * 默认存储
	 */
	public static final int DEFAULT_DB = 0;

	/**
	 * 用户信息
	 */
	public static final int USER_CACHE_DB = 1;

	/**
	 * 验证码信息
	 */
	public static final int CAPTCHA_CACHE_DB = 2;

	/**
	 * 短信频率
	 */
	public static final int SMS_FREQUENCE_CACHE_DB = 3;
	
	/**
	 * 用户签约数据
	 */
	public static final int CA_SIGN_CACHE_DB = 4;
	
	/**
	 * 支付相关数据
	 */
	public static final int PAY = 5;
  /**
   * 订单相关数据
   */
  public static final int ORDER_DB = 12;

}
