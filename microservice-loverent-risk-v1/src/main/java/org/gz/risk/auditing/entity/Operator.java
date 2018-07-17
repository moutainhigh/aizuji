package org.gz.risk.auditing.entity;

import java.io.Serializable;

/**
 * @author JarkimZhu
 *         Created on 2017/2/20.
 * @since jdk1.8
 */
public enum Operator implements Serializable{
    EQUAL,
    GREATER,
    LESS,
    IN,
    HIT
    ;
}
