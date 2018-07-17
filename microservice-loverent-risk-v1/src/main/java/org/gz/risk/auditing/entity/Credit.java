package org.gz.risk.auditing.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author JarkimZhu
 *         Created on 2016/11/24.
 * @since jdk1.8
 */
public class Credit implements Serializable {

    /**
     * 通过
     */
    public static final int ACTION_PASS = 1;
    /**
     * 拒绝
     */
    public static final int ACTION_REFUSE = 2;
    /**
     * 退回
     */
    public static final int ACTION_BACK = 3;
    /**
     * 签约
     */
    public static final int ACTION_SIGN = 4;
    /**
     * 取消签约
     */
    public static final int ACTION_SIGN_CANCEL = 5;

    private String loanRecordId;
    private String processInstanceId;
    private String taskId;
    private String taskName;
    private String assignee;
    private String adminName;
    private String remark;
    private int action;
    private Double amount;
    private Date operateDate;
    private String reason;
    private Boolean lock;
    private Integer refuseCategory;

    public Integer getRefuseCategory() {
        return refuseCategory;
    }

    public void setRefuseCategory(Integer refuseCategory) {
        this.refuseCategory = refuseCategory;
    }

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLoanRecordId() {
        return loanRecordId;
    }

    public void setLoanRecordId(String loanRecordId) {
        this.loanRecordId = loanRecordId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
