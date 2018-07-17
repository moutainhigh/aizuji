/**
 * 
 */
package org.gz.warehouse.common.vo;

import org.gz.common.entity.BaseEntity;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2018年3月26日 下午4:42:20
 */
public class SaleProductResp extends BaseEntity {

	private static final long serialVersionUID = 3974761103254810228L;

	private Long productId;//产品ID

	private Long materielBasicId;//物料ID

	private Integer materielModelId;//型号ID

	private String imeiNo;//IMIE号

	private String snNo;//SN号

	private Integer productType;//产品类型（1：租赁；2:以租代购；3：出售）

	public SaleProductResp() {
		
	}
	
	public SaleProductResp(Long productId) {
		this.productId = productId;
	}
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getMaterielBasicId() {
		return materielBasicId;
	}

	public void setMaterielBasicId(Long materielBasicId) {
		this.materielBasicId = materielBasicId;
	}


	public Integer getMaterielModelId() {
		return materielModelId;
	}

	public void setMaterielModelId(Integer materielModelId) {
		this.materielModelId = materielModelId;
	}

	public String getImeiNo() {
		return imeiNo;
	}

	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}

	public String getSnNo() {
		return snNo;
	}

	public void setSnNo(String snNo) {
		this.snNo = snNo;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

}
