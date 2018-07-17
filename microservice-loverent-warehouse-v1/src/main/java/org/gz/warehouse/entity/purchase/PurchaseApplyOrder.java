/**
 * 
 */
package org.gz.warehouse.entity.purchase;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.gz.warehouse.entity.WarehouseBaseEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月18日 上午11:36:07
 */
public class PurchaseApplyOrder extends WarehouseBaseEntity {

	private static final long serialVersionUID = 7522436402017022179L;

	private String purchaseNo;// 采购申请单号,系统自动生成

	private Integer purchaseType=1;//采购类型：1:标准采购
	
	@NotNull(message = "请选择采购仓库")
	@Range(min = 1, message = "请选择采购仓库")
	private Integer warehouseInfoId=0;// 采购仓库ID

	@NotNull(message = "请选择供应商ID")
	@Range(min = 1, message = "请选择供应商ID")
	private Integer supplierId=0;// 供应商ID，关联supplier_basic_inifo主键

	private Date expectedReceiveDate;// 预计接收日期
	
	@NotBlank(message = "请填写预计收货日期")
	private String expectedReceiveDate_s;
	
	private Date realReceiveDate;//实际收货时间
	
	private String applyName;//申请人名称
	
	private Date approvedDateTime;// 审批时间

	@Range(min = 0, message = "审批人ID")
	private Long approvedId=0L;// 审批人ID
	
	private String approvedName;//审批人名称

	private Integer statusFlag=10;// 采购申请单标志 10:草稿，20：已提交 30：通过 40:拒绝 50：已收货 90:已废弃

	private BigDecimal sumAmount;// 采购物料合计金额

	@Length(max = 255, message = "备注信息长度不超过255字符")
	private String remark;// 备注

	@NotEmpty(message="采购订单物料明细列表不能为空")
	@Valid
	private List<PurchaseApplyOrderDetail> detailList;
	
	public String getPurchaseNo() {
		return purchaseNo;
	}

	public void setPurchaseNo(String purchaseNo) {
		this.purchaseNo = purchaseNo;
	}

	
	public Integer getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(Integer purchaseType) {
		this.purchaseType = purchaseType;
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

	public Date getExpectedReceiveDate() {
		return expectedReceiveDate;
	}

	public void setExpectedReceiveDate(Date expectedReceiveDate) {
		this.expectedReceiveDate = expectedReceiveDate;
	}

	public String getExpectedReceiveDate_s() {
		return expectedReceiveDate_s;
	}

	public void setExpectedReceiveDate_s(String expectedReceiveDate_s) {
		this.expectedReceiveDate_s = expectedReceiveDate_s;
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

	public Integer getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(Integer statusFlag) {
		this.statusFlag = statusFlag;
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

	public List<PurchaseApplyOrderDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<PurchaseApplyOrderDetail> detailList) {
		this.detailList = detailList;
	}

	public String getApplyName() {
		return applyName;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}

	public String getApprovedName() {
		return approvedName;
	}

	public void setApprovedName(String approvedName) {
		this.approvedName = approvedName;
	}
}
