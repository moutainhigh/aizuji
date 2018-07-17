package org.gz.order.backend.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.gz.common.entity.ResultPager;
import org.gz.order.backend.auth.AuthUserHelper;
import org.gz.order.backend.service.OrderPhoneVerifyInfoService;
import org.gz.order.common.dto.OrderPhoneVerifyInfoQvo;
import org.gz.order.common.entity.OrderPhoneVerifyInfo;
import org.gz.order.server.dao.OrderPhoneVerifyInfoDao;
import org.springframework.stereotype.Service;

@Service
public class OrderPhoneVerifyInfoServiceImpl implements OrderPhoneVerifyInfoService {

    @Resource
    private OrderPhoneVerifyInfoDao opviDao;

    @Override
    public ResultPager<OrderPhoneVerifyInfo> queryList(OrderPhoneVerifyInfoQvo qvo) {
        int totalNum = this.queryPageCount(qvo);
        List<OrderPhoneVerifyInfo> list = new ArrayList<OrderPhoneVerifyInfo>();
        if (totalNum > 0) {
            list = this.opviDao.queryList(qvo);
            for (OrderPhoneVerifyInfo ocd : list) {
                ocd.setCreateName(AuthUserHelper.getUserNameById(ocd.getCreateBy()));
                ocd.setUpdateName(AuthUserHelper.getUserNameById(ocd.getUpdateBy()));
            }
        }
        ResultPager<OrderPhoneVerifyInfo> data = new ResultPager<OrderPhoneVerifyInfo>(totalNum,
            qvo.getCurrPage(),
            qvo.getPageSize(),
            list);
        return data;
    }

    @Override
    public void add(OrderPhoneVerifyInfo orderPhoneVerifyInfo) {
        this.opviDao.add(orderPhoneVerifyInfo);
    }

    @Override
    public OrderPhoneVerifyInfo getById(Long id) {
        return this.opviDao.getById(id);
    }

    @Override
    public void update(OrderPhoneVerifyInfo orderPhoneVerifyInfo) {
        this.opviDao.update(orderPhoneVerifyInfo);
    }

    @Override
    public Integer queryPageCount(OrderPhoneVerifyInfoQvo qvo) {
        return this.opviDao.queryPageCount(qvo);
    }

    @Override
    public void delete(Long id) {
        this.opviDao.delete(id);
    }

}
