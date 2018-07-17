/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.oss.common.entity;

import java.util.Date;
import org.gz.common.entity.BaseEntity;

/**
 * ProductColumnRelation 实体类
 * 
 * @author zhangguoliang
 * @date 2017-12-19
 */
public class ProductColumnRelation extends BaseEntity{

	private static final long serialVersionUID = -6095463729488201156L;
	
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
    private Integer materielModelId;
    /**
     * 产品名字 
     */
    private String materielModelName;
    /**
     * 是否删除(0为正常，1为删除) 
     */
    private Integer isDeleted;
    /**
     * 排序号 
     */
    private Integer sortNum;
    /**
     * 图片url 
     */
    private String photoUrl;
    /**
     * 月租
     */
    private Integer price;
    /**
     * 创建时间 
     */
    private Date createTime;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
    public void setColumnId(Integer columnId) {
        this.columnId = columnId;
    }
    
    public Integer getColumnId() {
        return this.columnId;
    }
    
    public Integer getMaterielModelId() {
		return materielModelId;
	}

	public void setMaterielModelId(Integer materielModelId) {
		this.materielModelId = materielModelId;
	}
    
    public String getMaterielModelName() {
		return materielModelName;
	}

	public void setMaterielModelName(String materielModelName) {
		this.materielModelName = materielModelName;
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

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
    
}
