/**
 * 
 */
package org.gz.warehouse.service.purchase;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderQuery;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderVO;
import org.gz.warehouse.entity.purchase.PurchaseApprovedOrderVO;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2017年12月18日 上午11:33:32
 */
public interface PurchaseApprovedService {

	/** 
	* @Description: 待审批采购申请分页列表
	* @param  q
	* @return ResponseResult<ResultPager<PurchaseApplyOrderVO>>
	*/
	ResponseResult<ResultPager<PurchaseApplyOrderVO>> queryByPage(PurchaseApplyOrderQuery q);

	/** 
	* @Description: 待审批采购申请详情
	* @param id
	* @return ResponseResult<PurchaseApplyOrderVO>
	*/
	ResponseResult<PurchaseApplyOrderVO> queryDetail(Long id);

	/** 
	* @Description: 采购申请审批
	* @param  p
	* @return ResponseResult<PurchaseApprovedOrderVO>
	*/
	ResponseResult<PurchaseApprovedOrderVO> approved(PurchaseApprovedOrderVO p);

	/** 
	* @Description: 采购申请拒绝
	* @param  p
	* @return ResponseResult<PurchaseApprovedOrderVO>
	*/
	ResponseResult<PurchaseApprovedOrderVO> reject(PurchaseApprovedOrderVO p);

}
