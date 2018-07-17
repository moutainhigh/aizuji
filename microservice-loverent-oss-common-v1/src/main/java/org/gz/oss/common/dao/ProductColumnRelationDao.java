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
import org.gz.oss.common.entity.ProductColumnRelation;

/**
 * ProductColumnRelation Dao
 * 
 * @author zhangguoliang
 * @date 2017-12-19
 */
@Mapper
public interface ProductColumnRelationDao{
	
	public void update(UpdateDto<ProductColumnRelation> updateDto);
	
	public void add(ProductColumnRelation relation);
	
	public void addBatch(List<ProductColumnRelation> relationList);
	
	public List<ProductColumnRelation> queryListByColumnId(Integer columnId);
	
	public ProductColumnRelation getById(Integer id);
	
	public void deleteBycolumnId(Integer columnId);

	public List<ProductColumnRelation> selectByMaterielModelId(
			Integer materielModelId);

	public void updateRelation(ProductColumnRelation relation);

	public void deleteById(Integer id);

	public List<ProductColumnRelation> queryAvalableListByColumnId(Integer columnId);
}
