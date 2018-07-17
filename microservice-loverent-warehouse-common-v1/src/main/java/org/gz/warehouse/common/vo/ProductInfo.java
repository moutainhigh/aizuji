package org.gz.warehouse.common.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.gz.common.entity.BaseEntity;
import org.gz.common.entity.ResultPager;

/**
 * 产品信息实体 Author Version Date Changes liuyt 1.0 2017年12月14日 Created
 */
public class ProductInfo extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9056461169152786835L;

	/**
	 * 主键id
	 */
	private Long id;

	/**
	 * 产品编号（生成规则：FP+8位自增长）
	 */
	private String productNo;

	/**
	 * 物料分类id
	 */
	@NotNull(message = "物料分类不能为null")
	private Integer materielClassId;

	/**
	 * 物料品牌id
	 */
	@NotNull(message = "物料品牌不能为null")
	private Integer materielBrandId;

	/**
	 * 物料型号id
	 */
	@NotNull(message = "物料型号不能为null")
	private Integer materielModelId;

	/**
	 * 物料规格批次号
	 */
	@NotBlank(message = "规格批次号不能为null")
	private String specBatchNo;

	/**
	 * 物料单位id
	 */
	@NotNull(message = "物料单位id不能为null")
	private Integer materielUnitId;
	
	/**
	 * 物料单位名称
	 */
	private String materielUnitName;
	
	/**
	 * 物料id
	 */
	private Long materielId;

	/**
	 * 租期id
	 */
	private Integer leaseTermId;

	/**
	 * 月租金
	 */
	private BigDecimal leaseAmount;
    /**
     * 日租金
     */
    private BigDecimal  dayLeaseAmount;

	/**
	 * 产品一次性保险费
	 */
	private BigDecimal premium;

	/**
	 * 溢价
	 */
	private BigDecimal floatAmount;

	/**
	 * 签约价值
	 */
	@NotNull(message = "签约价值不能为null")
	private BigDecimal signContractAmount;

	/**
	 * 显示价值
	 */
	@NotNull(message = "显示价值不能为null")
	private BigDecimal showAmount;

	/**
	 * 芝麻信用值
	 */
	private Integer sesameCredit;

	/**
	 * 是否删除（0：否；1：是）
	 */
	private Byte isDeleted;

	/**
	 * 租期折旧费（多个折旧费按，分隔）
	 */
	private String depreciateAmouts;

	/**
	 * 分类名字
	 */
	private String className;

	/**
	 * 品牌名字
	 */
	private String brandName;

	/**
	 * 型号名称
	 */
	private String modelName;

	/**
	 * 规格批次值
	 */
	private String specBatchNoValues;

	/**
	 * 租期值
	 */
	private Integer termValue;


	/**
	 * 缩略图地址
	 */
	private String thumbnailUrl;

	/**
	 * 创建时间
	 */
	private Date createOn;

	/**
	 * 更新时间
	 */
	private Date updateOn;

	/**
	 * 创建人
	 */
	private Long createBy;

	/**
	 * 更新人
	 */
	private Long updateBy;

	/**
	 * 物料名称
	 */
	private String materielName;

	/**
	 * 型号名称
	 */
	private String materielModelName;

	/**
	 * 新旧程度配置Id
	 */
	private Integer materielNewConfigId;

	/**
	 * 新旧程度配置值
	 */
	private String configValue;

	/**
	 * 产品类型（1：租赁；2:以租代购；3：出售）
	 */
	private Integer productType;

    /**
     * 商品信息列表
     */
    private List<WarehouseCommodityResp> warehouseCommodityList;

    /**
     * 碎屏险金额
     */
    private BigDecimal  brokenScreenAmount;

    /**
     * 物料主图地址
     */
    private String  attaUrl;

    /**
     * 最小月租金值
     */
    private BigDecimal  minAmount;

	/**
	 * 折扣率
	 */
	private BigDecimal discount;

	/**
	 * 相关附件列表
	 */
	private List<String> attaUrlList;

	/**
	 * 图文介绍
	 */
	private String materielIntroduction;

	/**
	 * 租金显示类型 1:显示月租金 2:显示日租金
	 */
	private Integer displayLeaseType;

	/**
	 * 商品SN
	 */
	private String snNo;

	/**
	 * 商品IMIE号
	 */
	private String imeiNo;

	/**
	 * 库存数量
	 */
	private Integer storageCount;

	/**
	 * 节省价
	 */
	private BigDecimal economizeValue;

	private ResultPager<WarehouseCommodityResp> warehouseCommodityResp;

	/**
	 * 总租金 = 租期 * 月租金
	 */
	private BigDecimal totalAmount;

	/**
	 * 库位编码
	 */
	private String locationCode;


    public ProductInfo(){

	}

	public ProductInfo(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getSpecBatchNo() {
		return specBatchNo;
	}

	public void setSpecBatchNo(String specBatchNo) {
		this.specBatchNo = specBatchNo;
	}

	public Integer getMaterielUnitId() {
		return materielUnitId;
	}

	public void setMaterielUnitId(Integer materielUnitId) {
		this.materielUnitId = materielUnitId;
	}

	public Integer getLeaseTermId() {
		return leaseTermId;
	}

	public void setLeaseTermId(Integer leaseTermId) {
		this.leaseTermId = leaseTermId;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
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

	public void setSesameCredit(Integer sesameCredit) {
		this.sesameCredit = sesameCredit;
	}

	public Byte getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Byte isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getDepreciateAmouts() {
		return depreciateAmouts;
	}

	public void setDepreciateAmouts(String depreciateAmouts) {
		this.depreciateAmouts = depreciateAmouts;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getSpecBatchNoValues() {
		return specBatchNoValues;
	}

	public void setSpecBatchNoValues(String specBatchNoValues) {
		this.specBatchNoValues = specBatchNoValues;
	}

	public Integer getTermValue() {
		return termValue;
	}

	public void setTermValue(Integer termValue) {
		this.termValue = termValue;
	}

	public Long getMaterielId() {
		return materielId;
	}

	public void setMaterielId(Long materielId) {
		this.materielId = materielId;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public Date getUpdateOn() {
		return updateOn;
	}

	public void setUpdateOn(Date updateOn) {
		this.updateOn = updateOn;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public String getMaterielName() {
		return materielName;
	}

	public void setMaterielName(String materielName) {
		this.materielName = materielName;
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

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

    public List<WarehouseCommodityResp> getWarehouseCommodityList() {
        return warehouseCommodityList;
    }

    public void setWarehouseCommodityList(List<WarehouseCommodityResp> warehouseCommodityList) {
        this.warehouseCommodityList = warehouseCommodityList;
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

    public String getAttaUrl() {
        return attaUrl;
    }

    public void setAttaUrl(String attaUrl) {
        this.attaUrl = attaUrl;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }


	public List<String> getAttaUrlList() {
		return attaUrlList;
	}

	public void setAttaUrlList(List<String> attaUrlList) {
		this.attaUrlList = attaUrlList;
	}

	public String getMaterielIntroduction() {
		return materielIntroduction;
	}

	public void setMaterielIntroduction(String materielIntroduction) {
		this.materielIntroduction = materielIntroduction;
	}

	public Integer getDisplayLeaseType() {
		return displayLeaseType;
	}

	public void setDisplayLeaseType(Integer displayLeaseType) {
		this.displayLeaseType = displayLeaseType;
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

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public Integer getStorageCount() {
		return storageCount;
	}

	public void setStorageCount(Integer storageCount) {
		this.storageCount = storageCount;
	}

	public BigDecimal getEconomizeValue() {
		return economizeValue;
	}

	public void setEconomizeValue(BigDecimal economizeValue) {
		this.economizeValue = economizeValue;
	}

	public ResultPager<WarehouseCommodityResp> getWarehouseCommodityResp() {
		return warehouseCommodityResp;
	}

	public void setWarehouseCommodityResp(ResultPager<WarehouseCommodityResp> warehouseCommodityResp) {
		this.warehouseCommodityResp = warehouseCommodityResp;
	}

	public String getMaterielModelName() {
		return materielModelName;
	}

	public void setMaterielModelName(String materielModelName) {
		this.materielModelName = materielModelName;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getMaterielUnitName() {
		return materielUnitName;
	}

	public void setMaterielUnitName(String materielUnitName) {
		this.materielUnitName = materielUnitName;
	}
	
	
}
