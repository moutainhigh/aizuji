/**
 * 
 */
package org.gz.warehouse.common.vo;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.gz.common.entity.QueryPager;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2018年3月26日 下午4:35:53
 */
public class SaleProductReq extends QueryPager{
	
	private static final long serialVersionUID = -2329388229499683983L;
	
	
	@NotNull(message="产品ID列表不能为null")
	@NotEmpty(message="产品ID列表不能为空")
	private List<Long> productIds;

	public List<Long> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<Long> productIds) {
		this.productIds = productIds;
	}
}