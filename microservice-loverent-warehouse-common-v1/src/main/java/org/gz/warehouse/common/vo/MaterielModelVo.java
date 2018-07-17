package org.gz.warehouse.common.vo;

import org.gz.common.entity.BaseEntity;

/**
 * @author WenMing.Zhou
 * @Description: ${todo}
 * @date 2018/3/28 17:27
 */
public class MaterielModelVo extends BaseEntity {

    private static final long serialVersionUID = 5358696450051415928L;


    private Integer id;//主键id

    private String  materielModelName;//物料型号名称

    private Integer materielClassId;//物料分类ID

    private Integer materielBrandId;//物料品牌Id

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaterielModelName() {
        return materielModelName;
    }

    public void setMaterielModelName(String materielModelName) {
        this.materielModelName = materielModelName;
    }

    public Integer getMaterielClassId() {
        return materielClassId;
    }

    public void setMaterielClassId(Integer materielClassId) {
        this.materielClassId = materielClassId;
    }

    public Integer getMaterielBrandId() {
        return materielBrandId;
    }

    public void setMaterielBrandId(Integer materielBrandId) {
        this.materielBrandId = materielBrandId;
    }
}
