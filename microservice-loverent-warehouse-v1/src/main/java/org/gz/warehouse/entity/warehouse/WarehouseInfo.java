package org.gz.warehouse.entity.warehouse;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

/**
 * 
 * @Description:TODO    仓库实体
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月16日 	Created
 */
public class WarehouseInfo implements Serializable {
    private Integer id;

    /**
     * 仓库编码
     */
    @NotBlank(message = "仓库编码不能为空")
    @Length(min = 1, max = 4, message = "仓库编码长度不超过4个字符")
    private String warehouseCode;

    /**
     * 仓库名称
     */
    @NotBlank(message = "仓库名称不能为空")
    @Length(min = 1, max = 16, message = "仓库编码长度不超过16个字符")
    private String warehouseName;

    /**
     * 所属仓库
     */
    @NotBlank(message = "所属仓库不能为空")
    private Long parentId;
    /**
     * 联系人
     */
    @NotBlank(message = "联系人不能为空")
    @Length(min = 1, max = 16, message = "联系人长度不超过16个字符")
    private String contacts;

    /**
     * 联系方式
     */
    @NotBlank(message = "联系方式不能为空")
    @Length(min = 1, max = 12, message = "联系人长度不超过12个字符")
    private String phone;
    /**
     * 地址
     */
    @NotBlank(message = "地址不能为空")
    @Length(min = 1, max = 128, message = "地址长度不超过128个字符")
    private String address;
    /**
     * 备注
     */
    private String remark;

    /**
     * 是否启用 0 否 1是
     */
    private Integer enableFlag;

    /**
     * 删除标识 0 否 1是
     */
    private Integer delFlag;

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
     * 修改时间
     */
    private Date updateOn;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(Integer enableFlag) {
        this.enableFlag = enableFlag;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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