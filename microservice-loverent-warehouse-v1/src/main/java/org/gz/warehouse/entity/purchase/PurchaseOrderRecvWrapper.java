/**
 * 
 */
package org.gz.warehouse.entity.purchase;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.gz.common.entity.BaseEntity;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.UniqueElements;

/**
 * @Title: 采购收货一级实体
 * @author hxj
 * @Description:
 * @date 2017年12月20日 下午5:29:16
 */
public class PurchaseOrderRecvWrapper extends BaseEntity {

	private static final long serialVersionUID = 564614989728598864L;

	@NotNull(message="采购申请订单ID不能为null")
	@Positive(message="非法采购申请订单ID")
	private Long purchaseApplyOrderId;//采购申请订单ID
	
	private Long operatorId;//操作人ID
	
	private String operatorName;//操作人姓名
	
	private Date operateOn;//操作时间
	
	@NotEmpty(message="请先扫描录入商品信息")
	@UniqueElements(message="物料采购申请列表不能重复")
	@Valid
	private List<PurchaseOrderRecvVO> recvList;

	public Long getPurchaseApplyOrderId() {
		return purchaseApplyOrderId;
	}

	public void setPurchaseApplyOrderId(Long purchaseApplyOrderId) {
		this.purchaseApplyOrderId = purchaseApplyOrderId;
	}

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Date getOperateOn() {
		return operateOn;
	}

	public void setOperateOn(Date operateOn) {
		this.operateOn = operateOn;
	}

	public List<PurchaseOrderRecvVO> getRecvList() {
		return recvList;
	}

	public void setRecvList(List<PurchaseOrderRecvVO> recvList) {
		this.recvList = recvList;
	}
}