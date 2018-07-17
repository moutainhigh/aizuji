package org.gz.app.ca.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class HmacUtils {

    private static final String HMACSHA256 = "HmacSHA256";

    /**
     * @Description: TODO 使用加密算法：HmacSHA256加密
     * @param key 加密使用的key
     * @param value 待加密的字符串
     * @return
     * @throws createBy:liaoqingji createDate:2017年12月14日
     */
    public static String hmac(String key, String value) {
        try {
            Mac hmacSHA256 = Mac.getInstance("HMACSHA256");
            SecretKey secretKey = new SecretKeySpec(key.getBytes(), HMACSHA256);
            hmacSHA256.init(secretKey);
            byte[] bytes = hmacSHA256.doFinal(value.getBytes());
            return byteArrayToHexString(bytes);
        } catch (NoSuchAlgorithmException e) {
            log.error("加密算法类型异常：{}",e.getMessage());
        } catch (InvalidKeyException e) {
            log.error("加密算法key异常：{}",e.getMessage());
        }
        return null;

    }
    /**
     * 
     * @Description: TODO 字节数组转字符串
     * @param array 字节数组
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月14日
     */
    private static String byteArrayToHexString(byte[] array) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; array != null && n < array.length; n++) {
            stmp = Integer.toHexString(array[n] & 0XFF);
            if (stmp.length() == 1) hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }
    
    public static void main(String[] args) {
        String str = "{\"evid\":\"O932439846922776585\",\"certificates\":[{\"type\":\"ID_CARD\",\"number\":\"220301198711200018\",\"name\":\"张三\"},{\"type\":\"CODE_ORG\",\"number\":\"745830607\",\"name\":\"天之云信息科技有限公司\"},{\"type\":\"CODE_USC\",\"number\":\"03519792JKQJEUCQ71\",\"name\":\"天之云信息科技有限公司\"},{\"type\":\"CODE_REG\",\"number\":\"330108000003512\",\"name\":\"天之云信息科技有限公司\"}]}";
        System.out.println(hmac("123456", str));
        
      
    }
}
