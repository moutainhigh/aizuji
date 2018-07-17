package org.gz.warehouse.entity.warehouse;

import org.gz.common.entity.BaseEntity;
import org.gz.common.hv.group.UpdateRecordGroup;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Title: 还机记录附件实体类
 * @author daiqingwen
 * @date 2018年3月6日 上午 10:37
 */
public class WarehouseReturnImages extends BaseEntity {

	private static final long serialVersionUID = 6580640048589061521L;

	@NotNull(groups = UpdateRecordGroup.class, message = "更新时，主键ID不能为null")
    private Long id;                //主键

    @NotNull(message = "配置名称不能为空")
    private Long returnRecordId;    //还机记录主键ID

    @NotNull(message = "配置名称不能为空")
    private Integer attaType;       //附件类型 1:包装 2：外观 3：IMEI号

    @NotNull(message = "配置名称不能为空")
    @NotBlank(message = "配置名称不能为空")
    private String attaUrl;         //附件地址

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReturnRecordId() {
        return returnRecordId;
    }

    public void setReturnRecordId(Long returnRecordId) {
        this.returnRecordId = returnRecordId;
    }

    public Integer getAttaType() {
        return attaType;
    }

    public void setAttaType(Integer attaType) {
        this.attaType = attaType;
    }

    public String getAttaUrl() {
        return attaUrl;
    }

    public void setAttaUrl(String attaUrl) {
        this.attaUrl = attaUrl;
    }
}
