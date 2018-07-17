package org.gz.order.server.service;

import org.gz.order.common.entity.RentRecordExtends;

public interface RentRecordExtendsService {

    /**
     * 插入数据
     * 
     * @param m
     * @return
     */
    Long add(RentRecordExtends m);
    
    /**
     * 通过单号获取租赁记录
     * @param rentRecordNo
     * @return
     */
    RentRecordExtends getByRentRecordNo(String rentRecordNo);
}
