package org.gz.oss.common.entity;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.gz.common.entity.BaseEntity;
import org.gz.common.hv.group.UpdateRecordGroup;

/**
 * 广告位实体类
 * @author daiqingwen
 * @date 2018/3/13
 */
public class Advertising extends BaseEntity {

    private static final long serialVersionUID = -4557208640055284995L;

    @NotNull(groups = UpdateRecordGroup.class, message = "更新时，主键ID不能为null")
    private Integer id;     //主键id

    @NotNull(message = "配置名称不能为空")
    private String image;   //图片路径

    @NotNull(message = "配置名称不能为空")
    private Integer type;   //类型 10 链接 20 售卖商品 30 租赁商品

    private String linkUrl; //链接地址

    private String imeiNo;  //商品imei

    private String snNo;    //商品SN编码

    private Integer position; //位置

    private Long operatingId;    //操作人ID

    private Date createTime;    //创建时间

    private Long productId;  //产品ID

    private Long modelId;   //型号ID

    private String modelName;    //型号名称

    private String specBatchNoValues;   //规格批次值

    private String configValue; //新旧程度配置值

    private Integer materielClassId;   //物料分类ID

    private Integer materielBrandId;    //物料品牌Id

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
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

    public Long getOperatingId() {
        return operatingId;
    }

    public void setOperatingId(Long operatingId) {
        this.operatingId = operatingId;
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

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
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

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
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
}
