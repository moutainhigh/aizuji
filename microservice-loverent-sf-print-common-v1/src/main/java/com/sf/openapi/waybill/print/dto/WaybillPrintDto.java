/**
 * 
 */
package com.sf.openapi.waybill.print.dto;

import org.gz.common.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = false)
public class WaybillPrintDto extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private String mailNo;
	
	private int expressType;
	
	private int payMethod;
	
	private String returnTrackingNo;
	
	private String monthAccount;
	
	private String orderNo;
	
	private String zipCode;
	
	private String destCode;
	
	private String payArea;
	
	private String addedService;
	
	private String childMailNo;
	
	private String mailNoStr;
	
	private String childMailNoStr;
	
	private int piece;
	
	private int childNum;
	
	private String cargo;
	
	private String sku;
	
	private String cargoCount;
	
	private String cargoUnit;
	
	private String remark;
	
	private String insureValue;
	
	private String codValue;
	
	private String deliverCompany;
	
	private String deliverName;
	
	private String deliverMobile;
	
	private String deliverTel;
	
	private String deliverProvince;
	
	private String deliverCity;
	
	private String deliverCounty;
	
	private String deliverAddress;
	
	private String deliverShipperCode;
	
	private String logo;
	
	private String sftelLogo;
	
	private String electric;
	
	private String consignerCompany;
	
	private String consignerName;
	
	private String consignerMobile;
	
	private String consignerTel;
	
	private String consignerProvince;
	
	private String consignerCity;
	
	private String consignerCounty;
	
	private String consignerAddress;
	
	private String consignerShipperCode;
}
