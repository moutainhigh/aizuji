/**
 * 
 */
package org.gz.warehouse.entity.purchase;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.gz.common.entity.BaseEntity;
import org.hibernate.validator.constraints.Range;

/**
 * @Title: 采购申请审批请求对象
 * @author hxj
 * @date 2017年12月19日 下午1:41:48
 */
public class PurchaseApprovedOrderVO extends BaseEntity {

	private static final long serialVersionUID = -5805457009520546662L;

	@NotNull(message = "物料采购申请单ID不能为空")
	@Range(min = 1, message = "非法物料采购申请单ID")
	private Long id;// 物料采购申请单ID

	private Integer statusFlag;//采购申请单标志 10:草稿，20：已提交 30：通过 40:拒绝 50：已收货
	
	private Long approvedId;// 审批人ID

	private String approvedName;// 审批人名称

	private Date approvedDateTime;// 审批时间

	private BigDecimal sumAmount;// 批准总额=SUM(批准数量*单价)

	private List<PurchaseApprovedOrderDetailVO> detailList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(Integer statusFlag) {
		this.statusFlag = statusFlag;
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

	public Date getApprovedDateTime() {
		return approvedDateTime;
	}

	public void setApprovedDateTime(Date approvedDateTime) {
		this.approvedDateTime = approvedDateTime;
	}

	public BigDecimal getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(BigDecimal sumAmount) {
		this.sumAmount = sumAmount;
	}

	public List<PurchaseApprovedOrderDetailVO> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<PurchaseApprovedOrderDetailVO> detailList) {
		this.detailList = detailList;
	}
}
