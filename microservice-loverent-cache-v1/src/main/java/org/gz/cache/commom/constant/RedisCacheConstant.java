package org.gz.cache.commom.constant;

/**
 * const
 * 
 * @author yangdx
 *
 */
public final class RedisCacheConstant
{
	
	public static final String CACHE_KEY_SEPARATOR = ":";

	public static final String NULL_CACHE_KEY = "null:key";
	
	
	/*********一级键  START********/
	public static final String FL_APP_AZJ = "app_azj";
	
	
	/*********一级键  END********/
	
	
	/*********业务级键  START********/
	public static final String SMS_CAPTCHE = "sms_cpt";
	public static final String USER_INFO = "user";
	public static final String PHONE_LOGIN = "phone";
	public static final String LOGIN_TYPE = "login_type";
	public static final String LOGIN_TOKEN = "token";
	public static final String REGISTED_PHONE = "registed_phone";
	public static final String SMS_FREQUENCY = "fq";
	
	
	public static final String WEBANK_CACHE = "webank"; 
	public static final String WEBANK_ACCESS_TOKEN = "access_token";
	public static final String WEBANK_TOKEN_REQ_LOCK = "token_lock";
	
	public static final String ORDER_APPLY = "order_apply";
	
	public static final String CA_SIGN = "ca";
	public static final String CA_RECORD = "record";
	public static final String CA_ACCOUNT = "account";
	public static final String CA_SEAL_DATA = "seal";
	public static final String CA_ACCOUNT_ORGANIZE = "organize";
	
	public static final String PAY = "pay";
	public static final String PAY_LIANLIAN = "lp";
	public static final String CITY_CODE = "cc";
	
	/*********业务级键  END********/
	
	
}
