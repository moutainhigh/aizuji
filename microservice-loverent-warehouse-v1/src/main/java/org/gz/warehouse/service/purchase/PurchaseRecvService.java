/**
 * 
 */
package org.gz.warehouse.service.purchase;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderQuery;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderVO;
import org.gz.warehouse.entity.purchase.PurchaseOrderRecvWrapper;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2017年12月20日 下午5:37:53
 */
public interface PurchaseRecvService {

	/** 
	* @Description: 采购收获列表-查询已审批的采购申请
	* @param  q
	* @return ResponseResult<ResultPager<PurchaseApplyOrderVO>>
	*/
	ResponseResult<ResultPager<PurchaseApplyOrderVO>> queryByPage(PurchaseApplyOrderQuery q);

	/** 
	* @Description: 
	* @param @param id
	* @param @return
	* @return ResponseResult<?>
	* @throws 
	*/
	ResponseResult<PurchaseApplyOrderVO> queryDetail(Long id);

	/** 
	* @Description: 
	* @param @param p
	* @param @return
	* @return ResponseResult<PurchaseOrderRecvWrapper>
	* @throws 
	*/
	ResponseResult<PurchaseOrderRecvWrapper> recv(PurchaseOrderRecvWrapper p);

}
