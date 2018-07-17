package org.gz.warehouse.constants;


/**
 * 产品类型枚举类
 * @Description:TODO
 * Author	Version		Date		Changes
 * liuyt 	1.0  		2018年3月6日 	Created
 */
public enum ProductTypeEnum {
    TYPE_LEASE(1, "租赁"), TYPE_PURCHASING(2, "以租代购"), TYPE_SALE(3, "出售");

    private int    code;

    private String desc;

    ProductTypeEnum(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
