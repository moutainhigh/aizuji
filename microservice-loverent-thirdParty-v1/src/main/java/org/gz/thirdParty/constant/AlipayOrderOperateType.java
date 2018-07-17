package org.gz.thirdParty.constant;
/**
 * 
 * @Description:订单完结类型枚举
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月30日 	Created
 */
public enum AlipayOrderOperateType {
	/**
	 * 取消
	 */
	CANCEL("CANCEL","取消"),
	/**
	 * 分期扣款
	 */
	INSTALLMENT("INSTALLMENT","分期扣款"),
	/**
	 * 完结
	 */
	FINISH("FINISH","完结");
	
	private String code;
	private String desc;
	private AlipayOrderOperateType(String code, String desc) {
		this.code = code;
		this.desc = desc;
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
