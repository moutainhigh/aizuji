/**
 * 
 */
package org.gz.mq.exception;

/**
 * 
 * @author hxj
 * @date 2017年11月9日下午5:41:19
 */
public class MessageException extends Exception {

	private static final long serialVersionUID = 7728692896393700517L;

	/**
	 * 
	 */
	public MessageException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 */
	public MessageException(String message, Throwable cause) {
		super(message, cause);

	}

	/**
	 * @param message
	 */
	public MessageException(String message) {
		super(message);

	}

	/**
	 * @param cause
	 */
	public MessageException(Throwable cause) {
		super(cause);

	}

}
