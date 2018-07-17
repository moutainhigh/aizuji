package org.gz.oss.common.Enum;

/**
 * 商品类型枚举类
 * @author daiqingwen
 * @Date 2018-3-19 上午 9:54
 *
 */
public enum CommodityType {
    LINK_URL(10,"链接"),
    SALE(20,"售卖"),
    LEASE(30,"租赁");

    private int code;

    private String name;


    CommodityType(int code, String name) {
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
