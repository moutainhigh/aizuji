package org.gz.user.commons;

public enum UserStatus {
	NORMAL(1),DISABLED(2);
	
	private Integer code;
	
	private UserStatus(Integer code) {
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	
}
