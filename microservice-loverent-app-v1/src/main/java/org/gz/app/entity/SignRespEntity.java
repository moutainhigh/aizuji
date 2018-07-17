package org.gz.app.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 银行卡签约类
 *
 * @author duzl
 */
public class SignRespEntity {

    private static final long serialVersionUID = 1L;
    
    private String ret_code;
    private String ret_msg;
    private String sign_type;
    private String sign;
    private String result_sign;
    private String oid_partner;
    private String user_id;
    
    @JsonProperty("agreeno")
    private String no_agree;
    
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
	public String getResult_sign() {
		return result_sign;
	}
	public void setResult_sign(String result_sign) {
		this.result_sign = result_sign;
	}
	public String getOid_partner() {
		return oid_partner;
	}
	public void setOid_partner(String oid_partner) {
		this.oid_partner = oid_partner;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getNo_agree() {
		return no_agree;
	}
	public void setNo_agree(String no_agree) {
		this.no_agree = no_agree;
	}
   
    
}
