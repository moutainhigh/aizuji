package org.gz.oss.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateToolUtils {

	/**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     * 
     * @param nowTime 当前时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return boolean
     */
    public static Byte isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime() || nowTime.getTime() == endTime.getTime()) {
            return 20;//已生效
        }
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return 20;//已生效
        } else if (date.before(begin)){
            return 10;//待生效
        } else {
        	return 30;//已过期
        }
    }
    
    public static void main(String[] args) throws Exception {
		Date d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-03-23 15:12:29");
		Date d2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-03-24 15:12:32");
		Byte result = isEffectiveDate(new Date(), d1, d2);
		System.out.println(result);
    	
	}
}
