package org.gz.sms.sender;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.StringUtils;
import org.gz.sms.constants.SmsChannelType;
import org.gz.sms.dto.SmsCaptcheDto;
import org.gz.sms.dto.SmsDto;
import org.gz.sms.entity.SmsSendRecord;
import org.gz.sms.mapper.SmsSendRecordMapper;
import org.gz.sms.util.HttpClientUtil;
import org.gz.sms.util.IdGenerator;
import org.gz.sms.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;

import com.alibaba.fastjson.JSONObject;

@Slf4j
public class YunTongXunMessageSender extends AbstractMessageSender {
	
	private static final String CHANNEL_TYPE = SmsChannelType.SMS_CHANNEL_TYPE_Y_T_X;

	@Autowired
	private SmsSendRecordMapper smsSendRecordMapper;
	
	/**
     * 云通讯应用id
     */
    private static final String YUN_TONG_XUN_APP_ID = "8aaf07086077a6e60160915a6ee40b5c";
	
	/**
     * 云通讯主账号ID
     */
    private static final String ACCOUNT_SID = "8aaf07086077a6e60160915a6e8a0b55";
    
    /**
     * 云通讯token
     */
    private static final String AUTH_TOKEN = "81a38a73144e47649b291cf9ec159c54";
    
    /**
     * 云通讯url
     */
    private static final String YUN_TONG_XUN_SMS_SEND_URL= "https://app.cloopen.com:8883";
    
    private static final String COLON = ":";
    
    /**
     * 云通讯接口调用成功
     */
    public final static String YUN_TONG_XUN_SUCCESS_CODE = "000000";

	@Override
	public ResponseResult<String> sendCaptche(SmsCaptcheDto dto) {
		log.info("SmsAtomServiceImpl sendMessageByYunTongXun param: {}",JSONObject.toJSONString(dto));
		ResponseResult<String> result = new ResponseResult<>();
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String time = localDateTime.format(formatter);
        
        String sig = MD5.digest(ACCOUNT_SID + AUTH_TOKEN + time);
        String url = YUN_TONG_XUN_SMS_SEND_URL+"/2013-12-26/Accounts/"+ACCOUNT_SID+"/SMS/TemplateSMS?sig="+sig;
        Map<String, Object> map = new HashMap<String, Object>();
        // 接收手机号 
        map.put("to", dto.getPhone());
        map.put("appId", YUN_TONG_XUN_APP_ID);
        // 测试模板id是215162。测试模板的内容是：【云通讯】您使用的是云通讯短信模板，您的验证码是{1}，请于{2}分钟内正确输入
        map.put("templateId", 229402);
        
        List<String> list = new ArrayList<>();
        list.add(dto.getCaptche());
        map.put("datas", list);
        //  可选参数 第三方自定义消息id，最大支持32位，同账号下24H内不允许重复。重复返回错误码160054=请求重复
        String smsId = IdGenerator.idCreateByTime();
        map.put("reqId", smsId);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", Base64Utils.encodeToString((ACCOUNT_SID + COLON + time).getBytes()));
        headers.put("Accept", "application/json");
        
        String jsonParam = JSONObject.toJSONString(map);
        String str = HttpClientUtil.doPostJson(url, headers,jsonParam);
        log.info("---------- sendMessageByYunTongXun str：{}",str);
        log.info("----------云通讯发送短信结果：{}",JSONObject.parse(str));
        JSONObject jsonObject = JSONObject.parseObject(str);
        // 接口调用成功
        Integer sendStatus = 2;
        String retCode = jsonObject.getString("statusCode");
        if(YUN_TONG_XUN_SUCCESS_CODE.equals(retCode)){
        	try {
    			//添加发送记录
            	SmsSendRecord sendRecord = new SmsSendRecord();
    			sendRecord.setContent(dto.getCaptche());
    			sendRecord.setMobile(dto.getPhone());
    			sendRecord.setRetCode(retCode);
    			sendRecord.setRetDesc("");
    			sendRecord.setSendStatus(sendStatus);
    			sendRecord.setSendTime(new Date());
    			sendRecord.setServiceCode(dto.getSmsType());
    			sendRecord.setSmsChannel(CHANNEL_TYPE);
    			
    			smsSendRecordMapper.insert(sendRecord);
            } catch (Exception e) {
    			log.error("sendCaptche add send record failed, e: {}", e);
    		}
        	
        	sendStatus = 1;
            JSONObject j = jsonObject.getJSONObject("templateSMS");
            String smsMessageSid = j.getString("smsMessageSid");
            // 短信发送流水号
            result.setData(smsMessageSid);
            result.setErrCode(0);
        }else {
            result.setErrCode(-1);
        }
        
		return result;
	}
	
    
    @Override
	public boolean typeMatch(String channelType) {
		if (StringUtils.isEmpty(channelType)) {
			return false;
		}
		
		return CHANNEL_TYPE.equals(channelType);
	}


	@Override
	public ResponseResult<String> sendStockSignInform(SmsDto dto) {
		log.info("SmsAtomServiceImpl sendMessageByYunTongXun param: {}",JSONObject.toJSONString(dto));
		ResponseResult<String> result = new ResponseResult<>();
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String time = localDateTime.format(formatter);
        
        String sig = MD5.digest(ACCOUNT_SID + AUTH_TOKEN + time);
        String url = YUN_TONG_XUN_SMS_SEND_URL+"/2013-12-26/Accounts/"+ACCOUNT_SID+"/SMS/TemplateSMS?sig="+sig;
        Map<String, Object> map = new HashMap<String, Object>();
        // 接收手机号 
        map.put("to", dto.getPhone());
        map.put("appId", YUN_TONG_XUN_APP_ID);
        // 测试模板id是215162。测试模板的内容是：【云通讯】您使用的是云通讯短信模板，您的验证码是{1}，请于{2}分钟内正确输入
        
        if(dto.getTemplateId() != null ){
          map.put("templateId", dto.getTemplateId());
        }else {
          map.put("templateId", 227542);
        }
        
        
        map.put("datas", dto.getDatas());
        //  可选参数 第三方自定义消息id，最大支持32位，同账号下24H内不允许重复。重复返回错误码160054=请求重复
        String smsId = IdGenerator.idCreateByTime();
        map.put("reqId", smsId);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", Base64Utils.encodeToString((ACCOUNT_SID + COLON + time).getBytes()));
        headers.put("Accept", "application/json");
        
        String jsonParam = JSONObject.toJSONString(map);
        String str = HttpClientUtil.doPostJson(url, headers,jsonParam);
        log.info("---------- sendMessageByYunTongXun str：{}",str);
        log.info("----------云通讯发送短信结果：{}",JSONObject.parse(str));
        JSONObject jsonObject = JSONObject.parseObject(str);
        // 接口调用成功
        Integer sendStatus = 2;
        String retCode = jsonObject.getString("statusCode");
        if(YUN_TONG_XUN_SUCCESS_CODE.equals(retCode)){
        	sendStatus = 1;
            JSONObject j = jsonObject.getJSONObject("templateSMS");
            String smsMessageSid = j.getString("smsMessageSid");
            // 短信发送流水号
            result.setData(smsMessageSid);
            result.setErrCode(0);
        }else {
            result.setErrCode(-1);
        }
        
        try {
			//添加发送记录
        	SmsSendRecord sendRecord = new SmsSendRecord();
			sendRecord.setContent("");
			sendRecord.setMobile(dto.getPhone());
			sendRecord.setRetCode(retCode);
			sendRecord.setRetDesc("");
			sendRecord.setSendStatus(sendStatus);
			sendRecord.setSendTime(new Date());
			sendRecord.setServiceCode(dto.getSmsType());
			sendRecord.setSmsChannel(CHANNEL_TYPE);
			
			smsSendRecordMapper.insert(sendRecord);
        } catch (Exception e) {
			log.error("sendCaptche add send record failed, e: {}", e);
		}
		return result;
	}

}
