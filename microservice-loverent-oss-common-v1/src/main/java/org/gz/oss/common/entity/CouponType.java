package org.gz.oss.common.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.gz.common.entity.BaseEntity;

public class CouponType  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	/** 主键 */
    private Long id;

    /** 优惠券id */
    private Long couponId;

    /** 机型id */
    private Long modelId;

    /** 产品id */
    private Long productId;

    /** 商品imei */
    private String imeiNo;

    /** 商品sn */
    private String snNo;

    /** 类型 10 售卖 20 租赁 */
    private Byte status;

    /** 创建时间 */
    private Date createNo;
    
    //扩展字段
    /** 规格 */
    private String cpecificate;
    
    /** 新旧程度配置值 */
    private String configValue;

    /** 规格批次值 */
    private String specBatchNoValue;
    
    /** 型号名称 */
    private String modelName;
    
    /** 签约价格 */
    private BigDecimal signContractAmount;

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

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getImeiNo() {
        return imeiNo;
    }

    public void setImeiNo(String imeiNo) {
        this.imeiNo = imeiNo == null ? null : imeiNo.trim();
    }

    public String getSnNo() {
        return snNo;
    }

    public void setSnNo(String snNo) {
        this.snNo = snNo == null ? null : snNo.trim();
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

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getCpecificate() {
		return cpecificate;
	}

	public void setCpecificate(String cpecificate) {
		this.cpecificate = cpecificate;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public String getSpecBatchNoValue() {
		return specBatchNoValue;
	}

	public void setSpecBatchNoValue(String specBatchNoValue) {
		this.specBatchNoValue = specBatchNoValue;
	}

	public BigDecimal getSignContractAmount() {
		return signContractAmount;
	}

	public void setSignContractAmount(BigDecimal signContractAmount) {
		this.signContractAmount = signContractAmount;
	}

}
