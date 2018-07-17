package org.gz.warehouse.web.controller.warehouse;

import javax.validation.Valid;

import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.web.controller.BaseController;
import org.gz.warehouse.entity.warehouse.WarehouseInfo;
import org.gz.warehouse.entity.warehouse.WarehouseInfoQuery;
import org.gz.warehouse.service.warehouse.WarehouseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @Description:    仓库控制器
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月16日 	Created
 */
@RestController
@RequestMapping("/warehouseInfo")
@Slf4j
public class WarehouseInfoController extends BaseController {
    
	@Autowired
    private WarehouseInfoService warehouseInfoService;
    
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> add(@Valid @RequestBody WarehouseInfo m, BindingResult bindingResult) {
        //验证数据 
        ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
        if (validateResult == null) {
            try {
                return this.warehouseInfoService.insertSelective(m);
            } catch (Exception e) {
                log.error("新增库位异常：{}",e.getLocalizedMessage());
                return ResponseResult.build(1000, "", null);
            }
        }
        return validateResult;
    }
    
    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> update(@Valid @RequestBody WarehouseInfo m, BindingResult bindingResult) {
        //验证数据 
        ResponseResult<String> validateResult = super.getValidatedResult(bindingResult);
        if (validateResult == null) {
            try {
                return this.warehouseInfoService.updateByPrimaryKeySelective(m);
            } catch (Exception e) {
                log.error("更新库位异常：{}",e.getLocalizedMessage());
                return ResponseResult.build(1000, "", null);
            }
        }
        return validateResult;
    }
    
    @PostMapping(value = "/queryPage", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> queryPage(@RequestBody WarehouseInfoQuery m, BindingResult bindingResult) {
        if(m!=null) {
            try {
                return this.warehouseInfoService.queryByPage(m);
            } catch (Exception e) {
                log.error("获取库位分页列表失败：{}",e.getLocalizedMessage());
                return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
            }
        }
        return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
    }
    
    @PostMapping(value = "/queryById", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseResult<?> queryById(@RequestBody WarehouseInfo m, BindingResult bindingResult) {
        if(m!=null && m.getId() != null) {
            try {
                return this.warehouseInfoService.selectByPrimaryKey(m.getId());
            } catch (Exception e) {
                log.error("获取仓库信息失败：{}",e.getLocalizedMessage());
                return ResponseResult.build(ResponseStatus.DATABASE_ERROR.getCode(), ResponseStatus.DATABASE_ERROR.getMessage(), null);
            }
        }
        return ResponseResult.build(ResponseStatus.PARAMETER_VALIDATION.getCode(), ResponseStatus.PARAMETER_VALIDATION.getMessage(), null);
    }
    
   
}
