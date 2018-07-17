package org.gz.warehouse.common.vo;

/**
 * 仓位编码枚举类
 */
public enum WarehouseLocationCodeEnum {
	AVAILABLE("AVAILABLE", "可售"),
	SALES("SALES", "待售"), 
	RESERVE("RESERVE", "冻结"), 
	TRANSIT_OUT("TRANSIT_OUT", "出库在途"), 
	RENTING("RENTING", "在租"), 
	BUY_END("BUY_END", "买断");

	private String code;

	private String name;

	WarehouseLocationCodeEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
