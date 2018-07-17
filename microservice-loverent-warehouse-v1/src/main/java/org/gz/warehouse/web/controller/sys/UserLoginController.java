/**
 * 
 */
package org.gz.warehouse.web.controller.sys;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.gz.common.resp.ResponseResult;
import org.gz.common.web.controller.BaseController;
import org.gz.warehouse.constants.UserLoginConstants;
import org.gz.warehouse.entity.sys.SysAdmin;
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
@RequestMapping("/user")
public class UserLoginController extends BaseController {

	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<SysAdmin> login(@Valid @RequestBody SysAdmin m, BindingResult bindingResult, HttpSession session) {
		ResponseResult<String> vr = super.getValidatedResult(bindingResult);
		if(vr==null) {
			if(m.getLoginName().equals(UserLoginConstants.DEF_LOGIN_NAME)&&m.getLoginPasswd().equals(UserLoginConstants.DEF_LOGIN_PASSWD)) {
				m.setId(1);
				session.setAttribute(UserLoginConstants.KEY_SESSION_USER, m);
				return ResponseResult.buildSuccessResponse();
			}
			return ResponseResult.build(1000, "用户名或密码错误", m);
		}
		return ResponseResult.build(1000, vr.getErrMsg(), m);
	}

	@PostMapping(value = "/loginOut", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<SysAdmin> loginOut(HttpSession session) {
		if(session!=null) {
			session.invalidate();
			return ResponseResult.buildSuccessResponse();
		}
		return ResponseResult.build(1000,"删除会话失败", null);
	}

}
