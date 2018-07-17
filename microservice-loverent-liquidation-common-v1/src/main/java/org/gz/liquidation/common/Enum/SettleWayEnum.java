package org.gz.liquidation.common.Enum;
/**
 * 
 * @Description:TODO	结清方式枚举
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年2月11日 	Created
 */
public enum SettleWayEnum {
	/**
	 * 租金结清
	 */
	RENT("RENT","租金结清"),
	/**
	 * 归还结清
	 */
	RETURN("RETURN","归还结清"),
	/**
	 * 连连支付
	 */
	BUYOUT("BUYOUT","买断结清");
	
	private String code;
	private String desc;
	
	private SettleWayEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public static String getDesc(String code) {  
        for (SettleWayEnum c : SettleWayEnum.values()) {  
            if (c.getCode().equalsIgnoreCase(code)) {  
                return c.desc;  
            }  
        }  
        return "";  
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
