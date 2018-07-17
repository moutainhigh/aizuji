package org.gz.oss.common.editor;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomDatePropertyEditor extends PropertyEditorSupport{

	private static final String DATE_TIME_FORMAT="yyyy-MM-dd HH:mm:ss";
	
	private static final String DATE_FORMAT="yyyy-MM-dd";
	
	@Override
    public void setAsText(String text) throws IllegalArgumentException {
        SimpleDateFormat format = new SimpleDateFormat(DATE_TIME_FORMAT);
        Date date = null;
        try {
            date = format.parse(text);
        } catch (ParseException e) {
            format = new SimpleDateFormat(DATE_FORMAT);
            try {
                date = format.parse(text);
            } catch (ParseException e1) {
                log.error("时间解析异常:{}",e1.getMessage());
            }
        }
        setValue(date);
    }
}
