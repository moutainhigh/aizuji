/**
 * 
 */
package org.gz.mq.common;

/**
 * 
 * @author hxj
 * @date 2017年11月13日上午11:32:30
 */
public enum StatusCodeEnum {

	CODE_SUCCESS(0, "操作成功"),

	CODE_IP_CHECK_FAILED(1000, "白名单检查失败"),

	CODE_SECRET_CHECK_FAILED(1001, "SECRET检查失败"),

	CODE_INVALID_PARAM(2000, "参数检验失败"),

	CODE_SEND_FAILED(3000, "消息发送失败");

	private int code;

	private String msg;

	private StatusCodeEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
