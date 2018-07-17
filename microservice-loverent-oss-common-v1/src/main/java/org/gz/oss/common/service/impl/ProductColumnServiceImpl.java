package org.gz.oss.common.service.impl;

import java.util.List;

import org.gz.oss.common.dao.ProductColumnDao;
import org.gz.oss.common.dto.UpdateDto;
import org.gz.oss.common.entity.ProductColumn;
import org.gz.oss.common.service.ProductColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductColumnServiceImpl implements ProductColumnService {

	@Autowired
	private ProductColumnDao productColumnDao;
	
	@Override
	public void add(ProductColumn productColumn) {
		productColumnDao.add(productColumn);
	}

	@Override
	public List<ProductColumn> findAll() {
		return productColumnDao.findAll();
	}

	@Override
	public ProductColumn getById(Integer id) {
		return productColumnDao.getById(id);
	}

	@Override
	public void batchUpdate(List<ProductColumn> columnList) {
		if(columnList!=null && columnList.size()>0){
			for (ProductColumn column : columnList) {
				UpdateDto<ProductColumn> updateDto = new UpdateDto<>();
				ProductColumn updateWhere  = new ProductColumn();
				updateWhere.setId(column.getId());
				updateDto.setUpdateCloumn(column);
				updateDto.setUpdateWhere(updateWhere);
				productColumnDao.update(updateDto);
			}
		}
	}

	@Override
	public void update(ProductColumn column) {
		UpdateDto<ProductColumn> updateDto = new UpdateDto<>();
		ProductColumn updateWhere  = new ProductColumn();
		updateWhere.setId(column.getId());
		updateDto.setUpdateCloumn(column);
		updateDto.setUpdateWhere(updateWhere);
		productColumnDao.update(updateDto);
	}

}
