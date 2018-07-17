package org.gz.workorder.backend.filter;

import org.gz.common.entity.AuthUser;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.JsonUtils;
import org.gz.order.common.Enum.OrderResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

@Component
public class AuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);


    private static final String[] EXCLUDES = {
           "/auth/login"
    };



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    	HttpSession session = request.getSession(true);
        if(!shouldNotAuth(request)){
            if(session.getAttribute("authUser") == null) {     	
            	write(response,JsonUtils.toJsonString(ResponseResult.build(OrderResultCode.UNAUTHORIZED_ACCESS.getCode(), "unauthorized access", null)));
                return;
            }
        }
        /*AuthUser authUser=new AuthUser();
    	authUser.setUserName("test");
    	authUser.setPassWord("test");
    	session.setAttribute("authUser", authUser);*/
        filterChain.doFilter(request, response);
    }
    
    private void write(HttpServletResponse response, String message) {
      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      PrintWriter out = null;
      try {
          out = response.getWriter();
          out.print(message);
      } catch (IOException e) {
      } finally {
          if (out != null) {
              out.flush();
              out.close();
          }
      }
    }

    private boolean shouldNotAuth(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        Pattern  pattern= null;
        for (String regex : EXCLUDES) {
        	pattern =  Pattern.compile(regex);
            if(pattern.matcher(requestUri).find()) {
                return true;
            }
        }
        return false;
    }

}
