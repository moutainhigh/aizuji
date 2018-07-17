/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.oss.common.dao;

import java.util.List;

import org.gz.oss.common.dto.RecommendDto;
import org.gz.oss.common.dto.UpdateDto;
import org.gz.oss.common.entity.Recommend;
/**
 * Recommend Dao
 * 
 * @author liuyt
 * @date 2018-03-20
 */
public interface RecommendDao {

    void update(UpdateDto<Recommend> updateDto);

    void add(Recommend recommend);

    List<Recommend> findAll();

    Recommend getById(Integer id);

    void delete(Integer id);
	
	/**
	 * 
	 * @Description: 如果需要分页或动态排序查询，将参数修改为QueryDto,再将查询条件、动态排序、分页查询数据放进QueryDto即可，无须修改mapper文件
	 * @return
	 * @throws
	 * createBy:zhuangjianfa              
	 * createDate:2017年1月25日上午11:15:06
	 */
	public List<Recommend> queryList(RecommendDto dto);
}
