/**
 * 
 */
package org.gz.warehouse.entity.supplier;

import org.gz.common.entity.BaseEntity;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月28日 下午1:43:32
 */
public class SupplierBasicInfo extends BaseEntity {

	private static final long serialVersionUID = 4494616378284832509L;

	private Integer id;// 供应商ID

	private String supplierName;// 供应商名称

	private String supplierCode;// 供应商编码

	private String supplierAddress;// 供应商地址

	private String contact;// 联系人

	private String contactTel;// 联系电话

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierCode() {
		return supplierCode;
	}

	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	public String getSupplierAddress() {
		return supplierAddress;
	}

	public void setSupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

}
