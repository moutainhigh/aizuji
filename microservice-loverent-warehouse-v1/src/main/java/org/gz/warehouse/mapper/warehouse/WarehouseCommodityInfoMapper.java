/**
 * 
 */
package org.gz.warehouse.mapper.warehouse;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.warehouse.common.vo.WarehouseCommodityReq;
import org.gz.warehouse.common.vo.WarehouseCommodityResp;
import org.gz.warehouse.entity.warehouse.WarehouseCommodityInfo;
import org.gz.warehouse.entity.warehouse.WarehouseCommodityInfoQuery;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2017年12月21日 下午3:48:23
 */
@Mapper
public interface WarehouseCommodityInfoMapper {

	/** 
	* @Description: 批量入库
	* @param  commodityList
	* @return int
	*/
	int batchInsert(List<WarehouseCommodityInfo> commodityList);

	/** 
	* @Description: 更新商品仓库，库位
	* @param  updateCommodity
	* @return int
	*/
	int updateWarehouseLocation(WarehouseCommodityInfo updateCommodity);

	/** 
	* @Description: 
	* @param @param commodityQuery
	* @param @return
	* @return List<WarehouseCommodityInfo>
	* @throws 
	*/
	List<WarehouseCommodityInfo> queryPageList(WarehouseCommodityInfoQuery commodityQuery);

	/** 
	* @Description: 
	* @param @param q
	* @param @return
	* @return int
	* @throws 
	*/
	int queryWarehouseLocationCommodityCount(WarehouseCommodityReq q);

	/** 
	* @Description: 
	* @param @param q
	* @param @return
	* @return List<WarehouseCommodityResp>
	* @throws 
	*/
	List<WarehouseCommodityResp> queryWarehouseLocationCommodityList(WarehouseCommodityReq q);

}
