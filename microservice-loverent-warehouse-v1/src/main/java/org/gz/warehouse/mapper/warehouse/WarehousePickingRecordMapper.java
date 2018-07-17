/**
 * 
 */
package org.gz.warehouse.mapper.warehouse;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gz.warehouse.entity.warehouse.WarehousePickingRecord;
import org.gz.warehouse.entity.warehouse.WarehousePickingRecordQuery;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2017年12月29日 上午11:57:45
 */
@Mapper
public interface WarehousePickingRecordMapper {

	/** 
	* @Description: 
	* @param @param q
	* @return void
	* @throws 
	*/
	int insert(WarehousePickingRecord q);

	/** 
	* @Description: 
	* @param @param pickQuery
	* @param @return
	* @return List<WarehousePickingRecord>
	* @throws 
	*/
	List<WarehousePickingRecord> queryPageList(WarehousePickingRecordQuery pickQuery);

	/** 
	* @Description: 
	* @param @param pickRecord
	* @return void
	* @throws 
	*/
	int update(WarehousePickingRecord pickRecord);

	/** 
	* @Description: 
	* @param @param q
	* @param @return
	* @return int
	* @throws 
	*/
	int queryPageCount(WarehousePickingRecordQuery q);

	/** 
	* @Description: 撤销拣货
	* @param temp
	*/
	int undoPick(WarehousePickingRecord temp);

	public WarehousePickingRecord selectByPrimaryKey(@Param("id")Long id);
}
