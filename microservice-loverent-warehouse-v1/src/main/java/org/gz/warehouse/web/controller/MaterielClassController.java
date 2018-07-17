package org.gz.warehouse.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.AssertUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.warehouse.entity.MaterielClass;
import org.gz.warehouse.entity.MaterielClassBrandReq;
import org.gz.warehouse.entity.MaterielClassBrandVO;
import org.gz.warehouse.entity.MaterielClassQuery;
import org.gz.warehouse.service.materiel.MaterielClassService;
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

import lombok.extern.slf4j.Slf4j;

/**
 * 物料分类控制器
 * @author hxj
 *
 */
@RestController
@RequestMapping("/materielClass")
@Slf4j
public class MaterielClassController extends BaseController {

	@Autowired
	private MaterielClassService materielClassService;
	
	/**
	 * 新增数据
	* @Description: 
	* @param m
	* @param bindingResult
	* @return ResponseResult<?>
	 */
	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> add(@Valid @RequestBody MaterielClass m, BindingResult bindingResult) {
		//验证数据 
		ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
		if (validateResult == null) {
			try {
				return this.materielClassService.insert(m);
			} catch (Exception e) {
				return ResponseResult.build(1000, "", null);
			}
		}
		return validateResult;
	}
	
	/**
	 * 
	* @Description: 查询所有品牌，并根据指定materielClassId返回其使用的数量
	* @param materielClassId
	* @return ResponseResult<List<MaterielClassBrandVO>>
	 */
	@GetMapping("/queryBrands/{materielClassId}")
	public ResponseResult<List<MaterielClassBrandVO>> queryBrands(@PathVariable("materielClassId") Integer materielClassId){
		return this.materielClassService.queryBrands(materielClassId);
	}
	
	
	/**
	 * 
	* @Description:判断能否从分类中删除品牌ID,依据materiel_basic_info表
	* @param m
	* @param bindingResult
	* @return ResponseResult<?>
	 */
	@PostMapping(value = "/canDeleteBrand", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> canDeleteBrand(@Valid @RequestBody MaterielClassBrandReq m, BindingResult bindingResult) {
		//验证数据 
		ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
		if (validateResult == null) {
				return this.materielClassService.canDeleteBrand(m);
		}
		return validateResult;
	}
	
	
	/**
	 * 配置品牌
	* @Description: 
	* @param m
	* @param bindingResult
	* @return ResponseResult<?>
	 */
	@PostMapping(value = "/configBrand", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> configBrand(@Valid @RequestBody MaterielClassBrandReq m, BindingResult bindingResult) {
		//验证数据 
		ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
		if (validateResult == null) {
			try {
				return this.materielClassService.configBrand(m);
			} catch (Exception e) {
				return ResponseResult.build(1000, "", null);
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
	public ResponseResult<?> update(@Valid @RequestBody MaterielClass m, BindingResult bindingResult) {
		//验证数据 
		ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
		if (validateResult == null&&AssertUtils.isPositiveNumber4Int(m.getId())) {
			try {
				return this.materielClassService.update(m);
			} catch (Exception e) {
				return ResponseResult.build(1000, "", null);
			}
		}
		return validateResult;
	}
	
	/**
	 * 批量删除物料分类
	 * @param ids
	 * @return
	 */
	@GetMapping(value = "/batchDelete/{ids}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> batchDelete(@PathVariable("ids") String ids) {
		//验证数据 
		if (StringUtils.hasText(ids)) {
			try {
				return this.materielClassService.deleteByIds(ids);
			} catch (Exception e) {
				log.error("删除物料分类失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
	}
	
	/**
	 * 获取物料分类详情
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/getDetail/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> getDetail(@PathVariable("id") Integer id) {
		//验证数据 
		if (AssertUtils.isPositiveNumber4Int(id)) {
			try {
				return this.materielClassService.selectByPrimaryKey(id);
			} catch (Exception e) {
				log.error("获取物料分类详情失败：{}",e.getLocalizedMessage());
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
	public ResponseResult<ResultPager<MaterielClass>> queryByPage(@RequestBody MaterielClassQuery m){
		if(m!=null) {
			try {
				return this.materielClassService.queryByPage(m);
			} catch (Exception e) {
				log.error("获取物料分类分页列表失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
	}

    /**
     * 获取所有物料分类列表  树状返回
     * @param m
     * @return
     */
    @PostMapping(value = { "/queryAllMaterielClassList", "/api/queryAllMaterielClassList" }, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<MaterielClass> queryAllMaterielClassList() {
        try {
            MaterielClass mc = this.materielClassService.queryAllMaterielClassList();
            return ResponseResult.buildSuccessResponse(mc);
        } catch (Exception e) {
            log.error("获取所有物料分类列表失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }
}
