/**
 * 
 */
package org.gz.liquidation.common.Enum;


public enum LiquidationErrorCode {
	/**
	 * 编码规则：前两位：模块名称,中两位:功能名称,后两位：错误编码
	 */
	/**
	 * 买断-订单信息有误
	 */
	REPAYMENT_BUYOUT_ORDER_ERROR(100001, "订单信息有误"), 
	/**
	 * 买断-未找到还款计划
	 */
	REPAYMENT_BUYOUT_QUERY_ERROR(100002, "未找到还款计划"), 
	/**
	 * 买断-存在逾期罚金
	 */
	REPAYMENT_BUYOUT_LATEFEES_ERROR(100003, "存在逾期罚金"), 
	/**
	 * 存在处理中的订单
	 */
	REPAYMENT_BUYOUT_PROCESSED_ERROR(100004, "存在处理中的订单"), 
	/**
	 * 订单状态不满足买断条件
	 */
	REPAYMENT_BUYOUT_STATE_ERROR(100005, "订单状态不满足买断条件"), 
	/**
	 * 当期订单未结清
	 */
	REPAYMENT_BUYOUT_UNSETTLED_ERROR(100006, "当期订单未结清"), 
	/**
	 * 清算金额参数有误
	 */
	REPAYMENT_SETTLE_AMOUT_ERROR(200001, "清算金额参数有误"), 
	/**
	 * 首期支付已成功
	 */
	DOWN_PAYMENT_SUCCESS(800000, "首期支付已成功"), 
	/**
	 * 优惠券查询异常
	 */
	DOWN_PAYMENT_COUPON_ERROR(801000, "优惠券查询异常"), 
	/**
	 * 首期支付重复
	 */
	PAYMENT_DOWNPAYMENT_REPEAT_ERROR(30000, "首期支付重复"), 
	/**
	 * 存在处理中的首期支付交易
	 */
	PAYMENT_DOWNPAYMENT_PROCESSED_ERROR(300002, "存在处理中的首期支付交易"), 
	/**
	 * 存在处理中的租金支付交易
	 */
	PAYMENT_RENT_PROCESSED_ERROR(301000, "存在处理中的租金支付交易"), 
	/**
	 * 租金支付金额超过允许支付的最大值
	 */
	PAYMENT_RENT_MAX_AMOUNT_ERROR(301001, "租金支付金额超过允许支付的最大值"), 
	/**
	 * 存在处理中的买断支付交易
	 */
	PAYMENT_BUYOUT_PROCESSED_ERROR(302000, "存在处理中的买断支付交易"), 
	/**
	 * 买断交易已完成
	 */
	PAYMENT_BUYOUT_REPEAT_ERROR(302001, "买断交易已完成"),
	/**
	 * 存在处理中租金
	 */
	PAYMENT_RENT_REPEAT_ERROR(303000, "存在处理中租金"),
	/**
	 * 未找到对应的产品信息
	 */
	SIGN_DOSIGN_PRODUCT_ERROR(400000, "未找到对应的产品信息"),
	/**
	 * 未找到首期款支付记录，请先进行支付
	 */
	SIGN_DOSIGN_DOWNPAYMENT_ERROR(500000, "未找到首期款支付记录，请先进行支付"),
	/**
	 *此订单号没有产生滞纳金 
	 */
	LATE_FEE_DOREMISSION_DATA_ERROR(600000,"此订单号没有产生滞纳金"),
	/**
	 *减免滞纳金不能为0或负数
	 */
	LATE_FEE_DOREMISSION_MIN_AMOUNT_ERROR(600001,"减免滞纳金不能为0或负数"),
	/**
	 *减免滞纳金不能超过滞纳金总额
	 */
	LATE_FEE_DOREMISSION_MAX_AMOUNT_ERROR(600002,"减免滞纳金不能超过滞纳金总额"),
	/**
	 * 此订单号没有未结清的租金
	 */
	MANUAL_SETTLE_RENT_DATA_ERROR(700000,"此订单号没有未结清的租金"),
	/**
	 * 交易类型错误
	 */
	MANUAL_SETTLE_TRADE_TYPE_ERROR(701000,"交易类型错误"),
	/**
	 * 交易流水号已经存在
	 */
	MANUAL_SETTLE_TRADE_SN_ERROR(701001,"交易流水号已经存在"),
	/**
	 * 还款计划数据异常
	 */
	MANUAL_SETTLE_RETURN_DATA_ERROR(702000,"还款计划数据异常"),
	/**
	 * 订单状态不符合逾期买断条件
	 */
	MANUAL_SETTLE_OVEDUE_BUYOUT_STATE_ERROR(703000,"订单状态不符合逾期买断条件"),
	/**
	 * 订单状态不符合正常买断条件
	 */
	MANUAL_SETTLE_NOMAL_BUYOUT_STATE_ERROR(704000,"订单状态不符合正常买断条件"),
	/**
	 * 订单状态不符合租金清偿条件
	 */
	MANUAL_SETTLE_REPAY_RENT_ERROR(705000,"订单状态不符合租金清偿条件"),
	/**
	 * 订单状态不符合回收条件
	 */
	MANUAL_SETTLE_RETURN_ERROR(706000,"订单状态不符合回收条件"),
	/**
	 * 订单未使用过优惠券或者优惠券已经退还
	 */
	COUPON_RETURN_ERROR(900000,"订单未使用过优惠券或者优惠券已经退还"),
	/**
	 * 交易流水记录不存在
	 */
	CREDIT_PAY_TRANSACTION_ERROR(110000,"交易流水记录不存在"),
	/**
	 * 订单记录不存在
	 */
	CREDIT_PAY_ORDER_ERROR(111000,"订单记录不存在"),
	;

	/**
	 * 错误代码
	 */
	private int code;

	/**
	 * 错误消息
	 */
	private String message;

	LiquidationErrorCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
