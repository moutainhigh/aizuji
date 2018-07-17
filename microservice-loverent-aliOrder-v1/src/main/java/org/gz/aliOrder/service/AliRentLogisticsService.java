package org.gz.aliOrder.service;

import java.util.List;

import org.gz.aliOrder.entity.RentLogistics;


public interface AliRentLogisticsService {

  /**
   * 按条件查询物流列表信息
   * 
   * @param dto
   * @return
   * @throws createBy:临时工 createDate:2018年3月8日
   */
  public List<RentLogistics> queryList(RentLogistics dto);

  /**
   * 添加物流表信息
   * 
   * @param RentLogisticsDto rentLogistics
   * @return
   * @throws createBy:临时工 createDate:2018年3月9日
   */
  public Long add(RentLogistics rentLogistics);

  /**
   * 通过主键id修改物流表信息
   * 
   * @param rentLogistics
   * @return
   * @throws createBy:临时工 createDate:2018年3月9日
   */
  public int update(RentLogistics rentLogistics);
}
