/**
 * 
 */
package org.gz.warehouse.entity.warehouse;

import java.util.Date;

import org.gz.common.entity.BaseEntity;

/**
 * @Title:库存调整申请基表
 * @author hxj
 * @Description:
 * @date 2017年12月20日 下午5:19:21
 */
public class WarehouseAdjustBasic extends BaseEntity {

	private static final long serialVersionUID = -7287225318819989213L;

	private Long id;

	private String applicantSeq;// 申请单号

	private Long applicantId;// 申请人ID

	private String applicantName;// 申请人姓名

	private Date applicantDateTime;// 申请时间

	private Long approverId;// 审批人ID

	private String approverName;// 审批人姓名

	private Integer statusFlag;// 状态标志 10：草稿 20:已提交 30：已审批 40：已拒绝 50：已废弃 90：已删除

	private String remark;// 备注

	private Long version = 1L;// 并发版本控制号

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApplicantSeq() {
		return applicantSeq;
	}

	public void setApplicantSeq(String applicantSeq) {
		this.applicantSeq = applicantSeq;
	}

	public Long getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(Long applicantId) {
		this.applicantId = applicantId;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public Date getApplicantDateTime() {
		return applicantDateTime;
	}

	public void setApplicantDateTime(Date applicantDateTime) {
		this.applicantDateTime = applicantDateTime;
	}

	public Long getApproverId() {
		return approverId;
	}

	public void setApproverId(Long approverId) {
		this.approverId = approverId;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public Integer getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(Integer statusFlag) {
		this.statusFlag = statusFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}
