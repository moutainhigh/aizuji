package org.gz.oss.common.Enum;

public enum ShopwindowType {

    ACTIVITY(10,"促销活动橱窗"),
    SELL(20,"售卖橱窗"),
    LEASE(30,"租赁橱窗");

    private int code;

    private String name;


    ShopwindowType(int code, String name) {
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
