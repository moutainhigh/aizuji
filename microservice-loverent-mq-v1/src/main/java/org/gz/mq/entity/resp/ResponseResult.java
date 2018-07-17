/**
 * 
 */
package org.gz.mq.entity.resp;

import org.gz.mq.common.StatusCodeEnum;

/**
 * 针对前端的响应结果
 * 
 * @author hxj
 * @date 2017年11月9日下午3:44:53
 */
public class ResponseResult {

	public static final ResponseResult OPT_SUCCESS = new ResponseResult(StatusCodeEnum.CODE_SUCCESS.getCode(), StatusCodeEnum.CODE_SUCCESS.getMsg());
	
	public static final ResponseResult OPT_SEND_FAILED = new ResponseResult(StatusCodeEnum.CODE_SEND_FAILED.getCode(), StatusCodeEnum.CODE_SEND_FAILED.getMsg());
	
	public static final ResponseResult OPT_INVALID_PARAM =  new ResponseResult(StatusCodeEnum.CODE_INVALID_PARAM.getCode(), StatusCodeEnum.CODE_INVALID_PARAM.getMsg());
	
	public static final ResponseResult OPT_IPWHITELIST_CHECK_FAILED =new ResponseResult(StatusCodeEnum.CODE_IP_CHECK_FAILED.getCode(), StatusCodeEnum.CODE_IP_CHECK_FAILED.getMsg());

	public static final ResponseResult OPT_SECRET_CHECK_FAILED=new ResponseResult(StatusCodeEnum.CODE_SECRET_CHECK_FAILED.getCode(), StatusCodeEnum.CODE_SECRET_CHECK_FAILED.getMsg());

	private int errorCode;

	private String errorMsg;

	public ResponseResult() {

	}

	public ResponseResult(int errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public static ResponseResult build(int errorCode,String errorMsg){
		return new ResponseResult(errorCode,errorMsg);
	}
	
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
