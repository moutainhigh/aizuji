package org.gz.order.server.service;

import java.util.List;

import org.gz.order.common.entity.RentInvoice;

public interface RentInvoiceService {

  /**
   * 按条件查询发票
   * @param dto
   * @return
   */
  public List<RentInvoice> queryList(RentInvoice dto);

  /**
   * 通过订单编号更新发票信息
   * 
   * @param rentInvoice
   * @return
   * @throws createBy:临时工 createDate:2018年3月29日
   */
  int update(RentInvoice rentInvoice);

}
