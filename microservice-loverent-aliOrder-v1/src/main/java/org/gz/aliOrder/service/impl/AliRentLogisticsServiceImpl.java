package org.gz.aliOrder.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.gz.aliOrder.commons.UpdateDto;
import org.gz.aliOrder.dao.RentLogisticsDao;
import org.gz.aliOrder.entity.RentLogistics;
import org.gz.aliOrder.service.AliRentLogisticsService;
import org.springframework.stereotype.Service;

@Service
public class AliRentLogisticsServiceImpl implements AliRentLogisticsService {

  @Resource
  private RentLogisticsDao rentLogisticsDao;

  @Override
  public List<RentLogistics> queryList(RentLogistics dto) {
    return rentLogisticsDao.queryList(dto);
  }

  @Override
  public Long add(RentLogistics rentLogistics) {
    return rentLogisticsDao.add(rentLogistics);
  }

  @Override
  public int update(RentLogistics rentLogistics) {
    UpdateDto<RentLogistics> updateDto = new UpdateDto<RentLogistics>();
    updateDto.setUpdateCloumn(rentLogistics);
    RentLogistics whereCloumn = new RentLogistics();
    whereCloumn.setId(rentLogistics.getId());
    updateDto.setUpdateWhere(whereCloumn);
    return rentLogisticsDao.update(updateDto);
  }

}
