/**
 * 
 */
package org.gz.warehouse.mapper.purchase;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.warehouse.entity.purchase.PurchaseOrderRecv;

/**
 * @Title: 
 * @author hxj
 * @date 2017年12月21日 下午2:51:35
 */
@Mapper
public interface PurchaseOrderRecvMapper {

	/** 
	* @Description: 
	* @param orderRecvList
	* @return int
	*/
	int batchInsert(List<PurchaseOrderRecv> orderRecvList);

}
