package org.gz.webank.common;

/**
 * 常量信息
 * 
 * @author yangdx
 */
public interface ConfigureConst {
	
	/**请求成功标识*/
	public static final String SUCCESS_CODE = "0";

	/**授权类型*/
	public static final String GRANT_TYPE = "client_credential";
	
	/**版本号*/
	public static final String VERSION = "1.0.0";
	
	public static final String NONCE_TYPE = "NONCE";
	
	public static final String SIGN_TYPE = "SIGN";
	/**获取token*/
	public static final String ACCESS_TOKEN_URL = "https://idasc.webank.com/api/oauth2/access_token";
	/**获取tickit*/
	public static final String TICKET_URL = "https://idasc.webank.com/api/oauth2/api_ticket";
	
	
}
