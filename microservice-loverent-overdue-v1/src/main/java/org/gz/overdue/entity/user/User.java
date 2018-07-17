package org.gz.overdue.entity.user;

import org.gz.common.entity.BaseEntity;

public class User extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7063653266396818860L;
	
	
	private Long id = 1L;
	
	private String userName = "admin";
	
	private String password = "admin";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
