/**
 * 
 */
package org.gz.warehouse.mapper.supplier;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.warehouse.entity.supplier.SupplierBasicInfo;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2017年12月28日 下午1:49:51
 */
@Mapper
public interface SupplierMapper {

	List<SupplierBasicInfo> queryAllSuppliers();

}
