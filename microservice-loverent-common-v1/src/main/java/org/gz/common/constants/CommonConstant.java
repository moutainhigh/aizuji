package org.gz.common.constants;

/**
 * 系统公共调用的数据常量定义在这里
 */
public class CommonConstant
{

	/** 系统内部通讯参数，默认字符编码定义 */
	public final static String CharacterEncoding_Default = "utf-8";

	/** 系统内部通讯参数，时间日期格式定义 */
	public final static String DatetimePattern_Date = "yyyy-MM-dd";

	/** 系统内部通讯参数，时间日期格式定义 */
	public final static String DatetimePattern_Full = "yyyy-MM-dd HH:mm:ss.SSS";

	/** 系统内部通讯参数，时间日期格式定义 */
	public final static String DatetimePattern_Lite = "yyyy-MM-dd HH:mm:ss";

	/** 日期格式 yyyyMMddHHmmssSSS */
	public final static String DatetimePattern_Long = "yyyyMMddHHmmssSSS";

	public final static String DatetimePattern_long14 = "yyyyMMddHHmmss";

	/**密码登录*/
	public final static String USER_LOGIN_TYPE_PWD = "1";
	/**短信码登录*/
	public final static String USER_LOGIN_TYPE_SMS = "2";
	
	/**身份证正面*/
	public final static String IDCARD_FACE = "f";
	/**身份证反面*/
	public final static String IDCARD_BACK = "b";
	
	/**密码登录*/
	public static final String LOGIN_TYPE_PWD = "1";
	/**三方授权登录*/
	public static final String LOGIN_TYPE_THIRD_PARTY = "2";
	
	/**H5-OCR身份证正面*/
	public final static String H5_IDCARD_FACE = "0";
	/**H5-OCR身份证反面*/
	public final static String H5_IDCARD_BACK = "1";
}
