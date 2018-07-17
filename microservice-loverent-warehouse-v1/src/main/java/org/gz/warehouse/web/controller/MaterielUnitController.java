package org.gz.warehouse.web.controller;

import javax.validation.Valid;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.AssertUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.warehouse.entity.MaterielUnit;
import org.gz.warehouse.entity.MaterielUnitQuery;
import org.gz.warehouse.service.materiel.MaterielUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 物料单位控制器
 * @author hxj
 *
 */
@RestController
@RequestMapping("/materielUnit")
@Slf4j
@Api(value = "/")
public class MaterielUnitController extends BaseController {

	@Autowired
	private MaterielUnitService materielUnitService;
	
	/**
	 * 新增物料单位
	 * @param m
	 * @param bindingResult
	 * @return
	 */
    @ApiOperation(value = "新增物料单位", httpMethod = "POST",
            notes = "操作成功返回errorCode=0",
            response = ResponseResult.class
    )
	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> add(@Valid @RequestBody MaterielUnit m, BindingResult bindingResult) {
		//验证数据 
		ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
		if (validateResult == null) {
			try {
				return this.materielUnitService.insert(m);
			} catch (Exception e) {
				log.error("新增物料单位失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return validateResult;
	}
	
	/**
	 * 修改物料单位
	 * @param m
	 * @param bindingResult
	 * @return
	 */
	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> update(@Valid @RequestBody MaterielUnit m, BindingResult bindingResult) {
		//验证数据 
		ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
		if (validateResult == null&&AssertUtils.isPositiveNumber4Int(m.getId())) {
			try {
				return this.materielUnitService.update(m);
			} catch (Exception e) {
				log.error("修改物料单位失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return validateResult;
	}
	
	/**
	 * 设置物料单位的启用、信用标志
	 * @param m
	 */
	@PostMapping(value = "/setEnableFlag", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> setEnableFlag(@RequestBody MaterielUnit m) {
		//验证数据,必须设置id,以及期望的enableFlag
		if (AssertUtils.isPositiveNumber4Int(m.getId())&&AssertUtils.isPositiveNumber4Int(m.getEnableFlag())) {
			try {
				return this.materielUnitService.setEnableFlag(m);
			} catch (Exception e) {
				log.error("设置物料单位启/停用标志失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
	}

	/**
	 * 批量删除物料单位
	 * @param ids
	 * @return
	 */
	@PostMapping(value = "/batchDelete/{ids}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> batchDelete(@PathVariable String ids) {
		//验证数据 
		if (StringUtils.hasText(ids)) {
			try {
				return this.materielUnitService.deleteByIds(ids);
			} catch (Exception e) {
				log.error("删除物料单位失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
	}
	
	/**
	 * 获取物料单位详情
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "获取物料单位详情", httpMethod = "POST",
	            notes = "操作成功返回errorCode=0",
	            response = ResponseResult.class
	)
	@ApiImplicitParam(name = "id", value = "物料单位主键ID", required = true, dataTypeClass = Integer.class, paramType = "path")
	@GetMapping(value = "/getDetail/{id}")
	public ResponseResult<?> getDetail(@PathVariable Integer id) {
		//验证数据 
		if (AssertUtils.isPositiveNumber4Int(id)) {
			try {
				return this.materielUnitService.selectByPrimaryKey(id);
			} catch (Exception e) {
				log.error("获取物料单位详情失败：{}",e.getLocalizedMessage());
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
	public ResponseResult<ResultPager<MaterielUnit>> queryByPage(@RequestBody MaterielUnitQuery m){
		if(m!=null) {
			try {
				return this.materielUnitService.queryByPage(m);
			} catch (Exception e) {
				log.error("获取物料单位分页列表失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
	}
	
}
