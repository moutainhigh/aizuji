package org.gz.order.backend.rest;



import org.gz.common.resp.ResponseResult;
import org.gz.common.web.controller.BaseController;
import org.gz.order.common.Enum.BackRentState;
import org.gz.order.common.Enum.ChannelNo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author phd
 */
@RestController
@RequestMapping("/common")
public class CommonController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(CommonController.class);


    /**
     * 下单渠道
     */
    @PostMapping(value = "/channelNos")
    public ResponseResult<?>  channelNos() {
    	return ResponseResult.buildSuccessResponse(ChannelNo.all());
    }

    /**
     * 后台租赁状态
     */
    @PostMapping(value = "/backRentStates")
    public ResponseResult<?>  backRentStates() {
    	return ResponseResult.buildSuccessResponse(BackRentState.all());
    }
    
    
    /**
     * 获取登陆用户
     */
    @PostMapping(value = "/authUser")
    public ResponseResult<?>  authUser() {
    	return ResponseResult.buildSuccessResponse(getAuthUser());
    }
    
    

}
