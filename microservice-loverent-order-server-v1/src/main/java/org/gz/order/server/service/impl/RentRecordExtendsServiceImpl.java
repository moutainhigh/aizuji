package org.gz.order.server.service.impl;

import javax.annotation.Resource;

import org.gz.order.common.entity.RentRecordExtends;
import org.gz.order.server.dao.RentRecordExtendsDao;
import org.gz.order.server.service.RentRecordExtendsService;
import org.springframework.stereotype.Service;

@Service
public class RentRecordExtendsServiceImpl implements RentRecordExtendsService {

    @Resource
    private RentRecordExtendsDao rentRecordExtendsDao;

    @Override
    public Long add(RentRecordExtends m) {
        return rentRecordExtendsDao.add(m);
    }

	@Override
	public RentRecordExtends getByRentRecordNo(String rentRecordNo) {
		return rentRecordExtendsDao.getByRentRecordNo(rentRecordNo);
	}

}
