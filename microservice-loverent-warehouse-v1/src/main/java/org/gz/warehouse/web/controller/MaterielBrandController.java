package org.gz.warehouse.web.controller;

import java.util.List;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.AssertUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.warehouse.entity.MaterielBrand;
import org.gz.warehouse.entity.MaterielBrandQuery;
import org.gz.warehouse.service.materiel.MaterielBrandService;
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


/**
 * 物料品牌控制器
 * @author hxj
 */
@RestController
@RequestMapping("/materielBrand")
@Slf4j
public class MaterielBrandController extends BaseController{

	@Autowired
	private MaterielBrandService materielBrandService;
	
	/**
	 * 新增数据
	* @Description: 
	* @param m
	* @param bindingResult
	* @return ResponseResult<?>
	 */
	@PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> add(@Valid @RequestBody MaterielBrand m, BindingResult bindingResult) {
		//验证数据 
		ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
		if (validateResult == null) {
			try {
				return this.materielBrandService.insert(m);
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
	public ResponseResult<?> update(@Valid @RequestBody MaterielBrand m, BindingResult bindingResult) {
		//验证数据 
		ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
		if (validateResult == null&&AssertUtils.isPositiveNumber4Long(m.getId())) {
			try {
				return this.materielBrandService.update(m);
			} catch (Exception e) {
				return ResponseResult.build(1000, "", null);
			}
		}
		return validateResult;
	}
	
	/**
	 * 批量删除物料品牌
	 * @param ids
	 * @return
	 */
	@GetMapping(value = "/batchDelete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> batchDelete(@PathVariable("ids") String ids) {
		//验证数据 
		if (StringUtils.hasText(ids)) {
			try {
				return this.materielBrandService.deleteByIds(ids);
			} catch (Exception e) {
				log.error("删除物料品牌失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
	}
	
	/**
	 * 获取物料品牌详情
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/getDetail/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<?> getDetail(@PathVariable("id") Long id) {
		//验证数据 
		if (AssertUtils.isPositiveNumber4Long(id)) {
			try {
				return this.materielBrandService.selectByPrimaryKey(id);
			} catch (Exception e) {
				log.error("获取物料品牌详情失败：{}",e.getLocalizedMessage());
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
	public ResponseResult<ResultPager<MaterielBrand>> queryByPage(@RequestBody MaterielBrandQuery m){
		if(m!=null) {
			try {
				return this.materielBrandService.queryByPage(m);
			} catch (Exception e) {
				log.error("获取物料品牌分页列表失败：{}",e.getLocalizedMessage());
				return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
			}
		}
		return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
	}

    /**
     * 通过物料分类id查询对应的品牌列表
     * @param classId
     * @return
     */
    @PostMapping(value = { "/queryMaterielBrandListByClassId", "/api/queryMaterielBrandListByClassId" }, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<List<MaterielBrand>> queryMaterielBrandListByClassId(@RequestParam("classId") Integer classId) {
        if (classId != null) {
            try {
                List<MaterielBrand> list = this.materielBrandService.queryMaterielBrandListByClassId(classId);
                return ResponseResult.buildSuccessResponse(list);
            } catch (Exception e) {
                log.error("通过物料分类id查询对应的品牌列表失败：{}", e);
                return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                    ResponseStatus.DATABASE_ERROR.getMessage(),
                    null);
            }
        }
        return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(),
            ResponseStatus.PARAMETER_VALIDATION.getMessage(),
            null);
    }
}
