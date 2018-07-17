/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.risk.dao.util;

import com.alibaba.fastjson.JSON;
import com.google.gson.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * 
 * @Description:JSON类型处理类
 * Author	Version		Date		Changes
 * zhuangjianfa 	1.0  		2017年7月1日 	Created
 */
public class JsonUtil {

    private static final Logger logger = Logger.getLogger(JsonUtil.class.getName());

	public static Gson getGson() {
		return new Gson();
	}

	
	
	
	/**
	 * 
	 * @Description: 将对象转为JSON字符串(忽略NULL值) 
	 * @param src
	 * @return
	 * @throws
	 * createBy:zhuangjianfa            
	 * createDate:2017年7月3日
	 */
	public static String toJson(Object src) {
		if(src == null){
			return null;
		}
		if(src instanceof String){
			return (String)src;
		}
		return getGson().toJson(src);
	}

	/**
	 * 
	 * @Description: 将对象转为JSON字符串(不忽略NULL值) 
	 * @param src
	 * @param serializeNulls
	 * @return
	 * @throws
	 * createBy:zhuangjianfa            
	 * createDate:2017年7月3日
	 */
	public static String toJson(Object src, boolean serializeNulls) {
		if (serializeNulls)
			return new GsonBuilder().serializeNulls().create().toJson(src);
		return toJson(src);
	}

	/**
	 * 
	 * @Description: 将JSON字符串转为对象 
	 * @param json
	 * @param classOfT
	 * @return
	 * @throws JsonSyntaxException
	 * @throws
	 * createBy:zhuangjianfa            
	 * createDate:2017年7月3日
	 */
	public static <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
		return getGson().fromJson(json, classOfT);
	}

	/**
     * 
     * @Description: 将JSON字符串转为列表对象 
     * @param json
     * @param classOfT
     * @return
     * @throws JsonSyntaxException
     * @throws
     * createBy:zhuangjianfa            
     * createDate:2017年7月3日
     */
    public static <T> List<T> fromTResultJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        List<T> list = new ArrayList<T>();
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(json).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("data");
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonElement el = jsonArray.get(i);
            T object = null;
            try {
            	object = fromJson(el.toString(),classOfT);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
            list.add(object);
        }
        logger.info("响应：{}"+JsonUtil.toJson(list));
        return list;
    }


    /**
     * 
     * @Description: 将JSON格式的字符串转换为List<T>类型的对象 
     * @param json
     * @param clz
     * @return
     * @throws
     * createBy:zhuangjianfa            
     * createDate:2017年7月3日
     */
	public static <T> List<T> deserializeList(String json, Class<T> clz) {
		String jsonStr = "";
		if (!(null == json || json.trim().equals(""))) {
			if (!json.startsWith("[")) {
				jsonStr = "[" + json + "]";
			} else {
				jsonStr = json;
			}
			return JSON.parseArray(jsonStr, clz);
		} else {
			return new ArrayList<T>();
		}
	}

	/**
	 * 
	 * @Description: 从请求体中读取客户端发送的JSON串 
	 * @param stream
	 * @return
	 * @throws
	 * createBy:zhuangjianfa            
	 * createDate:2017年7月3日
	 */
	public static String readStringFromRequestBody(InputStream stream) {
		if(stream == null){
			return null;
		}
		StringBuffer sb = new StringBuffer();
		char[] buf = new char[2048];
		int len = -1;
		try {
			InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
			while ((len = reader.read(buf)) != -1) {
				sb.append(new String(buf, 0, len));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
				}
			}
		}
		return sb.toString();
	}
}
