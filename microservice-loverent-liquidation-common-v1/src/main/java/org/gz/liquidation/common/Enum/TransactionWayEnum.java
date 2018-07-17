package org.gz.liquidation.common.Enum;
/**
 * 
 * @Description:TODO	交易方式枚举
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月26日 	Created
 */
public enum TransactionWayEnum {
	/**
	 * 支付宝
	 */
	ALIPAY("alipay","支付宝"),
	/**
	 * 微信
	 */
	WECHAT("WeChat","微信"),
	/**
	 * 连连支付
	 */
	LIANLIAN("LianLian","连连支付"),
	/**
	 * 银行卡支付
	 */
	BANKCARD("BankCard","银行卡支付");
	private TransactionWayEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	private String code;
	private String desc;
	
	public static String getDesc(String code) {  
        for (TransactionWayEnum c : TransactionWayEnum.values()) {  
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
