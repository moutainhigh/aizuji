package org.gz.warehouse.entity;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.gz.common.entity.BaseEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

/**
 * 物料分类表
 * Author	Version		Date		Changes
 * liuyt 	1.0  		2017年12月12日 	Created
 */
public class MaterielClass extends BaseEntity {

    private static final long serialVersionUID = 992410984157055990L;

    private Integer id;//主键

    @NotBlank(message="分类名称不能为空")
    @Length(min=1,max=30,message="分类名称长度不超过30个字符")
    private String  typeName;//分类名称

    private String typeCode;//分类编码,系统自动生成，生成规则:C+5位数字
    
    private Integer typeLevel;//分类层级，系统自动生成

    private Integer sortOrder;//预留字段，排列顺序为主键ID

    @NotNull(message="父分类ID不能为null")
    @Range(min=0,message="非法父分类ID值")
    private Integer  parentId;//父分类ID

    private List<MaterielClass> childClassList = new ArrayList<MaterielClass>();//子分类列表

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public Integer getTypeLevel() {
        return typeLevel;
    }

    public void setTypeLevel(Integer typeLevel) {
        this.typeLevel = typeLevel;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<MaterielClass> getChildClassList() {
        return childClassList;
    }

    public void setChildClassList(List<MaterielClass> childClassList) {
        this.childClassList = childClassList;
    }

}
