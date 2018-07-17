package org.gz.common.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
/**
 * 
 * @Description:TODO	微信签名生成工具类
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年1月12日 	Created
 */
public class WeiXinSignUtils {
	/**
	 * 
	 * @Description: TODO 
	 * @param characterEncoding 编码格式 默认UTF-8
	 * @param parameters 需要生成签名的参数
	 * @param merchantKey 商户key（去微信商户平台获取）
	 * @return 签名
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年1月12日
	 */
	public static String createSign(String characterEncoding,SortedMap<String,Object> parameters,String merchantKey){  
        if(StringUtils.isBlank(characterEncoding)){
        	characterEncoding = "UTF-8";
        }
		StringBuffer sb = new StringBuffer();  
        Set<Entry<String, Object>> es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）  
        Iterator<Entry<String, Object>> it = es.iterator();  
        while(it.hasNext()) {  
            Map.Entry<String, Object> entry = (Map.Entry<String, Object>)it.next();  
            String k = (String)entry.getKey();  
            Object v = entry.getValue();  
            if(null != v && !"".equals(v)   
                    && !"sign".equals(k) && !"key".equals(k)) {  
                sb.append(k + "=" + v + "&");  
            }  
        }  
        sb.append("key=" + merchantKey);  
        return Md5Algorithm.getInstance().md5Digest(sb.toString().getBytes()).toUpperCase();
    }  
	
	public static void main(String[] args) {
		/* 9A0A8659F005D6984697E2CA0A9CF3B7 //注：MD5签名方式  
		     参考  https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=4_3
		*/		
		SortedMap<String, Object> sortMap = new TreeMap<>();
		sortMap.put("appid", "wxd930ea5d5a258f4f");
		sortMap.put("mch_id", "10000100");
		sortMap.put("device_info", "1000");
		sortMap.put("body", "test");
		sortMap.put("nonce_str", "ibuaiVcKdpRxkhJA");
		
		String str = createSign("", sortMap, "192006250b4c09247ec02edce69f6a2d");
		
		System.out.println(str);
		System.out.println(Md5Algorithm.getInstance().md5Digest(str.getBytes()).toUpperCase());
	}
}
