package org.gz.order.backend.rest;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.web.controller.BaseController;
import org.gz.order.backend.service.DistributeService;
import org.gz.order.common.Enum.BackRentState;
import org.gz.order.common.dto.UpdateOrderStateReq;
import org.gz.order.common.entity.RentRecord;
import org.gz.order.server.service.RentRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 解约管理
 * @author phd
 */
@RestController
@RequestMapping("/termination")
public class TerminationController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(TerminationController.class);

    @Autowired
    RentRecordService rentRecordService;
    @Autowired
    DistributeService distributeService;
    
    /**
     * 完成解约
     * @param rentRecord
     */
    @PostMapping(value = "/prematuretermination")
    public ResponseResult<?> prematuretermination(RentRecord rentRecord) {
    	//库存接口 查询订单状态是否为已收货
    	//更新订单状态为完成解约 添加状态记录
    	try {
			UpdateOrderStateReq req=new UpdateOrderStateReq();
			req.setRentRecordNo(rentRecord.getRentRecordNo());
			req.setState(BackRentState.PrematureTermination.getCode());
			return distributeService.updateOrderState(req);
		} catch (Exception e) {
			logger.error("完成解约：{}",e.getLocalizedMessage());
			return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(),ResponseStatus.DATA_UPDATED_ERROR.getMessage(),null);
		}
    }

}
