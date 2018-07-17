package org.gz.oss.common.entity;

import java.util.Date;

import org.gz.common.entity.BaseEntity;

public class CouponRelation extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	/** 主键id */
    private Long id;

    /** 优惠券id */
    private Long couponId;

    /** 优惠券礼包id */
    private Long backageId;

    /** 优惠券是否使用  0 未使用  1 使用 */
    private Byte couponStatus;

    /** 用户ID */
    private Long userId;

    /** 用户名 */
    private String userName;

    /** 优惠券使用时间 */
    private Date useTime;

    /** 领取时间 */
    private Date receiveTime;

    /** 创建时间 */
    private Date createNo;
    
    /** 是否退还 0 未退还 1 已退还 */
    private Byte isReturn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getBackageId() {
        return backageId;
    }

    public void setBackageId(Long backageId) {
        this.backageId = backageId;
    }

    public Byte getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(Byte couponStatus) {
        this.couponStatus = couponStatus;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Date getCreateNo() {
        return createNo;
    }

    public void setCreateNo(Date createNo) {
		this.createNo = createNo;
	}
    
	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Byte getIsReturn() {
		return isReturn;
	}

	public void setIsReturn(Byte isReturn) {
		this.isReturn = isReturn;
	}
    
}
