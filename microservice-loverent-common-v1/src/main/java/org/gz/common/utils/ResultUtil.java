package org.gz.common.utils;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;

/**
 * 
 * @author yangdx
 *
 */
public class ResultUtil {

	public static void buildResultWithResponseStatus(ResponseResult<?> result, ResponseStatus status) {
		result.setErrCode(status.getCode());
		result.setErrMsg(status.getMessage());
	}
}
