package org.gz.warehouse.common.vo;

import org.gz.common.entity.BaseEntity;

import java.util.Objects;

public class SaleProductShortResp extends BaseEntity {

    private Long productId;//产品ID

    private String imeiNo;//IMIE号

    private String snNo;//SN号

    private Integer shopwindowId;   //橱窗位id

    private Integer position;       //位置

    private Integer type;           //商品类型 20 售卖商品 30 租赁商品

    private Long modelId;           //型号ID

    private Integer activityId;     //活动ID

    private ProductInfo productInfo;  //产品信息

    private String modelName;       // 型号名称

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleProductShortResp that = (SaleProductShortResp) o;
        return Objects.equals(productId, that.productId) &&
                Objects.equals(imeiNo, that.imeiNo) &&
                Objects.equals(snNo, that.snNo) &&
                Objects.equals(shopwindowId, that.shopwindowId) &&
                Objects.equals(position, that.position) &&
                Objects.equals(type, that.type) &&
                Objects.equals(modelId, that.modelId) &&
                Objects.equals(activityId, that.activityId) &&
                Objects.equals(productInfo, that.productInfo) &&
                Objects.equals(modelName, that.modelName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(productId, imeiNo, snNo, shopwindowId, position, type, modelId, activityId, productInfo, modelName);
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

    public Integer getShopwindowId() {
        return shopwindowId;
    }

    public void setShopwindowId(Integer shopwindowId) {
        this.shopwindowId = shopwindowId;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public ProductInfo getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
