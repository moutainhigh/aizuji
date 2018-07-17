package org.gz.oss.backend.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.gz.common.entity.AuthUser;
import org.gz.oss.backend.helper.AuthUserHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthFilter extends OncePerRequestFilter {

	private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

	private static final String[] EXCLUDES = { "/auth/login", "/login.html", "/css|js|image|fonts/.*" };

	@Resource
	private AuthUserHelper authUserHelper;

	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
        HttpSession session = request.getSession();
        session.setAttribute("authUser", new AuthUser(1L,"admin"));
        /*
        AuthUser authUser = new AuthUser();
        AuthUser user= authUserHelper.getUser(authUser.getUserName(), authUser.getPassWord());
        if (!shouldNotAuth(request)) {
            if (session.getAttribute("admin") == null) {
                if (request.getHeader("x-requested-with") != null
                    && request.getHeader("x-requested-with").equals("XMLHttpRequest")) {
        		    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);  
        		    PrintWriter out = null;  
        		    try {  
        		        out = response.getWriter();  
        		        String json = JsonUtils.toJsonString(ResponseResult.build(900, "用户会话已过期!", null));
        		        out.write(json);
        		    } catch (IOException e) {  
        		        e.printStackTrace();  
        		    } finally {  
        		        if (out != null) {  
        		            out.close();  
        		        }  
        		    }  
                } else {
                    response.sendRedirect(request.getContextPath() + "/login.html");
                }
                return;
            }
        }
        */
        filterChain.doFilter(request, response);
    }

	private boolean shouldNotAuth(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		Pattern pattern = null;
		for (String regex : EXCLUDES) {
			pattern = Pattern.compile(regex);
			if (pattern.matcher(requestUri).find()) {
				return true;
			}
		}
		return false;
	}

}
