/**
 * 
 */
package org.gz.warehouse.service.purchase;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrder;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderQuery;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderVO;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2017年12月18日 上午11:33:32
 */
public interface PurchaseApplyService {

	/** 
	* @Description: 插入数据
	* @param  p
	* @return ResponseResult<?>
	*/
	ResponseResult<PurchaseApplyOrder> insert(PurchaseApplyOrder p);

	/** 
	* @Description: 获取采购分页列表
	* @param q
	* @return ResponseResult<ResultPager<PurchaseApplyOrderVO>>
	*/
	ResponseResult<ResultPager<PurchaseApplyOrderVO>> queryByPage(PurchaseApplyOrderQuery q);

	/** 
	* @Description: 获取详情
	* @param id
	* @return ResponseResult<PurchaseApplyOrderVO>
	*/
	ResponseResult<PurchaseApplyOrderVO> queryDetail(Long id);

	/** 
	* @Description: 
	* @param @param id
	* @param @return
	* @return ResponseResult<?>
	* @throws 
	*/
	ResponseResult<PurchaseApplyOrder> updateToSubmitFlag(Long id);

	/** 
	* @Description: 
	* @param @param p
	* @param @return
	* @return ResponseResult<?>
	* @throws 
	*/
	ResponseResult<PurchaseApplyOrder> update(PurchaseApplyOrder p);

}
