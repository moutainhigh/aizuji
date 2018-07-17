/**
 * 
 */
package org.gz.mq.exception;

/**
 * 
 * @author hxj
 * @date 2017年11月9日下午5:46:51
 */
public class ConvertException extends Exception {

	private static final long serialVersionUID = -3374170208575466510L;

	/**
	 * 
	 */
	public ConvertException() {
		super();

	}

	/**
	 * @param message
	 * @param cause
	 */
	public ConvertException(String message, Throwable cause) {
		super(message, cause);

	}

	/**
	 * @param message
	 */
	public ConvertException(String message) {
		super(message);

	}

	/**
	 * @param cause
	 */
	public ConvertException(Throwable cause) {
		super(cause);

	}

}
