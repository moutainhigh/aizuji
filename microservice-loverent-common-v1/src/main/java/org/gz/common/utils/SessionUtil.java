package org.gz.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author yangdx
 *
 */
public class SessionUtil {

	public static final String TOKEN = "AZJTK";
	public final static int DEFAULT_EXPIRE_TIME = -1;
	
	public static void addCookie(String token, HttpServletResponse response) {
		Cookie cookie = new Cookie(TOKEN, token);
		cookie.setMaxAge(DEFAULT_EXPIRE_TIME);
		response.addCookie(cookie);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	public static String getCookieValue(HttpServletRequest request, String cookieName) {
		String token = null;
		Cookie[] cookies = request.getCookies();
		if(cookies==null) return null;
		for (Cookie cookie : cookies) {
			if(cookieName.equalsIgnoreCase(cookie.getName())){
				token=cookie.getValue();
				break;
			}
		}
		return token;
	}

	public static boolean notInclude(HttpServletRequest request, String token) {
		Cookie[] cookies = request.getCookies();
		if(cookies==null) return true;
		for (Cookie cookie : cookies) {
			if(TOKEN.equalsIgnoreCase(cookie.getName())){
				String extoken=cookie.getValue();
				if(token.equals(extoken)) return false;
				break;
			}
		}
		return true;
	}

	public static String getToken(HttpServletRequest request) {
		String token = request.getHeader(SessionUtil.TOKEN);
		if(StringUtils.isEmpty(token)) {
			Cookie[] cookies = request.getCookies();
			if(cookies != null) {
				for (Cookie cookie : cookies) {
					if(SessionUtil.TOKEN.equalsIgnoreCase(cookie.getName())){
						token=cookie.getValue();
					}
				}
			}
		}
		return token;
	}
}
