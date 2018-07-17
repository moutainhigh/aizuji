/**
 * 
 */
package org.gz.warehouse.service.warehouse.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.constants.AdjustReasonEnum;
import org.gz.warehouse.constants.AdjustTypeEnum;
import org.gz.warehouse.entity.warehouse.WarehouseChangeRecord;
import org.gz.warehouse.entity.warehouse.WarehouseChangeRecordDetailQuery;
import org.gz.warehouse.entity.warehouse.WarehouseChangeRecordDetailVO;
import org.gz.warehouse.entity.warehouse.WarehouseChangeRecordQuery;
import org.gz.warehouse.entity.warehouse.WarehouseChangeRecordVO;
import org.gz.warehouse.mapper.warehouse.WarehouseChangeRecordMapper;
import org.gz.warehouse.service.warehouse.WarehouseChangeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月23日 上午9:03:19
 */
@Service
public class WarehouseChangeRecordServiceImpl implements WarehouseChangeRecordService {

	@Autowired
	private WarehouseChangeRecordMapper changeRecordMapper;

	@Override
	public ResponseResult<ResultPager<WarehouseChangeRecordVO>> queryAggregationPage(WarehouseChangeRecordQuery q) {
		int totalNum = this.changeRecordMapper.queryPageCount(q);
		List<WarehouseChangeRecordVO> list = new ArrayList<WarehouseChangeRecordVO>(0);
		if (totalNum > 0) {
			list = this.changeRecordMapper.queryPageList(q);
		}
		ResultPager<WarehouseChangeRecordVO> data = new ResultPager<WarehouseChangeRecordVO>(totalNum, q.getCurrPage(),
				q.getPageSize(), list);
		return ResponseResult.buildSuccessResponse(data);
	}

	@Override
	public ResponseResult<ResultPager<WarehouseChangeRecordDetailVO>> queryDetailPage(WarehouseChangeRecordDetailQuery q) {
		int totalNum = this.changeRecordMapper.queryDetailPageCount(q);
		List<WarehouseChangeRecordDetailVO> list = new ArrayList<WarehouseChangeRecordDetailVO>(0);
		if (totalNum > 0) {
			list = this.changeRecordMapper.queryDetailPageList(q);
		}
		ResultPager<WarehouseChangeRecordDetailVO> data = new ResultPager<WarehouseChangeRecordDetailVO>(totalNum, q.getCurrPage(),
				q.getPageSize(), list);
		return ResponseResult.buildSuccessResponse(data);
	}
	
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
	public int insertChangeRecord(Long materielBasicId,String sourceOrderNo, Integer  warehouseId, Integer locationId, Date operateOn,AdjustTypeEnum adjustType,String adjustBatchNo,AdjustReasonEnum adjustReason, String storageBatchNo,int changeQuantity,Long operatorId,String operatorName) {
		WarehouseChangeRecord newRecord = new WarehouseChangeRecord();
		newRecord.setAdjustBatchNo(adjustBatchNo);
		newRecord.setAdjustReason(adjustReason.getReason());
		newRecord.setAdjustType(adjustType.getCode());
		newRecord.setChangeQuantity(changeQuantity);
		newRecord.setOperatorId(operatorId);
		newRecord.setOperatorName(operatorName);
		newRecord.setOperateOn(operateOn);
		newRecord.setMaterielBasicId(materielBasicId);
		newRecord.setSourceOrderNo(sourceOrderNo);
		newRecord.setStorageBatchNo(storageBatchNo);
		newRecord.setWarehouseId(warehouseId);
		newRecord.setWarehouseLocationId(locationId);
		return this.changeRecordMapper.insert(newRecord);
	}

}
