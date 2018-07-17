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
 * ProductColumnRelation  Dto 对象
 * 
 * @author zhangguoliang
 * @date 2017-12-19
 */
public class ProductColumnRelationDto{

    /**
     * id自增主键 
     */
    private Integer id;
    /**
     * 配置栏目id 
     */
    private Integer columnId;
    /**
     * 产品id 
     */
    private Integer productId;
    /**
     * 产品名字 
     */
    private String productName;
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
    public void setColumnId(Integer columnId) {
        this.columnId = columnId;
    }

    public Integer getColumnId() {
        return this.columnId;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductId() {
        return this.productId;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return this.productName;
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

