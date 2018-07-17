package org.gz.liquidation.common.dto.lianlian;

import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @Description:连连支付订单查询响应DTO
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年4月3日 	Created
 */
@Setter @Getter
public class LianlianOrderQueryResp {
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
    private String card_no;  
}
