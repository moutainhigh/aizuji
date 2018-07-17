package org.gz.sms.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * 
 * @Description:TODO    随机id生成器
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年11月10日 	Created
 */
public class IdGenerator {
    /**
     * 
     * @Description: TODO 生成20位数字字符串
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年11月10日
     */
    public static String idCreateByTime(){
        LocalDateTime localDateTime =  LocalDateTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String str = df.format(localDateTime);
        String random = String.valueOf(Math.round(Math.random()*1000000));
        return str+random;
        
    }
    
    
}
