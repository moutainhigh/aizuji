package org.gz.user.hystrix;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.gz.user.dto.ContactInfoQueryDto;
import org.gz.user.service.ContactInfoService;
import org.springframework.stereotype.Component;

/**
 * hystrix impl
 * 
 * @author yangdx
 *
 */
@Component
public class ContactInfoServiceHystrixImpl implements ContactInfoService {

	@Override
	public ResponseResult<ContactInfoQueryDto> queryContactInfoByPage(
			ContactInfoQueryDto queryDto) {
		ResponseResult<ContactInfoQueryDto> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}
}
