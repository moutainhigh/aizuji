package org.gz.oss.common.Enum;


public enum ImageResultCode {

    IMG_SUFFIX_ERROR(40003, "图片格式错误"),
    /**
     * 图片上传失败
     */
    IMG_UPLOAD_FAIL(40004, "图片 上传失败");
    
    /**
     * 编码
     */
    private int code;
    
    /**
     * 消息
     */
    private String message;
    
    private ImageResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public int getCode() {   
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public String getMessage(Object ...args) {
        return String.format(message,args);
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
