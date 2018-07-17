/**
 * 
 */
package org.gz.warehouse.web.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.web.controller.BaseController;
import org.gz.warehouse.common.vo.MaterielModelVo;
import org.gz.warehouse.common.vo.MaterielUnitShortResp;
import org.gz.warehouse.entity.MaterielBrand;
import org.gz.warehouse.entity.MaterielClass;
import org.gz.warehouse.entity.MaterielModel;
import org.gz.warehouse.entity.MaterielModelQuery;
import org.gz.warehouse.entity.MaterielNewConfig;
import org.gz.warehouse.entity.MaterielSpecVo;
import org.gz.warehouse.entity.MaterielUnit;
import org.gz.warehouse.service.materiel.MaterielBrandService;
import org.gz.warehouse.service.materiel.MaterielClassService;
import org.gz.warehouse.service.materiel.MaterielModelService;
import org.gz.warehouse.service.materiel.MaterielNewConfigService;
import org.gz.warehouse.service.materiel.MaterielSpecService;
import org.gz.warehouse.service.materiel.MaterielUnitService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title:通用数据控制器
 * @author hxj
 * @date 2018年3月19日 下午5:46:06
 */
@RestController
public class GeneralDataController extends BaseController {

	@Autowired
	private MaterielClassService materielClassService;

	@Autowired
	private MaterielBrandService materielBrandService;

	@Autowired
	private MaterielModelService materielModelService;

	@Autowired
	private MaterielSpecService materielSpecService;

	@Autowired
	private MaterielNewConfigService newConfigService;

	@Autowired
	private MaterielUnitService unitService;
	
	 /**
     * 获取所有物料分类列表  树状返回
     * @param
     * @return
     */
    @GetMapping(value = "/api/queryAllMaterielClassList")
    public ResponseResult<MaterielClass> queryAllMaterielClassList() {
        try {
            MaterielClass mc = this.materielClassService.queryAllMaterielClassList();
            return ResponseResult.buildSuccessResponse(mc);
        } catch (Exception e) {
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
        }
    }
    
    /**
     * 通过物料分类id查询对应的品牌列表
     * @param classId
     * @return
     */
    @GetMapping(value = "/api/queryMaterielBrandListByClassId/{classId}")
    public ResponseResult<List<MaterielBrand>> queryMaterielBrandListByClassId(@PathVariable("classId") Integer classId) {
        if (classId != null) {
            try {
                List<MaterielBrand> list = this.materielBrandService.queryMaterielBrandListByClassId(classId);
                return ResponseResult.buildSuccessResponse(list);
            } catch (Exception e) {
                return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                    ResponseStatus.DATABASE_ERROR.getMessage(),
                    null);
            }
        }
        return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(),  ResponseStatus.PARAMETER_VALIDATION.getMessage(),null);
    }
    
    /**
     * 根据物料品牌id查询所有型号列表
     * @param
     * @return
     */
    @GetMapping(value = "/api/queryMaterielModelListByBrandId/{brandId}")
    public ResponseResult<List<MaterielModel>> queryMaterielModelListByBrandId(@PathVariable("brandId") Integer brandId) {
        try {
            List<MaterielModel> list = this.materielModelService.queryMaterielModelListByBrandId(brandId);
            return ResponseResult.buildSuccessResponse(list);
        } catch (Exception e) {
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }

    /**
     * 根据物料品牌id查询所有型号列表(缩略图)
     * @param m
     * @return
     */
    @PostMapping(value = "/api/queryMaterielModelPicListByBrandId", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<ResultPager<MaterielModel>> queryMaterielModelPicListByBrandId(@RequestBody MaterielModelQuery materielModelQuery) {
        try {
            ResultPager<MaterielModel> list = this.materielModelService.queryMaterielModelPicListByBrandId(materielModelQuery);
            return ResponseResult.buildSuccessResponse(list);
        } catch (Exception e) {
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                    ResponseStatus.DATABASE_ERROR.getMessage(),
                    null);
        }
    }
    
    /**
     * 根据物料型号id查询所有规格列表
     * @param m
     * @return
     */
    @GetMapping(value = "/api/queryMaterielSpecListByModelId/{modelId}")
    public ResponseResult<List<MaterielSpecVo>> queryMaterielSpecListByModelId(@PathVariable("modelId") Integer modelId) {
        try {
            List<MaterielSpecVo> list = this.materielSpecService.queryMaterielSpecListByModelId(modelId);
            return ResponseResult.buildSuccessResponse(list);
        } catch (Exception e) {
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(),null);
        }
    }
    
	/**
	 * 获取所有新旧配置
	 */
	@GetMapping(value = "/api/getAllNewConfigs")
	public ResponseResult<List<MaterielNewConfig>> getAllNewConfigs() {
		return this.newConfigService.queryAllavailableNewConfigs();
	}


	/**
	 * 
	* @Description：查询型号相关信息
	* @param  modelIdList
	* @return ResponseResult<List<MaterielModelVo>>
	 */
	@PostMapping(value = "/api/getMaterielModelById")
    public ResponseResult<List<MaterielModelVo>> getMaterielModelById(@RequestBody List<Integer> modelIdList){
        try {
            List<MaterielModelVo> materielModelById = materielModelService.getMaterielModelById(modelIdList);
            return ResponseResult.buildSuccessResponse(materielModelById);
        } catch (Exception e) {
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(),null);
        }

    }
	
	/**
	 * 查询所有可用物料单位列表
	* @Description: 
	* @param @return
	* @return ResponseResult<List<MaterielUnitShortResp>>
	* @throws
	 */
	@GetMapping(value = "/api/queryAllUnitList")
	public List<MaterielUnitShortResp> queryAllUnitList(){
		ResponseResult<List<MaterielUnit>> resp = this.unitService.queryAllEnabledList();
		List<MaterielUnitShortResp> result = new ArrayList<MaterielUnitShortResp>(0);
		if(resp!=null&&resp.isSuccess()&&CollectionUtils.isNotEmpty(resp.getData())) {
		    result = new ArrayList<MaterielUnitShortResp>(resp.getData().size());
			MaterielUnitShortResp target = null;
			for(MaterielUnit unit :resp.getData()) {
				target = new MaterielUnitShortResp();
				BeanUtils.copyProperties(unit, target);
				result.add(target);
			}
		}
		return result;
	}

}
