package org.gz.warehouse.entity.warehouse;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

/**
 * @author 库位实体
 */
public class StorageLocation implements Serializable {
    private Integer id;

    /**
     * 库位编码
     */
    @NotBlank(message = "库位编码不能为空")
    @Length(min = 1, max = 4, message = "库位编码长度不超过4个字符")
    private String locationCode;

    /**
     * 库位名称
     */
    @NotBlank(message = "库位名称不能为空")
    @Length(min = 1, max = 8, message = "库位名称长度不超过8个字符")
    private String locationName;
    /**
     * 备注
     */
    @Length(min = 1, max = 8, message = "备注长度不超过128个字符")
    
    private String remark;

    /**
     * 删除标识 0 否 1 是
     */
    private Integer delFlag;

    /**
     * 是否启用 0 否 1 是
     */
    private Integer enableFlag;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 修改人
     */
    private Long updateBy;

    /**
     * 创建时间
     */
    private Date createOn;

    /**
     * 最后修改时间
     */
    private Date updateOn;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    
    public String getRemark() {
        return remark;
    }

    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(Integer enableFlag) {
        this.enableFlag = enableFlag;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }

    public Date getUpdateOn() {
        return updateOn;
    }

    public void setUpdateOn(Date updateOn) {
        this.updateOn = updateOn;
    }
}