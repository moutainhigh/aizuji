/**
 * 
 */
package org.gz.warehouse.entity.purchase;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.gz.common.entity.BaseEntity;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.UniqueElements;

/**
 * @Title: 采购收货外层实体
 * @author hxj
 * @Description:
 * @date 2017年12月20日 下午5:29:16
 */
public class PurchaseOrderRecvVO extends BaseEntity {

	private static final long serialVersionUID = -2645325006539296645L;

	@NotNull(message="采购订单申请明细ID不能为null")
	@Positive(message="非法采购订单申请明细ID值")
	private Long purchaseApplyOrderDetailId;// 采购订单申请明细ID

	private String recvBatchNo;//用于跟踪数据的接收批次号，系统自动生成
	
	@NotEmpty(message="商品明细列表不能为空")
	@UniqueElements(message="商品明细列表不能重复")
	@Valid
	private List<PurchaseOrderRecvDetailVO> detailList;//商品明细

	public PurchaseOrderRecvVO() {
		
	}
	
	
	public PurchaseOrderRecvVO(Long purchaseApplyOrderDetailId,String recvBatchNo,List<PurchaseOrderRecvDetailVO> detailList) {
		super();
		this.purchaseApplyOrderDetailId = purchaseApplyOrderDetailId;
		this.recvBatchNo = recvBatchNo;
		this.detailList = detailList;
	}


	public Long getPurchaseApplyOrderDetailId() {
		return purchaseApplyOrderDetailId;
	}

	public void setPurchaseApplyOrderDetailId(Long purchaseApplyOrderDetailId) {
		this.purchaseApplyOrderDetailId = purchaseApplyOrderDetailId;
	}

	public String getRecvBatchNo() {
		return recvBatchNo;
	}

	public void setRecvBatchNo(String recvBatchNo) {
		this.recvBatchNo = recvBatchNo;
	}

	public List<PurchaseOrderRecvDetailVO> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<PurchaseOrderRecvDetailVO> detailList) {
		this.detailList = detailList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((purchaseApplyOrderDetailId == null) ? 0 : purchaseApplyOrderDetailId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PurchaseOrderRecvVO other = (PurchaseOrderRecvVO) obj;
		if (purchaseApplyOrderDetailId == null) {
			if (other.purchaseApplyOrderDetailId != null)
				return false;
		} else if (!purchaseApplyOrderDetailId.equals(other.purchaseApplyOrderDetailId))
			return false;
		return true;
	}

	
}