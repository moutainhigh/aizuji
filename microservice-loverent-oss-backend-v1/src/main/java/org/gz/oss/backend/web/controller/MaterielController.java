package org.gz.oss.backend.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.web.controller.BaseController;
import org.gz.oss.common.feign.IMaterielService;
import org.gz.warehouse.common.vo.MaterielUnitShortResp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MaterielController extends BaseController {

    @Resource
    private IMaterielService iMaterielService;

    /**
     * 获取物料分类列表
     * @param m
     * @return
     */
    @PostMapping(value = "/materiel/queryAllMaterielClassList")
    public ResponseResult<?> queryAllMaterielClassList() {
        try {
            return iMaterielService.queryAllMaterielClassList();
        } catch (Exception e) {
            log.error("queryAllMaterielClassList失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }

    /**
     * 通过物料分类id查询物料品牌列表
     * @param m
     * @return
     */
    @PostMapping(value = "/materiel/queryMaterielBrandListByClassId")
    public ResponseResult<?> queryMaterielBrandListByClassId(Integer classId) {
        try {
            return iMaterielService.queryMaterielBrandListByClassId(classId);
        } catch (Exception e) {
            log.error("queryAllMaterielClassList失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }

    /**
     * 通过物料品牌id查询型号列表
     * @param m
     * @return
     */
    @PostMapping(value = "/materiel/queryMaterielModelListByBrandId")
    public ResponseResult<?> queryMaterielModelListByBrandId(Integer brandId) {
        try {
            return iMaterielService.queryMaterielModelListByBrandId(brandId);
        } catch (Exception e) {
            log.error("queryMaterielModelListByBrandId失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }

    /**
     * 通过物料型号id查询规格批次列表
     * @param m
     * @return
     */
    @PostMapping(value = "/materiel/queryMaterielSpecListByModelId")
    public ResponseResult<?> queryMaterielSpecListByModelId(Integer modelId) {
        try {
            return iMaterielService.queryMaterielSpecListByModelId(modelId);
        } catch (Exception e) {
            log.error("queryMaterielSpecListByModelId失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }
    
    @GetMapping(value = "/materiel/queryAllUnitList")
    public ResponseResult<List<MaterielUnitShortResp>> queryAllUnitList(){
    	return ResponseResult.buildSuccessResponse(iMaterielService.queryAllUnitList());
    }
}
