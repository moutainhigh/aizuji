package org.gz.order.backend.rest;


import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.web.controller.BaseController;
import org.gz.order.backend.service.SignPayService;
import org.gz.order.common.entity.RentRecord;
import org.gz.order.common.entity.RentState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 签约支付管理
 * @author phd
 */
@RestController
@RequestMapping("/signpay")
public class SignPayController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(SignPayController.class);

    @Autowired
    SignPayService signPayService;
    
    /**
     * 完成支付
     * RentRecord RentState 
     */
    @PostMapping(value = "/pay")
    public ResponseResult<?> pay(RentRecord rentRecord) {
    	try {
    		return signPayService.pay(rentRecord,getAuthUser());
    	} catch (Exception e) {
			logger.error("完成支付：{}",e.getLocalizedMessage());
			return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(),ResponseStatus.DATA_UPDATED_ERROR.getMessage(),null);
		}
    }
    
    /**
     * 取消订单
     * RentRecord RentState 
     */
    @PostMapping(value = "/cancel")
    public ResponseResult<?> cancel(RentState rentState) {
		try {
			return signPayService.cancel(rentState,getAuthUser());
		} catch (Exception e) {
			logger.error("取消订单：{}",e.getLocalizedMessage());
			return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(),ResponseStatus.DATA_UPDATED_ERROR.getMessage(),null);
		}
    }

}
