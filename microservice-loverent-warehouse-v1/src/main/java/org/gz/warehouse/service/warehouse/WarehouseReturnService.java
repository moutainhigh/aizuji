package org.gz.warehouse.service.warehouse;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.common.vo.ApplyReturnReq;
import org.gz.warehouse.entity.warehouse.WarehouseReturnImages;
import org.gz.warehouse.entity.warehouse.WarehouseReturnRecord;
import org.gz.warehouse.entity.warehouse.WarehouseReturnRecordVO;

import java.util.List;

/**
 * 还机记录处理类
 * @author daiqingwen
 * @date 2018年3月6日 2018年3月6日 上午10:08
 */
public interface WarehouseReturnService {

    /**
     * 查询所有还机记录
     * @return WarehouseReturnRecord
     * @param returnRecord
     */
    ResponseResult<ResultPager<WarehouseReturnRecord>> queryPageList(WarehouseReturnRecordVO returnRecord);

    /**
     * 新增还机记录
     * @param returnRecord
     * @return ResponseResult
     */
    ResponseResult<WarehouseReturnRecord> transitln(WarehouseReturnRecord returnRecord);

    /**
     * 修改还机记录
     * @param returnRecord
     * @return ResponseResult
     */
    ResponseResult<WarehouseReturnRecord> updateRecord(WarehouseReturnRecord returnRecord);

    /**
     * 删除还机记录
     * @param id
     * @return ResponseResult
     */
    ResponseResult<?> deleteRecord(String id);

    /**
     * 获取还机记录详情
     * @param id
     * @return
     */
    ResponseResult<WarehouseReturnRecord> queryRecordDetail(Long id);

	/** 
	* @Description:  申请还机
	* @param req
	* @return ResponseResult<ApplyReturnReq>
	*/
	ResponseResult<ApplyReturnReq> applyReturn(ApplyReturnReq req);
}
