package org.gz.oss.common.entity;

import org.gz.common.entity.BaseEntity;
import org.gz.common.hv.group.UpdateRecordGroup;

import javax.validation.constraints.NotNull;

/**
 * @author WenMing.Zhou
 * @Description: ${todo}
 * @date 2018/3/27 16:19
 */
public class MiniAppShopwindow extends BaseEntity {

    private static final long serialVersionUID = -2101923430757171997L;

    @NotNull(groups = UpdateRecordGroup.class, message = "更新时，主键ID不能为null")
    private Integer id;     //主键ID

    @NotNull(message = "配置名称不能为空")
    private String name;    //橱窗名称

    private Integer type;   //橱窗类型 10 促销橱窗 20 售卖橱窗 30 租赁橱窗

    private Integer position;   //位置

    private String image;    //图片路径

    private Integer totalCount;    //橱窗位对应商品的总数


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

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
