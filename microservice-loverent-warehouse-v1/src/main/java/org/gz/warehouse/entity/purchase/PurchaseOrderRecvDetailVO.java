package org.gz.warehouse.entity.purchase;

import javax.validation.constraints.NotBlank;

import org.gz.common.entity.BaseEntity;
import org.hibernate.validator.constraints.Length;

/**
 * @Title: 采购收货内层实体
 * @author hxj
 * @Description:
 * @date 2017年12月20日 下午5:29:16
 */
public class PurchaseOrderRecvDetailVO extends BaseEntity {

	private static final long serialVersionUID = -8011392770694621531L;
	
	@NotBlank(message="商品批次号不能为空")
	@Length(max=50,message="商品批次号长度不超过50个字符")
	private String batchNo;// 商品批次号

	@NotBlank(message="商品SN不能为空")
	@Length(max=50,message="商品SN长度不超过50个字符")
	private String snNo;//商品SN

	@NotBlank(message="商品IMIE号不能为空")
	@Length(max=50,message="商品IMIE号长度不超过50个字符")
	private String imieNo;//商品IMIE号


	public PurchaseOrderRecvDetailVO() {
		
	}
	
	
	public PurchaseOrderRecvDetailVO(String batchNo,String snNo,String imieNo) {
		this.batchNo = batchNo;
		this.snNo = snNo;
		this.imieNo = imieNo;
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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((batchNo == null) ? 0 : batchNo.hashCode());
		result = prime * result + ((imieNo == null) ? 0 : imieNo.hashCode());
		result = prime * result + ((snNo == null) ? 0 : snNo.hashCode());
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
		PurchaseOrderRecvDetailVO other = (PurchaseOrderRecvDetailVO) obj;
		if (batchNo == null) {
			if (other.batchNo != null)
				return false;
		} else if (!batchNo.equals(other.batchNo))
			return false;
		if (imieNo == null) {
			if (other.imieNo != null)
				return false;
		} else if (!imieNo.equals(other.imieNo))
			return false;
		if (snNo == null) {
			if (other.snNo != null)
				return false;
		} else if (!snNo.equals(other.snNo))
			return false;
		return true;
	}
}