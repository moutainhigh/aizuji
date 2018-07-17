/**
 * 
 */
package org.gz.warehouse.service.warehouse;

import javax.validation.Valid;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.common.vo.BuyEndVO;
import org.gz.warehouse.common.vo.ConfirmSignVO;
import org.gz.warehouse.common.vo.RentingReq;
import org.gz.warehouse.common.vo.UndoPickReq;
import org.gz.warehouse.common.vo.WarehousePickQuery;
import org.gz.warehouse.common.vo.WarehousePickResult;
import org.gz.warehouse.entity.warehouse.WarehousePickingRecord;
import org.gz.warehouse.entity.warehouse.WarehousePickingRecordQuery;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2017年12月29日 上午10:07:40
 */
public interface WarehouseOutService {

	/** 
	* @Description: 
	* @param q
	* @return ResponseResult<WarehousePickingRecord>
	*/
	ResponseResult<WarehousePickingRecord> pick(WarehousePickingRecord q);

	/**
	 * 
	* @Description:撤销拣货
	* @param q
	* @return ResponseResult<WarehousePickingRecord>
	 */
	ResponseResult<WarehousePickingRecord> undoPick(WarehousePickingRecord q);
	
	/** 
	* @Description: 
	* @param @param q
	* @param @return
	* @return ResponseResult<WarehousePickingRecord>
	* @throws 
	*/
	ResponseResult<WarehousePickingRecord> out(@Valid WarehousePickingRecord q);

	/** 
	* @Description: 
	* @param @param q
	* @param @return
	* @return ResponseResult<WarehousePickResult>
	* @throws 
	*/
	ResponseResult<WarehousePickResult> pickQuery(WarehousePickQuery q);

	/** 
	* @Description: 
	* @param @param q
	* @param @return
	* @return ResponseResult<ConfirmSignVO>
	* @throws 
	*/
	ResponseResult<ConfirmSignVO> confirmSign(ConfirmSignVO q);

	/** 
	* @Description: 
	* @param q
	* @return ResponseResult<ResultPager<WarehousePickingRecord>>
	*/
	ResponseResult<ResultPager<WarehousePickingRecord>> queryByPage(WarehousePickingRecordQuery q);

	/** 
	* @Description: 
	* @param q
	* @return ResponseResult<BuyEndVO>
	* @throws 
	*/
	ResponseResult<BuyEndVO> buyEnd(BuyEndVO q);

	/** 
	* @Description: 
	* @param @param q
	* @param @return
	* @return ResponseResult<RentingReq>
	* @throws 
	*/
	ResponseResult<RentingReq> renting(RentingReq q);

	/** 
	* @Description: 撤销拣货
	* @param  q
	* @return ResponseResult<UndoPickReq>
	*/
	ResponseResult<UndoPickReq> undoPick(UndoPickReq q);
}
