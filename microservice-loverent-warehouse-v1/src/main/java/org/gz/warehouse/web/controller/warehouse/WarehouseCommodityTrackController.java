package org.gz.warehouse.web.controller.warehouse;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.web.controller.BaseController;
import org.gz.warehouse.entity.warehouse.WarehouseCommodityTrackQuery;
import org.gz.warehouse.entity.warehouse.WarehouseCommodityTrackVO;
import org.gz.warehouse.service.warehouse.WarehouseCommodityTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: 物料库存跟踪控制器
 * @author hxj
 * @Description:
 * @date 2018年1月2日 下午2:51:51
 */
@RestController
@RequestMapping("/commodityTrack")
public class WarehouseCommodityTrackController extends BaseController {

	@Autowired
	private WarehouseCommodityTrackService trackService;

	/**
	 * 
	* @Description: 查询物料库存跟踪分页列表
	* @param  q
	* @return ResponseResult<ResultPager<WarehouseCommodityTrackVO>>
	 */
	@PostMapping(value = "/queryByPage")
	public ResponseResult<ResultPager<WarehouseCommodityTrackVO>> queryByPage(@RequestBody WarehouseCommodityTrackQuery q){
		return this.trackService.queryByPage(q);
	}
	
	
}
