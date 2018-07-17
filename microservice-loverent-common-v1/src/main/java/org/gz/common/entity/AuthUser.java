package org.gz.common.entity;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

/**
 * 访问授权用户实体类
 * @author phd
 */
public class AuthUser extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 主键Id
     */
    private Long id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
	@Length(min = 1, max = 10, message = "用户名长度不超过10个字符")
    private String userName;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
	@Length(min = 1, max = 20, message = "密码长度不超过20个字符")
    private String passWord;

	public AuthUser() {

	}

	public AuthUser(Long id, String userName) {
		this.id = id;
		this.userName = userName;
	}

	public AuthUser(Long id, String userName, String password) {
		this(id, userName);
		this.passWord = password;
	}
	
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

  
}
