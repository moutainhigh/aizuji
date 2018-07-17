package org.gz.sms.dto;

import java.io.Serializable;
/**
 * 短信验证码
 * @author yangdx
 *
 */
public class SmsCaptcheDto implements Serializable{
	
    private static final long serialVersionUID = -4880766197616080448L;
    
    private String phone;
    
    /**短信业务类型*/
    private Integer smsType;
    
    /**短信渠道*/
    private String channelType;
    
    /**短信验证码*/
    private String captche;
    
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getSmsType() {
		return smsType;
	}
	public void setSmsType(Integer smsType) {
		this.smsType = smsType;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public String getCaptche() {
		return captche;
	}
	public void setCaptche(String captche) {
		this.captche = captche;
	}
	
}
