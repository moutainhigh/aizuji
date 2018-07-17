package org.gz.order.server.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.gz.common.resp.ResponseResult;
import org.gz.order.common.dto.UpdateDto;
import org.gz.order.common.entity.UserHistory;
import org.gz.order.server.dao.UserHistoryDao;
import org.gz.order.server.service.UserHistoryService;
import org.springframework.stereotype.Service;

@Service
public class UserHistoryServiceImpl implements UserHistoryService {


  @Resource
  private UserHistoryDao userHistoryDao;

  @Override
  public Long update(UserHistory userHistory) {
    UpdateDto<UserHistory> updateDto = new UpdateDto<UserHistory>();
    updateDto.setUpdateCloumn(userHistory);

    UserHistory whereCloumn = new UserHistory();
    whereCloumn.setRentRecordNo(userHistory.getRentRecordNo());
    updateDto.setUpdateWhere(whereCloumn);
    return userHistoryDao.update(updateDto);
  }

    @Override
    public UserHistory queryUserHistory(String rentRecordNo) {
        UserHistory query = new UserHistory();
        query.setRentRecordNo(rentRecordNo);
        List<UserHistory> list = userHistoryDao.queryList(query);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

}
