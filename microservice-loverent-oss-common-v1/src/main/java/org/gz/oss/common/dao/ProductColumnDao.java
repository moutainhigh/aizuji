/**
 * Copyright © 2014 GZJF Corp. All rights reserved.
 * This software is proprietary to and embodies the confidential
 * technology of GZJF Corp.  Possession, use, or copying
 * of this software and media is authorized only pursuant to a
 * valid written license from GZJF Corp or an authorized sublicensor.
 */
package org.gz.oss.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.oss.common.dto.UpdateDto;
import org.gz.oss.common.entity.ProductColumn;

/**
 * ProductColumn Dao
 * 
 * @author zhangguoliang
 * @date 2017-12-19
 */
@Mapper
public interface ProductColumnDao{
	
	public void update(UpdateDto<ProductColumn> updateDto);
	
	public void add(ProductColumn productColumn);
	
	public List<ProductColumn> findAll();
	
	public ProductColumn getById(Integer id);
}
