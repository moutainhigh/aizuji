/**
 * 
 */
package org.gz.oss.common.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.gz.common.entity.BaseEntity;

/**
 * @Title: 售卖商品统计请求
 * @author hxj
 * @Description:
 * @date 2018年4月2日 上午10:59:40
 */
public class SaleProductCountReq extends BaseEntity {

	private static final long serialVersionUID = 1419214118977023958L;

	@NotNull(message = "售卖产品ID不能为null")
	private List<Long> productIds;// 售卖产品ID

	public List<Long> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<Long> productIds) {
		this.productIds = productIds;
	}

}
