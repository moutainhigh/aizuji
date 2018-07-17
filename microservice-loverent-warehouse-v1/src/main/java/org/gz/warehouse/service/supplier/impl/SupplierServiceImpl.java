/**
 * 
 */
package org.gz.warehouse.service.supplier.impl;

import java.util.List;

import org.gz.warehouse.entity.supplier.SupplierBasicInfo;
import org.gz.warehouse.mapper.supplier.SupplierMapper;
import org.gz.warehouse.service.supplier.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2017年12月28日 下午1:42:19
 */
@Service
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	private SupplierMapper supplierMapper;
	
	@Override
	public List<SupplierBasicInfo> queryAllSuppliers() {
		return supplierMapper.queryAllSuppliers();
	}

}
