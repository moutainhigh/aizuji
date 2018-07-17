/**
 * 
 */
package org.gz.warehouse.service.warehouse;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.common.vo.SigningQuery;
import org.gz.warehouse.common.vo.SigningResult;
import org.gz.warehouse.common.vo.WarehouseMaterielCount;
import org.gz.warehouse.common.vo.WarehouseMaterielCountQuery;
import org.gz.warehouse.entity.warehouse.WarehouseAggregationVO;
import org.gz.warehouse.entity.warehouse.WarehouseMaterielAggregationWrapper;
import org.gz.warehouse.entity.warehouse.WarehouseRetrieveQuery;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2017年12月22日 上午11:06:28
 */
public interface WarehouseRetrieveService {

	/** 
	* @Description: 库存汇总查询
	* @param q
	* @return ResponseResult<ResultPager<WarehouseAggregationVO>>
	*/
	ResponseResult<ResultPager<WarehouseAggregationVO>> queryAggregationPage(WarehouseRetrieveQuery q);

	/** 
	* @Description: 物料
	* @param @param q
	* @param @return
	* @return ResponseResult<ResultPager<WarehouseMaterielAggregationWrapper>>
	* @throws 
	*/
	ResponseResult<ResultPager<WarehouseMaterielAggregationWrapper>> queryMaterielAggregationPage(WarehouseRetrieveQuery q);

	/** 
	* @Description: 
	* @param q
	* @return ResponseResult<WarehouseMaterielCount>
	*/
	ResponseResult<WarehouseMaterielCount> queryWarehouseMaterielCount(WarehouseMaterielCountQuery q);

	/** 
	* @Description: 
	* @param q
	* @return ResponseResult<SigningResult>
	*/
	ResponseResult<SigningResult> signing(SigningQuery q);

	/** 
	* @Description: 
	* @param q
	* @return ResponseResult<SigningResult>
	*/
	ResponseResult<SigningResult> signFailed(SigningQuery q);
			

}
