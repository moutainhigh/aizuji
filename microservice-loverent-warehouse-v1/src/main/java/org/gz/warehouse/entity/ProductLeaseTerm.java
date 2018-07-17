package org.gz.warehouse.entity;

/**
 * 产品租期实体
 * @Description:TODO
 * Author	Version		Date		Changes
 * liuyt 	1.0  		2017年12月14日 	Created
 */
public class ProductLeaseTerm {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 租期描述
     */
    private String  termDesc;

    /**
     * 租期值（单位：月）
     */
    private Integer termValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTermDesc() {
        return termDesc;
    }

    public void setTermDesc(String termDesc) {
        this.termDesc = termDesc;
    }

    public Integer getTermValue() {
        return termValue;
    }

    public void setTermValue(Integer termValue) {
        this.termValue = termValue;
    }
}
