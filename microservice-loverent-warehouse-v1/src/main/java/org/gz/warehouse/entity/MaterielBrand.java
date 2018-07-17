package org.gz.warehouse.entity;

import javax.validation.constraints.NotBlank;

import org.gz.common.entity.BaseEntity;
import org.hibernate.validator.constraints.Length;


/**
 * 物料品牌表
 * @author hxj
 */
public class MaterielBrand extends BaseEntity {
    
    private static final long serialVersionUID = -1190732354202345675L;

    private Long  id;//主键

   
    @NotBlank(message="品牌名称不能为空")
    @Length(min=1,max=30,message="品牌名称长度不超过30个字符")
    private String brandName;//品牌名称

    @NotBlank(message="品牌编码不能为空")
    @Length(min=1,max=30,message="品牌编码长度不超过30个字符")
    private String brandCode;//品牌编码

    @Length(min=1,max=255,message="备注长度不超过255个字符")
    private String remark;//备注

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
