package org.gz.app.web.controller;

import javax.validation.Valid;

import org.gz.app.entity.SysAdmin;
import org.gz.app.service.user.SysAdminService;
import org.gz.common.resp.ResponseResult;
import org.gz.common.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/admin")
public class SysAdminController extends BaseController {

	/*@Autowired*/
	private SysAdminService adminService;

	@PostMapping(value = "/addSysAdmin", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> addSysAdmin(@Valid @RequestBody SysAdmin admin, BindingResult bindingResult) {
		//验证数据 
		ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
		if (validateResult == null) {
			try {
				return this.adminService.addSysAdmin(admin);
			} catch (Exception e) {
				return ResponseResult.build(1000, "", null);
			}
		}
		return validateResult;
	}
}
