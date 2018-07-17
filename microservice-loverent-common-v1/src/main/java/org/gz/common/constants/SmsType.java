package org.gz.common.constants;

public enum SmsType {
	LOGIN(100, "login"),
	REGISTER(101, "register"),
	MODIFY_PWD(102, "modify_pwd"),
 STOCK_SIGN_INFORM(103, "stock_sign_inform"),
 THIRD_LOGIN(104, "modify_pwd"),
 APPLY_SUCCESS(229391, "apply_success"), // 申请成功
 PAY_NOTICE(229392, "pay_notice"), // 提醒支付
 PAY_SUCCESS(229393, "pay_success"), // 支付成功
 SEND_NOTICE(231589, "send_notice"), // 发货通知
 RECIVE_NOTICE(231587, "recive_notice"), // 签收通知
 REFUSE_NOTICE(229396, "refuse_notice"), // 拒绝通知
 H5REFUSE_NOTICE(231590, "h5refuse_notice"), // H5拒绝通知
 RENTPAY_NOTICE(229397, "rentpay_notice"), // 租金缴纳
 OVERDUE_NOTICE(229398, "overdue_notice"), // 逾期提醒
 BUTOUT_SUCCESS(229399, "buyout_success"), // 买断成功
 RETURN_SUCCESS(229400, "return_success"), // 归还成功
 SIGN_SUCCESS(229401, "sign_success"), // 签约成功

 SAlESEND_NOTICE(240987, "salesend_notice"), // 售卖订单发货通知
 SAlEBUTOUT_SUCCESS(240988, "salebuyout_success"); // 售卖订单买断通知
	
	private Integer type;
	
	private String cacheKey;
	
	private SmsType(Integer type, String cacheKey) {
		this.type = type;
		this.cacheKey = cacheKey;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCacheKey() {
		return cacheKey;
	}

	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}

	public static SmsType getSmsTypeByType(Integer type) {
		SmsType[] types = SmsType.values();
		for (SmsType e : types) {
			if (e.type.equals(type)) {
				return e;
			}
		}
		return null;
	}
}
