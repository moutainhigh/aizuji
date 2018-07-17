package org.gz.liquidation.entity;

import java.util.Date;

import org.gz.common.entity.BaseEntity;

/**
 * 
 * @Description:系统公共配置实体类
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年2月12日 	Created
 */
public class SystemCommonConfigEntity extends BaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8240973487626060602L;

	private Long id;

    /**
     * 配置项名称
     */
    private String configName;

    /**
     * 配置代码
     */
    private String configCode;

    /**
     * 配置群组code
     */
    private String configGroupCode;

    /**
     * 配置群组名称
     */
    private String configGroupName;
    /**
     * 配置内容
     */
    private String configContent;
    /**
     * 类型 0:开关 1:图片 2:文本
     */
    private Integer type;
    /**
     * 状态 0:禁用 1:正常 2:删除
     */
    private Integer status;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createOn;
    /**
     * 更新时间
     */
    private Date updateOn;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getConfigCode() {
		return configCode;
	}

	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}

	public String getConfigGroupCode() {
		return configGroupCode;
	}

	public void setConfigGroupCode(String configGroupCode) {
		this.configGroupCode = configGroupCode;
	}

	public String getConfigGroupName() {
		return configGroupName;
	}

	public void setConfigGroupName(String configGroupName) {
		this.configGroupName = configGroupName;
	}

	public String getConfigContent() {
		return configContent;
	}

	public void setConfigContent(String configContent) {
		this.configContent = configContent;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public Date getUpdateOn() {
		return updateOn;
	}

	public void setUpdateOn(Date updateOn) {
		this.updateOn = updateOn;
	}

}