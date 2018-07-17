package org.gz.warehouse.entity;

import java.util.Date;

import org.gz.common.entity.BaseEntity;

/**
 * 库存基础实体类
 * 
 * @author hxj
 *
 */
public class WarehouseBaseEntity extends BaseEntity {

	private static final long serialVersionUID = -5588264367523640087L;

	protected Long id;

	protected Long createBy;

	protected Date createOn = new Date();

	protected Long updateBy;

	protected Date updateOn = new Date();

	protected Long version = 1L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateOn() {
		return updateOn;
	}

	public void setUpdateOn(Date updateOn) {
		this.updateOn = updateOn;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
	
}
