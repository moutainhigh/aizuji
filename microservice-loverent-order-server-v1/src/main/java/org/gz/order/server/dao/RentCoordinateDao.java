package org.gz.order.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.order.common.dto.UpdateDto;
import org.gz.order.common.entity.RentCoordinate;

/**
 * RentCoordinate Dao
 * 
 * @author pk
 * @date 2017-12-20
 */
@Mapper
public interface RentCoordinateDao {

  /**
   * @param dto
   * @return
   * @throws createBy:临时工 createDate:2017年12月27日
   */
  public List<RentCoordinate> queryList(RentCoordinate dto);

  /**
   * 插入数据
   * 
   * @param RentCoordinate m
   * @return
   */
  Long add(RentCoordinate m);

  /**
   * 更新数据
   * 
   * @param m
   * @return
   * @throws createBy:临时工 createDate:2017年12月28日
   */
  int update(UpdateDto<RentCoordinate> m);
}
