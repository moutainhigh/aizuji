package org.gz.liquidation.web.controller.remission;

import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.groups.Default;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.JsonUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.liquidation.common.Page;
import org.gz.liquidation.common.dto.LateFeeRemissionReq;
import org.gz.liquidation.common.dto.QueryDto;
import org.gz.liquidation.common.dto.RemissionLogReq;
import org.gz.liquidation.common.dto.RemissionLogResp;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.liquidation.service.remission.RemissionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @Description:	减免记录控制器
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年1月22日 	Created
 */
@Slf4j
@RestController
@RequestMapping("/remissionLog")
public class RemissionLogController extends BaseController{
	
	@Autowired
	private RemissionLogService remissionLogService;
	
	@Autowired
	private Validator validator;
	
	@ApiOperation(value = "分页查询减免记录(清算后台系统调用)", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/queryList", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResultPager<RemissionLogResp> queryList(@RequestBody RemissionLogReq remissionLogReq){
		
		log.info(">>>>>>>>>>>>>>>> queryList:{}",JsonUtils.toJsonString(remissionLogReq));
		try {
			
			QueryDto queryDto = new QueryDto();
			Page page = new Page();
			
			if(remissionLogReq.getCurrPage() < 1){
				page.setStart(1);
			}else{
				page.setStart(remissionLogReq.getCurrPage());
			}
			
			page.setPageSize(remissionLogReq.getPageSize());
			queryDto.setPage(page);
			queryDto.setQueryConditions(remissionLogReq);
			
			return remissionLogService.selectPage(queryDto);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			return new ResultPager<>(0,0,0,null);
		}
		
	}
	
	@ApiOperation(value = "滞纳金减免", httpMethod = "POST",
            notes = "操作成功返回 errorCode=0 ",
            response = ResponseResult.class)
	@PostMapping(value = "/lateFee/remission", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> doLateFeeRemission(@RequestBody @Valid LateFeeRemissionReq lateFeeRemissionReq){
		
		log.info(LiquidationConstant.LOG_PREFIX+"doLateFeeRemission:{}",JsonUtils.toJsonString(lateFeeRemissionReq));
	    ResponseResult<String> validateResult = super.getValidatedResult(this.validator,lateFeeRemissionReq,Default.class);
		if(validateResult != null){
			return validateResult;
		}
		
	    return remissionLogService.doLateFeeRemission(lateFeeRemissionReq);
		
	}
}
