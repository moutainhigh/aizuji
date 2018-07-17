package org.gz.liquidation.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.gz.common.entity.BaseEntity;

/**
 * 
 * @Description:优惠券使用记录实体
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月26日 	Created
 */
public class CouponUseLogEntity extends BaseEntity {
    /**
     * id
     */
    private Long id;

    /**
     * 优惠券id
     */
    private Long couponId;
    private String orderSN;
    private Long userId;

    /**
     * 用户姓名
     */
    private String realName;

    private String phone;

    /**
     * 产品型号
     */
    private String productModel;

    /**
     * 产品类型
     */
    private Byte productType;

    /**
     * 优惠券金额
     */
    private BigDecimal amount;

    /**
     * 使用场景
     */
    private Integer usageScenario;

    /**
     * 备注
     */
    private String remark;
    /**
     * 资金流水号
     */
    private String fundsSN;

    private Date createOn;

    private static final long serialVersionUID = 1L;

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

    public String getOrderSN() {
		return orderSN;
	}

	public void setOrderSN(String orderSN) {
		this.orderSN = orderSN;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public Byte getProductType() {
        return productType;
    }

    public void setProductType(Byte productType) {
        this.productType = productType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getUsageScenario() {
        return usageScenario;
    }

    public void setUsageScenario(Integer usageScenario) {
        this.usageScenario = usageScenario;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFundsSN() {
		return fundsSN;
	}

	public void setFundsSN(String fundsSN) {
		this.fundsSN = fundsSN;
	}

	public Date getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }
}