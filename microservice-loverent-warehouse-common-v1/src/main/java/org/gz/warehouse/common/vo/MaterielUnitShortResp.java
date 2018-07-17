/**
 * 
 */
package org.gz.warehouse.common.vo;

import org.gz.common.entity.BaseEntity;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2018年4月3日 上午9:01:36
 */
public class MaterielUnitShortResp extends BaseEntity {

	private static final long serialVersionUID = -1439345836259240157L;

	private Integer id;// 主键

	private String unitName;// 单位名称

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
	
	
}
