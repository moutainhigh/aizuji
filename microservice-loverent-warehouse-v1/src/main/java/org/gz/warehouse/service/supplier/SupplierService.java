/**
 * 
 */
package org.gz.warehouse.service.supplier;

import java.util.List;

import org.gz.warehouse.entity.supplier.SupplierBasicInfo;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2017年12月28日 下午1:41:55
 */
public interface SupplierService {

	List<SupplierBasicInfo> queryAllSuppliers();

}
