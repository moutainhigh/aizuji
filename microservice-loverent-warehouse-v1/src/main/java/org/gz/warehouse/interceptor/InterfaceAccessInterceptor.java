/**
 * 
 */
package org.gz.warehouse.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Title: 外部系统接口访问拦截器
 * @author hxj
 * @Description:
 * @date 2018年1月8日 下午12:45:53
 */
public class InterfaceAccessInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		String requestURI = request.getRequestURI();
		String 	contentType = request.getContentType();
		System.err.println("InterfaceAccessInterceptor URI:"+requestURI+",contentType="+contentType);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView view)
			throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception ex)
			throws Exception {
	}
}
