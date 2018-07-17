/**
 * 
 */
package org.gz.warehouse.entity.purchase;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gz.common.utils.AssertUtils;
import org.gz.warehouse.entity.WarehouseBaseEntity;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月18日 上午11:36:07
 */
public class PurchaseApplyOrderVO extends WarehouseBaseEntity {

	private static final long serialVersionUID = 7522436402017022179L;

	private Integer purchaseType;// 1:标准类型

	private String purchaseType_s;

	private String purchaseNo;// 采购申请单号,系统自动生成

	private Integer warehouseInfoId;// 采购仓库ID

	private Integer supplierId;// 供应商ID，关联supplier_basic_inifo主键

	private String applyName;

	private Date expectedReceiveDate;// 预计接收日期
	
	private Date realReceiveDate;//实际收货时间

	private Date approvedDateTime;// 审批时间

	private Long approvedId;// 审批人ID

	private String approvedName;

	private Integer statusFlag;// 采购申请单标志 10:草稿，20：已提交 30：通过 40:拒绝 50：已收货

	private String statusFlag_s;

	private BigDecimal sumAmount;// 采购物料合计金额

	private String remark;// 备注

	private String createOn_s;

	private String updateOn_s;

	private String expectedReceiveDate_s;

	private String realReceiveDate_s;

	private String approvedDateTime_s;

	private String warehouseName;

	private String supplierName;

	private String supplierCode;

	private List<PurchaseApplyOrderDetailVO> detailList;

	public Integer getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(Integer purchaseType) {
		this.purchaseType = purchaseType;
		if (AssertUtils.isPositiveNumber4Int(purchaseType)) {
			switch (purchaseType.intValue()) {
			case 1:
				this.purchaseType_s = "标准采购";
				break;
			default:
				this.purchaseType_s = "";
				break;
			}
		}
	}

	public String getPurchaseType_s() {
		return purchaseType_s;
	}

	public void setPurchaseType_s(String purchaseType_s) {
		this.purchaseType_s = purchaseType_s;
	}

	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	public Integer getWarehouseInfoId() {
		return warehouseInfoId;
	}

	public void setWarehouseInfoId(Integer warehouseInfoId) {
		this.warehouseInfoId = warehouseInfoId;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getApplyName() {
		return applyName;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}

	public Date getExpectedReceiveDate() {
		return expectedReceiveDate;
	}

	public void setExpectedReceiveDate(Date expectedReceiveDate) {
		this.expectedReceiveDate = expectedReceiveDate;
	}

	public Date getRealReceiveDate() {
		return realReceiveDate;
	}

	public void setRealReceiveDate(Date realReceiveDate) {
		this.realReceiveDate = realReceiveDate;
	}

	public Date getApprovedDateTime() {
		return approvedDateTime;
	}

	public void setApprovedDateTime(Date approvedDateTime) {
		this.approvedDateTime = approvedDateTime;
	}

	public Long getApprovedId() {
		return approvedId;
	}

	public void setApprovedId(Long approvedId) {
		this.approvedId = approvedId;
	}

	public String getApprovedName() {
		return approvedName;
	}

	public void setApprovedName(String approvedName) {
		this.approvedName = approvedName;
	}

	public Integer getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(Integer statusFlag) {
		this.statusFlag = statusFlag;
		if (AssertUtils.isPositiveNumber4Int(statusFlag)) {
			switch (statusFlag.intValue()) {
			case 10:
				this.statusFlag_s = "草稿";
				break;
			case 20:
				this.statusFlag_s = "已提交";
				break;
			case 30:
				this.statusFlag_s = "已审批";
				break;
			case 40:
				this.statusFlag_s = "已拒绝";
				break;
			case 50:
				this.statusFlag_s = "已收货";
				break;
			case 90:
				this.statusFlag_s = "已废弃";
				break;
			default:
				this.statusFlag_s = "";
				break;
			}
		}
	}

	public String getStatusFlag_s() {
		return statusFlag_s;
	}

	public void setStatusFlag_s(String statusFlag_s) {
		this.statusFlag_s = statusFlag_s;
	}

	public BigDecimal getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(BigDecimal sumAmount) {
		this.sumAmount = sumAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateOn_s() {
		return createOn_s;
	}

	public void setCreateOn_s(String createOn_s) {
		this.createOn_s = createOn_s;
	}

	public String getUpdateOn_s() {
		return updateOn_s;
	}

	public void setUpdateOn_s(String updateOn_s) {
		this.updateOn_s = updateOn_s;
	}

	public String getExpectedReceiveDate_s() {
		return expectedReceiveDate_s;
	}

	public void setExpectedReceiveDate_s(String expectedReceiveDate_s) {
		this.expectedReceiveDate_s = expectedReceiveDate_s;
	}

	public String getRealReceiveDate_s() {
		return realReceiveDate_s;
	}

	public void setRealReceiveDate_s(String realReceiveDate_s) {
		this.realReceiveDate_s = realReceiveDate_s;
	}

	public String getApprovedDateTime_s() {
		return approvedDateTime_s;
	}

	public void setApprovedDateTime_s(String approvedDateTime_s) {
		this.approvedDateTime_s = approvedDateTime_s;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public List<PurchaseApplyOrderDetailVO> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<PurchaseApplyOrderDetailVO> detailList) {
		this.detailList = detailList;
	}
}
