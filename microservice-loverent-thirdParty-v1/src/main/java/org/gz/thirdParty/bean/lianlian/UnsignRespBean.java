package org.gz.thirdParty.bean.lianlian;

/**
 * 用户解约返回
 *
 * @author hening
 */
public class UnsignRespBean extends TranBaseBean {

    private static final long serialVersionUID = 1L;
    private String ret_code;       
    private String ret_msg;       
    private String sign_type;       
    private String sign;
	public String getRet_code() {
		return ret_code;
	}
	public void setRet_code(String ret_code) {
		this.ret_code = ret_code;
	}
	public String getRet_msg() {
		return ret_msg;
	}
	public void setRet_msg(String ret_msg) {
		this.ret_msg = ret_msg;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}       
}
