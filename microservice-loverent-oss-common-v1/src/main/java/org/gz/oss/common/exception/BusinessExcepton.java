/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.oss.common.exception;


/**
 * 业务异常，用于我们明确知道问题点的地方，如注册时我们发现此账号名已被注册
 * Author	Version		Date		Changes
 * zhuangjianfa 	1.0  		2017年7月1日 	Created
 * @since 1.
 */
public class BusinessExcepton extends RuntimeException{
    
    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;

    
    /**
     * Class constructors.
     * @param code
     * @param message
     */
    public BusinessExcepton(Integer code, String message){
        super();
        this.code = code;
        this.message = message;
    }


    /**
     * 异常编码
     */
    private Integer code;
    
    /**
     * 异常消息
     */
    private String message;

    
    /**
     * Returns this code object.
     * @return this code
     */
    public Integer getCode() {
        return code;
    }

    
    /**
     * Sets this code.
     * @param code this code to set
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    
    /**
     * Returns this message object.
     * @return this message
     */
    public String getMessage() {
        return message;
    }

    
    /**
     * Sets this message.
     * @param message this message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
