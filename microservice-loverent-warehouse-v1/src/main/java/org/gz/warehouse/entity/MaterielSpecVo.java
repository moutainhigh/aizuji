package org.gz.warehouse.entity;

/**
 * 物料规格Vo
 * @Description:TODO
 * Author	Version		Date		Changes
 * liuyt 	1.0  		2017年12月15日 	Created
 */
public class MaterielSpecVo {

    /**
     * 规格批次号
     */
    private String specBatchNo;

    /**
     * 规格批次值（多个值用，分隔）
     */
    private String specValues;

    public String getSpecBatchNo() {
        return specBatchNo;
    }

    public void setSpecBatchNo(String specBatchNo) {
        this.specBatchNo = specBatchNo;
    }

    public String getSpecValues() {
        return specValues;
    }

    public void setSpecValues(String specValues) {
        this.specValues = specValues;
    }
}
