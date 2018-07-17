/**
 * 
 */
package org.gz.warehouse.entity.purchase;

import javax.validation.constraints.NotNull;

import org.gz.common.entity.BaseEntity;
import org.hibernate.validator.constraints.Range;

/**
 * @Title: 采购申请审批明细请求对象
 * @author hxj
 * @Description:
 * @date 2017年12月19日 下午1:44:02
 */
public class PurchaseApprovedOrderDetailVO extends BaseEntity {

	private static final long serialVersionUID = -5805457009520546662L;

	@NotNull(message = "物料采购申请单明细ID不能为空")
	@Range(min = 1, message = "非法物料采购申请单明细ID")
	private Long id;

	private Integer approvedQuantity;// 批准数量

	public PurchaseApprovedOrderDetailVO() {
		
	}
	
	public PurchaseApprovedOrderDetailVO(Integer approvedQuantity) {
		this.approvedQuantity=approvedQuantity;
	}
	
	public PurchaseApprovedOrderDetailVO(Long id,Integer approvedQuantity) {
		this.id = id;
		this.approvedQuantity=approvedQuantity;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getApprovedQuantity() {
		return approvedQuantity;
	}

	public void setApprovedQuantity(Integer approvedQuantity) {
		this.approvedQuantity = approvedQuantity;
	}

}
