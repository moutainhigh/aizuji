package org.gz.oss.common.entity;

import org.gz.common.entity.BaseEntity;

/**
 * 橱窗位参数实体类
 * @author daiqingwen
 * @date 2018/3/14 下午 18:21
 */
public class ShopwindowVO extends BaseEntity {

    private static final long serialVersionUID = 3171979539373246117L;

    private Integer id;     //主键ID

    private String name;    //橱窗名称

    private Integer type;   //橱窗类型 10 促销橱窗 20 售卖橱窗 30 租赁橱窗

    private Integer position;   //位置

    private String image;    //图片路径

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
