package org.gz.workorder.backend.rest;


import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.web.controller.BaseController;
import org.gz.order.common.dto.OrderDetailResp;
import org.gz.order.common.dto.RentRecordQuery;
import org.gz.order.common.dto.WorkOrderRsp;
import org.gz.user.dto.UserInfoDto;
import org.gz.user.entity.AppUser;
import org.gz.workorder.backend.dto.AppUserExtendDto;
import org.gz.workorder.backend.service.InfoSearchService;
import org.gz.workorder.dto.SearchRecordQueryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * 信息查询
 * @author phd
 */
@RestController
@RequestMapping("/infosearch")
@Slf4j
public class InfoSearchController extends BaseController {

    @Autowired
    InfoSearchService infoSearchService;

    /**
     * 客服查询记录
     */
    @PostMapping(value = "/workorder/searchrecord", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<SearchRecordQueryDto> workordersearchrecord(@RequestBody SearchRecordQueryDto searchRecordQueryDto) {
        try {
            ResponseResult<SearchRecordQueryDto> responseResult =infoSearchService.queryPageSearchRecord(searchRecordQueryDto);
            return responseResult;
        } catch (Exception e) {
        	log.error("客户查询记录：{}", e);
            return ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
    }

    /**
     * 获取用户个人信息
     */
    @GetMapping(value = "/user/find")
    public ResponseResult<?> userfind(Long userId) {
		try {
			ResponseResult<AppUser> result= infoSearchService.queryUserById(userId);
			return result;
		} catch (Exception e) {
			log.error("获取用户个人信息：{}",e);
			return ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),null);
		}
    }
    
    /**
     * 订单列表
     */
    @PostMapping(value = "/order/list", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<ResultPager<WorkOrderRsp>> orderlist(@RequestBody RentRecordQuery rentRecordQuery) {
        try {
        	ResponseResult<ResultPager<WorkOrderRsp>> result= infoSearchService.orderlist(rentRecordQuery,getAuthUser());
            return result;
        } catch (Exception e) {
        	log.error("订单列表：{}", e);
            return ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
    }
    
    /**
     * 订单详情
     */
    @GetMapping(value = "/order/detail")
    public ResponseResult<OrderDetailResp> queryOrderDetailForWork(String rentRecordNo) {
        try {
        	ResponseResult<OrderDetailResp> responseResult =infoSearchService.queryOrderDetailForWork(rentRecordNo);
            return responseResult;
        } catch (Exception e) {
        	log.error("订单详情：{}", e);
            return ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
    }
    
    /**
     * 还款计划
     */
    @GetMapping(value = "/repayment/schedule")
    public ResponseResult<?> repaymentSchedule(String rentRecordNo) {
        try {
        	ResponseResult<?> responseResult =infoSearchService.repaymentSchedule(rentRecordNo);
            return responseResult;
        } catch (Exception e) {
        	log.error("还款计划：{}", e);
            return ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
    }
    
    /**
     * 最新的交易记录
     */
    @GetMapping(value = "/transactionRecord/latest")
    public ResponseResult<?> latest(String rentRecordNo) {
        try {
        	ResponseResult<?> responseResult =infoSearchService.transactionRecordLatest(rentRecordNo);
            return responseResult;
        } catch (Exception e) {
        	log.error("交易记录：{}", e);
            return ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
    }

    /**
     * 重置密码
     * @param appUser
     * @return
     */
    @PostMapping(value = "/user/resetPassword", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<String> resetPassword(@RequestBody AppUser appUser) {
        try {
        	ResponseResult<String> result= infoSearchService.resetPassword(appUser,getAuthUser());
            return result;
        } catch (Exception e) {
        	log.error("重置密码：{}", e);
            return ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
    }
    
    /**
     * 查询用户
     */
    @PostMapping(value = "/user/queryByCondition", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<UserInfoDto> queryByCondition(@RequestBody AppUserExtendDto appUserExtendDto) {
        try {
        	ResponseResult<UserInfoDto> result= infoSearchService.queryByCondition(appUserExtendDto);
            return result;
        } catch (Exception e) {
        	log.error("查询用户：{}", e);
            return ResponseResult.build(ResponseStatus.UNKNOW_SYSTEM_ERROR.getCode(),
                ResponseStatus.UNKNOW_SYSTEM_ERROR.getMessage(),
                null);
        }
    }
    
}
