/**
 * 
 */
package org.gz.warehouse.web.controller;

import javax.validation.Valid;

import org.gz.common.entity.QueryPager;
import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.web.controller.BaseController;
import org.gz.warehouse.entity.MaterielNewConfig;
import org.gz.warehouse.service.materiel.MaterielNewConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * @Title: 新旧程度业务控制器
 * @author hxj
 * @Description:
 * @date 2018年3月5日 上午9:15:25
 */
@RestController
@RequestMapping("/newConfig")
@Slf4j
public class MaterielNewConfigController extends BaseController {

	@Autowired
	private MaterielNewConfigService newConfigService;

	/**
	 * 分页查询新旧程度业务
	 * @return List<MaterielNewConfig>
	 */
	@GetMapping(value = "/queryAll")
	public ResponseResult<ResultPager<MaterielNewConfig>> queryAll(@RequestBody QueryPager page) {
		try {
			return this.newConfigService.queryForPage(page);
		} catch (Exception e){
			log.error("获取新旧程度业务失败：{}",e.getLocalizedMessage());
			return ResponseResult.build(ResponseStatus.DATA_REQUERY_ERROR.getCode(), ResponseStatus.DATA_REQUERY_ERROR.getMessage(), null);
		}

	}
	
	/**
	 * 新增新旧程度业务
	 * @param config
	 * @return ResponseResult
	 */
	@PostMapping(value = "/insertNewConfig")
	public ResponseResult<MaterielNewConfig> insertConfig( @RequestBody MaterielNewConfig config) {
		if(StringUtils.isEmpty(config)){
			return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), "参数为空", null);
		}
		try {
			return this.newConfigService.insertMaterielNewConfig(config);
		} catch (Exception e){
			log.error("新增新旧程度业务失败：{}",e.getLocalizedMessage());
			return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), ResponseStatus.DATA_CREATE_ERROR.getMessage(), null);
		}


	}
	
	/**
	 * 修改新旧程度业务
	 * @param config
	 * @return ResponseResult
	 */
	@PostMapping(value = "/updateNewConfig" )
	public ResponseResult<MaterielNewConfig> updateConfigs( @Valid @RequestBody MaterielNewConfig config){
		if(StringUtils.isEmpty(config)){
			return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), "参数为空", null);
		}
		try {
			return this.newConfigService.updateMaterielNewConfig(config);
		} catch (Exception e){
			log.error("修改新旧程度业务失败：{}",e.getLocalizedMessage());
			return ResponseResult.build(ResponseStatus.DATA_CREATE_ERROR.getCode(), ResponseStatus.DATA_CREATE_ERROR.getMessage(), null);
		}

	}
	
	/**
	 * 删除新旧程度业务
	 * @param id
	 * @return ResponseResult
	 */
	@PostMapping(value = "/deleteNewConfig" )
	public ResponseResult<?> deleteConfigs( @Valid @RequestBody int id){
		if(StringUtils.isEmpty(id)){
			return ResponseResult.build(ResponseStatus.DATA_DELETED_ERROR.getCode(), "参数为空", null);
		}
		try {
			return this.newConfigService.deleteById(id);
		} catch (Exception e){
			log.error("删除新旧程度业务失败：{}",e.getLocalizedMessage());
			return ResponseResult.build(ResponseStatus.DATA_DELETED_ERROR.getCode(), ResponseStatus.DATA_DELETED_ERROR.getMessage(), null);
		}
	}

}
