
package com.sf.openapi.waybill.print.constant;

public enum TemplateTypeEnum {

	TEMPLATE_100MM_150MM("poster_100mm150mm", "支持两联、三联，尺寸为100mm*150mm"), 
	TEMPLATE_100MM_210MM("V3_poster_100mm210mm", "支持两联、三联，尺寸为100mm*210mm"),
	TEMPLATE_SIGN_CONFIRM("sign_confirm", "签收确认单")
	;
	
	private String type;

	private String desc;

	private TemplateTypeEnum(String type, String desc) {
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