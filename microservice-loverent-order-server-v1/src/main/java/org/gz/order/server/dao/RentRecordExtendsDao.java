package org.gz.order.server.dao;

/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.order.common.dto.UpdateDto;
import org.gz.order.common.entity.RentRecordExtends;
/**
 * RentRecordExtends Dao
 * 
 * @author pk
 * @date 2017-12-13
 */
@Mapper
public interface RentRecordExtendsDao {
	
	/**
	 * 
	 * @Description: 如果需要分页或动态排序查询，将参数修改为QueryDto,再将查询条件、动态排序、分页查询数据放进QueryDto即可，无须修改mapper文件
	 * @return
	 * @throws
	 * createBy:zhuangjianfa              
	 * createDate:2017年1月25日上午11:15:06
	 */
    public List<RentRecordExtends> queryList(RentRecordExtends dto);

    /**
     * 插入数据
     * 
     * @param m
     * @return
     */
    Long add(RentRecordExtends m);

    /**
     * 更新数据
     * 
     * @param m
     * @return
     * @throws createBy:临时工 createDate:2017年12月18日
     */
    int update(UpdateDto<RentRecordExtends> m);

    /**
     * 通过订单编号查询订单扩展对象
     * 
     * @param rentRecordNo
     * @return
     * @throws createBy:临时工 createDate:2017年12月18日
     */
    public RentRecordExtends getByRentRecordNo(String rentRecordNo);
}
