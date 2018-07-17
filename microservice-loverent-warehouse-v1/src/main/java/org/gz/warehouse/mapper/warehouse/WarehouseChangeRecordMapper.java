/**
 * 
 */
package org.gz.warehouse.mapper.warehouse;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.warehouse.entity.warehouse.WarehouseChangeRecord;
import org.gz.warehouse.entity.warehouse.WarehouseChangeRecordDetailQuery;
import org.gz.warehouse.entity.warehouse.WarehouseChangeRecordDetailVO;
import org.gz.warehouse.entity.warehouse.WarehouseChangeRecordQuery;
import org.gz.warehouse.entity.warehouse.WarehouseChangeRecordVO;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2017年12月21日 下午3:51:41
 */
@Mapper
public interface WarehouseChangeRecordMapper {

	/** 
	* @Description: 
	* @param  changeRecord
	* @return int
	*/
	int insert(WarehouseChangeRecord changeRecord);

	/** 
	* @Description: 
	* @param @param q
	* @param @return
	* @return int
	* @throws 
	*/
	int queryPageCount(WarehouseChangeRecordQuery q);

	/** 
	* @Description: 
	* @param @param q
	* @param @return
	* @return List<WarehouseChangeRecordVO>
	* @throws 
	*/
	List<WarehouseChangeRecordVO> queryPageList(WarehouseChangeRecordQuery q);

	/** 
	* @Description: 
	* @param @param q
	* @param @return
	* @return int
	* @throws 
	*/
	int queryDetailPageCount(WarehouseChangeRecordDetailQuery q);

	/** 
	* @Description: 
	* @param @param q
	* @param @return
	* @return List<WarehouseChangeRecordDetailVO>
	* @throws 
	*/
	List<WarehouseChangeRecordDetailVO> queryDetailPageList(WarehouseChangeRecordDetailQuery q);

	/** 
	* @Description: 
	* @param @param updateRecord
	* @return void
	* @throws 
	*/
	int decreaseWarehouseLocationQuantity(WarehouseChangeRecord updateRecord);

	/** 
	* @Description: 
	* @param @param updateRecord
	* @return void
	* @throws 
	*/
	int increaseWarehouseLocationQuantity(WarehouseChangeRecord updateRecord);

}
