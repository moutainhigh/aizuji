package org.gz.user.commons;

public interface UserConstants {
	
	/**用户通讯录批量插入条数*/
	public static final int CONTACT_SAVE_BATCH_SIZE = 500;
	
	/**通讯录名字截取长度*/
	public static final int CONTACT_NAME_MAX_LENGTH = 20;
	
	/**通讯录电话截取长度*/
	public static final int CONTACT_PHONE_MAX_LENGTH = 20;
	
	public static final String EMPOWER_TYPE_WEIXIN = "weixin";

	public static final String EMPOWER_TYPE_ALIPAY = "alipay";
	
	/**授权状态-已绑定并登录成功*/
	public static final String EMPOWER_STATUS_SUCCESS = "1";
	/**授权状态-未绑定*/
	public static final String EMPOWER_STATUS_BIND = "2";
	
	/**绑定状态-成功*/
	public static final String BIND_PHONE_SUCCESS = "1";
	/**绑定状态-失败*/
	public static final String BIND_PHONE_FAILED = "2";
}
