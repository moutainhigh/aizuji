package org.gz.workorder.backend.rest;



import org.gz.common.resp.ResponseResult;
import org.gz.common.web.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author phd
 */
@RestController
@RequestMapping("/common")
public class CommonController extends BaseController {

    
    /**
     * 获取登陆用户
     */
    @GetMapping(value = "/authuser")
    public ResponseResult<?>  authUser() {
    	return ResponseResult.buildSuccessResponse(getAuthUser());
    }
    
    

}
