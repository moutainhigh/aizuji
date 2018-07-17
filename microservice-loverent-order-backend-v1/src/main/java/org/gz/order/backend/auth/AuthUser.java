package org.gz.order.backend.auth;

import org.gz.common.entity.BaseEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

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
    private Integer id;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
