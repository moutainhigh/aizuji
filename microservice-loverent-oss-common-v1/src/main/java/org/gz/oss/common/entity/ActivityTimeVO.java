package org.gz.oss.common.entity;

import org.gz.common.entity.BaseEntity;
import org.gz.common.hv.group.UpdateRecordGroup;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 活动时间参数实体类
 * @author daiqingwen
 * @date 2018/3/14 下午 18:21
 */
public class ActivityTimeVO extends BaseEntity {

    private static final long serialVersionUID = 3171979539373246117L;

    @NotNull(groups = UpdateRecordGroup.class, message = "更新时，主键ID不能为null")
    private Long id;    //主键ID

    @NotNull(message = "配置名称不能为空")
    private Integer shopwindowId;   //橱窗ID

    @NotNull(message = "配置名称不能为空")
    private String startTime;     //活动开始时间

    @NotNull(message = "配置名称不能为空")
    private String endTime;   //活动结束时间

    private Date createTime;     //创建时间

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
}
