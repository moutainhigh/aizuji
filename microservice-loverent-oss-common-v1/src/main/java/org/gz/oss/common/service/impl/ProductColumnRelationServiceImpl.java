package org.gz.oss.common.service.impl;

import java.util.List;

import org.gz.oss.common.dao.ProductColumnRelationDao;
import org.gz.oss.common.entity.ProductColumnRelation;
import org.gz.oss.common.service.ProductColumnRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductColumnRelationServiceImpl implements ProductColumnRelationService{
	@Autowired
	private ProductColumnRelationDao productColumnRelationDao;

	@Override
	public void add(ProductColumnRelation relation) {
		productColumnRelationDao.add(relation);
	}

	@Override
	public ProductColumnRelation getById(Integer id) {
		return productColumnRelationDao.getById(id);
	}

	@Override
	public void addBatch(List<ProductColumnRelation> relationList) {
		if(relationList!=null && relationList.size()>0){
			productColumnRelationDao.addBatch(relationList);
		}
	}
	
	@Override
	public void updateBatch(List<ProductColumnRelation> relationReqList) {
		if(relationReqList!=null && relationReqList.size()>0){
			for (ProductColumnRelation relation : relationReqList) {
				productColumnRelationDao.updateRelation(relation);
			}
		}
		
	}

	@Override
	public List<ProductColumnRelation> queryListByColumnId(Integer columnId) {
		return productColumnRelationDao.queryListByColumnId(columnId);
	}

	@Override
	public void deleteBycolumnId(Integer columnId) {
		productColumnRelationDao.deleteBycolumnId(columnId);
	}

	@Override
	public List<ProductColumnRelation> selectByMaterielModelId(
			Integer materielModelId) {
		return productColumnRelationDao.selectByMaterielModelId(materielModelId);
	}

	@Override
	public void updateRelation(ProductColumnRelation relation) {
		productColumnRelationDao.updateRelation(relation);
	}

	@Override
	public void deleteRelation(Integer id) {
		productColumnRelationDao.deleteById(id);
	}

	@Override
	public ProductColumnRelation getRelation(Integer id) {
		return productColumnRelationDao.getById(id);
	}

	

}
