package org.gz.order.common.Enum;

/**
 * 前端订单状态
 * @author phd
 */
public enum FrontOrderState {
	        
	Process(1,"进行中"),
	Complete(2,"已完成"),
	Cancel(3,"已取消");
	
	private int code;
    private String message;
    
    FrontOrderState(int code, String message) {
        this.code = code;
        this.message = message;
    }
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public static FrontRentState getByCode(Integer code) {
        if (code != null) {
            for (FrontRentState objEnum : FrontRentState.values()) {
                if (objEnum.getCode()==code.intValue()) {
                    return objEnum;
                }
            }
        }
        return null;
    }
		
}
