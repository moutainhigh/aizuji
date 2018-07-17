package org.gz.liquidation.backend.rest;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.JsonUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.liquidation.backend.feign.IRentCollectionService;
import org.gz.liquidation.common.dto.RemissionLogReq;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @Description:租后控制器
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年2月11日 	Created
 */
@Slf4j
@RestController
@RequestMapping("/remissionLog")
public class RemissionController extends BaseController {

	@Autowired
	private IRentCollectionService rentCollectionService;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
    }
	
	/**
	 * 
	 * @Description: 减免记录分页查询
	 * @param lateFeeRemissionReq
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月11日
	 */
	@PostMapping(value = "/queryList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> queryList(RemissionLogReq remissionLogReq){
		
		log.info(LiquidationConstant.LOG_PREFIX+"queryList:{}",JsonUtils.toJsonString(remissionLogReq));
		
		return ResponseResult.buildSuccessResponse(rentCollectionService.queryList(remissionLogReq));
		
	}
}
