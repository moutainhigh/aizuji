/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.risk.auditing.entity;

/**
 * 请求参数基类，所有调用服务参数请求继承此类
 * Author	      Version		Date		Changes
 * zhuangjianfa 	1.0  		2017年6月30日 	Created
 * @since 1.
 */
public class BaseReq {

    /**
     * 业务日志标识
     */
    private String  logSign;


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
