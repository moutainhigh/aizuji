package org.gz.order.server.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.order.common.dto.UpdateDto;
import org.gz.order.common.entity.UserHistory;
/**
 * UserHistory Dao
 * 
 * @author pk
 * @date 2018-01-28
 */
@Mapper
public interface UserHistoryDao {
	
	/**
	 * 
	 * @Description: 如果需要分页或动态排序查询，将参数修改为QueryDto,再将查询条件、动态排序、分页查询数据放进QueryDto即可，无须修改mapper文件
	 * @return
	 * @throws
	 * createBy:zhuangjianfa              
	 * createDate:2017年1月25日上午11:15:06
	 */
  public List<UserHistory> queryList(UserHistory dto);

  /**
   * 插入数据
   * 
   * @param m
   * @return
   */
  Long add(UserHistory m);

  /**
   * 插入数据
   * 
   * @param m
   * @return
   */
  Long update(UpdateDto<UserHistory> m);

  /**
   * 通过rentRecordNolist查询历史用户列表信息
   * 
   * @param list
   * @return
   * @throws createBy:临时工 createDate:2018年1月29日
   */
  public List<UserHistory> queryInfoByRentRecordNos(List<String> list);

}
