package org.gz.oss.common.dto;

import org.gz.common.entity.BaseEntity;

import javax.validation.constraints.NotNull;

/**
 * @author WenMing.Zhou
 * @Description: ${todo}
 * @date 2018/3/28 10:10
 */
public class MiniAppShopwindowVo extends BaseEntity {

    private static final long serialVersionUID = -5379036605729056495L;

    @NotNull(message = "橱窗Id不能为空")
    private Integer id; //主键ID

    private String name; //橱窗名称

    @NotNull(message = "橱窗位置不能为空")
    private Integer position; // 橱窗位置

    private Integer type;  //橱窗类型 10 促销橱窗 20 售卖橱窗 30 租赁橱窗

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
}
