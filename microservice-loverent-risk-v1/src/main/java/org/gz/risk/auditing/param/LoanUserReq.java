package org.gz.risk.auditing.param;

import java.io.Serializable;
import java.util.Date;

import org.gz.risk.auditing.entity.BaseReq;

public class LoanUserReq extends BaseReq implements Serializable{

    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * 订单号
     */
    private String loanRecordId;
    
    /**
     * 用户id
     */
    private Long userId;
    
    /**
     * 申请时间 
     */
    private Date applyTime;

    /**
     * 分页起始下标
     */
    private int offset;
    
    /**
     * 分页结束下标
     */
    private int rows;
    
    public String getLoanRecordId() {
        return loanRecordId;
    }

    public void setLoanRecordId(String loanRecordId) {
        this.loanRecordId = loanRecordId;
    }

    
    public Long getUserId() {
        return userId;
    }

    
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    
    public int getOffset() {
        return offset;
    }

    
    public void setOffset(int offset) {
        this.offset = offset;
    }

    
    public int getRows() {
        return rows;
    }

    
    public void setRows(int rows) {
        this.rows = rows;
    }

    
    public Date getApplyTime() {
        return applyTime;
    }

    
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

}
