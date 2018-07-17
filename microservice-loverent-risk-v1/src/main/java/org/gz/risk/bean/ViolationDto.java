/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.risk.bean;
import java.util.Date;

/**
 * Violation  Dto 对象
 * 
 * @author zhangguoliang
 * @date 2017-07-19
 */
public class ViolationDto extends BaseDto {

    /**
     * id 
     */
    private Long violationId;
    /**
     * 违规键 
     */
    private String violationKey;
    /**
     * 违规值 
     */
    private String violationValue;
    /**
     * 违规连接 
     */
    private String meal;
    /**
     * 用户id 
     */
    private Long userId;
    /**
     * 属于哪个平台 
     */
    private Integer belong;
    /**
     * 录入时间 
     */
    private Date inputTime;
    
    /**
     * loanRecord 
     */
    private String loanRecord;

    
    public Long getViolationId() {
        return violationId;
    }

    public void setViolationId(Long violationId) {
        this.violationId = violationId;
    }

    public void setViolationKey(String violationKey) {
        this.violationKey = violationKey;
    }

    public String getViolationKey() {
        return this.violationKey;
    }
    public void setViolationValue(String violationValue) {
        this.violationValue = violationValue;
    }

    public String getViolationValue() {
        return this.violationValue;
    }
    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getMeal() {
        return this.meal;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return this.userId;
    }
    public void setBelong(Integer belong) {
        this.belong = belong;
    }

    public Integer getBelong() {
        return this.belong;
    }
    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }

    public Date getInputTime() {
        return this.inputTime;
    }

    public String getLoanRecord() {
        return loanRecord;
    }
    
    public void setLoanRecord(String loanRecord) {
        this.loanRecord = loanRecord;
    }
}

