package org.gz.liquidation.common.Enum;
/**
 * 
 * @Description:TODO	来源类别枚举
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月26日 	Created
 */
public enum SourceTypeEnum {

	SALES_ORDER("SALES_ORDER","销售订单"),
	SALES_RETURN("SALES_RETURN","销售退回"),
	PURCHASE_ORDER("PURCHASE_ORDER","采购订单"),
	PURCHASE_RETURN("PURCHASE_RETURN","采购退回");
	private SourceTypeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	private String code;
	private String desc;
	
	public static String getDesc(String code) {  
        for (SourceTypeEnum c : SourceTypeEnum.values()) {  
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
