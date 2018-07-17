package org.gz.sms.entity;

import java.util.Date;

/**
 * 短信发送记录
 * @author yangdx
 *
 */
public class SmsSendRecord {
	/***/
    private Long smsId;
    /**手机号*/
    private String mobile;
    /**发送内容	*/
    private String content;
    /**发送时间*/
    private Date sendTime;
    /**1-成功 2-失败*/
    private Integer sendStatus;
    /**返回码*/
    private String retCode;
    /**返回描述*/
    private String retDesc;
    /**短信业务编号  SmsType*/
    private Integer serviceCode;
    /**短信发送渠道*/
    private String smsChannel;
    
	public Long getSmsId() {
		return smsId;
	}

	public void setSmsId(Long smsId) {
		this.smsId = smsId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Integer getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetDesc() {
		return retDesc;
	}

	public void setRetDesc(String retDesc) {
		this.retDesc = retDesc;
	}

	public Integer getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(Integer serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getSmsChannel() {
		return smsChannel;
	}

	public void setSmsChannel(String smsChannel) {
		this.smsChannel = smsChannel;
	}
    
}