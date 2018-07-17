package org.gz.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class LianPayUtils {
	public static String genSign(Map<String, String> createMap,String RSAKey,String MD5Key) {
        String sign_type = createMap.get("sign_type");
        // // 生成待签名串
        String sign_src = genSignDataNotEmpty(createMap);
        System.out.println(sign_src);
        if ("MD5".equals(sign_type)) {
            sign_src += "&key=" + MD5Key;
            return signMD5(sign_src);
        }
        if ("RSA".equals(sign_type)) {
            return getSignRSA(sign_src,RSAKey);
        }
        return null;
    }
    
    private static String signMD5(String signSrc) {
        try {
            return Md5Algorithm.getInstance().md5Digest(
                    signSrc.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getSignRSA(String sign_src,String RSAKey) {
        return TraderRSAUtil.sign(RSAKey, sign_src);
    }
    
    private static String genSignDataNotEmpty(Map<String, String> createMap) {
        StringBuilder content = new StringBuilder();

        // 按照key做首字母升序排列
        List<String> keys = new ArrayList<>(createMap.keySet());
        Collections.sort(keys, String.CASE_INSENSITIVE_ORDER);
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            // sign 和ip_client 不参与签名
            if ("sign".equals(key)) {
                continue;
            }
            String value = createMap.get(key);
            // 空串不参与签名
            if (null == value || "".equals(value)) {
                continue;
            }
            content.append(i == 0 ? "" : "&").append(key).append("=").append(value);
        }
        String signSrc = content.toString();
        if (signSrc.startsWith("&")) {
            signSrc = signSrc.replaceFirst("&", "");
        }

        return signSrc;
    }
}
