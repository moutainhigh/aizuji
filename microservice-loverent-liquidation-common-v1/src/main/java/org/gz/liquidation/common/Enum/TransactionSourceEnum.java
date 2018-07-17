package org.gz.liquidation.common.Enum;

/**
 * 
 * @Description:	交易来源枚举
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月26日 	Created
 */
public enum TransactionSourceEnum {

	H5("H5","H5"),
	APP("APP","APP应用"),
	BACK_END("BACK_END","后台系统"),
	ALI_APPLET("ALI_APPLET","阿里小程序");
	private String code;
	private String desc;
	
	private TransactionSourceEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public static TransactionSourceEnum getEnum(String code) {  
        for (TransactionSourceEnum c : TransactionSourceEnum.values()) {  
            if (c.getCode().equalsIgnoreCase(code)) {  
                return c;  
            }  
        }  
        return null;  
    }
	
	public static String getDesc(String code) {  
        for (TransactionSourceEnum c : TransactionSourceEnum.values()) {  
            if (c.getCode().equalsIgnoreCase(code)) {  
                return c.desc;  
            }  
        }  
        return null;  
    }  
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
