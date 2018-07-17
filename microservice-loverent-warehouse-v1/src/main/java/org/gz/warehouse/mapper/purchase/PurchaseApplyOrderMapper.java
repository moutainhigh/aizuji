/**
 * 
 */
package org.gz.warehouse.mapper.purchase;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrder;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderQuery;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderVO;
import org.gz.warehouse.entity.warehouse.StorageLocation;
import org.gz.warehouse.entity.warehouse.StorageLocationQuery;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月18日 下午1:38:11
 */
@Mapper
public interface PurchaseApplyOrderMapper {

	/** 
	* @Description: 插入数据
	* @param vr
	* @return int
	*/
	int insert(PurchaseApplyOrder p);

	/** 
	* @Description: 
	* @param @param p
	* @return void
	* @throws 
	*/
	int updateSumAmount(PurchaseApplyOrder p);

	/** 
	* @Description: 获取分页总数
	* @param q
	* @return int
	*/
	int queryPageCount(PurchaseApplyOrderQuery q);

	/** 
	* @Description: 获取分页列表
	* @param q
	* @return List<PurchaseApplyOrderVO>
	*/
	List<PurchaseApplyOrderVO> queryPageList(PurchaseApplyOrderQuery q);

	/** 
	* @Description: 查询详情
	* @param id
	* @return PurchaseApplyOrder
	*/
	PurchaseApplyOrderVO selectByPrimaryKey(@Param("id")Long id);

	/** 
	* @Description: 修改采购申请订单状态
	* @param order
	* @return int
	*/
	int updateStatusFlag(PurchaseApplyOrder order);

	/** 
	* @Description: 采购申请审批
	* @param order
	* @return int
	*/
	int approved(PurchaseApplyOrder order);

	/** 
	* @Description: 采购申请拒绝
	* @param order
	* @return int
	*/
	int reject(PurchaseApplyOrder order);

	/** 
	* @Description: 
	* @param @param string
	* @param @param i
	* @param @return
	* @return StorageLocation
	* @throws 
	*/
	StorageLocation queryWarehouseLocation(String string, int i);

	/** 
	* @Description: 根据订单以及库位名称查询库位
	* @param  locationQuery
	* @return StorageLocation
	*/
	StorageLocation queryWarehouseLocation(StorageLocationQuery locationQuery);

	/** 
	* @Description: 
	* @param @param id
	* @return void
	* @throws 
	*/
	int deleteApplyOrderDetail(@Param("purchaseApplyOrderId")Long purchaseApplyOrderId);

	/** 
	* @Description: 
	* @param @param id
	* @return void
	* @throws 
	*/
	int deleteApplyOrder(@Param("id")Long id);

}
