package org.gz.warehouse.constants;

/**
 * 物料状态枚举类
 * Author	Version		Date		Changes
 * daiqingwen 	1.0  	2018年3月8日 	Created
 */
public enum MaterielStatusEnum {

    NEW(10, "新机"), GOOD(20, "好机"),BAD(30,"坏机");

    private int code;

    private String name;

    MaterielStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
