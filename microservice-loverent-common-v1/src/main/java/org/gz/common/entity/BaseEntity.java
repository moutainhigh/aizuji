/**
 * 
 */
package org.gz.common.entity;

import java.io.Serializable;

import org.gz.common.utils.JsonUtils;

/**
 * 
 * @Title: 各种实体类的基类
 * @author hxj
 * @Description:
 * @date 2017年12月4日 下午2:48:23
 */
public class BaseEntity implements Serializable {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = -4225260000145200619L;

	@Override
	public String toString() {
		return JsonUtils.toJsonString(this);
	}

}
