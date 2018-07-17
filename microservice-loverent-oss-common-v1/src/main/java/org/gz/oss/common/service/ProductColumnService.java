package org.gz.oss.common.service;

import java.util.List;

import org.gz.oss.common.entity.ProductColumn;

public interface ProductColumnService {
	
	void add(ProductColumn column);
	
	List<ProductColumn> findAll();
	
	ProductColumn getById(Integer id);
	
	void batchUpdate(List<ProductColumn> columnList);
	
	void update(ProductColumn column);
}
