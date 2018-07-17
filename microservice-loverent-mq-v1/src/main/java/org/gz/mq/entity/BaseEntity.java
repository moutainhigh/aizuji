/**
 * 
 */
package org.gz.mq.entity;

import java.io.Serializable;

import org.gz.mq.utils.JsonUtils;

/**
 * 
 * @author hxj
 * @date 2017年11月9日上午11:35:28
 */
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 7473665940638912163L;

	@Override
	public String toString() {
		return JsonUtils.toJsonString(this);
	}
}
