package org.gz.overdue.web.controller;


import javax.annotation.Resource;
import javax.validation.Valid;

import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.web.controller.BaseController;
import org.gz.overdue.entity.SettleOrder;
import org.gz.overdue.service.settle.SettleService;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * 产品信息控制器
 * @Description:TODO
 * Author	Version		Date		Changes
 * hening 	1.0  		2018年2月1日 	Created
 */
@RestController
@RequestMapping("/api/settleOrder")
@Slf4j
public class SettleController extends BaseController {

	@Resource
    private SettleService settleService;
	
    /**
     * 新增记录
     * @param settleOrder
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> add(@Valid @RequestBody SettleOrder settleOrder, BindingResult bindingResult) {
        // 验证数据
        ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
        if (validateResult == null) {
            try {
                this.settleService.addSettle(settleOrder);
                validateResult = ResponseResult.buildSuccessResponse();
            } catch (ServiceException se) {
                return ResponseResult.build(se.getErrorCode(), se.getErrorMsg(), null);
            } catch (Exception e) {
                log.error("新增记录失败：{}", e);
                return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                    ResponseStatus.DATABASE_ERROR.getMessage(),
                    null);
            }
        }
        return validateResult;
    }
    
    /**
     * 删除记录
     * @param settleOrder
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/del", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> del(@Valid @RequestParam String id, BindingResult bindingResult) {
        // 验证数据
        ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
        if (validateResult == null) {
            try {
                this.settleService.deleteSettle(id);
                validateResult = ResponseResult.buildSuccessResponse();
            } catch (ServiceException se) {
                return ResponseResult.build(se.getErrorCode(), se.getErrorMsg(), null);
            } catch (Exception e) {
                log.error("新增记录失败：{}", e);
                return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                    ResponseStatus.DATABASE_ERROR.getMessage(),
                    null);
            }
        }
        return validateResult;
    }
  
}
