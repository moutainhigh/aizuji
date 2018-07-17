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
 * Banner 实体类
 * 
 * @author zhangguoliang
 * @date 2017-12-15
 */
public class BannerV1 extends BaseEntity{

	private static final long serialVersionUID = 2516221028967275592L;
	
	/**
     * 主键Id 
     */
    private Integer id;
    /**
     * 条幅图标路径 
     */
    private String bannerImgUrl;
    /**
     * 链接路径或者商品id 
     */
    private String linkValue;
    /**
     * 排序号 
     */
    private Integer sortNum;
    /**
     * 资源类型，1-banner链接，2-banner商品，3-新潮er
     */
    private Integer resource;
    /**
     * 添加人Id 
     */
    private Integer createUserId;
    /**
     * 修改人Id 
     */
    private Integer updateUserId;
    /**
     * 创建时间 
     */
    private Date createTime;
    /**
     * 最后修改时间 
     */
    private Date lastUpdateTime;
    /**
     * 是否删除(0为正常，1为删除) 
     */
    private Integer isDeleted;
    
    /*
     * 冗余字段
     */
    /**
     * 配置类型 
     */
    private String resourceStr;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setBannerImgUrl(String bannerImgUrl) {
        this.bannerImgUrl = bannerImgUrl;
    }
    
    public String getBannerImgUrl() {
        return this.bannerImgUrl;
    }
    
    public void setLinkValue(String linkValue) {
        this.linkValue = linkValue;
    }
    
    public String getLinkValue() {
        return this.linkValue;
    }
    
    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }
    
    public Integer getSortNum() {
        return this.sortNum;
    }
    
    public void setResource(Integer resource) {
        this.resource = resource;
    }
    
    public Integer getResource() {
        return this.resource;
    }
    
    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }
    
    public Integer getCreateUserId() {
        return this.createUserId;
    }
    
    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }
    
    public Integer getUpdateUserId() {
        return this.updateUserId;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
    
    public Date getLastUpdateTime() {
        return this.lastUpdateTime;
    }
    
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
    
    public Integer getIsDeleted() {
        return this.isDeleted;
    }

	public String getResourceStr() {
		if(this.resource==null){
			return "";
		}
		switch (this.resource) {
		case 1:
			return "banner链接";
		case 2:
			return "banner商品";
		case 3:
			return "新潮er";
		default:
			return "";
		}
	}
    
}
