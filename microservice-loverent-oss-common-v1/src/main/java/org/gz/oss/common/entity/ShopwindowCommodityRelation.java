package org.gz.oss.common.entity;

import org.gz.common.entity.BaseEntity;
import org.gz.common.hv.group.UpdateRecordGroup;
import org.gz.warehouse.common.vo.ProductInfo;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 橱窗对应商品关系实体类
 * @author daiqingwen
 * @date 2018/3/15 下午 15:47
 */
public class ShopwindowCommodityRelation extends BaseEntity {

    private static final long serialVersionUID = 3171979539373246117L;

    @NotNull(groups = UpdateRecordGroup.class, message = "更新时，主键ID不能为null")
    private Long id;                //主键

    private Integer shopwindowId;   //橱窗位id

    private Long productId;         //产品id

    private String imeiNo;          //商品imei

    private String snNo;            //商品SN编码

    private Integer position;       //位置

    private Integer type;           //商品类型 20 售卖商品 30 租赁商品

    private Long modelId;           //型号ID

    private Integer activityId;     //活动ID

    private ProductInfo productInfo;  //产品信息

    private String modelName;       // 型号名称

    private List<ShopwindowCommodityRelation> attaList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getShopwindowId() {
        return shopwindowId;
    }

    public void setShopwindowId(Integer shopwindowId) {
        this.shopwindowId = shopwindowId;
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

    public List<ShopwindowCommodityRelation> getAttaList() {
        return attaList;
    }

    public void setAttaList(List<ShopwindowCommodityRelation> attaList) {
        this.attaList = attaList;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
