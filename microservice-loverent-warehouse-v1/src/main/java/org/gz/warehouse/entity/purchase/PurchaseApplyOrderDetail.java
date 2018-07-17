/**
 * 
 */
package org.gz.warehouse.entity.purchase;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.gz.common.entity.BaseEntity;
import org.hibernate.validator.constraints.Range;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月18日 上午11:50:33
 */
public class PurchaseApplyOrderDetail extends BaseEntity {

	private static final long serialVersionUID = -3791206349486308188L;

	private Long id;

	private Long purchaseApplyOrderId;// 物料采购申请单ID

	@NotNull(message = "物料基础信息ID不能为空")
	@Range(min = 1, message = "非法物料基础信息ID")
	private Long materielBasicId;// 物料基础信息ID

	@NotNull(message = "排列顺序不能为空")
	@Range(min = 1, message = "非法排列顺序")
	private Long sortOrder;// 排列顺序

	@Range(min = 0, message = "非法建议数量")
	private Integer suggestQuantity = 0;// 建议数量

	@NotNull(message = "申请数量不能为空")
	@Range(min = 1, message = "非法申请数量")
	private Integer applyQuantity;// 申请数量

	@Range(min = 0, message = "非法审批数量")
	private Integer approvedQuantity=0;//

	private Integer realRecvQuantity=0;//实收数量 
	
	private Integer diffQuantity=0;//差异数量=收货数量 -批准数量
	
	private BigDecimal unitPrice;

	public PurchaseApplyOrderDetail() {
		
	}
	
	
	public PurchaseApplyOrderDetail(Long materielBasicId, Long sortOrder,
			 Integer applyQuantity,  BigDecimal unitPrice) {
		this.materielBasicId = materielBasicId;
		this.sortOrder = sortOrder;
		this.applyQuantity = applyQuantity;
		this.unitPrice = unitPrice;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPurchaseApplyOrderId() {
		return purchaseApplyOrderId;
	}

	public void setPurchaseApplyOrderId(Long purchaseApplyOrderId) {
		this.purchaseApplyOrderId = purchaseApplyOrderId;
	}

	public Long getMaterielBasicId() {
		return materielBasicId;
	}

	public void setMaterielBasicId(Long materielBasicId) {
		this.materielBasicId = materielBasicId;
	}

	public Long getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Long sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Integer getSuggestQuantity() {
		return suggestQuantity;
	}

	public void setSuggestQuantity(Integer suggestQuantity) {
		this.suggestQuantity = suggestQuantity;
	}

	public Integer getApplyQuantity() {
		return applyQuantity;
	}

	public void setApplyQuantity(Integer applyQuantity) {
		this.applyQuantity = applyQuantity;
	}

	public Integer getApprovedQuantity() {
		return approvedQuantity;
	}

	public void setApprovedQuantity(Integer approvedQuantity) {
		this.approvedQuantity = approvedQuantity;
	}

	public Integer getRealRecvQuantity() {
		return realRecvQuantity;
	}


	public void setRealRecvQuantity(Integer realRecvQuantity) {
		this.realRecvQuantity = realRecvQuantity;
	}


	public Integer getDiffQuantity() {
		return diffQuantity;
	}


	public void setDiffQuantity(Integer diffQuantity) {
		this.diffQuantity = diffQuantity;
	}


	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
}
