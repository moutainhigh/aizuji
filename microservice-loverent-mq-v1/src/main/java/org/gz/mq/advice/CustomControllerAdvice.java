/**
 * 
 */
package org.gz.mq.advice;

import org.gz.mq.entity.resp.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author hxj
 * @date 2017年11月9日下午4:35:42
 */
@ControllerAdvice(annotations = { RestController.class })
public class CustomControllerAdvice {

	private static Logger logger = LoggerFactory.getLogger(CustomControllerAdvice.class);

	/**
	 * 全局异常捕捉处理
	 * 
	 * @param ex
	 * @return ResponseResult
	 */
	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public ResponseResult errorHandler(Exception ex) {
		logger.error("errorHandler:{}", ex.getLocalizedMessage());
		return ResponseResult.OPT_INVALID_PARAM;
	}
}
