/**
 * 
 */
package org.gz.warehouse.entity;

import org.gz.common.entity.BaseEntity;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月27日 下午3:40:50
 */
public class Attachment extends BaseEntity {

	private static final long serialVersionUID = 3415698560698745550L;

	private String attaUrl;

	public String getAttaUrl() {
		return attaUrl;
	}

	public void setAttaUrl(String attaUrl) {
		this.attaUrl = attaUrl;
	}

}
