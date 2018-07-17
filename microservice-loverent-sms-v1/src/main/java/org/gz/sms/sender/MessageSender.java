package org.gz.sms.sender;

import java.util.List;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.gz.sms.dto.SmsCaptcheDto;
import org.gz.sms.dto.SmsDto;
import org.gz.sms.supports.RegistrationClassHolder;

/**
 * 
 * @author yangdx
 *
 */
public interface MessageSender {

	ResponseResult<String> sendCaptche(SmsCaptcheDto dto);
	
	boolean typeMatch(String channelType);
	
	public static class Factory {
		
		public static MessageSender getMessageSender(String channelType) {
			List<MessageSender> senders = RegistrationClassHolder.getImplementClazz(MessageSender.class);
			
			if(senders == null || senders.isEmpty()) {
				return null;
			}
			
			for(MessageSender sender : senders) {
				if(sender.typeMatch(channelType)) {
					return sender;
				}
			}
			
			return null;
		}
		
		public static ResponseResult<String> sendCaptche(SmsCaptcheDto dto) {
			String channelType = dto.getChannelType();

			MessageSender sender = getMessageSender(channelType);
			if (sender != null) {
				return sender.sendCaptche(dto);
			} else {
				ResponseResult<String> result = new ResponseResult<>();
				ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SMS_CHANNEL_ERROR);
				return result;
			}
		}

		public static ResponseResult<String> sendStockSignInform(SmsDto dto) {
			String channelType = dto.getChannelType();

			MessageSender sender = getMessageSender(channelType);
			if (sender != null) {
				return sender.sendStockSignInform(dto);
			} else {
				ResponseResult<String> result = new ResponseResult<>();
				ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SMS_CHANNEL_ERROR);
				return result;
			}
		}
	}

	ResponseResult<String> sendStockSignInform(SmsDto dto);
	
}
