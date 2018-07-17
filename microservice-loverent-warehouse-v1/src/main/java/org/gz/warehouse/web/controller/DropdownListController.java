/**
 * 
 */
package org.gz.warehouse.web.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.gz.common.constants.ZTreeSimpleNode;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.web.controller.BaseController;
import org.gz.warehouse.entity.*;
import org.gz.warehouse.entity.supplier.SupplierBasicInfo;
import org.gz.warehouse.entity.warehouse.WarehouseInfo;
import org.gz.warehouse.entity.warehouse.WarehouseInfoQuery;
import org.gz.warehouse.service.materiel.*;
import org.gz.warehouse.service.supplier.SupplierService;
import org.gz.warehouse.service.warehouse.WarehouseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2017年12月26日 下午8:03:37
 */
@RestController
@RequestMapping("/dropdownList")
@Slf4j
public class DropdownListController extends BaseController{

	@Autowired
	private MaterielClassService materielClassService;
	
	@Autowired
	private MaterielBrandService materielBrandService;
	
	@Autowired
    private MaterielModelService materielModelService;
	
	@Autowired
	private MaterielSpecService materielSpecService;
	
	@Autowired
	private MaterielUnitService materielUnitService;
	
	@Autowired
	private WarehouseInfoService warehouseInfoService;
	
	@Autowired
	private SupplierService supplierService;

	@Autowired
    private MaterielNewConfigService newConfig;
	
	 /**
     * 获取所有物料分类列表  树状返回
     * @param m
     * @return
     */
    @PostMapping(value = "/queryAllMaterielClassList",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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

    /**
     * 获取所有新旧配置
     * @return MaterielNewConfig
     */
    @GetMapping(value = "/getAllNewConfig")
    public ResponseResult<List<MaterielNewConfig>> getAll(){
        return this.newConfig.getAllNewConfig();
    }

    
    /**
     * 获取所有物料分类ZTree树
     */
    @PostMapping(value = "/getMaterielClassZTree")
    public ResponseResult<ZTreeSimpleNode> getMaterielClassZTree() {
    	return ResponseResult.buildSuccessResponse(this.materielClassService.getZTree(0));
    }
    
    
    /**
     * 通过物料分类id查询对应的品牌列表
     * @param classId
     * @return
     */
    @GetMapping(value = "/queryMaterielBrandListByClassId/{classId}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<List<MaterielBrand>> queryMaterielBrandListByClassId(@PathVariable("classId") Integer classId) {
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
    
    

    /**
     * 根据物料品牌id查询所有型号列表
     * @param m
     * @return
     */
    @GetMapping(value = "/queryMaterielModelListByBrandId/{brandId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<List<MaterielModel>> queryMaterielModelListByBrandId(@PathVariable("brandId") Integer brandId) {
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
     * 根据物料型号id查询所有规格列表
     * @param m
     * @return
     */
    @GetMapping(value = "/queryMaterielSpecListByModelId/{modelId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<List<MaterielSpecVo>> queryMaterielSpecListByModelId(@PathVariable("modelId") Integer modelId) {
        try {
            List<MaterielSpecVo> list = this.materielSpecService.queryMaterielSpecListByModelId(modelId);
            return ResponseResult.buildSuccessResponse(list);
        } catch (Exception e) {
            log.error("根据物料品牌id查询所有型号列表失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(),null);
        }
    }
    
    /**
	 * 获取物料单位列表
	 * @return
	 */
	@PostMapping(value = "/queryAllUnitList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<List<MaterielUnit>> queryAllList(){
		try {
			return this.materielUnitService.queryAllEnabledList();
		} catch (Exception e) {
			log.error("获取物料单位列表失败：{}",e.getLocalizedMessage());
			return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
		}
	}
	
 	/**
	 * 获取仓库列表
	 */
	@PostMapping(value = "/queryAllWarehouseInfoList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseResult<List<WarehouseInfo>> queryAllWarehouseInfoList(){
		try {
			WarehouseInfoQuery q = new WarehouseInfoQuery();
			q.setWarehouseCode("S011");
			q.setCurrPage(1);
			q.setPageSize(Integer.MAX_VALUE);
			return ResponseResult.buildSuccessResponse(this.warehouseInfoService.queryPageList(q));
		} catch (Exception e) {
			return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
		}
	}
	
	/**
	 * 获取供应商列表
	 */
	@PostMapping(value = "/queryAllSuppliers")
	public ResponseResult<List<SupplierBasicInfo>> queryAllSuppliers(){
		return ResponseResult.buildSuccessResponse(this.supplierService.queryAllSuppliers());
	}
		
}
