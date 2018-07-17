package org.gz.liquidation.common.Enum;
/**
 * 
 * @Description:TODO	交易类型枚举
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月26日 	Created
 */
public enum TransactionTypeEnum {
	/**
	 * 首期款交易
	 */
	FIRST_RENT("FIRST_RENT","首期款交易"),
	/**
	 * 归还
	 */
	RETURN("RETURN","归还"),
	/**
	 * 买断
	 */
	BUYOUT("BUYOUT","买断"),
	/**
	 * 逾期买断
	 */
	OVERDUE_BUYOUT("OVERDUE_BUYOUT","逾期买断"),
	/**
	 * 未收货违约
	 */
	NOT_RECEIVE_BREACH("NOT_RECEIVE_BREACH","未收货违约"),
	/**
	 * 租金
	 */
	RENT("RENT","租金"),
	/**
	 * 分期扣款
	 */
	INSTALLMENT("INSTALLMENT","分期扣款"),
	/**
	 * 出售
	 */
	SALE("SALE","出售");
	
	private TransactionTypeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	private String code;
	private String desc;
	
	public static TransactionTypeEnum getEnum(String code) {  
        for (TransactionTypeEnum c : TransactionTypeEnum.values()) {  
            if (c.getCode().equalsIgnoreCase(code)) {  
                return c;  
            }  
        }  
        return null;  
    } 
	public static String getDesc(String code) {  
        for (TransactionTypeEnum c : TransactionTypeEnum.values()) {  
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
