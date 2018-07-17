package org.gz.warehouse.common.vo;

import java.math.BigDecimal;

import org.gz.common.entity.BaseEntity;

/**
 * @author WenMing.Zhou
 * @Description: ${todo}
 * @date 2018/3/24 11:16
 */
public class RentProductResp extends BaseEntity {

	private static final long serialVersionUID = -5857570613601299880L;

	/**
	 * 主键id
	 */
	private Long productId;

	/**
	 * 物料id
	 */
	private Long materielId;

	/**
	 * 物料分类id
	 */
	private Integer materielClassId;

	/**
	 * 物料品牌id
	 */
	private Integer materielBrandId;

	/**
	 * 物料型号id
	 */
	private Integer materielModelId;

	/**
	 * 签约价值
	 */
	private BigDecimal signContractAmount;

	/**
	 * 显示价值
	 */
	private BigDecimal showAmount;

	/**
	 * 型号名称
	 */
	private String modelName;

	/**
	 * 规格批次值
	 */
	private String specBatchNoValues;

	/**
	 * 缩略图地址
	 */
	private String thumbnailUrl;

	/**
	 * 新旧程度配置Id
	 */
	private Integer materielNewConfigId;

	/**
	 * 新旧程度配置值
	 */
	private String configValue;

	/**
	 * 折扣
	 */
	private BigDecimal discount;

	/**
	 * 产品类型，1：正常租赁；2:以租代购；3：售卖0
	 */
	private Integer productType;

	/**
	 * 租金显示类型 1:显示月租金 2:显示日租金
	 */
	private Integer displayLeaseType;

	/**
	 * 月租金
	 */
	private BigDecimal leaseAmount=new BigDecimal(0);

	/**
	 * 月租金
	 */
	private BigDecimal dayLeaseAmount=new BigDecimal(0);

	private String productNo; //产品编号

	private String imieNo; //商品IMIE号

	private String snNo; //商品SN

	private BigDecimal brokenScreenAmount;	//碎屏险金额

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getImieNo() {
		return imieNo;
	}

	public void setImieNo(String imieNo) {
		this.imieNo = imieNo;
	}

	public String getSnNo() {
		return snNo;
	}

	public void setSnNo(String snNo) {
		this.snNo = snNo;
	}

	/**
	 * 租期
	 */
	private Integer termValue;
	
	/**
	 * 节省金额
	 */
	private BigDecimal saveAmount=new BigDecimal(0); 

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getMaterielId() {
		return materielId;
	}

	public void setMaterielId(Long materielId) {
		this.materielId = materielId;
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

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
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

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public Integer getDisplayLeaseType() {
		return displayLeaseType;
	}

	public void setDisplayLeaseType(Integer displayLeaseType) {
		this.displayLeaseType = displayLeaseType;
	}

	public BigDecimal getLeaseAmount() {
		return leaseAmount;
	}

	public void setLeaseAmount(BigDecimal leaseAmount) {
		this.leaseAmount = leaseAmount;
	}

	public BigDecimal getDayLeaseAmount() {
		return dayLeaseAmount;
	}

	public void setDayLeaseAmount(BigDecimal dayLeaseAmount) {
		this.dayLeaseAmount = dayLeaseAmount;
	}

	public Integer getTermValue() {
		return termValue;
	}

	public void setTermValue(Integer termValue) {
		this.termValue = termValue;
	}

	public BigDecimal getSaveAmount() {
		return saveAmount;
	}

	public void setSaveAmount(BigDecimal saveAmount) {
		this.saveAmount = saveAmount;
	}

	public BigDecimal getBrokenScreenAmount() {
		return brokenScreenAmount;
	}

	public void setBrokenScreenAmount(BigDecimal brokenScreenAmount) {
		this.brokenScreenAmount = brokenScreenAmount;
	}
}
