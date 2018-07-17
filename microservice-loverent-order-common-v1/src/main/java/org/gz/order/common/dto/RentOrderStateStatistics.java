package org.gz.order.common.dto;

/**
 * 订单状态统计类
 * @Description:TODO
 * Author	Version		Date		Changes
 * liuyt 	1.0  		2017年12月27日 	Created
 */
public class RentOrderStateStatistics {

    /**
     * 订单状态
     */
    private Byte    state;

    /**
     * 对应该状态的订单值
     */
    private Integer orderCount;

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }
}
