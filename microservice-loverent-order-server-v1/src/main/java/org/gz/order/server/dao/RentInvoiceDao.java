package org.gz.order.server.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.order.common.dto.UpdateDto;
import org.gz.order.common.entity.RentInvoice;
/**
 * RentInvoice Dao
 * 
 * @author pk
 * @date 2018-03-12
 */
@Mapper
public interface RentInvoiceDao {
	
	/**
	 * 
	 * @Description: 如果需要分页或动态排序查询，将参数修改为QueryDto,再将查询条件、动态排序、分页查询数据放进QueryDto即可，无须修改mapper文件
	 * @return
	 * @throws
	 * createBy:zhuangjianfa              
	 * createDate:2017年1月25日上午11:15:06
	 */
  public List<RentInvoice> queryList(RentInvoice dto);

  /**
   * 添加发票信息
   * 
   * @param rentInvoice
   * @return
   * @throws createBy:临时工 createDate:2018年3月12日
   */
  public Long add(RentInvoice rentInvoice);

  /**
   * 更新
   * 
   * @param m
   * @return
   * @throws createBy:临时工 createDate:2018年3月29日
   */

  int update(UpdateDto<RentInvoice> m);
}
