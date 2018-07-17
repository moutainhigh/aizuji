package org.gz.user.service.atom.impl;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.gz.cache.commom.constant.CacheTTLConst;
import org.gz.cache.service.captche.CaptcheCacheService;
import org.gz.cache.service.user.UserCacheService;
import org.gz.common.constants.SmsType;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.StringUtils;
import org.gz.sms.constants.SmsChannelType;
import org.gz.sms.constants.SmsSendFrequecyInterval;
import org.gz.sms.dto.SmsCaptcheDto;
import org.gz.sms.service.SmsSendService;
import org.gz.user.entity.AppUser;
import org.gz.user.exception.UserServiceException;
import org.gz.user.mapper.AppUserMapper;
import org.gz.user.service.atom.UserSmsCaptcheAtomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

@Service
@Slf4j
public class UserSmsCaptcheAtomServiceImpl implements UserSmsCaptcheAtomService {

	@Autowired
	private SmsSendService smsSendService;
	
	@Autowired
	private CaptcheCacheService captcheCacheService;
	
	@Autowired
	private UserCacheService userCacheService;
	
	@Autowired
	private AppUserMapper appUserMapper;
	
	@Override
	public void sendCaptche(JSONObject body) {
		String smsType = body.getString("smsType");
		String phoneNum = body.getString("phoneNum");
		SmsType e = SmsType.getSmsTypeByType(Integer.valueOf(smsType));
		if (e == null) {
			return;
		}
		
		switch (e) {
			case LOGIN:
				sendLoginCaptcha(phoneNum);
				break;
	
			case REGISTER:
				sendRegisterCaptcha(phoneNum);
				break;
				
			case MODIFY_PWD:
				sendModifyPwdCaptcha(phoneNum);
				break;	
			default:
				sendCaptchaOnlyChechFrequncy(phoneNum);
		}
	}

	/**
	 * 发送短信
	 * @param phoneNum
	 */
	private void sendCaptchaOnlyChechFrequncy(String phoneNum) {
		SmsType smsType = SmsType.THIRD_LOGIN;
		
		//检查短信发送频率
		checkSmsSendFrequency(phoneNum, smsType);
		
		//检查手机号码格式
		checkTelFormatIsValid(phoneNum);
		
		String captcha = StringUtils.getRandCaptchaValue(4);

		SmsCaptcheDto dto = new SmsCaptcheDto();
		dto.setCaptche(captcha);
		dto.setChannelType(SmsChannelType.SMS_CHANNEL_TYPE_Y_T_X);
		dto.setPhone(phoneNum);
		dto.setSmsType(smsType.getType());
		ResponseResult<String> smsRst = smsSendService.sendSmsCaptche(dto);
		
		if (smsRst.getErrCode() == 0) {
			captcheCacheService.setTelCaptcha(phoneNum, captcha, 
					smsType, 
					CacheTTLConst.MODIFY_PWD_SMS_CAPTCHE_TTL);
		} else {
			throw new UserServiceException(ResponseStatus.SMS_SEND_FAILED.getCode(), 
					ResponseStatus.SMS_SEND_FAILED.getMessage());
		}
	}

	/**
	 * 修改密码验证码
	 * @param body
	 */
	private void sendModifyPwdCaptcha(String phoneNum) {
		SmsType smsType = SmsType.MODIFY_PWD;
		
		//检查短信发送频率
		checkSmsSendFrequency(phoneNum, smsType);
		
		//检查手机号码格式
		checkTelFormatIsValid(phoneNum);
		
		//检查手机号是否注册
		if (!checkTelIsRegisted(phoneNum)) {
			log.error("send captche failed, tel is not registed, tel:{}", phoneNum);
			throw new UserServiceException(ResponseStatus.TEL_NOT_REGISTED.getCode(),
					ResponseStatus.TEL_NOT_REGISTED.getMessage());
		}
		
		String captcha = StringUtils.getRandCaptchaValue(4);

		SmsCaptcheDto dto = new SmsCaptcheDto();
		dto.setCaptche(captcha);
		dto.setChannelType(SmsChannelType.SMS_CHANNEL_TYPE_Y_T_X);
		dto.setPhone(phoneNum);
		dto.setSmsType(smsType.getType());
		ResponseResult<String> smsRst = smsSendService.sendSmsCaptche(dto);
		
		if (smsRst.getErrCode() == 0) {
			captcheCacheService.setTelCaptcha(phoneNum, captcha, 
					smsType, 
					CacheTTLConst.MODIFY_PWD_SMS_CAPTCHE_TTL);
		} else {
			throw new UserServiceException(ResponseStatus.SMS_SEND_FAILED.getCode(), 
					ResponseStatus.SMS_SEND_FAILED.getMessage());
		}
	}

	/**
	 * 注册验证码
	 * @param body
	 */
	private void sendRegisterCaptcha(String phoneNum) {
		SmsType smsType = SmsType.REGISTER;
		
		//检查短信发送频率
		checkSmsSendFrequency(phoneNum, smsType);
		
		//检查手机号码格式
		checkTelFormatIsValid(phoneNum);
		
		//检查手机号是否注册
		if (checkTelIsRegisted(phoneNum)) {
			log.error("send register captcha failed, tel is not registed, tel:{}", phoneNum);
			throw new UserServiceException(ResponseStatus.TEL_REGISTED.getCode(),
					ResponseStatus.TEL_REGISTED.getMessage());
		}
		
		String captcha = StringUtils.getRandCaptchaValue(4);

		SmsCaptcheDto dto = new SmsCaptcheDto();
		dto.setCaptche(captcha);
		dto.setChannelType(SmsChannelType.SMS_CHANNEL_TYPE_Y_T_X);
		dto.setPhone(phoneNum);
		dto.setSmsType(smsType.getType());
		ResponseResult<String> smsRst = smsSendService.sendSmsCaptche(dto);
		
		if (smsRst.getErrCode() == 0) {
			captcheCacheService.setTelCaptcha(phoneNum, captcha, 
					smsType, 
					CacheTTLConst.MODIFY_PWD_SMS_CAPTCHE_TTL);
		} else {
			throw new UserServiceException(ResponseStatus.SMS_SEND_FAILED.getCode(), 
					ResponseStatus.SMS_SEND_FAILED.getMessage());
		}
	}

	/**
	 * 登录验证码
	 * @param body
	 */
	private void sendLoginCaptcha(String phoneNum) {
		SmsType smsType = SmsType.LOGIN;
		
		//检查手机号码格式
		checkTelFormatIsValid(phoneNum);
				
		//检查短信发送频率
		checkSmsSendFrequency(phoneNum, smsType);
		
		//检查手机号是否注册
		if (!checkTelIsRegisted(phoneNum)) {
			log.error("send captche failed, tel is not registed, tel:{}", phoneNum);
			throw new UserServiceException(ResponseStatus.TEL_NOT_REGISTED.getCode(),
					ResponseStatus.TEL_NOT_REGISTED.getMessage());
		}
		
		String captcha = StringUtils.getRandCaptchaValue(4);

		SmsCaptcheDto dto = new SmsCaptcheDto();
		dto.setCaptche(captcha);
		dto.setChannelType(SmsChannelType.SMS_CHANNEL_TYPE_Y_T_X);
		dto.setPhone(phoneNum);
		dto.setSmsType(smsType.getType());
		ResponseResult<String> smsRst = smsSendService.sendSmsCaptche(dto);
		
		if (smsRst.getErrCode() == 0) {
			captcheCacheService.setTelCaptcha(phoneNum, captcha, 
					smsType, 
					CacheTTLConst.MODIFY_PWD_SMS_CAPTCHE_TTL);
		} else {
			throw new UserServiceException(ResponseStatus.SMS_SEND_FAILED.getCode(), 
					ResponseStatus.SMS_SEND_FAILED.getMessage());
		}
	}
	
	/**
	 * 检查短信发送频率
	 * @param phoneNum
	 * @param smsType
	 */
	private void checkSmsSendFrequency(String phoneNum, SmsType smsType) {
		String snumber = captcheCacheService.getSmsCaptchaSendNumber(phoneNum, smsType);
		if (snumber != null) {
			Integer num = Integer.valueOf(snumber);
			if (num > SmsSendFrequecyInterval.CAPTCHA_SEND_NUMBER) {
				throw new UserServiceException(ResponseStatus.SMS_SEND_LIMITED.getCode(), 
						ResponseStatus.SMS_SEND_LIMITED.getMessage());
			}
			
			String stime = captcheCacheService.getLastSendTime(phoneNum, smsType);
			if (!StringUtils.isEmpty(stime)) {
				Long lastSendTime = Long.valueOf(stime);
				Long now = new Date().getTime();
				
				if (now - lastSendTime < SmsSendFrequecyInterval.CAPTCHA_SEND_INTERVAL) {
					throw new UserServiceException(ResponseStatus.SMS_SEND_FREQUENTLY.getCode(), 
							ResponseStatus.SMS_SEND_FREQUENTLY.getMessage());
				}
			}
		}
		
	}
	
	/**
	 * 检查手机号格式
	 * @param phoneNum
	 * @return
	 */
	private void checkTelFormatIsValid(String phoneNum) {
		if (!StringUtils.checkTel(phoneNum)) {
			log.error("register phoneNum is invild,tel:{}", phoneNum);
			throw new UserServiceException(ResponseStatus.TEL_FORMAT_ERROR.getCode(),
					ResponseStatus.TEL_FORMAT_ERROR.getMessage());
		}
	}
	
	/**
	 * 检查手机号是否已注册
	 * @param phoneNum
	 */
	private boolean checkTelIsRegisted(String phoneNum) {
//		return userCacheService.checkPhoneIsRegisted(phoneNum);
		AppUser user = appUserMapper.findUserByTel(phoneNum);
		return user != null;
	}

}
