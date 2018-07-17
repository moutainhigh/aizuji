package org.gz.order.backend.rest;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.order.backend.service.ContactInfoProxy;
import org.gz.user.dto.ContactInfoQueryDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @Description:TODO
 * Author	Version		Date		Changes
 * liuyt 	1.0  		2017年12月29日 	Created
 */
@RestController
@RequestMapping("/orderCreditDetail")
public class ContactInfoController {

    private Logger                   logger = LoggerFactory.getLogger(ContactInfoController.class);

    @Autowired
    private ContactInfoProxy contactInfoProxy;

    /**
     * 查询列表
     * RentRecord RentState 
     */
    @GetMapping(value = "/queryContactInfoByPage")
    public ResponseResult<?> queryContactInfoByPage(ContactInfoQueryDto queryDto) {
        try {
            return contactInfoProxy.queryContactInfoByPage(queryDto);
        } catch (Exception e) {
            logger.error("queryContactInfoByPage错误：{}", e);
            return ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
    }
}
