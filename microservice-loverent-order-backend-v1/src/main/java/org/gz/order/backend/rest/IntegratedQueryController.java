package org.gz.order.backend.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.web.controller.BaseController;
import org.gz.order.backend.service.IntegratedQueryService;
import org.gz.order.common.dto.RecordAndExtendsQuery;
import org.gz.order.common.dto.RentOrderStateStatistics;
import org.gz.order.common.entity.RentCoordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 综合查询
 * @author phd
 */
@RestController
@RequestMapping("/integration")
public class IntegratedQueryController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(IntegratedQueryController.class);
    @Autowired
    IntegratedQueryService integratedQueryService;
    
    
    /**
     * 订单列表查询 
     * RentRecord RentRecordExtends 
     */
    @PostMapping(value = "/list")
    public ResponseResult<?> list(RecordAndExtendsQuery recordAndExtendsQuery) {
    	try {
    		return integratedQueryService.list(recordAndExtendsQuery);
    	} catch (Exception e) {
    		logger.error("订单列表查询：{}",e.getLocalizedMessage());
    		return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(),ResponseStatus.DATA_REQUERY_ERROR.getMessage(),null);
    	}
    }
    
    /**
     * 订单详情
     * RentRecord RentRecordExtends 
     */
    @GetMapping(value = "/detail/{id}")
    public ResponseResult<?> detail(@PathVariable("id") Long id) {
		try {
			return integratedQueryService.detail(id);
		} catch (Exception e) {
			logger.error("订单详情查询：{}",e.getLocalizedMessage());
			return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(),ResponseStatus.DATA_REQUERY_ERROR.getMessage(),null);
		}
    }
    

    /**
     * 查询订单流程
     * RentState
     */
    @PostMapping(value = "/rentState")
    public ResponseResult<?> rentState(String rentRecordNo) {
		try {
			return integratedQueryService.rentState(rentRecordNo);
		} catch (Exception e) {
			logger.error("订单流程列表查询：{}",e.getLocalizedMessage());
			return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(),ResponseStatus.DATA_REQUERY_ERROR.getMessage(),null);
		}
    }
    
    /**
     * 产品类别
     */
    @PostMapping(value = "/allMaterielClassList")
    public ResponseResult<?> allMaterielClassList() {
    	try {
    		return integratedQueryService.allMaterielClassList();
		} catch (Exception e) {
			logger.error("产品类别：{}",e.getLocalizedMessage());
			return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(),ResponseStatus.DATA_REQUERY_ERROR.getMessage(),null);
		}
    }

    /**
     * 查询下单人经纬度轨迹
     */
    @PostMapping(value = "/queryRentCoordinateByRecordNo")
    public ResponseResult<?> queryRentCoordinateByRecordNo(String rentRecordNo) {
        try {
            List<RentCoordinate> list = integratedQueryService.queryRentCoordinateByRecordNo(rentRecordNo);
            return ResponseResult.buildSuccessResponse(list);
        } catch (Exception e) {
            logger.error("查询下单人经纬度轨迹：{}", e);
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(),
                ResponseStatus.DATA_REQUERY_ERROR.getMessage(),
                null);
        }
    }

    /**
     * 根据用户id查询各状态订单统计
     */
    @PostMapping(value = "/queryRentRecordStatusStatistics")
    public ResponseResult<?> queryRentRecordStatusStatistics(Long userId) {
        try {
            List<RentOrderStateStatistics> list = integratedQueryService.queryRentRecordStatusStatistics(userId);
            return ResponseResult.buildSuccessResponse(list);
        } catch (Exception e) {
            logger.error("根据用户id查询各状态订单统计：{}", e);
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(),
                ResponseStatus.DATA_REQUERY_ERROR.getMessage(),
                null);
        }
    }
    
    /**
     * 查询订单数据和用户履约数据
     */
    @PostMapping(value = "/queryRentListAndUserContract")
    public ResponseResult<?> queryRentListAndUserContract(RecordAndExtendsQuery recordAndExtendsQuery) {
        try {
            return integratedQueryService.queryRentListAndUserContract(recordAndExtendsQuery);
        } catch (Exception e) {
            logger.error("查询订单数据和用户履约数据error：{}", e);
            return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(),
                ResponseStatus.DATA_REQUERY_ERROR.getMessage(),
                null);
        }
    }
    
    /**
     * 查询订单发票信息
     * @param rentRecordNo
     * @return
     */
    @PostMapping(value = "/invoice")
    public ResponseResult<?> invoice(String rentRecordNo) {
		try {
			return integratedQueryService.invoice(rentRecordNo);
		} catch (Exception e) {
			logger.error("订单发票信息：{}",e);
			return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(),ResponseStatus.DATA_REQUERY_ERROR.getMessage(),null);
		}
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }
}
