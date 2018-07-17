package org.gz.warehouse.web.controller.warehouse;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.web.controller.BaseController;
import org.gz.warehouse.entity.warehouse.WarehouseChangeRecordDetailQuery;
import org.gz.warehouse.entity.warehouse.WarehouseChangeRecordDetailVO;
import org.gz.warehouse.entity.warehouse.WarehouseChangeRecordQuery;
import org.gz.warehouse.entity.warehouse.WarehouseChangeRecordVO;
import org.gz.warehouse.service.warehouse.WarehouseChangeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
@RequestMapping("/warehouseChangeRecord")
public class WarehouseChangeRecordController extends BaseController {
    
	@Autowired
    private WarehouseChangeRecordService changeRecordService;
    
    /**
     * 
    * @Description: 物料变化汇总查询
    * @param  q
    * @return ResponseResult<ResultPager<WarehouseAggregationVO>>
     */
    @PostMapping(value = "/queryAggregationPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<ResultPager<WarehouseChangeRecordVO>> queryAggregationPage(@RequestBody WarehouseChangeRecordQuery q) {
    	return this.changeRecordService.queryAggregationPage(q);
    }
    
    /**
     * 
    * @Description: 物料变化明细查询
    * @param q
    * @return ResponseResult<ResultPager<WarehouseChangeRecordDetailVO>>
     */
    @PostMapping(value = "/queryDetailPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<ResultPager<WarehouseChangeRecordDetailVO>> queryDetailPage(@RequestBody WarehouseChangeRecordDetailQuery q) {
    	return this.changeRecordService.queryDetailPage(q);
    }
    
    
}
