package org.gz.thirdParty.constant;
/**
 * 
 * @Description:支付宝交易状态枚举
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月28日 	Created
 */
public enum AlipayTradeStatusEnum {
	/**
	 * 交易创建，等待买家付款
	 */
	WAIT_BUYER_PAY("WAIT_BUYER_PAY","交易创建，等待买家付款"),
	/**
	 * 交易支付成功
	 */
	TRADE_SUCCESS("TRADE_SUCCESS","交易支付成功"),
	/**
	 * 未付款交易超时关闭，或支付完成后全额退款
	 */
	TRADE_CLOSED("TRADE_CLOSED","未付款交易超时关闭，或支付完成后全额退款"),
	/**
	 * 交易结束，不可退款
	 */
	TRADE_FINISHED("TRADE_FINISHED","交易结束，不可退款");
	
	private String code;
	private String desc;
	
	private AlipayTradeStatusEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public static AlipayTradeStatusEnum getEnum(String code) {  
        for (AlipayTradeStatusEnum c : AlipayTradeStatusEnum.values()) {  
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
