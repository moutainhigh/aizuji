package org.gz.warehouse.common.vo;

import java.math.BigDecimal;
import java.util.List;

import org.gz.common.entity.QueryPager;

/**
 * 产品查询vo
 * @Description:TODO
 * Author	Version		Date		Changes
 * liuyt 	1.0  		2017年12月14日 	Created
 */
public class ProductInfoQvo extends QueryPager {

    /**
     * 
     */
    private static final long serialVersionUID = -934336113934720966L;
    
    /**
     * 物料分类id
     */
    private Integer           materielClassId;

    /**
     * 物料品牌id
     */
    private Integer           materielBrandId;

    /**
     * 物料型号id
     */
    private Integer           materielModelId;

    /**
     * 规格批次号
     */
    private String            specBatchNo;

    /**
	 * 物料单位ID
	 */
	private Integer materielUnitId;
	
    /**
     * 物料规格值（多个值用,隔开）
     */
    private String            materielSpecValue;

    /**
     * 是否删除（0：否；1：是）
     */
    private Byte              isDeleted;

    /**
     * 物料id
     */
    private Long              materielId;

    /**
     * 租期id
     */
    private Integer           leaseTermId;

    /**
     * 租金
     */
    private BigDecimal        leaseAmount;

    /**
     * 日租金
     */
    private BigDecimal        dayLeaseAmount;

    /**
     * 产品一次性保险费
     */
    private BigDecimal        premium;

    /**
     * 溢价
     */
    private BigDecimal        floatAmount;

    /**
     * 签约价值
     */
    private BigDecimal        signContractAmount;

    /**
     * 显示价值
     */
    private BigDecimal        showAmount;

    /**
     * 芝麻信用值
     */
    private Integer           sesameCredit;

    /**
     * 产品类型（1：租赁；2:以租代购；3：出售） 
     */
    private Integer           productType;

    /**
     * 新旧程度配置
     */
    private Integer           materielNewConfigId;

    /**
     * 碎屏险金额
     */
    private BigDecimal        brokenScreenAmount;

    /**
     * 商品SN
     */
    private String snNo;

    /**
     * 商品IMIE号
     */
    private String imeiNo;

    /**
     * 物料型号列表
     */
    private List<Long> materielModelIdList;
    /**
     * 产品id列表
     */
    private List<Long> idList;

    private List<ProductInfoQvo> paramList;

    /**
     * 是否是主物料   主物料标志 0：否 1：是
     */
    private Integer materielFlag;


    public Integer getMaterielFlag() {
        return materielFlag;
    }

    public void setMaterielFlag(Integer materielFlag) {
        this.materielFlag = materielFlag;
    }

    /**
     * 产品ID
     */
    private Long productId;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getMaterielClassId() {
        return materielClassId;
    }

    public void setMaterielClassId(Integer materielClassId) {
        this.materielClassId = materielClassId;
    }

    public Integer getMaterielBrandId() {
        return materielBrandId;
    }

    public void setMaterielBrandId(Integer materielBrandId) {
        this.materielBrandId = materielBrandId;
    }

    public Integer getMaterielModelId() {
        return materielModelId;
    }

    public void setMaterielModelId(Integer materielModelId) {
        this.materielModelId = materielModelId;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getSpecBatchNo() {
        return specBatchNo;
    }

    public void setSpecBatchNo(String specBatchNo) {
        this.specBatchNo = specBatchNo;
    }

    public Long getMaterielId() {
        return materielId;
    }

    public void setMaterielId(Long materielId) {
        this.materielId = materielId;
    }

    public Integer getLeaseTermId() {
        return leaseTermId;
    }

    public void setLeaseTermId(Integer leaseTermId) {
        this.leaseTermId = leaseTermId;
    }

    public BigDecimal getLeaseAmount() {
        return leaseAmount;
    }

    public void setLeaseAmount(BigDecimal leaseAmount) {
        this.leaseAmount = leaseAmount;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    public BigDecimal getFloatAmount() {
        return floatAmount;
    }

    public void setFloatAmount(BigDecimal floatAmount) {
        this.floatAmount = floatAmount;
    }

    public BigDecimal getSignContractAmount() {
        return signContractAmount;
    }

    public void setSignContractAmount(BigDecimal signContractAmount) {
        this.signContractAmount = signContractAmount;
    }

    public BigDecimal getShowAmount() {
        return showAmount;
    }

    public void setShowAmount(BigDecimal showAmount) {
        this.showAmount = showAmount;
    }

    public Integer getSesameCredit() {
        return sesameCredit;
    }

    public String getMaterielSpecValue() {
        return materielSpecValue;
    }

    public void setMaterielSpecValue(String materielSpecValue) {
        this.materielSpecValue = materielSpecValue;
    }

    public void setSesameCredit(Integer sesameCredit) {
        this.sesameCredit = sesameCredit;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Integer getMaterielNewConfigId() {
        return materielNewConfigId;
    }

    public void setMaterielNewConfigId(Integer materielNewConfigId) {
        this.materielNewConfigId = materielNewConfigId;
    }

    public BigDecimal getDayLeaseAmount() {
        return dayLeaseAmount;
    }

    public void setDayLeaseAmount(BigDecimal dayLeaseAmount) {
        this.dayLeaseAmount = dayLeaseAmount;
    }

    public BigDecimal getBrokenScreenAmount() {
        return brokenScreenAmount;
    }

    public void setBrokenScreenAmount(BigDecimal brokenScreenAmount) {
        this.brokenScreenAmount = brokenScreenAmount;
    }

    public List<Long> getMaterielModelIdList() {
        return materielModelIdList;
    }

    public void setMaterielModelIdList(List<Long> materielModelIdList) {
        this.materielModelIdList = materielModelIdList;
    }
    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

    public String getSnNo() {
        return snNo;
    }

    public void setSnNo(String snNo) {
        this.snNo = snNo;
    }

    public String getImeiNo() {
        return imeiNo;
    }

    public void setImeiNo(String imeiNo) {
        this.imeiNo = imeiNo;
    }

    public List<ProductInfoQvo> getParamList() {
        return paramList;
    }

    public void setParamList(List<ProductInfoQvo> paramList) {
        this.paramList = paramList;
    }

	public Integer getMaterielUnitId() {
		return materielUnitId;
	}

	public void setMaterielUnitId(Integer materielUnitId) {
		this.materielUnitId = materielUnitId;
	}
    
    
}
