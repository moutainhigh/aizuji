package org.gz.sms.exception;

/**
 * 
 * @author yangdx
 *
 */
public class SmsServiceException extends RuntimeException {

    private static final long serialVersionUID = -5875371379845226068L;

    /**
	 * 异常状态码
	 */
	private int code;

	/**
	 * 异常信息
	 */
	private String message;

	public SmsServiceException(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public SmsServiceException() {
        super();
    }

    public SmsServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmsServiceException(Throwable cause) {
        super(cause);
    }

    public SmsServiceException(String message) {
        super(message);
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
