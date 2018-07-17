/**
 * 
 */
package org.gz.warehouse.common.vo;

import javax.validation.constraints.NotBlank;

import org.gz.common.entity.BaseEntity;

/**
 * @Title: 物料型号查询实体
 * @author hxj
 * @Description:
 * @date 2018年1月4日 上午9:57:49
 */
public class MaterielModelReq extends BaseEntity {

	private static final long serialVersionUID = -1313546436701827930L;

	@NotBlank(message = "型号ID字符串不能为空")
	private String ids;// 以逗号分隔的型号ID字符串

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
}
