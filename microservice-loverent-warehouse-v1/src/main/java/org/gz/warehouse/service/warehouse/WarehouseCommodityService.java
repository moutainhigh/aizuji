/**
 * 
 */
package org.gz.warehouse.service.warehouse;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.common.vo.WarehouseCommodityReq;
import org.gz.warehouse.common.vo.WarehouseCommodityResp;

/**
 * @Title: 库存商品业务处理接口
 * @author hxj
 * @date 2018年3月14日 下午4:16:18
 */
public interface WarehouseCommodityService {


	/**
	 * 
	* @Description: 
	* @param  q
	* @return ResponseResult<ResultPager<WarehouseCommodityResp>>
	 */
	ResponseResult<ResultPager<WarehouseCommodityResp>> queryWarehouseLocationCommoditys(WarehouseCommodityReq q);

}
