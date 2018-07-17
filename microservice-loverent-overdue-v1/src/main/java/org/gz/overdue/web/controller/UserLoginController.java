package org.gz.overdue.web.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.gz.common.resp.ResponseResult;
import org.gz.common.web.controller.BaseController;
import org.gz.overdue.constants.UserLoginConstants;
import org.gz.overdue.entity.user.User;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月26日 下午8:03:37
 */
@RestController
@RequestMapping("/api/user")
public class UserLoginController extends BaseController {

	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<User> add(@Valid @RequestBody User m, BindingResult bindingResult, HttpSession session) {
		ResponseResult<String> vr = super.getValidatedResult(bindingResult);
		if(vr==null) {
			if(m.getUserName().equals(UserLoginConstants.USER_NAME)&&m.getPassword().equals(UserLoginConstants.USER_PASSWORD)) {
				m.setId(1L);
				session.setAttribute(UserLoginConstants.KEY_SESSION_USER, m);
				return ResponseResult.buildSuccessResponse();
			}
			return ResponseResult.build(1000, "用户名或密码错误", m);
		}
		return ResponseResult.build(1000, vr.getErrMsg(), m);
	}

}
