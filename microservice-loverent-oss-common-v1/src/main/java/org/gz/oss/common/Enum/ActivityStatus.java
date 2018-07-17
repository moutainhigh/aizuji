package org.gz.oss.common.Enum;

/**
 * 商品类型枚举类
 * @author daiqingwen
 * @Date 2018-3-19 下午 13:43
 *
 */
public enum ActivityStatus {
    START(10,"已开始"),
    UNSTART(20,"未开始"),
    OVER(30,"结束");

    private int code;

    private String name;

    ActivityStatus(int code, String name) {
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
