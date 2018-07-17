package org.gz.warehouse.common.vo;

/**
 * 仓库编码枚举类
 */
public enum OrderSourceEnum {

	APP(1, "APP&HTML5"), Applets(2, "小程序");

	private int code;

	private String name;

	OrderSourceEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
