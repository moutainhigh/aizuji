package org.gz.oss.common.entity;

import java.util.Date;
import java.util.List;

import org.gz.common.entity.BaseEntity;

public class CouponBackage extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	/** 主键 */
    private Long id;

    /** 优惠券礼包名称 */
    private String name;

    /** 领券资格 10 新注册用户 20 指定发放 30 所有用户 */
    private Byte qualifications;

    /** 有效期开始时间 */
    private Date validityStartTime;

    /** 有效期结束时间 */
    private Date validityEndTime;

    /** 表数据是否删除  1 删除  0 未删 */
    private Byte status;

    /** 创建时间 */
    private Date createNo;

    /** 更新时间 */
    private Date updateNo;
    
    /** 优惠券列表*/
    private List<Coupon> couponList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getQualifications() {
        return qualifications;
    }

    public void setQualifications(Byte qualifications) {
        this.qualifications = qualifications;
    }

    public Date getValidityStartTime() {
        return validityStartTime;
    }

    public void setValidityStartTime(Date validityStartTime) {
        this.validityStartTime = validityStartTime;
    }

    public Date getValidityEndTime() {
        return validityEndTime;
    }

    public void setValidityEndTime(Date validityEndTime) {
        this.validityEndTime = validityEndTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreateNo() {
        return createNo;
    }

    public void setCreateNo(Date createNo) {
        this.createNo = createNo;
    }

    public Date getUpdateNo() {
        return updateNo;
    }

    public void setUpdateNo(Date updateNo) {
        this.updateNo = updateNo;
    }

	public List<Coupon> getCouponList() {
		return couponList;
	}

	public void setCouponList(List<Coupon> couponList) {
		this.couponList = couponList;
	}
    
}