/**
 * 
 */
package org.gz.warehouse.constants;

/**
 * @Title: 调整
 * @author hxj
 * @Description:
 * @date 2018年1月10日 上午9:35:36
 */
public enum AdjustReasonEnum {
	REASON_SIGNING("签约"),
	REASON_SALE("售卖"),
	REASON_CANCEL_ORDER("取消订单"),
	REASON_PICK("拣货"),
	REASON_UNDO_PICK("撤销拣货"),
	REASON_OUT("出库"),
	REASON_IN("入库"),
	REASON_RENTING("在租"),
	REASON_AD_DESTROY_ORDER("提前解约"),
	REASON_RECV("收货"),
	REASON_BUYEND("买断"), 
	REASON_DIFF("物流差异"),
	;
	
	private String reason;

	AdjustReasonEnum(String reason) {
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}
}
