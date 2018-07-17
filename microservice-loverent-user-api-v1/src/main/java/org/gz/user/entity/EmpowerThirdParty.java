package org.gz.user.entity;

import java.util.Date;

/**
 * 第三方授权登录
 * 
 * @author yangdx
 *
 */
public class EmpowerThirdParty {
	/**用户ID*/
	private Long userId;
	/**支付宝user_id*/
	private String alipayUserId;
	/**支付宝头像*/
	private String alipayAvatar;
	/**支付宝昵称*/
	private String alipayNickName;
	/**微信openid*/
	private String weixniOpenId;
	/**微信头像*/
	private String weixinAvatar;
	/**微信昵称*/
	private String weixinNickName;
	
	private Date createTime;
	
	private Date updateTime;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAlipayUserId() {
		return alipayUserId;
	}

	public void setAlipayUserId(String alipayUserId) {
		this.alipayUserId = alipayUserId;
	}

	public String getAlipayAvatar() {
		return alipayAvatar;
	}

	public void setAlipayAvatar(String alipayAvatar) {
		this.alipayAvatar = alipayAvatar;
	}

	public String getAlipayNickName() {
		return alipayNickName;
	}

	public void setAlipayNickName(String alipayNickName) {
		this.alipayNickName = alipayNickName;
	}

	public String getWeixniOpenId() {
		return weixniOpenId;
	}

	public void setWeixniOpenId(String weixniOpenId) {
		this.weixniOpenId = weixniOpenId;
	}

	public String getWeixinAvatar() {
		return weixinAvatar;
	}

	public void setWeixinAvatar(String weixinAvatar) {
		this.weixinAvatar = weixinAvatar;
	}

	public String getWeixinNickName() {
		return weixinNickName;
	}

	public void setWeixinNickName(String weixinNickName) {
		this.weixinNickName = weixinNickName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
