package org.gz.order.api.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.gz.order.api.service.OrderCreditDetailService;
import org.gz.order.common.dto.OrderCreditDetailQvo;
import org.gz.order.common.entity.OrderCreditDetail;
import org.gz.order.server.dao.OrderCreditDetailDao;
import org.springframework.stereotype.Service;

@Service
public class OrderCreditDetailServiceImpl implements OrderCreditDetailService {

    @Resource
    private OrderCreditDetailDao orderCreditDetailDao;

    @Override
    public void add(OrderCreditDetail orderCreditDetail) {
        orderCreditDetailDao.add(orderCreditDetail);
    }

    @Override
    public List<OrderCreditDetail> queryHistoryCreditListByUserId(OrderCreditDetailQvo qvo) {
        return orderCreditDetailDao.queryHistoryCreditListByUserId(qvo);
    }
}
