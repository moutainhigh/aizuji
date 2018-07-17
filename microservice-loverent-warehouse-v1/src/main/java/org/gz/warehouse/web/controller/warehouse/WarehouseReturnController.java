package org.gz.warehouse.web.controller.warehouse;

import java.util.Date;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.web.controller.BaseController;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.order.common.dto.RentRecordQuery;
import org.gz.warehouse.entity.warehouse.WarehouseReturnRecord;
import org.gz.warehouse.entity.warehouse.WarehouseReturnRecordVO;
import org.gz.warehouse.feign.OrderService;
import org.gz.warehouse.service.warehouse.WarehouseReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;


/**
 * @Title:
 * @author daiqingwen
 * @Description: 还机记录控制器
 * @date 2018年3月6日 上午9:58
 */
@RestController
@RequestMapping("/returnRecord")
@Slf4j
public class WarehouseReturnController extends BaseController {

    @Autowired
    private WarehouseReturnService returnService;

    @Autowired
    private OrderService orderService;
    
    /**
    * @Description: 调用订单系统查询还机订单分页列表
    * @param rentRecordQuery
    * @return ResponseResult<ResultPager<OrderDetailResp>>
     */
    @PostMapping("/queryPageReturnOrderList")
    public ResponseResult<ResultPager<OrderDetailResp>> queryPageReturnOrderList(@RequestBody RentRecordQuery rentRecordQuery){
    	return this.orderService.queryPageReturnOrderList(rentRecordQuery);
    }
    
    
    /**
     * 分页查询所有还机记录
     * @param returnRecord
     * @return WarehouseReturnRecord
     */
    @PostMapping(value = "/queryAllRecord")
    public ResponseResult<ResultPager<WarehouseReturnRecord>> query(@RequestBody WarehouseReturnRecordVO returnRecord){
        try {
            return this.returnService.queryPageList(returnRecord);
        } catch (Exception e){
            log.error("获取还机记录失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), ResponseStatus.DATA_REQUERY_ERROR.getMessage(), null);
        }
    }

    /**
     * 获取还机记录详情
     * @param id
     * @return WarehouseReturnImages
     */
    @GetMapping(value = "/queryRecordDetail/{id}")
    public ResponseResult<WarehouseReturnRecord> queryRecordDetail(@PathVariable Long id){
        if(id == null){
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), "参数为空", null);
        }
        try {
            return this.returnService.queryRecordDetail(id);
        } catch (Exception e){
            log.error("获取还机记录详情失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), ResponseStatus.DATA_REQUERY_ERROR.getMessage(), null);
        }
    }

   
    /**
     * 执行还机入库操作
     * @param returnRecord
     * @return ResponseResult
     */
    @PostMapping(value = "/updateReturnRecord")
    public ResponseResult<WarehouseReturnRecord> update(@RequestBody WarehouseReturnRecord returnRecord){
        if(StringUtils.isEmpty(returnRecord)){
            return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(), "参数为空", null);
        }
        try {
        	returnRecord.setOperateOn(new Date());
        	returnRecord.setOperatorId(1L);
        	returnRecord.setOperatorName("管理员");
            return this.returnService.transitln(returnRecord);
        } catch (Exception e){
            log.error("修改还机记录失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(), ResponseStatus.DATA_UPDATED_ERROR.getMessage(), null);
        }

    }

    /**
     * 删除还机记录
     * @param id
     * @return ResponseResult
     */
    @PostMapping(value = "/deleteReturnRecord", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> delete( @RequestBody String id){
        if(StringUtils.isEmpty(id)){
            return ResponseResult.build(ResponseStatus.DATA_DELETED_ERROR.getCode(), "参数为空", null);
        }
        try {
            return this.returnService.deleteRecord(id);
        } catch (Exception e){
            log.error("删除还机记录失败：{}",e.getLocalizedMessage());
            return ResponseResult.build(ResponseStatus.DATA_DELETED_ERROR.getCode(), ResponseStatus.DATA_DELETED_ERROR.getMessage(), null);
        }

    }



}
