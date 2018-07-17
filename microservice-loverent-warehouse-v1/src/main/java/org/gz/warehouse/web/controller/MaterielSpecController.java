package org.gz.warehouse.web.controller;

import java.util.List;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.AssertUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.warehouse.entity.MaterielModelSpec;
import org.gz.warehouse.entity.MaterielSpec;
import org.gz.warehouse.entity.MaterielSpecQuery;
import org.gz.warehouse.entity.MaterielSpecVo;
import org.gz.warehouse.service.materiel.MaterielSpecService;
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
 * 物料规格控制器
 * @author hxj
 *
 */
@RestController
@RequestMapping("/materielSpec")
@Slf4j
public class MaterielSpecController extends BaseController {

    @Autowired
    private MaterielSpecService materielSpecService;

    /**
     * 新增数据
     * @param m
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> add(@Valid @RequestBody MaterielSpec m, BindingResult bindingResult) {
        // 验证数据
        ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
        if (validateResult == null) {
            try {
                return this.materielSpecService.insert(m);
            } catch (Exception e) {
                return ResponseResult.build(1000, "", null);
            }
        }
        return validateResult;
    }

    /**
     * 修改物料规格
     * @param m
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> update(@Valid @RequestBody MaterielSpec m, BindingResult bindingResult) {
        // 验证数据
        ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
        if (validateResult == null && AssertUtils.isPositiveNumber4Int(m.getId())) {
            try {
                return this.materielSpecService.update(m);
            } catch (Exception e) {
                log.error("修改物料规格失败：{}", e.getLocalizedMessage());
                return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                    ResponseStatus.DATABASE_ERROR.getMessage(),
                    null);
            }
        }
        return validateResult;
    }

    /**
     * 设置物料规格启/停用标志
     * @param m
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/setEnableFlag", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> setEnableFlag(@RequestBody MaterielSpec m) {
        // 验证数据
        if (AssertUtils.isPositiveNumber4Int(m.getId()) && AssertUtils.isPositiveNumber4Int(m.getEnableFlag())) {
            try {
                return this.materielSpecService.setEnableFlag(m);
            } catch (Exception e) {
                log.error("设置物料规格启/停用标志失败：{}", e.getLocalizedMessage());
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
     * 批量删除物料规格
     * @param ids
     * @return
     */
    @PostMapping(value = "/batchDelete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> batchDelete(@RequestParam String ids) {
        // 验证数据
        if (StringUtils.hasText(ids)) {
            try {
                return this.materielSpecService.deleteByIds(ids);
            } catch (Exception e) {
                log.error("删除物料规格失败：{}", e.getLocalizedMessage());
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
     * 获取物料详情
     * @param id
     * @return
     */
    @GetMapping(value = "/getDetail/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> getDetail(@PathVariable Integer id) {
        // 验证数据
        if (AssertUtils.isPositiveNumber4Int(id)) {
            try {
                return this.materielSpecService.selectByPrimaryKey(id);
            } catch (Exception e) {
                log.error("获取物料规格详情失败：{}", e.getLocalizedMessage());
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
     * 获取分页列表
     * @param m
     * @return
     */
    @PostMapping(value = "/queryByPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<ResultPager<MaterielSpec>> queryByPage(@RequestBody MaterielSpecQuery m) {
        if (m != null) {
            try {
                return this.materielSpecService.queryByPage(m);
            } catch (Exception e) {
                log.error("获取物料规格分页列表失败：{}", e.getLocalizedMessage());
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
     * 根据物料型号id查询所有规格列表
     * @param m
     * @return
     */
    @PostMapping(value = { "/queryMaterielSpecListByModelId", "/api/queryMaterielSpecListByModelId" }, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<List<MaterielSpecVo>> queryMaterielSpecListByModelId(@RequestParam("modelId") Integer modelId) {
        try {
            List<MaterielSpecVo> list = this.materielSpecService.queryMaterielSpecListByModelId(modelId);
            return ResponseResult.buildSuccessResponse(list);
        } catch (Exception e) {
            log.error("根据物料品牌id查询所有型号列表失败：{}", e);
            return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(),
                ResponseStatus.DATABASE_ERROR.getMessage(),
                null);
        }
    }

    /**
     * 查询某型号下已配置到产品中的所有规格值
     * @param m
     * @return
     */
    @PostMapping(value = "/api/queryAllSpecValueByModelId", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<List<MaterielModelSpec>> queryAllSpecValueByModelId(@RequestParam("modelId") Integer modelId) {
        if (modelId != null) {
            try {
                List<MaterielModelSpec> list = this.materielSpecService.queryAllSpecValueByModelId(modelId);
                return ResponseResult.buildSuccessResponse(list);
            } catch (Exception e) {
                log.error("查询某型号下已配置到产品中的所有规格值失败：{}", e);
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
     * 根据规格信息查询规格批次号
     * @param m
     * @return
     */
    @PostMapping(value = "/api/getSpecBatchNoBySpecInfo", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<String> getSpecBatchNoBySpecInfo(@RequestBody MaterielModelSpec mms) {
        if (mms != null && !StringUtils.isEmpty(mms.getMaterielSpecValue()) && mms.getMaterielModelId() != null) {
            try {
                String specBatchNo = this.materielSpecService.getSpecBatchNoBySpecInfo(mms);
                return ResponseResult.buildSuccessResponse(specBatchNo);
            } catch (Exception e) {
                log.error("根据规格信息查询规格批次号失败：{}", e);
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
