/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.risk.auditing.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * LoanDetail 实体类
 * 
 * @author pengk
 * @date 2017-07-19
 */
public class LoanDetailVo implements Serializable {


    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * id 
     */
    private Long id;
    /**
     * 借款编号 
     */
    private String loanRecordId;
    /**
     * 最后修改时间 
     */
    private Date processTime;
    /**
     * 借款状态 
     */
    private Integer state;

    public void setLoanRecordId(String loanRecordId) {
        this.loanRecordId = loanRecordId;
    }
    
    public String getLoanRecordId() {
        return this.loanRecordId;
    }
    
    public void setProcessTime(Date processTime) {
        this.processTime = processTime;
    }
    
    public Date getProcessTime() {
        return this.processTime;
    }
    
    public void setState(Integer state) {
        this.state = state;
    }
    
    public Integer getState() {
        return this.state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
