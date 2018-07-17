/**
 * 
 */
package org.gz.warehouse.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.gz.common.entity.BaseEntity;
import org.gz.common.hv.group.UpdateRecordGroup;
import org.hibernate.validator.constraints.Length;

/**
 * @Title:
 * @author hxj
 * @date 2018年3月2日 下午2:03:49
 */
public class MaterielNewConfig extends BaseEntity {

	private static final long serialVersionUID = -6300351340335194216L;

	@NotNull(groups = UpdateRecordGroup.class, message = "更新时，主键ID不能为null")
	private Integer id;// 主键

	@NotNull(message = "配置名称不能为空")
	@NotBlank(message = "配置名称不能为空")
	@Length(max = 20, message = "配置名称长度最大不超过20个字符")
	private String configName;// 配置名称

	@NotNull(message = "配置值不能为空")
	@NotBlank(message = "配置值不能为空")
	@Length(max = 20, message = "配置值长度最大不超过20个字符")
	private String configValue;// 配置值

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

}
