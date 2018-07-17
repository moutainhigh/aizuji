/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.risk.auditing.param;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.gz.risk.auditing.entity.BaseReq;
import org.gz.risk.auditing.entity.LoanDetailVo;

/**
 * @Description:用于更新订单表和loandetail表 Author Version Date Changes zhuangjianfa 1.0
 * 2017年7月19日 Created
 */
public class ForUpdateReq extends BaseReq implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    
    private LoanDetailVo        loanDetail;
    
    /**
     * 自增ID
     */
    private Long              id;
    /**
     * 贷款ID
     */
    private String            loanRecordId;
    /**
     * 属于哪个历史用户表（user_histroy）
     */
    private Long              historyId;
    /**
     * 申请时间
     */
    private Date              applyTime;
    /**
     * 备注
     */
    private String            remark;
    /**
     * 申请金额
     */
    private BigDecimal        applyAmount;
    /**
     * 放款金额
     */
    private BigDecimal        loanAmount;
    /**
     * 放款期限
     */
    private Integer           loanTimeLimit;
    /**
     * 到手金额
     */
    private BigDecimal        payAmount;
    /**
     * 信审结果
     */
    private Integer           creditApproval;
    /**
     * 审批结果(1,申请,2拒绝,10签约,11拒绝签约,20待放款,21已放款,31已结清)
     */
    private Integer           approvalResult;
    /**
     * 贷款产品ID
     */
    private Long              loanProductId;
    /**
     * 利息
     */
    private Double            interestRate;
    /**
     * 管理费率
     */
    private Double            managementRate;
    /**
     * 服务费率
     */
    private Double            serviceRate;
    /**
     * 手续费
     */
    private Double            poundageRate;
    /**
     * 滞纳金
     */
    private Double            overdueRate;
    /**
     * 期限
     */
    private Integer           duration;
    /**
     * 期限类型(1:月,2:日)
     */
    private Integer           durationType;
    /**
     * 还款方式(1:等本等息)
     */
    private Integer           lendWay;
    /**
     * 理由
     */
    private String            reason;
    /**
     * 是否锁定,(0:没有锁定,1:锁定)
     */
    private Integer           lock;
    /**
     * 有效性( 用于手动修改数据之后统计)
     */
    private Integer           valid;
    /**
     * level
     */
    private String            level;
    /**
     * 1,拍照,2,相册
     */
    private Integer           imgType;
    /**
     * 1 安卓,2 ios ,3 h5
     */
    private Integer           channelType;
    /**
     * 冗余产品名称
     */
    private String            productName;

    /**
     * userId用户id
     */
    private Long              userId;

    /**
     * Returns this userId object.
     * 
     * @return this userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets this userId.
     * 
     * @param userId this userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setLoanRecordId(String loanRecordId) {
        this.loanRecordId = loanRecordId;
    }

    public String getLoanRecordId() {
        return this.loanRecordId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public Long getHistoryId() {
        return this.historyId;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getApplyTime() {
        return this.applyTime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setApplyAmount(BigDecimal applyAmount) {
        this.applyAmount = applyAmount;
    }

    public BigDecimal getApplyAmount() {
        return this.applyAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public BigDecimal getLoanAmount() {
        return this.loanAmount;
    }

    public void setLoanTimeLimit(Integer loanTimeLimit) {
        this.loanTimeLimit = loanTimeLimit;
    }

    public Integer getLoanTimeLimit() {
        return this.loanTimeLimit;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public BigDecimal getPayAmount() {
        return this.payAmount;
    }

    public void setCreditApproval(Integer creditApproval) {
        this.creditApproval = creditApproval;
    }

    public Integer getCreditApproval() {
        return this.creditApproval;
    }

    public void setApprovalResult(Integer approvalResult) {
        this.approvalResult = approvalResult;
    }

    public Integer getApprovalResult() {
        return this.approvalResult;
    }

    public void setLoanProductId(Long loanProductId) {
        this.loanProductId = loanProductId;
    }

    public Long getLoanProductId() {
        return this.loanProductId;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getInterestRate() {
        return this.interestRate;
    }

    public void setManagementRate(Double managementRate) {
        this.managementRate = managementRate;
    }

    public Double getManagementRate() {
        return this.managementRate;
    }

    public void setServiceRate(Double serviceRate) {
        this.serviceRate = serviceRate;
    }

    public Double getServiceRate() {
        return this.serviceRate;
    }

    public void setPoundageRate(Double poundageRate) {
        this.poundageRate = poundageRate;
    }

    public Double getPoundageRate() {
        return this.poundageRate;
    }

    public void setOverdueRate(Double overdueRate) {
        this.overdueRate = overdueRate;
    }

    public Double getOverdueRate() {
        return this.overdueRate;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDuration() {
        return this.duration;
    }

    public void setDurationType(Integer durationType) {
        this.durationType = durationType;
    }

    public Integer getDurationType() {
        return this.durationType;
    }

    public void setLendWay(Integer lendWay) {
        this.lendWay = lendWay;
    }

    public Integer getLendWay() {
        return this.lendWay;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return this.reason;
    }

    public void setLock(Integer lock) {
        this.lock = lock;
    }

    public Integer getLock() {
        return this.lock;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Integer getValid() {
        return this.valid;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return this.level;
    }

    public void setImgType(Integer imgType) {
        this.imgType = imgType;
    }

    public Integer getImgType() {
        return this.imgType;
    }

    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    public Integer getChannelType() {
        return this.channelType;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return this.productName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns this loanDetail object.
     * 
     * @return this loanDetail
     */
    public LoanDetailVo getLoanDetail() {
        return loanDetail;
    }

    /**
     * Sets this loanDetail.
     * 
     * @param loanDetail this loanDetail to set
     */
    public void setLoanDetail(LoanDetailVo loanDetail) {
        this.loanDetail = loanDetail;
    }

}
