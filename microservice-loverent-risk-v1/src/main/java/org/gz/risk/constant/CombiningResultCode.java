/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.risk.constant;

/**
 * @Description:用户返回编码消息枚举类
 * Author	Version		Date		Changes
 * zhuangjianfa 	1.0  		2017年7月1日 	Created
 */
public enum CombiningResultCode{
    /**
     * 请求成功
     */
    DEAL_SUCCESS(0,"操作成功."),
    /**
     * 请求参数错误
     */
    REQ_PARA_ERROR(1,"%s"),
    /**
     * 服务异常
     */
    SERVICE_EXCEPTION(2,"系统异常,请稍候再试或联系客服人员处理,谢谢!"),
    
    /**
     * 注册时，当前名称已存在
     */
    USER_NAME_ISEXISTS(10001,"当前用户名已存在,无法使用,请使用其它用户名.");
    
    /**
     * 编码
     */
    private int code;
    
    /**
     * 消息
     */
    private String message;
    
    private CombiningResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public int getCode() {   
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public String getMessage(Object ...args) {
        return String.format(message,args);
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
