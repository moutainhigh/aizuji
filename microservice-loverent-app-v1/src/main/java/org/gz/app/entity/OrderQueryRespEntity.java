package org.gz.app.entity;

import java.util.List;

/**
 * 银行卡签约类
 *
 * @author duzl
 */
public class OrderQueryRespEntity {

    private static final long serialVersionUID = 1L;
    
    private String ret_code;
    private String ret_msg;
    private String user_id;
    private String count;
    private String sign_type;
    private String sign;
    private List<cardList> agreement_list;
   
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

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
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

	public List<cardList> getAgreement_list() {
		return agreement_list;
	}

	public void setAgreement_list(List<cardList> agreement_list) {
		this.agreement_list = agreement_list;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static class cardList{
    	private String no_agree;
    	private String card_no;
    	private String bank_code;
    	private String bank_name;
    	private String card_type;
    	private String bind_mobile;
		public String getNo_agree() {
			return no_agree;
		}
		public void setNo_agree(String no_agree) {
			this.no_agree = no_agree;
		}
		public String getCard_no() {
			return card_no;
		}
		public void setCard_no(String card_no) {
			this.card_no = card_no;
		}
		public String getBank_code() {
			return bank_code;
		}
		public void setBank_code(String bank_code) {
			this.bank_code = bank_code;
		}
		public String getBank_name() {
			return bank_name;
		}
		public void setBank_name(String bank_name) {
			this.bank_name = bank_name;
		}
		public String getCard_type() {
			return card_type;
		}
		public void setCard_type(String card_type) {
			this.card_type = card_type;
		}
		public String getBind_mobile() {
			return bind_mobile;
		}
		public void setBind_mobile(String bind_mobile) {
			this.bind_mobile = bind_mobile;
		}
    }
}
