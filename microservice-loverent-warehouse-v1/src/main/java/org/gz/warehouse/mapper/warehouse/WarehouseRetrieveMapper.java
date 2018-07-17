/**
 * 
 */
package org.gz.warehouse.mapper.warehouse;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gz.warehouse.common.vo.WarehouseMaterielCount;
import org.gz.warehouse.common.vo.WarehouseMaterielCountQuery;
import org.gz.warehouse.entity.warehouse.WarehouseAggregationVO;
import org.gz.warehouse.entity.warehouse.WarehouseCommodityInfoQuery;
import org.gz.warehouse.entity.warehouse.WarehouseCommodityShortVO;
import org.gz.warehouse.entity.warehouse.WarehouseLocationMaterielDetailVO;
import org.gz.warehouse.entity.warehouse.WarehouseMaterielAggregationWrapper;
import org.gz.warehouse.entity.warehouse.WarehouseMaterielDetailVO;
import org.gz.warehouse.entity.warehouse.WarehouseRetrieveQuery;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月22日 上午11:29:44
 */
@Mapper
public interface WarehouseRetrieveMapper {

	/** 
	* @Description: 
	* @param @param q
	* @param @return
	* @return int
	* @throws 
	*/
	int queryAggregationPageCount(WarehouseRetrieveQuery q);

	/** 
	* @Description: 
	* @param @param q
	* @param @return
	* @return List<WarehouseAggregationVO>
	* @throws 
	*/
	List<WarehouseAggregationVO> queryAggregationPageList(WarehouseRetrieveQuery q);

	/** 
	* @Description: 查询库存物料列表
	* @param @param q
	* @param @return
	* @return List<WarehouseMaterielAggregationWrapper>
	* @throws 
	*/
	List<WarehouseMaterielAggregationWrapper> queryWarehouseMaterielList(WarehouseRetrieveQuery q);

	/** 
	* @Description: 查询物料所在仓库列表
	* @param @param materielBasicId
	* @return List<WarehouseMaterielDetailVO>
	*/
	List<WarehouseMaterielDetailVO> queryWarehouseMaterielAggregationList(@Param("materielBasicId")Integer materielBasicId);

	/** 
	* @Description: 按物料ID,仓库ID分组统计仓位中的物料数量
	* @param materielBasicId
	* @param warehouseId
	* @return List<WarehouseLocationMaterielDetailVO>
	*/
	List<WarehouseLocationMaterielDetailVO> queryLocationMaterielAggregationList(@Param("materielBasicId")Integer materielBasicId,@Param("warehouseId")Integer warehouseId);

	/** 
	* @Description: 
	* @param @param q
	* @param @return
	* @return WarehouseMaterielCount
	* @throws 
	*/
	WarehouseMaterielCount queryWarehouseMaterielCount(WarehouseMaterielCountQuery q);

	/** 
	* @Description: 
	* @param @param commodityQuery
	* @param @return
	* @return List<WarehouseCommodityShortVO>
	* @throws 
	*/
	List<WarehouseCommodityShortVO> queryWarehouseCommodityPageList(WarehouseCommodityInfoQuery commodityQuery);

	/** 
	* @Description: 
	* @param warehouseId
	* @param locationIds
	* @return List<WarehouseLocationMaterielDetailVO>
	*/
	List<WarehouseLocationMaterielDetailVO> queryExcludeLocationMaterielAggregationList(@Param("warehouseId")Integer warehouseId,@Param("locationIds")List<Integer> locationIds);

}
