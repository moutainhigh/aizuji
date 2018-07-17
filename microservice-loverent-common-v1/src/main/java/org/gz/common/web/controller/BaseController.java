/**
 * 
 */
package org.gz.common.web.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.gz.common.constants.ObjectConstants;
import org.gz.common.entity.AuthUser;
import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月4日 下午4:14:17
 */
public class BaseController {
	
	

	public ResponseResult<String> getValidatedResult(BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ResponseResult<String> baseResponse = new ResponseResult<String>();
			List<FieldError> errors = bindingResult.getFieldErrors();
			StringBuilder stringBuilder = new StringBuilder();
			for (FieldError error : errors) {
				stringBuilder.append(error.getDefaultMessage()).append(";");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			baseResponse.setErrCode(ResponseStatus.PARAMETER_VALIDATION.getCode());
			baseResponse.setErrMsg(stringBuilder.toString());
			baseResponse.setData(ObjectConstants.STRING_EMPTY_DATA);
			return baseResponse;
		}
		return null;
	}
	
	public ResponseResult<String> getValidatedResult(Validator validator,Object obj,Class<?>... groups){
		if(validator!=null&&obj!=null&&groups!=null) {
			Set<ConstraintViolation<Object>> errSets = validator.validate(obj, groups);
			if(!errSets.isEmpty()) {
				ResponseResult<String> baseResponse = new ResponseResult<String>();
				StringBuilder stringBuilder = new StringBuilder();
				Iterator<ConstraintViolation<Object>> it = errSets.iterator();
				while(it.hasNext()) {
					stringBuilder.append(it.next().getMessage()).append(";");
				}
				stringBuilder.deleteCharAt(stringBuilder.length() - 1);
				baseResponse.setErrCode(ResponseStatus.PARAMETER_VALIDATION.getCode());
				baseResponse.setErrMsg(stringBuilder.toString());
				baseResponse.setData(ObjectConstants.STRING_EMPTY_DATA);
				return baseResponse;
			}
		}
		return null;
	}
	
	public ResponseResult<String> getValidatedResult(Validator validator,Object obj){
		return getValidatedResult(validator,obj,Default.class);
	}
	
	public Long getAuthUserId(){
		AuthUser obj= getAuthUser();
		return obj!=null?obj.getId():null;
	}
	
	public AuthUser getAuthUser(){
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpSession session = attrs.getRequest().getSession(true);
		Object obj = session.getAttribute("authUser");
		if (null==obj) {
			throw new ServiceException(1000,"无法从会话中获取到用户登陆信息");
		}
		return (AuthUser) obj;
	}
}
