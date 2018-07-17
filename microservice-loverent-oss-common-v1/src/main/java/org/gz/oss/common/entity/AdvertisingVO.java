package org.gz.oss.common.entity;

import org.gz.common.entity.BaseEntity;
import org.gz.common.hv.group.UpdateRecordGroup;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
/**
 * 广告位参数实体类
 * @author daiqingwen
 * @date 2018/3/13
 */
public class AdvertisingVO extends BaseEntity {

    private static final long serialVersionUID = 3171979539373246117L;

    private Integer id;     //主键id

    private String image;   //图片路径

    private Integer type;   //类型 10 链接 20 商品

    private String linkUrl; //链接地址

    private String imeiNo;  //商品imei

    private String snNo;    //商品SN编码

    private Integer position; //位置

    private Long operatingId;    //操作人ID

    private Date createTime;    //创建时间

    private Integer productId;  //产品ID

    private Long modelId;   //型号ID

    private List<AdvertisingVO> attaList; //需要移动的广告位

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

    public List<AdvertisingVO> getAttaList() {
        return attaList;
    }

    public void setAttaList(List<AdvertisingVO> attaList) {
        this.attaList = attaList;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getOperatingId() {
        return operatingId;
    }

    public void setOperatingId(Long operatingId) {
        this.operatingId = operatingId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }
}
