package org.gz.oss.common.entity;

import java.util.Date;

import org.gz.common.entity.BaseEntity;

public class CouponUseDetail extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	/** 主键 */
    private Long id;

    /** 优惠券id */
    private Long couponId;

    /** 用户id */
    private Long userId;

    /** 用户名 */
    private String userName;

    /** 订单id */
    private Long orderId;

    /** 订单编号 */
    private String orderNo;

    /** 商品 */
    private String goodsDetail;

    /** 消费类型 1:租赁 2:以租代购 3:出售 */
    private Integer consumeType;

    /** 领取时间 */
    private Date receiveTime;

    /** 使用时间 */
    private Date useTime;

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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail == null ? null : goodsDetail.trim();
    }

    public Integer getConsumeType() {
        return consumeType;
    }

    public void setConsumeType(Integer consumeType) {
        this.consumeType = consumeType;
    }

    public Date getCreateNo() {
        return createNo;
    }

    public void setCreateNo(Date createNo) {
        this.createNo = createNo;
    }

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	public Byte getIsReturn() {
		return isReturn;
	}

	public void setIsReturn(Byte isReturn) {
		this.isReturn = isReturn;
	}
	
}
