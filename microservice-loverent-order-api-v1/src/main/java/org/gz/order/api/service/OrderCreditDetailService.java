package org.gz.order.api.service;

import java.util.List;

import org.gz.order.common.dto.OrderCreditDetailQvo;
import org.gz.order.common.entity.OrderCreditDetail;

public interface OrderCreditDetailService {

    /**
     * 插入订单审核记录
     * @param orderCreditDetail
     * @throws
     * createBy:liuyt            
     * createDate:2018年1月29日
     */
    void add(OrderCreditDetail orderCreditDetail);

    /**
     * 根据用户id查询历史审核订单（排除当前订单）
     * @param qvo
     * @return
     * @throws
     * createBy:liuyt            
     * createDate:2018年1月29日
     */
    List<OrderCreditDetail> queryHistoryCreditListByUserId(OrderCreditDetailQvo qvo);
}
