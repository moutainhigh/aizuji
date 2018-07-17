package org.gz.warehouse.common.vo;

/**
 * 仓库编码枚举类
 */
public enum WarehouseCodeEnum {

	NEW("S011", "新机库"), 
	OLD("S012", "二手机库");

	private String code;

	private String name;

	WarehouseCodeEnum(String code, String name) {
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
