package org.gz.oss.common.entity;

import org.gz.common.entity.BaseEntity;
import org.gz.common.utils.AssertUtils;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 首页信息实体类
 * @author daiqingwen
 * @date 2018/3/21 上午 10:41
 */
public class IndexInfo extends BaseEntity{

    private static final long serialVersionUID = -6312391203433843588L;

    private Integer shopwindowId;           //橱窗ID

    private String name;                    //橱窗名称

    private String image;                   //橱窗图片

    private Integer shopwindowType;         //橱窗类型 10 促销橱窗 20 售卖橱窗 30 租赁橱窗

    private Integer shopwindowPosition;     //橱窗位置

    private Long commodityId;               //商品ID

    private Long productId;                 //产品id

    private String imeiNo;                  //商品imei

    private String snNo;                    //商品SN编码

    private Integer commodityPosition;      //商品位置

    private Integer commodityType;          //商品类型 20 售卖商品 30 租赁商品
    
    private String commodityType_s;

    private Long modelId;                   //型号ID

    private Integer activityId;             //活动ID

    private Date startTime;                 //活动开始时间

    private Date endTime;                   //活动结束时间

    private String materielSpecValue;       //规格值

    private String materielModelName;       //型号名称

    private String materielName;            //物料名称

    private String configValue;             //配置值

    private BigDecimal signContractAmount;  //签约价值

    private BigDecimal showAmount;          //显示价值

    private BigDecimal economizeValue;      //节省价

    private Integer termValue;              //租期值

    private BigDecimal leaseAmount;        //月租金

    private BigDecimal  dayLeaseAmount;     //日租金

    private Integer displayLeaseType;       //租金显示类型 1:显示月租金 2:显示日租金

    private BigDecimal discount;            //折扣率

    private Integer productType;            //产品类型（1：租赁；2:以租代购；3：出售）

    private Integer storageCount;           //库存数量

    private String locationCode;            //库位编码


    public Integer getShopwindowId() {
        return shopwindowId;
    }

    public void setShopwindowId(Integer shopwindowId) {
        this.shopwindowId = shopwindowId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getShopwindowType() {
        return shopwindowType;
    }

    public void setShopwindowType(Integer shopwindowType) {
        this.shopwindowType = shopwindowType;
    }

    public Integer getShopwindowPosition() {
        return shopwindowPosition;
    }

    public void setShopwindowPosition(Integer shopwindowPosition) {
        this.shopwindowPosition = shopwindowPosition;
    }

    public Long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
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
        this.imeiNo = imeiNo;
    }

    public String getSnNo() {
        return snNo;
    }

    public void setSnNo(String snNo) {
        this.snNo = snNo;
    }

    public Integer getCommodityPosition() {
        return commodityPosition;
    }

    public void setCommodityPosition(Integer commodityPosition) {
        this.commodityPosition = commodityPosition;
    }

    public Integer getCommodityType() {
        return commodityType;
    }

    public void setCommodityType(Integer commodityType) {
        this.commodityType = commodityType;
        if (AssertUtils.isPositiveNumber4Int(commodityType)) {
			switch (commodityType.intValue()) {
			case 30:
				this.commodityType_s = "信用租";
				break;
			case 20:
				this.commodityType_s = "超值买";
				break;
			default:
				this.commodityType_s = "";
				break;
			}
		}
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getMaterielSpecValue() {
        return materielSpecValue;
    }

    public void setMaterielSpecValue(String materielSpecValue) {
        this.materielSpecValue = materielSpecValue;
    }

    public String getMaterielModelName() {
        return materielModelName;
    }

    public void setMaterielModelName(String materielModelName) {
        this.materielModelName = materielModelName;
    }

    public String getMaterielName() {
        return materielName;
    }

    public void setMaterielName(String materielName) {
        this.materielName = materielName;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
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

    public BigDecimal getEconomizeValue() {
        return economizeValue;
    }

    public void setEconomizeValue(BigDecimal economizeValue) {
        this.economizeValue = economizeValue;
    }

    public Integer getTermValue() {
        return termValue;
    }

    public void setTermValue(Integer termValue) {
        this.termValue = termValue;
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

    public Integer getDisplayLeaseType() {
        return displayLeaseType;
    }

    public void setDisplayLeaseType(Integer displayLeaseType) {
        this.displayLeaseType = displayLeaseType;
    }

	public String getCommodityType_s() {
		return commodityType_s;
	}

	public void setCommodityType_s(String commodityType_s) {
		this.commodityType_s = commodityType_s;
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

    public Integer getStorageCount() {
        return storageCount;
    }

    public void setStorageCount(Integer storageCount) {
        this.storageCount = storageCount;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }
}
