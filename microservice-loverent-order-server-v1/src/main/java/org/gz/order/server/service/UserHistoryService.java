package org.gz.order.server.service;

import org.gz.order.common.entity.UserHistory;

public interface UserHistoryService {

  /**
   * 更新历史用户表信息
   * 
   * @param useryHistory
   * @return
   * @throws createBy:临时工 createDate:2018年1月30日
   */
  Long update(UserHistory userHistory);

    /**
     * 通过订单编号查询历史用户信息
     * 
     * @param rentRecordNo
     * @return
     * @throws createBy:临时工 createDate:2018年1月28日
     */
    UserHistory queryUserHistory(String rentRecordNo);
}
