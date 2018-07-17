/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.risk.bean;

import java.io.Serializable;

/**
 * 程序返回结果集
 * Author	Version		Date		Changes
 * zhuangjianfa 	1.0  		2017年7月1日 	Created
 * @since 1.
 */
public class Result implements Serializable{
    
    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;

    /**
     * 返回编码,默认是请求成功的
     */
    private Integer code = 0;
    
    /**
     * 返回消息
     */
    private String message = "操作成功.";

    /**
     * 返回结果集,resp
     */
    private Object data;
    
    /**
     * 总数
     */
    private Integer totalNum;
    
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

    
    /**
     * Returns this data object.
     * @return this data
     */
    public Object getData() {
        return data;
    }

    
    /**
     * Sets this data.
     * @param data this data to set
     */
    public void setData(Object data) {
        this.data = data;
    }

    
    /**
     * Returns this totalNum object.
     * @return this totalNum
     */
    public Integer getTotalNum() {
        return totalNum;
    }

    
    /**
     * Sets this totalNum.
     * @param totalNum this totalNum to set
     */
    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

}
