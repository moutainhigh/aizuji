package org.gz.order.common.dto;

import java.io.Serializable;

import org.gz.common.entity.QueryPager;

public class OrderPhoneVerifyInfoQvo extends QueryPager implements Serializable {


    /**
     * 订单号
     */
    private String            orderNo;


    public String getOrderNo() {
        return orderNo;
    }


    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }


}
