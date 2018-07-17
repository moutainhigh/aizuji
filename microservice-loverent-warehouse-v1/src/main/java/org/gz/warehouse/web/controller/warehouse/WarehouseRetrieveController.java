package org.gz.warehouse.web.controller.warehouse;

import org.gz.common.constants.ZTreeSimpleNode;
import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.AssertUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.warehouse.converter.PrefixTurnConverter;
import org.gz.warehouse.entity.warehouse.WarehouseAggregationVO;
import org.gz.warehouse.entity.warehouse.WarehouseMaterielAggregationWrapper;
import org.gz.warehouse.entity.warehouse.WarehouseRetrieveQuery;
import org.gz.warehouse.service.warehouse.WarehouseInfoService;
import org.gz.warehouse.service.warehouse.WarehouseRetrieveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 
 * @Title: 库存检索控制器
 * @author hxj
 * @date 2017年12月22日 上午10:55:56
 */
@RestController
@RequestMapping("/warehouseRetrieve")
public class WarehouseRetrieveController extends BaseController {
    
	@Autowired
    private WarehouseInfoService warehouseInfoService;
    
	@Autowired
	private WarehouseRetrieveService retrieveService;
	
	
    /**
     * 获取仓库仓位树
    * @return ResponseResult<ZTreeSimpleNode>
     */
    @GetMapping(value="/getTree")
    public ResponseResult<ZTreeSimpleNode> getTree(){
    	return ResponseResult.buildSuccessResponse(this.warehouseInfoService.getTree(1));
    }
    
    /**
     * 
    * @Description: 库存汇总查询
    * @param  q
    * @return ResponseResult<ResultPager<WarehouseAggregationVO>>
     */
    @PostMapping(value = "/queryAggregationPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<ResultPager<WarehouseAggregationVO>> queryAggregationPage(@RequestBody WarehouseRetrieveQuery q) {
    	if(q!=null&&AssertUtils.isPositiveNumber4Long(q.getLocationId())) {
    		try {
    			String locationId = new PrefixTurnConverter().revert(String.valueOf(q.getLocationId()));
    			q.setLocationId(Long.valueOf(locationId));
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	return this.retrieveService.queryAggregationPage(q);
    }
    
    /**
     * 
    * @Description: 物料仓位数量汇总，传参：materielBasicId
    * @param  q
    * @return ResponseResult<ResultPager<WarehouseMaterielAggregationWrapper>>
     */
    @PostMapping(value = "/queryMaterielAggregationPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<ResultPager<WarehouseMaterielAggregationWrapper>> queryMaterielAggregationPage(@RequestBody WarehouseRetrieveQuery q) {
    		return this.retrieveService.queryMaterielAggregationPage(q);
    }
}
