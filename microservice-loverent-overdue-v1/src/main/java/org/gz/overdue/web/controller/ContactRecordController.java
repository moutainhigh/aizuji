package org.gz.overdue.web.controller;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.gz.common.entity.ResultPager;
import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.DateUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.overdue.entity.ContactRecord;
import org.gz.overdue.entity.ContactRecordPage;
import org.gz.overdue.entity.user.User;
import org.gz.overdue.service.contactRecord.ContactRecordService;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * 联系记录处理类
 * @Description:TODO
 * Author	Version		Date		Changes
 * hening 	1.0  		2018年2月1日 	Created
 */
@RestController
@RequestMapping("/api/contactRecord")
@Slf4j
public class ContactRecordController extends BaseController {

	@Resource
    private ContactRecordService contactRecordService;
	
    /**
     * 新增记录
     * @param settleOrder
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> add(@Valid @RequestBody ContactRecord contactRecord, BindingResult bindingResult) {
        // 验证数据
        ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
        if (validateResult == null) {
            try {
            	User user = new User();
            	contactRecord.setOpPerson(user.getUserName());
            	contactRecord.setContactTime(new Date());
                this.contactRecordService.addContactRecord(contactRecord);
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
    public ResponseResult<?> del(@RequestBody ContactRecord contactRecord, BindingResult bindingResult) {
        // 验证数据
    	ResponseResult<String> validateResult = null;
    	if(contactRecord.getId()==null){
    		validateResult = ResponseResult.build(ResponseStatus.PARAMETER_ERROR.getCode(), ResponseStatus.PARAMETER_ERROR.getMessage(), null);
    	}
        if (validateResult == null) {
            try {
                this.contactRecordService.deleteSettle(contactRecord.getId()+"");
                validateResult = ResponseResult.buildSuccessResponse();
            } catch (ServiceException se) {
                return ResponseResult.build(se.getErrorCode(), se.getErrorMsg(), null);
            } catch (Exception e) {
                log.error("删除联系记录失败：{}", e);
                return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                    ResponseStatus.DATABASE_ERROR.getMessage(),
                    null);
            }
        }
        return validateResult;
    }
  
    /**
     * 修改记录
     * @param settleOrder
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> update(@Valid @RequestBody ContactRecord contactRecord, BindingResult bindingResult) {
        // 验证数据
        ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
        //验证ID是否存在
    	if(contactRecord.getId()==null){
    		validateResult = ResponseResult.build(ResponseStatus.PARAMETER_ERROR.getCode(), ResponseStatus.PARAMETER_ERROR.getMessage(), null);
    	}
        if (validateResult == null) {
            try {
            	contactRecord.setContactTime(new Date());
                this.contactRecordService.updateContactRecord(contactRecord);
                validateResult = ResponseResult.buildSuccessResponse();
            } catch (ServiceException se) {
                return ResponseResult.build(se.getErrorCode(), se.getErrorMsg(), null);
            } catch (Exception e) {
                log.error("修改联系记录失败：{}", e);
                return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                    ResponseStatus.DATABASE_ERROR.getMessage(),
                    null);
            }
        }
        return validateResult;
    }
    
    /**
     * 查询记录(分页)
     * @param settleOrder
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/queryPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> queryPage(@Valid @RequestBody ContactRecordPage contactRecordPage, BindingResult bindingResult) {
        // 验证数据
        ResponseResult<?> validateResult = super.getValidatedResult(bindingResult);
        if (validateResult == null) {
            try {
            	List<ContactRecord> list = this.contactRecordService.queryList(contactRecordPage);
            	Integer sum = this.contactRecordService.queryCount(contactRecordPage);
            	ResultPager<ContactRecord> result = new ResultPager<>(sum,contactRecordPage.getCurrPage(),contactRecordPage.getPageSize(),list);
            	result.getData().forEach(l->{
            		l.setContactTimeDesc(DateUtils.getString(l.getContactTime(),DateUtils.FMT_LONG_DATE));
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
        return validateResult;
    }
    
    /**
     * 查询记录总数
     * @param settleOrder
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/queryPageCount", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> queryPageCount(@Valid @RequestBody ContactRecordPage contactRecord, BindingResult bindingResult) {
    	// 验证数据
    	ResponseResult<?> validateResult = super.getValidatedResult(bindingResult);
    	if (validateResult == null) {
    		try {
    			Integer sum = this.contactRecordService.queryCount(contactRecord);
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
