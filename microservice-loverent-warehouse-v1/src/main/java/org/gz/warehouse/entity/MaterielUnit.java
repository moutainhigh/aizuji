package org.gz.warehouse.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.gz.common.entity.BaseEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 物料单位类
 * 
 * @author hxj
 *
 */
@ApiModel(value = "物料单位")
public class MaterielUnit extends BaseEntity {

	private static final long serialVersionUID = 5451687779169049756L;

	@ApiModelProperty(value = "主键,自动递增", required = true)
	private Integer id;// 主键

	@ApiModelProperty(value = "单位名称,最长不超过30个字符", required = true)
	@NotBlank(message = "单位名称不能为空")
	@Length(min = 1, max = 30, message = "单位名称长度不超过30个字符")
	private String unitName;// 单位名称

	@ApiModelProperty(value = "单位编码,最长不超过30个字符", required = true)
	@NotBlank(message = "单位编码不能为空")
	@Length(min = 1, max = 30, message = "单位编码长度不超过30个字符")
	private String unitCode;// 单位编码

	@ApiModelProperty(value = "默认标志，可选值为0,1，默认为0", required = true)
	@NotNull(message = "默认值不能为null")
	@Range(min = 0, max = 1, message = "非法默认值")
	private Integer enableFlag = 0;// 是否默认 0:否 1:是

	@ApiModelProperty(value = "备注,长度不超过255个字符", required = false)
	@Length(min = 1, max = 255, message = "备注长度不超过255个字符")
	private String remark;// 备注

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public Integer getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(Integer enableFlag) {
		this.enableFlag = enableFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
