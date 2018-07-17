package org.gz.sms.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 短信
 * @author phd
 */
public class SmsDto implements Serializable{
	
    private static final long serialVersionUID = -4880766197616080448L;
    
    private String phone;
    
    /**短信业务类型*/
    private Integer smsType;
    
    /**短信渠道*/
    private String channelType;
    
    /**
   * 短信模板id
   */
  private Integer templateId;
  /**
   * 参数
   */
    private List<String> datas=new ArrayList<>();
    
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
	public List<String> getDatas() {
		return datas;
	}
	public void setDatas(List<String> datas) {
		this.datas = datas;
	}

  public Integer getTemplateId() {
    return templateId;
  }

  public void setTemplateId(Integer templateId) {
    this.templateId = templateId;
  }

	
}
