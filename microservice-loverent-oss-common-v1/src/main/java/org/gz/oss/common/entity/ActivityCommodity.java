package org.gz.oss.common.entity;

import org.gz.common.entity.BaseEntity;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 活动橱窗参数实体
 * @author daiqingwen
 * @date 2018/3/24 下午 14:00
 */
public class ActivityCommodity extends BaseEntity {

    private static final long serialVersionUID = 6396481279340285221L;

    private Integer id;     //活动ID

    @NotNull(message = "配置名称不能为空")
    private Integer shopwindowId;   //橱窗ID

    @NotNull(message = "配置名称不能为空")
    private String startTime;     //活动开始时间

    @NotNull(message = "配置名称不能为空")
    private String endTime;   //活动结束时间

    private Date createTime;     //创建时间

    private String imeiNo;          //商品imei

    private String snNo;            //商品SN编码

    private Integer position;       //位置

    private Integer type;           //商品类型 20 售卖商品 30 租赁商品

    private Long modelId;           //型号ID

    private Long productId;         //产品id

    private Long commodityId;       //商品ID

    private List<ActivityCommodity> attaList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopwindowId() {
        return shopwindowId;
    }

    public void setShopwindowId(Integer shopwindowId) {
        this.shopwindowId = shopwindowId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public List<ActivityCommodity> getAttaList() {
        return attaList;
    }

    public void setAttaList(List<ActivityCommodity> attaList) {
        this.attaList = attaList;
    }

    public Long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
    }
}
