/**
 * 
 */
package com.sf.openapi.waybill.print.util;

import org.apache.commons.codec.binary.Base64;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2018年3月15日 下午3:11:03
 */
public class Base64Utils {

	public static String encode(byte[] bytes) {
		return new String(Base64.encodeBase64(bytes));
	}

	public static byte[] decode(String str) {
		return Base64.decodeBase64(str.getBytes());
	}

	public static void main(String[] args) {
		String s = "hello";
		String encodeStr = encode(s.getBytes());		
		String decodeStr = new String(decode(encodeStr));
		System.err.println(s.equals(decodeStr));
	}
}
