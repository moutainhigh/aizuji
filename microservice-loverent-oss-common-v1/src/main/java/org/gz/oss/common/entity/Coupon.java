package org.gz.oss.common.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.gz.common.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Coupon extends BaseEntity{
	
	private static final long serialVersionUID = 2516221028967275592L;
	
	/** 主键 */
    private Long id;

    /** 优惠券名称 */
    private String name;

    /** 优惠券礼包中的优惠券是否使用  0 未使用  1使用 */
    private Byte status;

    /** 金额 */
    private Integer amount;

    /** 优惠券类型  10 通用  20 售卖 30 租赁-买断 31 租赁-首期款 32 租赁-交租 */
    private Byte couponType;

    /** 是否礼包  1 是  0 否 */
    private Byte isBackage;

    /** 礼包id */
    private Long backageId;

    /** 有效期开始时间 */
    private Date validityStartTime;

    /** 有效期结束时间 */
    private Date validityEndTime;

    /** 领券资格 10 新注册用户 20 指定发放 30 所有用户 */
    private Byte qualifications;

    /** 领券规则详情 */
    private String description;

    /** 表数据是否删除   1 删除  0 未删 */
    private Byte flag;

    /** 创建时间 */
    private Date createNo;

    /** 更新时间 */
    private Date updateNo;
    
    //新增字段 2018-3-26
    /** 领券开始时间 */
    private Date receiveStartTime;

    /** 领券结束时间 */
    private Date receiveEndTime;
    
    
    //扩展字段  已领数量
    /** 已领数量 */
    private Integer hasNum;

	/** 已使用数量 */
    private Integer useNum;
    
    /** 优惠券状态  10 待生效  20 已生效   30 已过期*/ 
    private Byte couponStatus;
    
    /** 优惠券类型列表*/
    private List<CouponType> couponTypeList;

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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getCouponType() {
        return couponType;
    }

    public void setCouponType(Byte couponType) {
        this.couponType = couponType;
    }

    public Byte getIsBackage() {
        return isBackage;
    }

    public void setIsBackage(Byte isBackage) {
        this.isBackage = isBackage;
    }

    public Long getBackageId() {
        return backageId;
    }

    public void setBackageId(Long backageId) {
        this.backageId = backageId;
    }

    public Byte getQualifications() {
        return qualifications;
    }

    public void setQualifications(Byte qualifications) {
        this.qualifications = qualifications;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Byte getFlag() {
        return flag;
    }

    public void setFlag(Byte flag) {
        this.flag = flag;
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

	public Integer getHasNum() {
		return hasNum;
	}

	public void setHasNum(Integer hasNum) {
		this.hasNum = hasNum;
	}

	public Integer getUseNum() {
		return useNum;
	}

	public void setUseNum(Integer useNum) {
		this.useNum = useNum;
	}

	public Byte getCouponStatus() {
		return couponStatus;
	}

	public void setCouponStatus(Byte couponStatus) {
		this.couponStatus = couponStatus;
	}

	public List<CouponType> getCouponTypeList() {
		return couponTypeList;
	}

	public void setCouponTypeList(List<CouponType> couponTypeList) {
		this.couponTypeList = couponTypeList;
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
	
	public Date getReceiveStartTime() {
		return receiveStartTime;
	}

	public void setReceiveStartTime(Date receiveStartTime) {
		this.receiveStartTime = receiveStartTime;
	}

	public Date getReceiveEndTime() {
		return receiveEndTime;
	}

	public void setReceiveEndTime(Date receiveEndTime) {
		this.receiveEndTime = receiveEndTime;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

}