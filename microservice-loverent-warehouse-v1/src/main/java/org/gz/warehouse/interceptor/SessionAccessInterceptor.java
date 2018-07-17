/**
 * 
 */
package org.gz.warehouse.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.JsonUtils;
import org.gz.warehouse.constants.UserLoginConstants;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Title: 后台系统会话访问拦截器
 * @author hxj
 * @Description:
 * @date 2018年1月8日 上午9:52:44
 */
public class SessionAccessInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		String requestURI = request.getRequestURI();
		String 	contentType = request.getContentType();
		System.err.println("SessionAccessInterceptor requestURI:"+requestURI+",contentType="+contentType);
		HttpSession session = request.getSession();
		Object sessionObj = session.getAttribute(UserLoginConstants.KEY_SESSION_USER);
		/**
		if(sessionObj==null) {
			response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=utf-8");  
		    PrintWriter out = null;  
		    try {  
		        out = response.getWriter();  
		        String json = JsonUtils.toJsonString(ResponseResult.build(ResponseStatus.SESSION_ERROR.getCode(), ResponseStatus.SESSION_ERROR.getMessage(), null));
		        out.write(json);
		    } catch (IOException e) {  
		        e.printStackTrace();  
		    } finally {  
		        if (out != null) {  
		            out.close();  
		        }  
		    }  
		    return false;
		}
		**/
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
