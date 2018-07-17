package org.gz.app.ca.dto;


public class ResultDto {
    /**
     * 错误码
     */
    private int errCode;
    /**
     * 错误信息
     */
    private String msg;
    /**
     * 是否成功关联标识
     */
    private boolean success;
    
    public int getErrCode() {
        return errCode;
    }
    
    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
    
    public String getMsg() {
        return msg;
    }
    
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
}
