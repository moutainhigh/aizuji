package org.gz.warehouse.entity;

import javax.validation.constraints.NotBlank;

import org.gz.common.entity.BaseEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

/**
 * 物料规格实体
 * 
 * @author hxj
 *
 */
public class MaterielSpec extends BaseEntity {

	private static final long serialVersionUID = 1871098611730395523L;

	private Integer id;// 主键

	@NotBlank(message = "规格名称不能为空")
	@Length(min = 1, max = 30, message = "规格名称长度不超过30个字符")
	private String specName;// 规格名称

	@Length(min = 1, max = 50, message = "备注名称长度不超过50个字符")
	private String remark;// 备注

	@Range(min=0,max=1,message="非法启用标志值")
	private Integer enableFlag=0;//启用标志 0:否 1:是
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(Integer enableFlag) {
		this.enableFlag = enableFlag;
	}
	
}
