package org.gz.warehouse.web.controller.warehouse;

import javax.validation.Valid;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.AssertUtils;
import org.gz.common.web.controller.BaseController;
import org.gz.warehouse.entity.warehouse.StorageLocation;
import org.gz.warehouse.entity.warehouse.StorageLocationQuery;
import org.gz.warehouse.service.warehouse.StorageLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @Description:库位控制器
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月16日 	Created
 */
@RestController
@RequestMapping("/storageLocation")
@Slf4j
public class StorageLocationController extends BaseController {
    @Autowired
    private StorageLocationService storageLocationService;
    
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> add(@Valid @RequestBody StorageLocation m, BindingResult bindingResult) {
        //验证数据 
        ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
        if (validateResult == null) {
            try {
                return this.storageLocationService.insertSelective(m);
            } catch (Exception e) {
                log.error("新增库位异常：{}",e.getLocalizedMessage());
                return ResponseResult.build(1000, "", null);
            }
        }
        return validateResult;
    }
    
    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> update(@Valid @RequestBody StorageLocation m, BindingResult bindingResult) {
        //验证数据 
        ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
        if (validateResult == null) {
            try {
                return this.storageLocationService.updateByPrimaryKeySelective(m);
            } catch (Exception e) {
                log.error("更新库位异常：{}",e.getLocalizedMessage());
                return ResponseResult.build(1000, "", null);
            }
        }
        return validateResult;
    }
    @PostMapping(value = "/queryPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> queryPage(@RequestBody StorageLocationQuery m, BindingResult bindingResult) {
        if(m!=null) {
            try {
                return this.storageLocationService.queryByPage(m);
            } catch (Exception e) {
                log.error("获取库位分页列表失败：{}",e.getLocalizedMessage());
                return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
            }
        }
        return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
    }
    
    @PostMapping(value = "/queryById", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> queryById(@RequestBody StorageLocation m, BindingResult bindingResult) {
        if(m!=null && m.getId() != null) {
            try {
                return this.storageLocationService.selectByPrimaryKey(m.getId());
            } catch (Exception e) {
                log.error("获取库位信息失败：{}",e.getLocalizedMessage());
                return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
            }
        }
        return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
    }
    
    @PostMapping(value = "/updateEnableFlag", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> updateEnableFlag(@RequestBody StorageLocation m) {
      //验证数据 
        if (AssertUtils.isPositiveNumber4Int(m.getId())&&AssertUtils.isPositiveNumber4Int(m.getEnableFlag())) {
            try {
                return this.storageLocationService.updateEnableFlag(m);
            } catch (Exception e) {
                log.error("设置仓位启/停用标志失败：{}",e.getLocalizedMessage());
                return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
            }
        }
        return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
    }
    
    @PostMapping(value = "/deleteById/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> deleteById(@PathVariable Integer id) {
        if (AssertUtils.isPositiveNumber4Int(id)) {
            try {
                StorageLocation storageLocation = new StorageLocation();
                storageLocation.setId(id);
                return this.storageLocationService.deleteStorageLocation(storageLocation);
            } catch (Exception e) {
                log.error("删除库位失败：{}",e.getLocalizedMessage());
                return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
            }
        }
        return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
    }
}
