/**
 * 
 */
package org.gz.common.resp;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2017年12月4日 下午4:20:05
 */
public enum ResponseStatus {
	  	OK(200, "成功"),
	    DATA_CREATE_ERROR(100, "新增数据失败"),
	    DATA_REQUERY_ERROR(101, "查询数据失败"),
	    DATA_UPDATED_ERROR(102, "更新数据失败"),
	    DATA_DELETED_ERROR(103, "删除数据失败"),
	    DATA_INPUT_ERROR(104, "数据未输入"),
	    PARAMETER_VALIDATION(105, "参数验证失败-{0}"),
	    PARAMETER_ERROR(106, "参数错误"),
	    INVALID_CLIENT_ID(300, "clientID无效"),
	    INVALID_USER_NAME(301, "用户名错误"),
	    INVALID_PASSWORD(302, "密码错误"),
	    INVALID_TOKEN(303, "access_token无效"),
	    NO_AUTHORIZATION(304, "无Authorization传入"),
	    FALL_BACK(501, "无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！"),
	    SESSION_ERROR(900, "用户会话已过期!"),
	    DATABASE_ERROR(999, "数据库异常！"),
	    UNKNOW_SYSTEM_ERROR(1000, "系统未知异常"),
	    TEL_FORMAT_ERROR(1001, "手机号格式错误"),
	    TEL_REGISTED(1002, "手机号已注册"),
	    PASSWORD_FORMAT_ERROR(1003, "密码格式为6-12位字母数字组合"),
	    CAPTCHA_EXPIRED(1004, "短信验证码已过期"),
	    CAPTCHA_ERROR(1005, "短信验证码错误"),
	    LOGIN_FAILED(1006, "账号或密码错误"),
	    TEL_NOT_REGISTED(1007, "手机号未注册"),
	    FILE_UPLOAD_FAILED(1008, "上传失败"),
	    FILE_FETCH_FAILED(1009, "文件获取失败"),
	    LOGIN_FORBIDDEN(4000, "登录失效"),
	    PASSWORD_CONFIRM_ERROR(1007, "两次密码输入不一致"),
	    SMS_CHANNEL_ERROR(5001, "获取短信发送渠道失败"),
	    SMS_SEND_FAILED(5002, "短信发送失败"),
	    SMS_SEND_FREQUENTLY(5003, "短信发送频繁，请5分钟后尝试"),
	    SMS_SEND_LIMITED(5004, "短信发送次数已到上限"),
	    SERVICE_CALL_OVERTIME(8000, "服务不可用或超时"),
	    RISK_NOT_SUPPORT_TYPE(8001, "暂不支持这种审批类型"),
	    SIGN_ADD_ORG_ACC_FAILED(6001, "添加企业签约账户失败"),
	  	SIGN_ADD_USER_ACC_FAILED(6002, "添加用户签约账户失败"),
	  	SIGN_ADD_ORG_SEAL_FAILED(6003, "添加企业签约印章失败"),
	  	SIGN_ADD_USER_SEAL_FAILED(6004, "添加用户签约印章失败"),
	    WEI_XIN_ORDER_QUERY_FAILED(100000, "微信支付查询订单接口通讯失败"),
		WEI_XIN_ORDER_QUERY_ERROR(100001, "微信支付查询订单接口异常"),
	  	PRODUCT_HAS_CONFIG_ERROR(5005,"该产品存在相关联的橱窗配置"),

		/**
		 * 支付宝芝麻信用支付接口异常
		 */
		ALIPAY_CREDIT_PAY_ERROR(120000, "支付宝芝麻信用支付接口异常"),
		/**
		 * 支付宝订单查询接口异常
		 */
		ALIPAY_TRADE_QUERY_ERROR(130000, "支付宝订单查询接口异常");

	    // 成员变量
	    private int code;
	    private String message;

	    /**
	     * 构造方法
	     *
	     * @param code    错误码
	     * @param message 错误消息
	     */
	    ResponseStatus(int code, String message) {
	        this.code = code;
	        this.message = message;
	    }

	    /**
	     * @return 返回成员变量值
	     */
	    public int getCode() {
	        return code;
	    }

	    /**
	     * @return 返回成员变量值
	     */
	    public String getMessage() {
	        return message;
	    }
}
