
package com.sf.openapi.waybill.print.constant;

public enum PayMethodConstant {

	PREPAY("寄付", 1), COLLECT_PAY("到付", 2), THIRD_PAY("第三方付", 3);

	private String name;

	private int value;

	private PayMethodConstant(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public static String getName(int value) {
		if (value == 1)
			return PREPAY.getName();
		if (value == 2)
			return COLLECT_PAY.getName();
		if (value == 3) {
			return THIRD_PAY.getName();
		}
		return "";
	}
}