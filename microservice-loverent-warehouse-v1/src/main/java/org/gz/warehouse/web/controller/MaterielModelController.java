package org.gz.warehouse.web.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.AssertUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.warehouse.common.vo.ProductInfoQvo;
import org.gz.warehouse.entity.MaterielModel;
import org.gz.warehouse.entity.MaterielModelQuery;
import org.gz.warehouse.service.materiel.MaterielModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/materielModel")
@Slf4j
public class MaterielModelController extends BaseController {

    @Autowired
    private MaterielModelService materielModelService;

    /**
     * 根据物料品牌id查询所有型号列表
     * @param m
     * @return
     */
    @PostMapping(value = { "/queryMaterielModelListByBrandId", "/api/queryMaterielModelListByBrandId" }, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<List<MaterielModel>> queryMaterielModelListByBrandId(@RequestParam("brandId") Integer brandId) {
        try {
            List<MaterielModel> list = this.materielModelService.queryMaterielModelListByBrandId(brandId);
            return ResponseResult.buildSuccessResponse(list);
        } catch (Exception e) {
            log.error("根据物料品牌id查询所有型号列表失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }
    
    /**
	 * 新增品牌型号
	 * @param m
	 * @param bindingResult
	 * @return
	 */
    @ApiOperation(value = "新增品牌型号", httpMethod = "POST",
            notes = "操作成功返回errorCode=0",
            response = ResponseResult.class
    )
	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> add(@Valid @RequestBody MaterielModel m, BindingResult bindingResult) {
		//验证数据 
		ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
		if (validateResult == null) {
			try {
				return this.materielModelService.insert(m);
			} catch (Exception e) {
				log.error("新增品牌型号失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return validateResult;
	}
	
	/**
	 * 修改品牌型号
	 * @param m
	 * @param bindingResult
	 * @return
	 */
	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> update(@Valid @RequestBody MaterielModel m, BindingResult bindingResult) {
		//验证数据 
		ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
		if (validateResult == null&&AssertUtils.isPositiveNumber4Int(m.getId())) {
			try {
				return this.materielModelService.update(m);
			} catch (Exception e) {
				log.error("修改品牌型号失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return validateResult;
	}
	
	/**
	 * 设置品牌型号的启用、信用标志
	 * @param m
	 */
	@PostMapping(value = "/setEnableFlag", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> setEnableFlag(@RequestBody MaterielModel m) {
		//验证数据,必须设置id,以及期望的enableFlag
		if (AssertUtils.isPositiveNumber4Int(m.getId())&&AssertUtils.isPositiveNumber4Int(m.getEnableFlag())) {
			try {
				return this.materielModelService.setEnableFlag(m);
			} catch (Exception e) {
				log.error("设置品牌型号启/停用标志失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
	}

	/**
	 * 批量删除品牌型号
	 * @param ids
	 * @return
	 */
	@PostMapping(value = "/batchDelete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> batchDelete(@RequestParam String ids) {
		//验证数据 
		if (StringUtils.hasText(ids)) {
			try {
				return this.materielModelService.deleteByIds(ids);
			} catch (Exception e) {
				log.error("删除品牌型号失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
	}
	
	/**
	 * 获取品牌型号详情
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "获取品牌型号详情", httpMethod = "POST",
	            notes = "操作成功返回errorCode=0",
	            response = ResponseResult.class
	)
	@ApiImplicitParam(name = "id", value = "品牌型号主键ID", required = true, dataTypeClass = Integer.class, paramType = "path")
    @GetMapping(value = { "/getDetail/{id}", "/api/getDetail/{id}" })
	public ResponseResult<?> getDetail(@PathVariable Integer id) {
		//验证数据 
		if (AssertUtils.isPositiveNumber4Int(id)) {
			try {
				return this.materielModelService.selectByPrimaryKey(id);
			} catch (Exception e) {
				log.error("获取品牌型号详情失败：{}",e.getLocalizedMessage());
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
	public ResponseResult<ResultPager<MaterielModel>> queryByPage(@RequestBody MaterielModelQuery m){
		if(m!=null) {
			try {
				return this.materielModelService.queryByPage(m);
			} catch (Exception e) {
				log.error("获取品牌型号分页列表失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
	}
	
	/**
     * 获取所有物料型号
     * @return
     * @throws
     * createBy:zhangguoliang       
     * createDate:2017年12月22日
     */
    @PostMapping(value = "/api/queryAllMaterielModel")
    public ResponseResult<?> queryAllMaterielModel(@RequestBody ProductInfoQvo qvo) {
        try {
            List<MaterielModel> list = materielModelService.queryAllAvalibles(qvo);
        	return ResponseResult.buildSuccessResponse(list);
        } catch (Exception e) {
            log.error("queryAllProductList失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }

	/**
	 * 根据型号ID查询最低月租金
	 * @param vo
	 * @return ResponseResult
	 */
	@PostMapping(value = "/getMinAmountByModelId")
	public ResponseResult<?> getMinAmountByModelId(@RequestBody ProductInfoQvo vo){
    	if(vo.getMaterielModelIdList().size() <= 0){
			return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(),"参数为空",null);
		}
    	try {
    		return this.materielModelService.getMinAmountByModelId(vo);
		}catch (Exception e){
    		log.error("根据型号ID查询最低月租金失败：{}" + e);
			return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(),ResponseStatus.DATA_REQUERY_ERROR.getMessage(),null);
		}

	}

}
