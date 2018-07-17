package org.gz.common.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * ip utils
 * 
 * @author yangdx
 *
 */
public class IPUtil {
	public static String getRemoteAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown")) {
			ip = request.getRemoteAddr();
		}
		
		int index = ip.indexOf(",");
		if (index > 0) {
			return ip.substring(0, index);
		}
		return ip;
	}
}
