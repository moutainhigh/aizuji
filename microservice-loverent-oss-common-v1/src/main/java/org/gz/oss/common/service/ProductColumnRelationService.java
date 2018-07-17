package org.gz.oss.common.service;

import java.util.List;

import org.gz.oss.common.entity.ProductColumnRelation;

public interface ProductColumnRelationService {
	
	void add(ProductColumnRelation relation);
	
	List<ProductColumnRelation> queryListByColumnId(Integer columnId);
	
	ProductColumnRelation getById(Integer id);
	
	void addBatch(List<ProductColumnRelation> relationList);
	
	void updateBatch(List<ProductColumnRelation> relationReqList);
	
	void deleteBycolumnId(Integer columnId);

	List<ProductColumnRelation> selectByMaterielModelId(Integer materielModelId);

	void updateRelation(ProductColumnRelation relation);

	void deleteRelation(Integer id);

	ProductColumnRelation getRelation(Integer id);

	
}
