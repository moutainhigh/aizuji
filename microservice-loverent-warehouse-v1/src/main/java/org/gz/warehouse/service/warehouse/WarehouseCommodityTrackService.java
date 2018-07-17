/**
 * 
 */
package org.gz.warehouse.service.warehouse;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.entity.warehouse.WarehouseCommodityTrackQuery;
import org.gz.warehouse.entity.warehouse.WarehouseCommodityTrackVO;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2018年1月2日 下午2:53:26
 */
public interface WarehouseCommodityTrackService {

	/** 
	* @Description: 
	* @param  q
	* @return ResponseResult<ResultPager<WarehouseCommodityTrackVO>>
	*/
	ResponseResult<ResultPager<WarehouseCommodityTrackVO>> queryByPage(WarehouseCommodityTrackQuery q);

}
