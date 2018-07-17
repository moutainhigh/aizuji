/**
 * 
 */
package org.gz.warehouse.constants;

/**
 * @Title: 调整类型枚举
 * @author hxj
 * @Description:
 * @date 2018年1月10日 上午9:27:11
 */
public enum AdjustTypeEnum {
	TYPE_PURCHASE(1, "采购入库"), 
	TYPE_SALE(2,"销售"), 
	TYPE_MANUAL(3, "手动调整");

	private int code;

	private String desc;

	AdjustTypeEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

}
