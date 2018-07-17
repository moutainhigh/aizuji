package org.gz.workorder.backend.dto;


import org.gz.user.entity.AppUser;
public class AppUserExtendDto extends AppUser{

	/**
	 * 
	 */
	private String rentRecordNo;
	public String getRentRecordNo() {
		return rentRecordNo;
	}
	public void setRentRecordNo(String rentRecordNo) {
		this.rentRecordNo = rentRecordNo;
	}
	

}

