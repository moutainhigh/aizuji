package org.gz.app.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.gz.cache.service.user.UserCacheService;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.gz.common.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;


/**
 * 登录校验拦截器
 * @author dell
 *
 */
@Slf4j
public class LoginInterceptor extends HandlerInterceptorAdapter
{

	@Autowired
	private UserCacheService userCacheService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		String contextPath = request.getContextPath();
		String uri = request.getRequestURI();

		/*log.info("contextPath: {}", contextPath);*/
		log.info("uri: {}", uri);
		
		String token = SessionUtil.getToken(request);
		if (!userCacheService.tokenExist(token)) {
			buildResponseBody(response);
			return false;
		}
		return true;
	}

	private void buildResponseBody(HttpServletResponse response) {
		ResponseResult<String> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.LOGIN_FORBIDDEN);
		try {
			response.getWriter().println(JSONObject.toJSONString(result));
		} catch (Exception e) {
		}
	}

}
