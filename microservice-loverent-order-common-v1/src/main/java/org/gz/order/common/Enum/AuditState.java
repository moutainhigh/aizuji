package org.gz.order.common.Enum;


/**
 * 
 * @Description:TODO
 * Author	Version		Date		Changes
 * liuyt 	1.0  		2018年1月7日 	Created
 */
public enum AuditState {
    AuditPass(2, "审核通过"), AuditRefusal(1, "审核拒绝");

    private Integer code;

    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private AuditState(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
