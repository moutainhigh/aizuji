package org.gz.order.backend.rest;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.web.controller.BaseController;
import org.gz.order.backend.service.DistributeService;
import org.gz.order.common.entity.RentRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配货管理
 * @author phd
 */
@RestController
@RequestMapping("/distribute")
public class DistributeController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(DistributeController.class);

    @Autowired
    DistributeService distributeService;
    
    /**
     * 更新库存情况
     * RentRecord  
     */
    @PostMapping(value = "/inventoryupdate")
    public ResponseResult<?> inventoryupdate(RentRecord rentRecord) {
		try {
			return distributeService.inventoryupdate(rentRecord);
		} catch (Exception e) {
			logger.error("更新库存情况：{}",e.getLocalizedMessage());
			return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(),ResponseStatus.DATA_UPDATED_ERROR.getMessage(),null);
		}
    }
    
    /**
     * 下发签约通知
     * RentRecord
     */
    @PostMapping(value = "/signnotice")
    public ResponseResult<?> signnotice(RentRecord rentRecord) {
		try {
			return distributeService.signnotice(rentRecord,getAuthUser());
		} catch (Exception e) {
			logger.error("下发签约通知：{}",e.getLocalizedMessage());
			return ResponseResult.build(ResponseStatus.SMS_SEND_FAILED.getCode(),ResponseStatus.SMS_SEND_FAILED.getMessage(),null);
		}
    }
    
    /**
     * 更新配货状态
     * @param rentRecord
     */
    @PostMapping(value = "/updatestate")
    public ResponseResult<?> updatestate(RentRecord rentRecord) {
		try {	
			return distributeService.updatestate(rentRecord,getAuthUser());
		} catch (Exception e) {
			logger.error("更新配货状态：{}",e.getLocalizedMessage());
			return ResponseResult.build(ResponseStatus.SERVICE_CALL_OVERTIME.getCode(),ResponseStatus.SERVICE_CALL_OVERTIME.getMessage(),null);
		}
    }
    
    /**
     * 查看物流信息
     * @param rentRecord
     * @return
     */
    @PostMapping(value = "/logistics")
    public ResponseResult<?> logistics(RentRecord rentRecord) {
		try {
			return distributeService.logistics(rentRecord);
		} catch (Exception e) {
			logger.error("查看物流信息：{}",e.getLocalizedMessage());
			return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(),ResponseStatus.DATA_REQUERY_ERROR.getMessage(),null);
		}
    }
    
    /**
     * 申请提前解约
     * @param rentRecord
     */
    @PostMapping(value = "/terminationapply")
    public ResponseResult<?> terminationapply(RentRecord rentRecord) {
		try {
			return distributeService.terminationapply(rentRecord,getAuthUser());
		} catch (Exception e) {
			logger.error("申请提前解约：{}",e.getLocalizedMessage());
			return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(),ResponseStatus.DATA_UPDATED_ERROR.getMessage(),null);
		}
    }

    /**
     * 确认收货
     * @param rentRecord
     */
    @PostMapping(value = "/confirmreceive")
    public ResponseResult<?> confirmreceive(RentRecord rentRecord) {
    	try {
			return distributeService.confirmreceive(rentRecord,getAuthUser());
		} catch (Exception e) {
			logger.error("确认收货：{}",e.getLocalizedMessage());
			return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(),ResponseStatus.DATA_UPDATED_ERROR.getMessage(),null);
		}
    }
    
}
