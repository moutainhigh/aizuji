/**
 * 
 */
package org.gz.warehouse.common.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.gz.common.entity.QueryPager;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2018年3月14日 下午4:19:21
 */
public class WarehouseCommodityReq extends QueryPager {

	private static final long serialVersionUID = -6553721367961987225L;

	@NotNull(message = "物料ID不能为null")
	@Positive(message = "物料ID必须为正值")
	private Long materielBasicInfoId;// 物料ID

	private WarehouseCodeEnum warehouseCodeEnum = WarehouseCodeEnum.NEW;// 仓库编码枚举,默认为新机仓

	private WarehouseLocationCodeEnum warehouseLocationCodeEnum = WarehouseLocationCodeEnum.AVAILABLE;// 仓位编码枚举，默认为可售仓位

	private String warehouseCode;

	private String warehouseLocationCode;

	public WarehouseCommodityReq() {
		
	}
	
	public WarehouseCommodityReq(Long materielBasicInfoId) {
		this.materielBasicInfoId=materielBasicInfoId;
	}

	public WarehouseCommodityReq(Long materielBasicInfoId,WarehouseCodeEnum warehouseCodeEnum,WarehouseLocationCodeEnum warehouseLocationCodeEnum) {
		this.materielBasicInfoId=materielBasicInfoId;
		this.warehouseCodeEnum=warehouseCodeEnum;
		this.warehouseLocationCodeEnum=warehouseLocationCodeEnum;
		this.warehouseCode=warehouseCodeEnum.getCode();
		this.warehouseLocationCode=warehouseLocationCodeEnum.getCode();
	}
	
	public WarehouseCommodityReq(Long materielBasicInfoId,WarehouseCodeEnum warehouseCodeEnum,WarehouseLocationCodeEnum warehouseLocationCodeEnum,int currPage,int pageSize) {
		this(materielBasicInfoId,warehouseCodeEnum,warehouseLocationCodeEnum);
		this.currPage=currPage<1?1:currPage;
		this.pageSize=pageSize<1?1:pageSize;
	}
	
	
	public Long getMaterielBasicInfoId() {
		return materielBasicInfoId;
	}

	public void setMaterielBasicInfoId(Long materielBasicInfoId) {
		this.materielBasicInfoId = materielBasicInfoId;
	}

	public WarehouseCodeEnum getWarehouseCodeEnum() {
		return warehouseCodeEnum;
	}

	public void setWarehouseCodeEnum(WarehouseCodeEnum warehouseCodeEnum) {
		this.warehouseCodeEnum = warehouseCodeEnum;
	}

	public WarehouseLocationCodeEnum getWarehouseLocationCodeEnum() {
		return warehouseLocationCodeEnum;
	}

	public void setWarehouseLocationCodeEnum(WarehouseLocationCodeEnum warehouseLocationCodeEnum) {
		this.warehouseLocationCodeEnum = warehouseLocationCodeEnum;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getWarehouseLocationCode() {
		return warehouseLocationCode;
	}

	public void setWarehouseLocationCode(String warehouseLocationCode) {
		this.warehouseLocationCode = warehouseLocationCode;
	}

}
