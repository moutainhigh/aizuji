package org.gz.sms.service;

import org.gz.common.resp.ResponseResult;
import org.gz.sms.dto.SmsCaptcheDto;
import org.gz.sms.dto.SmsDto;
import org.gz.sms.sender.MessageSender;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsSendServiceImpl implements SmsSendService {
	
	@Override
	public ResponseResult<String> sendSmsCaptche(@RequestBody SmsCaptcheDto dto) {
		return MessageSender.Factory.sendCaptche(dto);
	}

	@Override
	public ResponseResult<String> sendSmsStockSignInform(@RequestBody SmsDto dto) {
		return MessageSender.Factory.sendStockSignInform(dto);
	}
	
	
}
