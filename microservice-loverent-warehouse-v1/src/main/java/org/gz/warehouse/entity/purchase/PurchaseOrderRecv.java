/**
 * 
 */
package org.gz.warehouse.entity.purchase;

import org.gz.common.entity.BaseEntity;

/**
 * @Title: 采购收货实体
 * @author hxj
 * @Description:
 * @date 2017年12月20日 下午5:29:16
 */
public class PurchaseOrderRecv extends BaseEntity {

	private static final long serialVersionUID = -2645325006539296645L;

	private Long id;
	
	private Long purchaseApplyOrderDetailId;// 采购订单申请明细ID

	private String recvBatchNo;//用于跟踪数据的接收批次号，系统自动生成
	
	private String batchNo;// 商品批次号

	private String snNo;//商品SN

	private String imieNo;//商品IMIE号
	
	public PurchaseOrderRecv() {
		
	}
	
	
	public PurchaseOrderRecv(Long purchaseApplyOrderDetailId, String recvBatchNo, String batchNo, String snNo,
			String imieNo) {
		super();
		this.purchaseApplyOrderDetailId = purchaseApplyOrderDetailId;
		this.recvBatchNo = recvBatchNo;
		this.batchNo = batchNo;
		this.snNo = snNo;
		this.imieNo = imieNo;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPurchaseApplyOrderDetailId() {
		return purchaseApplyOrderDetailId;
	}

	public void setPurchaseApplyOrderDetailId(Long purchaseApplyOrderDetailId) {
		this.purchaseApplyOrderDetailId = purchaseApplyOrderDetailId;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getSnNo() {
		return snNo;
	}

	public void setSnNo(String snNo) {
		this.snNo = snNo;
	}

	public String getImieNo() {
		return imieNo;
	}

	public void setImieNo(String imieNo) {
		this.imieNo = imieNo;
	}

	public String getRecvBatchNo() {
		return recvBatchNo;
	}

	public void setRecvBatchNo(String recvBatchNo) {
		this.recvBatchNo = recvBatchNo;
	}


}