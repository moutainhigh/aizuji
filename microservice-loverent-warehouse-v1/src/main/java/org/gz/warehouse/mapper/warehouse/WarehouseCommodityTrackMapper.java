/**
 * 
 */
package org.gz.warehouse.mapper.warehouse;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.warehouse.entity.warehouse.WarehouseCommodityTrack;
import org.gz.warehouse.entity.warehouse.WarehouseCommodityTrackQuery;
import org.gz.warehouse.entity.warehouse.WarehouseCommodityTrackVO;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2017年12月27日 上午11:43:35
 */
@Mapper
public interface WarehouseCommodityTrackMapper {

	/** 
	* @Description: 
	* @param @param commodityQuery
	* @param @return
	* @return List<WarehouseCommodityTrack>
	* @throws 
	*/
	List<WarehouseCommodityTrack> queryCommodityTrackPageList(WarehouseCommodityTrackQuery commodityQuery);

	/** 
	* @Description: 插入商品跟踪记录
	* @param  track
	* @return int
	*/
	int insert(WarehouseCommodityTrack track);

	/** 
	* @Description: 
	* @param @param trackList
	* @return void
	* @throws 
	*/
	int batchInsert(List<WarehouseCommodityTrack> trackList);

	/** 
	* @Description: 
	* @param @param q
	* @param @return
	* @return int
	* @throws 
	*/
	int queryPageCount(WarehouseCommodityTrackQuery q);

	/** 
	* @Description: 
	* @param @param q
	* @param @return
	* @return List<WarehouseCommodityTrackVO>
	* @throws 
	*/
	List<WarehouseCommodityTrackVO> queryPageList(WarehouseCommodityTrackQuery q);

}
