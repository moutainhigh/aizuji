package org.gz.oss.common.dto;

import org.gz.common.entity.BaseEntity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author WenMing.Zhou
 * @Description: ${todo}
 * @date 2018/3/27 17:11
 */
public class MiniAppShopwindowDto extends BaseEntity {

    private static final long serialVersionUID = 5440911713200440788L;

    private Integer id; //主键ID

    @NotNull(message = "橱窗名称不能为空")
    private String name; //橱窗名称

    @NotNull(message = "橱窗位置不能为空")
    private Integer position; // 橱窗位置

    @NotNull(message = "橱窗类型不能为空")
    private Integer type;  //橱窗类型 10 促销橱窗 20 售卖橱窗 30 租赁橱窗

    @NotNull(message = "商品列表信息不能为空")
    @Valid
    private List<MiniAppShopwindowCommodityDto> shopwindowCommodityList;


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

    public List<MiniAppShopwindowCommodityDto> getShopwindowCommodityList() {
        return shopwindowCommodityList;
    }

    public void setShopwindowCommodityList(List<MiniAppShopwindowCommodityDto> shopwindowCommodityList) {
        this.shopwindowCommodityList = shopwindowCommodityList;
    }
}
