/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.risk.dao;

import java.util.List;

import org.gz.risk.bean.UpdateDto;

/**
 * 
 * 所有dao的基类 
 * Author	Version		Date		Changes
 * zhuangjianfa 	1.0  		2017年7月1日 	Created
 * @since 1.
 */
public interface BaseDao<T> {
	
	/**
	 * 
	 * @Description: 根据ID获取数据 
	 * @param id
	 * @return
	 * @throws
	 * createBy:zhuangjianfa            
	 * createDate:2017年7月1日
	 */
	T getById(Integer id);
	
	/**
	 * 
	 * @Description: 查询所有 
	 * @return
	 * @throws
	 * createBy:zhuangjianfa            
	 * createDate:2017年7月1日
	 */
	List<T> findAll();
	
	/**
	 * 
	 * @Description: 单行数据新增 
	 * @param t
	 * @throws
	 * createBy:zhuangjianfa            
	 * createDate:2017年7月1日
	 */
	void add(Object t);
	
	/**
	 * @Description: 批量新增 
	 * @param list
	 * @throws
	 * createBy:zhuangjianfa            
	 * createDate:2017年7月1日
	 */
	void addBatch(List<?> list);
	
	/**
	 * @Description: 更新数据 
	 * @param dto 更新对象,将更新的数据和条件放到对象中，非数据库字段的需要自己补上
	 * @return
	 * @throws
	 * createBy:zhuangjianfa              
	 * createDate:2017年7月1日
	 */
	int update(UpdateDto<?> dto);
	
}
