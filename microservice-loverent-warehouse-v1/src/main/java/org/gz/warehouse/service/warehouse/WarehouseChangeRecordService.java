/**
 * 
 */
package org.gz.warehouse.service.warehouse;

import java.util.Date;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.constants.AdjustReasonEnum;
import org.gz.warehouse.constants.AdjustTypeEnum;
import org.gz.warehouse.entity.warehouse.WarehouseChangeRecordDetailQuery;
import org.gz.warehouse.entity.warehouse.WarehouseChangeRecordDetailVO;
import org.gz.warehouse.entity.warehouse.WarehouseChangeRecordQuery;
import org.gz.warehouse.entity.warehouse.WarehouseChangeRecordVO;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月23日 上午8:49:37
 */
public interface WarehouseChangeRecordService {

	/**
	 * @Description: 查询调整记录
	 * @param q
	 * @return ResponseResult<ResultPager<WarehouseChangeRecordVO>>
	 */
	ResponseResult<ResultPager<WarehouseChangeRecordVO>> queryAggregationPage(WarehouseChangeRecordQuery q);

	/** 
	* @Description: 
	* @param @param q
	* @param @return
	* @return ResponseResult<ResultPager<WarehouseChangeRecordDetailVO>>
	* @throws 
	*/
	ResponseResult<ResultPager<WarehouseChangeRecordDetailVO>> queryDetailPage(WarehouseChangeRecordDetailQuery q);
	
	/**
	 * 
	* @Description: 插入库存变化记录
	* @param materielBasicId 物料ID
	* @param sourceOrderNo	 源单号：采购单号/销售单号
	* @param warehouseId	仓库ID
	* @param locationId		仓位ID
	* @param operateOn		操作时间
	* @param adjustType		调整类型
	* @param adjustBatchNo	调整批次号
	* @param adjustReason	调整原因
	* @param storageBatchNo	入库批次号
	* @param changeQuantity	变更数量：正向调整为正值，负向调整为负值
	* @param operatorId		操作人ID
	* @param operatorName	操作人姓名
	 */
	int insertChangeRecord(Long materielBasicId,String sourceOrderNo, Integer  warehouseId, Integer locationId, Date operateOn,AdjustTypeEnum adjustType,String adjustBatchNo,AdjustReasonEnum adjustReason, String storageBatchNo,int changeQuantity,Long operatorId,String operatorName);

}
