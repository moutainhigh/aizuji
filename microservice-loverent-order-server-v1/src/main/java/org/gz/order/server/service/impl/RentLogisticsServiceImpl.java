package org.gz.order.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.gz.order.common.dto.UpdateDto;
import org.gz.order.common.entity.RentLogistics;
import org.gz.order.server.dao.RentLogisticsDao;
import org.gz.order.server.service.RentLogisticsService;
import org.springframework.stereotype.Service;

@Service
public class RentLogisticsServiceImpl implements RentLogisticsService {

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
