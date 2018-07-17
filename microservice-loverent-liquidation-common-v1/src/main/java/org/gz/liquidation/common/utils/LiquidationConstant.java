package org.gz.liquidation.common.utils;

public class LiquidationConstant {
	/**
	 * 科目出账
	 */
	public static String OUT = "OUT";
	/**
	 * 科目入账
	 */
	public static String IN = "IN";
	/**
	 * 结清方式：买断
	 */
	public static String SETTLEWAY_BUYOUT = "BUYOUT";
	/**
	 * 结清方式：租金
	 */
	public static String SETTLEWAY_RENT = "RENT";
	/**
	 * 结清方式：回收
	 */
	public static String SETTLEWAY_RECYCLE = "RECYCLE";
	/**
	 * 结清方式：滞纳金
	 */
	public static String SETTLEWAY_LATE_FEE = "LATE_FEE";
	/**
	 * 结清方式：滞纳金减免
	 */
	public static String SETTLEWAY_LATE_FEE_REMISSION = "LATE_FEE_REMISSION";
	/**
	 * 是否结清 1 是
	 */
	public static int SETTLE_FLAG_YES = 1;
	/**
	 * 是否结清 0 否
	 */
	public static int SETTLE_FLAG_NO = 0;
	/**
	 * 是否有效 1是
	 */
	public static int ENABLE_FLAG_YES = 1;
	/**
	 * 是否有效 0否
	 */
	public static int ENABLE_FLAG_NO = 0;
	/**
	 * 待支付:0
	 */
	public static int STATE_WAITING = 0;
	/**
	 * 交易中: 1
	 */
	public static int STATE_TRADING = 1;
	/**
	 * 交易成功: 2
	 */
	public static int STATE_SUCCESS = 2;
	/**
	 * 交易失败: 3
	 */
	public static int STATE_FAILURE = 3;
	/**
	 * 交易作废:4
	 */
	public static int STATE_CANCEL = 4;
	/**
	 * 正常买断 1
	 */
	public static int BUYOUT_TYPE_NOMAL = 1;
	/**
	 * 逾期买断 2
	 */
	public static int BUYOUT_TYPE_OVERDUE = 2;
	/**
	 * 租金 1
	 */
	public static int REPAYMENT_TYPE_RENT = 1;
	/**
	 * 滞纳金 2
	 */
	public static int REPAYMENT_TYPE_LATE_FEES = 2;
	/**
	 * 违约金 3
	 */
	public static int REPAYMENT_TYPE_LIQUIDATED_DAMAGES = 3;
	/**
	 * 首期租金缴纳
	 */
	public static String FIRST_RENT_REMARK = "首期租金";
	/**
	 * 逾期买断备注
	 */
	public static String BUYOUT_OVERDUE_REMARK = "逾期买断";
	/**
	 * 支付处理结果：成功
	 */
	public static String SUCCESS = "SUCCESS";
	/**
	 * 连连支付支付处理结果：失败
	 */
	public static String FAILURE = "FAILURE";
	/**
	 * log打印前缀
	 */
	public static String LOG_PREFIX = " <<<-------------------------->>> ";
	/**
	 * 微信支付 返回状态码FAIL
	 */
	public static String WEIXIN_FAIL = "FAIL";
	/**
	 * 滞纳金减免
	 */
	public static String LATE_FEE_REMISSION_REMARK = "滞纳金减免";
	/**
	 * 支付宝交易状态:交易支付成功
	 */
	public static String ALIPAY_TRADE_SUCCESS = "TRADE_SUCCESS";
	/**
	 * 状态:正常 0
	 */
	public static int STATE_NORMAL = 0;
	/**
	 * 状态:违约 1
	 */
	public static int STATE_BREACH = 1;
	/**
	 * 滞纳金利率配置code
	 */
	public static String CONFIG_CODE_LATE_FEE = "RATE_OF_LATE_FEE";
	/**
	 * 备注：偿还租金
	 */
	public static String REMARK_REPAY_RENT = "偿还租金";
	/**
	 * 备注：偿还滞纳金
	 */
	public static String REMARK_REPAY_LATEFEE = "偿还滞纳金";
	/**
	 * 手动清偿租金
	 */
	public static String REMARK_MANUAL_SETTLE_RENT = "手动清偿租金";
	/**
	 * 手动清偿滞纳金
	 */
	public static String REMARK_MANUAL_SETTLE_LATE_FEE = "手动清偿滞纳金";
	/**
	 * 手动清偿归还金
	 */
	public static String REMARK_MANUAL_SETTLE_GHJ = "手动清偿归还金";
	/**
	 * 手动清偿:归还
	 */
	public static String REMARK_MANUAL_SETTLE_RETURN = "手动清偿:归还";
	/**
	 * 正常买断买断金
	 */
	public static String REMARK_NORMAL_BUYOUT_AMOUNT = "正常买断买断金";
	/**
	 * 逾期买断买断金
	 */
	public static String REMARK_OVERDUE_BUYOUT_AMOUNT = "逾期买断买断金";
	/**
	 * 备注：滞纳金减免
	 */
	public static String REMARK_LATEFEE_REMISSION = "滞纳金减免";
	/**
	 * 备注：优惠券
	 */
	public static String REMARK_COUPON = "优惠券";
	/**
	 * 是否违约 否 0
	 */
	public static int BREACH_STATE_NO = 0;
	/**
	 * 是否违约 是 1
	 */
	public static int BREACH_STATE_YES = 1;
	/**
	 * 支付宝网关 公共错误码：10000 接口调用成功
	 */
	public static String ALIPAY_GATEWAY_CODE_10000 = "10000";
	/**
	 * 支付宝网关 公共错误码:40004 业务处理失败	对应业务错误码，明细错误码和解决方案请参见具体的API接口文档
	 */
	public static String ALIPAY_GATEWAY_CODE_40004 = "40004";
	/**
	 * 支付宝支付:交易不存在
	 */
	public static String ALIPAY_SUB_CODE_TRADE_NOT_EXIST = "ACQ.TRADE_NOT_EXIST";
	/**
	 * APP订单 0
	 */
	public static int ORDER_TYPE_APP = 0;
	/**
	 * 芝麻订单 1
	 */
	public static int ORDER_TYPE_ZM = 1;
	/**
	 * 备注：分期扣款
	 */
	public static String REMARK_INSTALLMENT = "分期扣款";
	/**
	 * 芝麻信用:首期款
	 */
	public static String REMARK_FIRST_INSTALLMENT = "芝麻信用:首期款";
	/**
	 * 备注：销售金
	 */
	public static String REMARK_SALE = "销售金";
	/**
	 * 优惠券使用场景 退回
	 */
	public static int COUPON_USAGE_SCENARIO_RETURN = 90;
}
