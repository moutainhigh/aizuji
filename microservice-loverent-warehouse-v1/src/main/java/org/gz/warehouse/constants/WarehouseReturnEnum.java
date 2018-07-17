package org.gz.warehouse.constants;

/**
 * 还机记录枚举类
 * @Description:TODO
 * Author	Version		Date		Changes
 * daiqingwen 	1.0  	2018年3月8日 	Created
 */
public enum WarehouseReturnEnum {

    TRANSITL_MIDWAY(10, "还机中"), TRANSITL_ALREADY(20, "已入库");

    private int code;

    private String name;

    WarehouseReturnEnum(int code, String name) {
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
