/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.risk.dao.util;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @Description:简单生成日志唯一编码
 * Author	Version		Date		Changes
 * zhuangjianfa 	1.0  		2017年7月1日 	Created
 */
public class LogIdGenerator {

    /**
     * 
     * @Description: 生成日志编码(UUID+当前时间+业务编码) 
     * @param businessCode
     * @return
     * @throws
     * createBy:zhuangjianfa            
     * createDate:2017年7月3日
     */
    public static String generateCode(String logId,String businessCode) {
        if(StringUtils.isNotBlank(logId))
            return logId;
        UUID uuid = UUID.randomUUID();
        long now = System.currentTimeMillis();
        return String.format("%s_%s_%s",uuid.toString(),String.valueOf(now),businessCode);
    }
}
