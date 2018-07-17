package org.gz.app.exception;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

	@ExceptionHandler(value=RuntimeException.class)
	@ResponseBody
	public ResponseResult<String> runtimeExceptionHandler(HttpServletRequest request, Exception e) {
		
		log.error("enter exceptionHandler...e: {}", e);
		
		ResponseResult<String> result = new ResponseResult<>();
		
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.UNKNOW_SYSTEM_ERROR);
	
		return result;
	}
	
}
