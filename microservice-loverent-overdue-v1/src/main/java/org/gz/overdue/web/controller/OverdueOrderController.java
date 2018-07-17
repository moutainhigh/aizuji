package org.gz.overdue.web.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gz.common.entity.ResultPager;
import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.DateUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.overdue.entity.OverdueOrder;
import org.gz.overdue.entity.OverdueOrderPage;
import org.gz.overdue.service.overdueOrder.OverdueOrderService;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * 逾期订单处理类
 * @Description:TODO
 * Author	Version		Date		Changes
 * hening 	1.0  		2018年2月1日 	Created
 */
@RestController
@RequestMapping("/api/overdueOrder")
@Slf4j
public class OverdueOrderController extends BaseController {

	@Resource
    private OverdueOrderService overdueOrderService;

    /**
     * 查询记录(分页)
     * @param settleOrder
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/queryPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> queryPage(@Valid @RequestBody OverdueOrderPage overdueOrderPage, BindingResult bindingResult) {
    	log.info("/api/overdueOrder/queryPage is begin:{}",JSON.toJSONString(overdueOrderPage));
        // 验证数据
        ResponseResult<?> validateResult = super.getValidatedResult(bindingResult);
        if (validateResult == null) {
            try {
            	overdueOrderPage.setPhone(overdueOrderPage.getUserPhone());
            	List<OverdueOrder> list = this.overdueOrderService.queryList(overdueOrderPage);
            	Integer sum = this.overdueOrderService.queryCount(overdueOrderPage);
            	ResultPager<OverdueOrder> result = new ResultPager<>(sum,overdueOrderPage.getCurrPage(),overdueOrderPage.getPageSize(),list);
            	result.getData().forEach(l->{
            		l.setAllocationTimeDesc(DateUtils.getString(l.getAllocationTime(),DateUtils.FMT_LONG_DATE));
            		l.setContactTimeDesc(DateUtils.getString(l.getContactTime(),DateUtils.FMT_LONG_DATE));
            		l.setOverdueTimeDesc(DateUtils.getString(l.getOverdueTime(),DateUtils.FMT_LONG_DATE));
            		l.setSignTimeDesc(DateUtils.getString(l.getSignTime(),DateUtils.FMT_LONG_DATE));
            	});
                validateResult = ResponseResult.buildSuccessResponse(result);
            } catch (ServiceException se) {
                return ResponseResult.build(se.getErrorCode(), se.getErrorMsg(), null);
            } catch (Exception e) {
                log.error("查询联系记录失败：{}", e);
                return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                    ResponseStatus.DATABASE_ERROR.getMessage(),
                    null);
            }
        }
        log.info("/api/overdueOrder/queryPage is end:{}",JSON.toJSONString(validateResult));
        return validateResult;
    }
    
    /**
     * 查询记录总数
     * @param settleOrder
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/queryPageCount", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> queryPageCount(@Valid @RequestBody OverdueOrderPage overdueOrderPage, BindingResult bindingResult) {
    	// 验证数据
    	ResponseResult<?> validateResult = super.getValidatedResult(bindingResult);
    	if (validateResult == null) {
    		try {
    			Integer sum = this.overdueOrderService.queryCount(overdueOrderPage);
    			validateResult = ResponseResult.buildSuccessResponse(sum);
    		} catch (ServiceException se) {
    			return ResponseResult.build(se.getErrorCode(), se.getErrorMsg(), null);
    		} catch (Exception e) {
    			log.error("查询联系记录总数失败：{}", e);
    			return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
    					ResponseStatus.DATABASE_ERROR.getMessage(),
    					null);
    		}
    	}
    	return validateResult;
    }
}
