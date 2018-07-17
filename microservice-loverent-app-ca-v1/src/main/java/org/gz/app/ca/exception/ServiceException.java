package org.gz.app.ca.exception;

/**
 * 通用服务异常
 * 
 * @author hxj
 *
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 6064411039804286293L;

	private int errorCode;

	private String errorMsg;

	public ServiceException() {
		super();
	}

	public ServiceException(int errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
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
