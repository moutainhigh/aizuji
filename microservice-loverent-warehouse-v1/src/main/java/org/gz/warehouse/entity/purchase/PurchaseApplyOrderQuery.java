/**
 * 
 */
package org.gz.warehouse.entity.purchase;

import org.gz.common.entity.QueryPager;
import org.springframework.util.StringUtils;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月18日 上午11:36:07
 */
public class PurchaseApplyOrderQuery extends QueryPager {

	private static final long serialVersionUID = 7522436402017022179L;

	private Long id;

	private String purchaseNo;// 采购申请单号,系统自动生成

	private Integer warehouseInfoId;// 采购仓库ID

	private Integer supplierId;// 供应商ID

	private String startExpectedRecvDate;// 预计接收日期

	private String endExpectedRecvDate;

	private String startApplyDate;// 申请开始时间

	private String endApplyDate;// 申请结束时间

	private Long applyId;// 申请人ID
	
	private String applyName;//申请人名称
	
	private Long approvedId;//审批人ID
	
	private String approvedName;//审批人名称

	private Integer statusFlag=-1;// 采购申请单标志 10:草稿，20：已提交 30：通过 40:拒绝 50：已收货 90:已丢弃

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getStartExpectedRecvDate() {
		return startExpectedRecvDate;
	}

	public void setStartExpectedRecvDate(String startExpectedRecvDate) {
		this.startExpectedRecvDate = startExpectedRecvDate;
	}

	public String getEndExpectedRecvDate() {
		return endExpectedRecvDate;
	}

	public void setEndExpectedRecvDate(String endExpectedRecvDate) {
		this.endExpectedRecvDate = endExpectedRecvDate;
	}

	public String getStartApplyDate() {
		return startApplyDate;
	}

	public void setStartApplyDate(String startApplyDate) {
		if(StringUtils.hasText(startApplyDate)&&startApplyDate.length()==10) {
			this.startApplyDate=startApplyDate+" 00:00:00";
		}else {
			this.startApplyDate = startApplyDate;
		}
	}

	public String getEndApplyDate() {
		return endApplyDate;
	}

	public void setEndApplyDate(String endApplyDate) {
		if(StringUtils.hasText(endApplyDate)&&endApplyDate.length()==10) {
			this.endApplyDate=endApplyDate+" 23:59:59";
		}else {
			this.endApplyDate = endApplyDate;
		}
	}
	
	public Long getApprovedId() {
		return approvedId;
	}

	public void setApprovedId(Long approvedId) {
		this.approvedId = approvedId;
	}

	public Long getApplyId() {
		return applyId;
	}

	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}

	public Integer getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(Integer statusFlag) {
		this.statusFlag = statusFlag;
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
