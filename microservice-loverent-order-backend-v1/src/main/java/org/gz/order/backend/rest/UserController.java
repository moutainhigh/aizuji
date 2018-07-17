package org.gz.order.backend.rest;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.JsonUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.order.backend.service.UserProxy;
import org.gz.order.common.entity.UserHistory;
import org.gz.user.dto.ContactInfoQueryDto;
import org.gz.user.dto.UserInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户controller
 * @Description:TODO
 * Author	Version		Date		Changes
 * liuyt 	1.0  		2017年12月27日 	Created
 */
@RestController
@RequestMapping("/userInfo")
public class UserController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserProxy userProxy;
    
    /**
     * 根据id查询用户
     */
    @PostMapping(value = "/queryUserById")
    public ResponseResult<?> queryUserById(@RequestParam(name = "userId") Long userId) {
        ResponseResult<?> result = null;
        try {
            logger.info("UserController queryUserById begin.. the param:{}", userId);
            result = userProxy.queryUserById(userId);
        } catch (Exception e) {
            logger.error("UserController queryUserById error：{}", e);
            result = ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
        logger.info("UserController queryUserById end.. the returnVal:{}", JsonUtils.toJsonString(result));
        return result;
    }
    
    /**
     * 根据userId查询用户信息
     */
    @PostMapping(value = "/queryUserInfoByUserId")
    public ResponseResult<UserInfoDto> queryUserInfoByUserId(@RequestParam(name = "userId") Long userId) {
        ResponseResult<UserInfoDto> result = null;
        try {
            logger.info("UserController queryUserInfoByUserId begin.. the param:{}", userId);
            result = userProxy.queryUserInfoByUserId(userId);
        } catch (Exception e) {
            logger.error("UserController queryUserInfoByUserId error：{}", e);
            result = ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
        logger.info("UserController queryUserInfoByUserId end.. the returnVal:{}", JsonUtils.toJsonString(result));
        return result;
    }

    /**
     * 根据订单号查询用户下单时快照
     */
    @PostMapping(value = "/queryUserSnapByOrderNo")
    public ResponseResult<?> queryUserSnapByOrderNo(@RequestParam(name = "orderNo") String orderNo) {
        ResponseResult<?> result = null;
        try {
            logger.info("UserController queryUserSnapByOrderNo begin.. the param:{}", orderNo);
            result = userProxy.queryUserSnapByOrderNo(orderNo);
        } catch (Exception e) {
            logger.error("UserController queryUserSnapByOrderNo error：{}", e);
            result = ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
        logger.info("UserController queryUserSnapByOrderNo end.. the returnVal:{}", JsonUtils.toJsonString(result));
        return result;
    }

    /**
     * 根据id查询用户联系地址
     */
    @PostMapping(value = "/queryAddressByUserId")
    public ResponseResult<?> queryAddressByUserId(@RequestParam(name = "userId") Long userId) {
        ResponseResult<?> result = null;
        try {
            logger.info("UserController queryAddressByUserId begin.. the param:{}", userId);
            result = userProxy.queryAddressByUserId(userId);
        } catch (Exception e) {
            logger.error("UserController queryAddressByUserId error：{}", e);
            result = ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
        logger.info("UserController queryAddressByUserId end.. the returnVal:{}", JsonUtils.toJsonString(result));
        return result;
    }

    /**
     * 更新用户信息
     */
    @PostMapping(value = "/updateUser")
    public ResponseResult<?> updateUser(UserHistory user) {
        ResponseResult<?> result = null;
        try {
            logger.info("UserController updateUser begin.. the param:{}", JsonUtils.toJsonString(user));
            result = userProxy.updateUser(user);
        } catch (ServiceException se) {
            result = ResponseResult.build(se.getErrorCode(), se.getErrorMsg(), null);
        } catch (Exception e) {
            logger.error("UserController updateUser error：{}", e);
            result = ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
        logger.info("UserController updateUser end.. the returnVal:{}", JsonUtils.toJsonString(result));
        return result;
    }

    /**
     * 查询用户通讯录
     */
    @PostMapping(value = "/queryContactInfoByPage")
    public ResponseResult<?> queryContactInfoByPage(ContactInfoQueryDto dto) {
        ResponseResult<?> result = null;
        try {
            logger.info("UserController queryContactInfoByPage begin.. the param:{}", JsonUtils.toJsonString(dto));
            result = userProxy.queryContactInfoByPage(dto);
        } catch (Exception e) {
            logger.error("UserController queryContactInfoByPage error：{}", e);
            result = ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
        logger.info("UserController queryContactInfoByPage end.. the returnVal:{}", JsonUtils.toJsonString(result));
        return result;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
}
