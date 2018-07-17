package org.gz.risk.constant;

/**
 * Created by ${CaoBang} on 2017/7/10.
 */
public enum BaiQiShiType {
    success("认证成功", 0),
    login_code("需要登录验证码", 1),
    twice_code("需要二次验证码", 2),
    failure_code("失败", 3);


    BaiQiShiType(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    private String msg;
    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
