package org.gz.user.entity;

import java.util.Date;

public class AddressInfo {
	/**ID*/
    private Long addrId;
    /**用户ID*/
    private Long userId;
    /**省*/
    private String addrProvince;
    /**市*/
    private String addrCity;
    /**区域*/
    private String addrArea;
    /**详细地址*/
    private String addrDetail;
    /**1-正常2-停用*/
    private Integer addrStatus;

    private Date createTime;

    private Date updateTime;

	public Long getAddrId() {
		return addrId;
	}

	public void setAddrId(Long addrId) {
		this.addrId = addrId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAddrProvince() {
		return addrProvince;
	}

	public void setAddrProvince(String addrProvince) {
		this.addrProvince = addrProvince;
	}

	public String getAddrCity() {
		return addrCity;
	}

	public void setAddrCity(String addrCity) {
		this.addrCity = addrCity;
	}

	public String getAddrArea() {
		return addrArea;
	}

	public void setAddrArea(String addrArea) {
		this.addrArea = addrArea;
	}

	public String getAddrDetail() {
		return addrDetail;
	}

	public void setAddrDetail(String addrDetail) {
		this.addrDetail = addrDetail;
	}

	public Integer getAddrStatus() {
		return addrStatus;
	}

	public void setAddrStatus(Integer addrStatus) {
		this.addrStatus = addrStatus;
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