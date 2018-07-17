/**
 * 
 */
package org.gz.warehouse.common.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.gz.common.entity.BaseEntity;

/**
 * @Title: 库存检货状态查询
 * @author hxj
 * @Description: 
 * @date 2017年12月29日 下午3:12:00
 */
public class WarehousePickQuery extends BaseEntity {

	private static final long serialVersionUID = 6189202978047127104L;
	
	@NotNull(message="订单号不能为null")
	@NotBlank(message="订单号不能为空")
	private String sourceOrderNo;

	public String getSourceOrderNo() {
		return sourceOrderNo;
	}

	public void setSourceOrderNo(String sourceOrderNo) {
		this.sourceOrderNo = sourceOrderNo;
	}
	
}
