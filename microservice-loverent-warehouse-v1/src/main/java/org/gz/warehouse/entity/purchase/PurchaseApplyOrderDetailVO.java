/**
 * 
 */
package org.gz.warehouse.entity.purchase;

import java.math.BigDecimal;

import org.gz.common.entity.BaseEntity;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月18日 上午11:50:33
 */
public class PurchaseApplyOrderDetailVO extends BaseEntity {

	private static final long serialVersionUID = -3791206349486308188L;

	private Long id;

	private Long purchaseApplyOrderId;// 物料采购申请单ID

	private Long materielBasicId;// 物料基础信息ID

	private String materielName;// 物料名称

	private String materielCode;// 物料编码

	private String materielModelName;// 型号名称

	private String specValues;// 规格值

	private String unitName;// 单位名称

	private Long sortOrder;// 排列顺序

	private Integer suggestQuantity;// 建议数量

	private Integer applyQuantity;// 申请数量

	private Integer approvedQuantity;// 批准数量

	private BigDecimal unitPrice;// 单价

	public PurchaseApplyOrderDetailVO() {

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

	public String getMaterielName() {
		return materielName;
	}

	public void setMaterielName(String materielName) {
		this.materielName = materielName;
	}

	public String getMaterielCode() {
		return materielCode;
	}

	public void setMaterielCode(String materielCode) {
		this.materielCode = materielCode;
	}

	public String getMaterielModelName() {
		return materielModelName;
	}

	public void setMaterielModelName(String materielModelName) {
		this.materielModelName = materielModelName;
	}

	public String getSpecValues() {
		return specValues;
	}

	public void setSpecValues(String specValues) {
		this.specValues = specValues;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
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

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
}
