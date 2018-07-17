package org.gz.order.common.dto;

import java.io.Serializable;

import org.gz.order.common.entity.RentRecord;

/**
 * 添加订单对象 Author Version Date Changes 临时工 1.0 2017年12月13日 Created
 */
public class AddOrderReq extends RentRecord implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 产品编号
     */
    private String            productNo;

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

}
