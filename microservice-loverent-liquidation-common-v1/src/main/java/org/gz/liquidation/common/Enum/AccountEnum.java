package org.gz.liquidation.common.Enum;
/**
 * 
 * @Description:TODO	科目枚举
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月22日 	Created
 */
public enum AccountEnum {
	/**
	 * 首期款
	 */
	SQK("SQK","首期款"),
	/**
	 * 滞纳金
	 */
	ZNJ("ZNJ","滞纳金"),
	/**
	 * 租金
	 */
	ZJ("ZJ","租金"),
	/**
	 * 保证金
	 */
	BZJ("BZJ","保证金"),
	/**
	 * 溢价金
	 */
	YJJ("YJJ","溢价金"),
	/**
	 * 买断金
	 */
	MDJ("MDJ","买断金"),
	/**
	 * 折旧违约金
	 */
	ZJWYJ("ZJWYJ","折旧违约金"),
	/**
	 * 归还金
	 */
	GHJ("GHJ","归还金"),
	/**
	 * 滞纳金减免
	 */
	ZNJJM("ZNJJM","滞纳金减免"),
	/**
	 * 销售金
	 */
	XSJ("XSJ","销售金"),
	/**
	 * 优惠券
	 */
	YHQ("YHQ","优惠券");
	
	private String accountCode;
	private String accountDesc;
	
	private AccountEnum(String accountCode, String accountDesc) {
		this.accountCode = accountCode;
		this.accountDesc = accountDesc;
	}
	
	public static AccountEnum getAccountEnum(String code) {  
        for (AccountEnum c : AccountEnum.values()) {  
            if (c.getAccountCode().equalsIgnoreCase(code)) {  
                return c;  
            }  
        }  
        return null;  
    } 
	
	public static String getAccountDesc(String code) {  
		for (AccountEnum c : AccountEnum.values()) {  
			if (c.getAccountCode().equalsIgnoreCase(code)) {  
				return c.accountDesc;  
			}  
		}  
		return null;  
	} 
	
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public String getAccountDesc() {
		return accountDesc;
	}
	public void setAccountDesc(String accountDesc) {
		this.accountDesc = accountDesc;
	}
	
}
