package org.gz.risk.bean;

import java.io.Serializable;
import java.util.Date;

public class ViolationResp implements Serializable{

    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;
    
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

    public String getViolationKey() {
        return violationKey;
    }
    
    public void setViolationKey(String violationKey) {
        this.violationKey = violationKey;
    }
    
    public String getViolationValue() {
        return violationValue;
    }
    
    public void setViolationValue(String violationValue) {
        this.violationValue = violationValue;
    }
    
    public String getMeal() {
        return meal;
    }
    
    public void setMeal(String meal) {
        this.meal = meal;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Integer getBelong() {
        return belong;
    }
    
    public void setBelong(Integer belong) {
        this.belong = belong;
    }
    
    public Date getInputTime() {
        return inputTime;
    }
    
    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }
    
    public String getLoanRecord() {
        return loanRecord;
    }
    
    public void setLoanRecord(String loanRecord) {
        this.loanRecord = loanRecord;
    }

}
