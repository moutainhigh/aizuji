package org.gz.oss.common.dto;

import org.gz.common.entity.BaseEntity;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author WenMing.Zhou
 * @Description: ${todo}
 * @date 2018/3/27 18:26
 */
public class MiniAppShopwindowCommodityDto extends BaseEntity {

    private static final long serialVersionUID = 3264072198035795619L;

    private Integer id; //主键Id

    @NotNull(message = "图片地址不能为空")
    private String image; //图片位置

//    @NotNull(message = "橱窗ID不能为空")
    private Integer shopwindowId; //橱窗Id


    private Integer productId; //产品Id

    private String imeiNo; //商品imei

    private String snNo; //商品SN编码

    @NotNull(message = "商品图片位置")
    private Integer position; //位置

    @NotNull(message = "商品类型不能为空")
    private Integer productType; // 商品类型 20 售卖商品 30 租赁商品

    @NotNull(message = "商品型号不能为空")
    private Integer modelId; //商品型号ID

    private Integer activityId; //活动ID

    private String modelName; //型号名称

    private Integer displayLeaseType;//租金显示类型 1:显示月租金 2:显示日租金

    private BigDecimal leaseAmount; //月租金

    private BigDecimal dayLeaseAmount; // 日租金

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

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getShopwindowId() {
        return shopwindowId;
    }

    public void setShopwindowId(Integer shopwindowId) {
        this.shopwindowId = shopwindowId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }
}
