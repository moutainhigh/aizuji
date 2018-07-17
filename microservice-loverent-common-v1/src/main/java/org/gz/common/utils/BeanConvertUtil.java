package org.gz.common.utils;

/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

/**
 * 
 * @Description:bean对象处理
 * Author	Version		Date		Changes
 * zhuangjianfa 	1.0  		2017年7月1日 	Created
 */
public class BeanConvertUtil {

	/**
	 * 
	 * @Description: 将一个对象属性复制到另一个对象中 
	 * @param source
	 * @param targetClass
	 * @return
	 * @throws
	 * createBy:zhuangjianfa            
	 * createDate:2017年7月3日
	 */
	public static <T> T convertBean(Object source,Class<T> targetClass) {
		T target = BeanUtils.instantiate(targetClass);
		BeanUtils.copyProperties(source, target);
		return target;
	}
	
	/**
	 * 
	 * @Description: 将一个列表的对象复制一份到新列表中 
	 * @param sourceList
	 * @param targetClass
	 * @return
	 * @throws
	 * createBy:zhuangjianfa            
	 * createDate:2017年7月3日
	 */
	public static <T> List<T> convertBeanList(List<?> sourceList,Class<T> targetClass) {
		List<T> list = new ArrayList<T>();
		if(sourceList == null || sourceList.size() == 0) {
			return list;
		}
		for (Object source : sourceList) {
			T target = BeanUtils.instantiate(targetClass);
			BeanUtils.copyProperties(source, target);
			list.add(target);
		}
		return list;
	}
	
}
