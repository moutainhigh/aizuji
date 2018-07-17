/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.oss.common.dto;
import java.util.Date;

/**
 * ProductColumn  Dto 对象
 * 
 * @author zhangguoliang
 * @date 2017-12-19
 */
public class ProductColumnDto{

    /**
     * id自增主键 
     */
    private Integer id;
    /**
     * 栏目名称 
     */
    private String columnName;
    /**
     * 是否删除(0为正常，1为删除) 
     */
    private Integer isDeleted;
    /**
     * 排序号 
     */
    private Integer sortNum;
    /**
     * 创建时间 
     */
    private Date createTime;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return this.columnName;
    }
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getIsDeleted() {
        return this.isDeleted;
    }
    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public Integer getSortNum() {
        return this.sortNum;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return this.createTime;
    }
}

