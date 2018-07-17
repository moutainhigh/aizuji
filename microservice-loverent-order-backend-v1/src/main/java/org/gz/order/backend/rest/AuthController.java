package org.gz.order.backend.rest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.gz.common.entity.AuthUser;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.web.controller.BaseController;
import org.gz.order.backend.auth.AuthUserHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会话授权
 * @author phd
 */
@RestController
@RequestMapping("/auth")
public class AuthController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Resource
    private AuthUserHelper authUserHelper;

    /**
     * 登陆
     */
    @PostMapping(value = "/login")
    public ResponseResult<?> login(@Valid AuthUser authUser,
        BindingResult bindingResult,HttpServletRequest request) {
    	ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
    	if (validateResult==null) {
			try {
				AuthUser user= authUserHelper.getUser(authUser.getUserName(), authUser.getPassWord());
				if (user!=null) {
					request.getSession().setAttribute("authUser", user);
					return ResponseResult.buildSuccessResponse();
				}
				return ResponseResult.build(ResponseStatus.LOGIN_FAILED.getCode(), ResponseStatus.LOGIN_FAILED.getMessage(), null);
			} catch (Exception e) {
				logger.error("登陆订单后台失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),ResponseStatus.DATABASE_ERROR.getMessage(),null);
			}
		}
    	return validateResult;
    }

    /**
     * 登出
     */
    @PostMapping(value = "/logout")
    public ResponseResult<?> logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return ResponseResult.buildSuccessResponse();
    }


}
