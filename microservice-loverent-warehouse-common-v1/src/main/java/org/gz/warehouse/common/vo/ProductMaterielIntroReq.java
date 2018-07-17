/**
 * 
 */
package org.gz.warehouse.common.vo;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.gz.common.entity.BaseEntity;

/**
 * @Title: 产品物料介绍请求对象
 * @author hxj
 * @Description: 
 * @date 2018年4月3日 下午3:59:28
 */
public class ProductMaterielIntroReq extends BaseEntity {

	private static final long serialVersionUID = 1284482098810039211L;
	
	@NotNull(message="产品ID列表不能为空")
	private List<Long> productIds;

	public List<Long> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<Long> productIds) {
		this.productIds = productIds;
	}
}
