package org.gz.warehouse.web.controller;

import javax.validation.Valid;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.AssertUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.warehouse.entity.MaterielBasicInfo;
import org.gz.warehouse.entity.MaterielBasicInfoQuery;
import org.gz.warehouse.entity.MaterielBasicInfoVO;
import org.gz.warehouse.service.materiel.MaterielBasicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;


/**
 * 物料基本信息控制器
 * @author hxj
 */
@RestController
@RequestMapping("/materielBasic")
@Api(value = "/")
@Slf4j
public class MaterielBasicInfoController extends BaseController{

	@Autowired
	private MaterielBasicInfoService materielBasicInfoService;
	
	/**
	 * 新增数据
	* @Description: 
	* @param m
	* @param bindingResult
	* @return ResponseResult<?>
	 */
	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> add(@Valid @RequestBody MaterielBasicInfo m, BindingResult bindingResult) {
		//验证数据 
		ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
		if (validateResult == null) {
			try {
				m.setCreateBy(1L);
				m.setUpdateBy(1L);
				return this.materielBasicInfoService.insert(m);
			} catch (Exception e) {
				log.error("新增物料信息失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return validateResult;
	}
	
	/**
	 * 修改数据
	* @Description: 
	* @param m
	* @param bindingResult
	* @return ResponseResult<?>
	 */
	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> update(@Valid @RequestBody MaterielBasicInfo m, BindingResult bindingResult) {
		//验证数据 
		ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
		if (validateResult == null) {
			try {
				return this.materielBasicInfoService.update(m);
			} catch (Exception e) {
				log.error("修改物料信息失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return validateResult;
	}
	
	/**
	 * 
	* @Description: 获取物料详情
	* @param id
	* @return ResponseResult<MaterielBasicInfo>
	 */
	@GetMapping(value="/getDetail/{id}")
	public ResponseResult<MaterielBasicInfo> getDetail(@PathVariable("id")Long id){
		//验证数据 
		if (AssertUtils.isPositiveNumber4Long(id)) {
			try {
				return this.materielBasicInfoService.getDetail(id);
			} catch (Exception e) {
				log.error("获取物料详情失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
	}
	
	/**
	 * 获取分页列表
	 * @param m
	 * @return
	 */
	@PostMapping(value = "/queryByPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<ResultPager<MaterielBasicInfoVO>> queryByPage(@RequestBody MaterielBasicInfoQuery m){
		if(m!=null) {
			try {
				return this.materielBasicInfoService.queryByPage(m);
			} catch (Exception e) {
				log.error("获取物料分页列表失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
	}
	
	/**
	 * 设置物料的启用、信用标志
	 * @param m
	 */
	@PostMapping(value = "/setEnableFlag", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<MaterielBasicInfo> setEnableFlag(@RequestBody MaterielBasicInfo m) {
		//验证数据,必须设置id,以及期望的enableFlag
		if (AssertUtils.isPositiveNumber4Long(m.getId())) {
			try {
				return this.materielBasicInfoService.setEnableFlag(m);
			} catch (Exception e) {
				log.error("设置物料的启用、信用标志失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
	}
}
