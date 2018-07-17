package org.gz.sms.service;


import org.gz.common.resp.ResponseResult;
import org.gz.sms.dto.SmsCaptcheDto;
import org.gz.sms.dto.SmsDto;
import org.gz.sms.hystrix.SmsSendServiceHystrixImpl;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="microservice-loverent-sms-v1", fallback=SmsSendServiceHystrixImpl.class)
public interface SmsSendService {
	
	/**
	 * 发送短信验证码
	 * @param dto
	 * @return
	 */
	@RequestMapping("/sms/sendSmsCaptche")
    public ResponseResult<String> sendSmsCaptche(@RequestBody SmsCaptcheDto dto);

	/**
	 * 有货签约通知
	 * @param dto
	 * @return
	 */
	@RequestMapping("/sms/sendSmsStockSignInform")
	public ResponseResult<String> sendSmsStockSignInform(@RequestBody SmsDto dto);
    
}
