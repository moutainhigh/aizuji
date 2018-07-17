package org.gz.user.exception;

/**
 * 业务处理异常
 * @author yangdx
 *
 */
public class UserServiceException extends RuntimeException {

    private static final long serialVersionUID = -5875371379845226068L;

    /**
	 * 异常状态码
	 */
	private int code;

	/**
	 * 异常信息
	 */
	private String message;

	public UserServiceException(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public UserServiceException() {
        super();
    }

    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserServiceException(Throwable cause) {
        super(cause);
    }

    public UserServiceException(String message) {
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
