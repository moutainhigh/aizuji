/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.risk.bean;


/**
 * vo基类,存放所有entity共有的属性
 * Author	Version		Date		Changes
 * zhuangjianfa 	1.0  		2017年7月1日 	Created
 * @since 1.
 */
public class BaseDto {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 业务日志标识
     */
    private String  logSign;
    
    /**
     * Returns this id object.
     * @return this id
     */
    public Long getId() {
        return id;
    }

    
    /**
     * Sets this id.
     * @param id this id to set
     */
    public void setId(Long id) {
        this.id = id;
    }


    
    /**
     * Returns this logSign object.
     * @return this logSign
     */
    public String getLogSign() {
        return logSign;
    }


    
    /**
     * Sets this logSign.
     * @param logSign this logSign to set
     */
    public void setLogSign(String logSign) {
        this.logSign = logSign;
    }
    
}
