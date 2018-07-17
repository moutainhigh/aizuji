package org.gz.warehouse.feign;

public class ResultCode {

	public static final ResultCode SUCCESS = new ResultCode(0, "success");
	
	public static final ResultCode TOKEN_FAILED = new ResultCode(9999, "令牌获取失败");
	
	public static final ResultCode ORDER_NUMBER_IS_NULL = new ResultCode(9998, "查询订单号不能为空");
	
	public static final ResultCode ORDER_TYPE_ERROR = new ResultCode(9997, "订单类型错误");
	
	public static final ResultCode SERVER_ERROR = new ResultCode(9996, "内部处理异常");

	public ResultCode() {

	}

	public ResultCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public ResultCode(Object data) {
		this.data = data;
	}

	/**
	 * 返回码
	 */
	private int code = 0;
	/**
	 * 返回信息
	 */
	private String message = "success";
	/**
	 * 返回数据
	 */
	private Object data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
