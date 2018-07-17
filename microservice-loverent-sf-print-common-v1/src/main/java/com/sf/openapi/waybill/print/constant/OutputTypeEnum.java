
package com.sf.openapi.waybill.print.constant;

public enum OutputTypeEnum {

	PRINT("print", "打印时弹出对话框"), NO_ARERT_PRINT("noAlertPrint", "打印时不弹出对话框"), IMAGE("image", "不打印，直接返回图片");
	
	private String type;

	private String desc;

	private OutputTypeEnum(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}