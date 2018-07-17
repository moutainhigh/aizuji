package org.gz.liquidation.backend.rest;


import javax.servlet.http.HttpSession;

import org.gz.common.resp.ResponseResult;
import org.gz.common.web.controller.BaseController;
import org.gz.liquidation.backend.auth.AuthUser;
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
     * 获取登陆用户
     */
    @PostMapping(value = "/admin")
    public ResponseResult<?>  admin(HttpSession session) {
    	return ResponseResult.buildSuccessResponse((AuthUser) session.getAttribute("admin"));
    }

}
