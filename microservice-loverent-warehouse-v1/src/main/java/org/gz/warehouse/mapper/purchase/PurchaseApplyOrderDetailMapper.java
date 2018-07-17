/**
 * 
 */
package org.gz.warehouse.mapper.purchase;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderDetail;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderDetailVO;
import org.gz.warehouse.entity.purchase.PurchaseApprovedOrderDetailVO;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月18日 下午1:38:11
 */
@Mapper
public interface PurchaseApplyOrderDetailMapper {

	/** 
	* @Description: 插入数据
	* @param detailList
	* @return int
	*/
	int batchInsert(List<PurchaseApplyOrderDetail> detailList);

	/** 
	* @Description: 根据采购申请主键获取采购物料清单
	* @param id
	* @return List<PurchaseApplyOrderDetailVO>
	*/
	List<PurchaseApplyOrderDetailVO> queryByPurchaseApplyOrderId(@Param("purchaseApplyOrderId")Long purchaseApplyOrderId);

	/** 
	* @Description: 根据主键查询详情
	* @param  id
	* @return PurchaseApplyOrderDetail
	*/
	PurchaseApplyOrderDetail selectByPrimaryKey(@Param("id")Long id);

	/** 
	* @Description: 更新审批数量
	* @param  v
	* @return int
	*/
	int updateApprovedQuantity(PurchaseApprovedOrderDetailVO v);

	/** 
	* @Description: 根据主键查询批量存在总数
	* @param @param idList
	* @return int
	*/
	int queryCountByIDList(List<Long> idList);

	/** 
	* @Description: 更新物料的实收数量，差异数量
	* @param  purchaseApplyOrderDetail
	*/
	int updateRecvQuantity(PurchaseApplyOrderDetail purchaseApplyOrderDetail);
}
