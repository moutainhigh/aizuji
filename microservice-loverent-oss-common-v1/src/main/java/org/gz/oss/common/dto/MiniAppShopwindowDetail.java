package org.gz.oss.common.dto;

import org.gz.common.entity.BaseEntity;


/**
 * @author WenMing.Zhou
 * @Description: ${todo}
 * @date 2018/3/29 10:24
 */

/**
 * Alipay橱窗详情实体类
 */
public class MiniAppShopwindowDetail extends BaseEntity {


    private static final long serialVersionUID = 2030156950341267518L;


    private Integer id; //主键ID

    private String name; //橱窗名称

    private Integer position; // 橱窗位置

    private Integer type;  //橱窗类型 10 促销橱窗 20 售卖橱窗 30 租赁橱窗



    private Integer shopwindowId; //橱窗Id

    private String image; //图片

    private Integer imageposition; //图片位置

    private Integer productType; // 商品类型 20 售卖商品 30 租赁商品

    private Integer modelId; //商品型号ID

    private String modelName; //型号名称

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getShopwindowId() {
        return shopwindowId;
    }

    public void setShopwindowId(Integer shopwindowId) {
        this.shopwindowId = shopwindowId;
    }

    public Integer getImageposition() {
        return imageposition;
    }

    public void setImageposition(Integer imageposition) {
        this.imageposition = imageposition;
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

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
