/**
 * 
 */
package org.gz.warehouse.common.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.gz.common.entity.BaseEntity;
import org.hibernate.validator.constraints.Range;

/**
 * @Title:撤销拣货实体
 * @author hxj
 * @date 2017年12月29日 上午9:51:55
 */
public class UndoPickReq extends BaseEntity {

	private static final long serialVersionUID = -4557417882850557988L;

	@NotNull(message = "销售单号不能为null")
	@NotBlank(message = "销售单号不能为空")
	private String sourceOrderNo;// 销售单号

	@NotNull(message = "物料ID不能为null")
	@Positive(message = "物料ID必须为正值")
	private Long materielBasicId;// 物料ID

	@NotNull(message="产品ID不能为空")
	@Positive(message="非法产品ID")
	private Long productId;// 产品ID

	@NotNull(message="订单来源不能为空")
	@Range(min=1,max=2,message="非法订单来源")
	private Integer orderSource;//订单来源 1：APP,2:小程序

	public String getSourceOrderNo() {
		return sourceOrderNo;
	}

	public void setSourceOrderNo(String sourceOrderNo) {
		this.sourceOrderNo = sourceOrderNo;
	}

	public Long getMaterielBasicId() {
		return materielBasicId;
	}

	public void setMaterielBasicId(Long materielBasicId) {
		this.materielBasicId = materielBasicId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(Integer orderSource) {
		this.orderSource = orderSource;
	}
	
	
}
