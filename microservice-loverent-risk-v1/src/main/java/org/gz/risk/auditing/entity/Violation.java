package org.gz.risk.auditing.entity;

import org.gz.risk.bean.ViolationResp;

/**
 * @author JarkimZhu
 *         Created on 2017/1/17.
 * @since jdk1.8
 */
public class Violation extends ViolationEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String content;

    public Violation() {}

    public Violation(ViolationResp entity) {
        setViolationId(entity.getViolationId());
        setViolationKey(entity.getViolationKey());
        setViolationValue(entity.getViolationValue());
        setBelong(entity.getBelong());
        setUserId(entity.getUserId());
        setMeal(entity.getMeal());
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
