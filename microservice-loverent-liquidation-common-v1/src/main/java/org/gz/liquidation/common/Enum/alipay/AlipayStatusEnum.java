package org.gz.liquidation.common.Enum.alipay;
/**
 * 
 * @Description:芝麻信用支付:交易状态枚举
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月31日 	Created
 */
public enum AlipayStatusEnum {
	/**
	 * 支付成功
	 */
	PAY_SUCCESS("PAY_SUCCESS","支付成功"),
	/**
	 * 支付处理中
	 */
	PAY_INPROGRESS("PAY_INPROGRESS","支付失败"),
	/**
	 * 支付失败
	 */
	PAY_FAILED("PAY_FAILED","支付失败");
	
	private String code;
	private String desc;
	
	private AlipayStatusEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public static AlipayStatusEnum getEnum(String code) {  
        for (AlipayStatusEnum c : AlipayStatusEnum.values()) {  
            if (c.getCode().equalsIgnoreCase(code)) {  
                return c;  
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
