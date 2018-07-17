package org.gz.sms.hystrix;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.gz.sms.dto.SmsCaptcheDto;
import org.gz.sms.dto.SmsDto;
import org.gz.sms.service.SmsSendService;
import org.springframework.stereotype.Component;

@Component
public class SmsSendServiceHystrixImpl implements SmsSendService {

	@Override
	public ResponseResult<String> sendSmsCaptche(SmsCaptcheDto dto) {
		ResponseResult<String> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<String> sendSmsStockSignInform(SmsDto dto) {
		ResponseResult<String> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

}
