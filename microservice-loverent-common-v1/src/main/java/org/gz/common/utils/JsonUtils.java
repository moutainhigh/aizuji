/**
 * 
 */
package org.gz.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Title: JSON工具类,底层使用Jackson实现
 * @author hxj
 * @Description: 包含JSON数据的序列化与反序列化
 * @date 2017年12月4日 下午2:57:15
 */
public final class JsonUtils {

	private static final ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * 
	* @Description: 将Java对象序列化JSON字符串
	* @param @param obj
	* @return String
	* @throws
	 */
	public static String toJsonString(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
