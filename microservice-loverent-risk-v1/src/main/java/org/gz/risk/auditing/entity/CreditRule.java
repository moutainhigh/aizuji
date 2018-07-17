package org.gz.risk.auditing.entity;

import java.io.Serializable;

/**
 * @author JarkimZhu
 *         Created on 2017/2/20.
 * @since jdk1.8
 */
public class CreditRule<E, T> implements Serializable{
    private String ruleName;
    private E leftFactor;
    private T rightFactor;
    private Operator operator;
    private String description;

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public E getLeftFactor() {
        return leftFactor;
    }

    public void setLeftFactor(E leftFactor) {
        this.leftFactor = leftFactor;
    }

    public T getRightFactor() {
        return rightFactor;
    }

    public void setRightFactor(T rightFactor) {
        this.rightFactor = rightFactor;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
