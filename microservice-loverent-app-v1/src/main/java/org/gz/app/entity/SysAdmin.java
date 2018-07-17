/**
 * 
 */
package org.gz.app.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.gz.common.entity.BaseEntity;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月4日 下午4:28:47
 */
public class SysAdmin extends BaseEntity {

	private static final long serialVersionUID = 4246814829334979158L;

	/**
	 * 主键ID
	 */
	private Long id;

	/**
	 * 登录名
	 */
	@NotNull(message = "登录名不能为null")
	@NotBlank(message = "登录名不能为空")
	private String loginName;

	/**
	 * 登录密码
	 */
	@NotNull(message = "登录密码不能为null")
	@NotBlank(message = "登录密码不能为空")
	private String loginPasswd;

	/**
	 * 真实名称
	 */
	private String realName;

	/**
	 * 出生日期:年-月-日
	 */
	private Date bornDate;

	/**
	 * 电话号码
	 */
	private String telephone;

	/**
	 * 有效标志 0:无效，1:有效
	 */
	@NotNull(message = "有效标志不能为null")
	@Range(min = 0, max = 1, message = "非法有效标志")
	private Integer validFlag;

	/**
	 * 记录版本号
	 */
	@NotNull(message = "版本号不能为null")
	@Range(min = 1, message = "非法版本号")
	private Long version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Date getBornDate() {
		return bornDate;
	}

	public void setBornDate(Date bornDate) {
		this.bornDate = bornDate;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Integer getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(Integer validFlag) {
		this.validFlag = validFlag;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}
