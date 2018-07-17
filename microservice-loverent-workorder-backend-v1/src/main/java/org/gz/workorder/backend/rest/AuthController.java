package org.gz.workorder.backend.rest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.gz.common.entity.AuthUser;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.web.controller.BaseController;
import org.gz.workorder.backend.auth.AuthUserHelper;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * 会话授权
 * @author phd
 */
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController extends BaseController {


    @Resource
    private AuthUserHelper authUserHelper;

    /**
     * 登陆
     */
    @PostMapping(value = "/login")
    public ResponseResult<?> login(@Valid @RequestBody AuthUser authUser,
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
				log.error("登陆工单后台失败：{}",e);
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),ResponseStatus.DATABASE_ERROR.getMessage(),null);
			}
		}
    	return validateResult;
    }

    /**
     * 登出
     */
    @GetMapping(value = "/logout")
    public ResponseResult<?> logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return ResponseResult.buildSuccessResponse();
    }


}
