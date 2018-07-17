package org.gz.thirdParty.bean.lianlian;


/**
 * 银行卡签约类
 *
 * @author duzl
 */
public class OrderQueryRespBean extends TranBaseBean {

    private static final long serialVersionUID = 1L;
    /**
     * SUCCESS 成功
     * WAITING 等待支付
     * PROCESSING 银行支付处理中
     * REFUND 退款
     * FAILURE 失败
     */
    private String result_pay;          // 支付结果
    private String oid_partner;          // 商户编号
    private String dt_order;          // 商户订单时间
    private String no_order;          // 商户订单编号
    private String oid_paybill;          // 连连订单编号
    private String money_order;          // 交易金额
    private String settle_date;          // 清算日期
    private String info_order;          // 订单描述
    private String pay_type;          // D 认证支付
    private String bank_code;          // 银行编号
    private String bank_name;          // 银行名称
    private String memo;          // 支付备注
    private String card_no;          // 银行卡号
	public String getResult_pay() {
		return result_pay;
	}
	public void setResult_pay(String result_pay) {
		this.result_pay = result_pay;
	}
	public String getOid_partner() {
		return oid_partner;
	}
	public void setOid_partner(String oid_partner) {
		this.oid_partner = oid_partner;
	}
	public String getDt_order() {
		return dt_order;
	}
	public void setDt_order(String dt_order) {
		this.dt_order = dt_order;
	}
	public String getNo_order() {
		return no_order;
	}
	public void setNo_order(String no_order) {
		this.no_order = no_order;
	}
	public String getOid_paybill() {
		return oid_paybill;
	}
	public void setOid_paybill(String oid_paybill) {
		this.oid_paybill = oid_paybill;
	}
	public String getMoney_order() {
		return money_order;
	}
	public void setMoney_order(String money_order) {
		this.money_order = money_order;
	}
	public String getSettle_date() {
		return settle_date;
	}
	public void setSettle_date(String settle_date) {
		this.settle_date = settle_date;
	}
	public String getInfo_order() {
		return info_order;
	}
	public void setInfo_order(String info_order) {
		this.info_order = info_order;
	}
	public String getPay_type() {
		return pay_type;
	}
	public void setPay_type(String pay_type) {
		this.pay_type = pay_type;
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
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}


}
