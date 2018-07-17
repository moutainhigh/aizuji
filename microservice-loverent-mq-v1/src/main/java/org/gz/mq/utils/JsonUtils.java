/**
 * 
 */
package org.gz.mq.utils;

import java.io.IOException;
import java.util.Map;
import java.util.SortedMap;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 使用Jackson的json工具类
 * @author hxj
 * @date 2017年11月9日下午6:27:42
 */
public final class JsonUtils {

	public static final ObjectMapper mapper = new ObjectMapper();

	public static String toJsonString(Object obj) {
		if (obj != null) {
			try {
				return mapper.writeValueAsString(obj);
			} catch (JsonProcessingException e) {
			}
		}
		return "{}";
	}

	public static JsonNode toTree(String json) {
		JsonNode node = null;
		if (StringUtils.hasText(json)) {
			try {
				node = mapper.readTree(json);
			} catch (IOException e) {
			}
		}
		return node;
	}

	@SuppressWarnings("unchecked")
	public static SortedMap<String, Object> toSortedMap(String json) {
		SortedMap<String, Object> map = null;
		if (StringUtils.hasText(json)) {
			try {
				map = mapper.readValue(json, SortedMap.class);
			} catch (Exception e) {
			}

		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String,Object> toMap(String json){
		Map<String,Object> map = null;
		if(StringUtils.hasText(json)){
			try {
				map = mapper.readValue(json, Map.class);
			} catch (Exception e) {
			}
		}
		return map;
	}
}