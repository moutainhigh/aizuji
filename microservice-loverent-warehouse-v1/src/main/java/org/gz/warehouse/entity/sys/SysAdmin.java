/**
 * 
 */
package org.gz.warehouse.entity.sys;

import javax.validation.constraints.NotBlank;

import org.gz.common.entity.BaseEntity;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2018年1月4日 上午11:06:29
 */
public class SysAdmin extends BaseEntity {

	private static final long serialVersionUID = -7560575977803265612L;

	private Integer id;

	@NotBlank(message="登录名不能为空")
	private String loginName;
	
	@NotBlank(message="登录密码不能为空")
	private String loginPasswd;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPasswd() {
		return loginPasswd;
	}

	public void setLoginPasswd(String loginPasswd) {
		this.loginPasswd = loginPasswd;
	}

}
