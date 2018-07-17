package org.gz.warehouse.service.warehouse.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.gz.common.entity.ResultPager;
import org.gz.common.exception.ServiceException;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.warehouse.common.WarehouseErrorCode;
import org.gz.warehouse.entity.warehouse.StorageLocation;
import org.gz.warehouse.entity.warehouse.StorageLocationQuery;
import org.gz.warehouse.entity.warehouse.WarehouseLocationRelation;
import org.gz.warehouse.mapper.warehouse.StorageLocationMapper;
import org.gz.warehouse.mapper.warehouse.WarehouseLocationRelationMapper;
import org.gz.warehouse.service.warehouse.StorageLocationService;
import org.springframework.stereotype.Service;

@Service
public class StorageLocationServiceImpl implements StorageLocationService {
    @Resource
    private StorageLocationMapper warehouseLocationMapper;
    @Resource
    private WarehouseLocationRelationMapper warehouseLocationRelationMapper;
    @Override
    public int insert(StorageLocation record) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ResponseResult<StorageLocation> insertSelective(StorageLocation record) {
     // 1.验证唯一性(规格名称)
        ResponseResult<StorageLocation> validateResult = this.validateWarehouseLocation(record);
        // 2.写入数据
        if (validateResult.isSuccess()) {
            try {
                if (this.warehouseLocationMapper.insertSelective(record) > 0) {
                    return ResponseResult.buildSuccessResponse(record);
                }
            } catch (Exception e) {
                // 转换底层异常
                throw new ServiceException(e.getLocalizedMessage());
            }
        }
        return validateResult;
    }

    @Override
    public ResponseResult<StorageLocation> selectByPrimaryKey(Integer id) {
        return ResponseResult.buildSuccessResponse(warehouseLocationMapper.selectByPrimaryKey(id));
    }

    @Override
    public ResponseResult<StorageLocation> updateByPrimaryKeySelective(StorageLocation record) {
        try {
            if (this.warehouseLocationMapper.updateByPrimaryKeySelective(record) > 0) {
                return ResponseResult.buildSuccessResponse(record);
            }
        } catch (Exception e) {
            // 转换底层异常
            throw new ServiceException(e.getLocalizedMessage());
        }
        return ResponseResult.build(ResponseStatus.DATA_UPDATED_ERROR.getCode(), ResponseStatus.DATA_UPDATED_ERROR.getMessage(), null);
    }

    @Override
    public int updateByPrimaryKey(StorageLocation record) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ResponseResult<ResultPager<StorageLocation>> queryByPage(StorageLocationQuery m) {
        int totalNum = this.warehouseLocationMapper.queryPageCount(m);
        List<StorageLocation> list = new ArrayList<StorageLocation>(0);
        if(totalNum>0) {
            list = this.warehouseLocationMapper.queryPageList(m);
        }
        ResultPager<StorageLocation> data = new ResultPager<StorageLocation>(totalNum, m.getCurrPage(), m.getPageSize(), list);
        return ResponseResult.buildSuccessResponse(data);
    }
    
    private ResponseResult<StorageLocation> validateWarehouseLocation(StorageLocation m) {
        StorageLocationQuery query = new StorageLocationQuery(m);
        int count = this.warehouseLocationMapper.queryPageCount(query);
        return count>0?ResponseResult.build(WarehouseErrorCode.WAREHOUSE_LOCATION_REPEAT_ERROR.getCode(), WarehouseErrorCode.WAREHOUSE_LOCATION_REPEAT_ERROR.getMessage(), m):ResponseResult.buildSuccessResponse();
    }

    @Override
    public ResponseResult<?> updateEnableFlag(StorageLocation storageLocation) {
        // 设置更新时间
        storageLocation.setUpdateOn(new Date());
        warehouseLocationMapper.updateByPrimaryKeySelective(storageLocation);
        return ResponseResult.buildSuccessResponse(storageLocation);
    }

    @Override
    public ResponseResult<StorageLocation> deleteStorageLocation(StorageLocation storageLocation) {
        ResponseResult<StorageLocation> result = canDelete(storageLocation);
        if(!result.isSuccess()) return result;
        // 库位暂为被库使用可以删除
        storageLocation.setDelFlag(1);
        storageLocation.setUpdateOn(new Date());
        warehouseLocationMapper.updateByPrimaryKeySelective(storageLocation);
        return ResponseResult.buildSuccessResponse();
    }
    
    private ResponseResult<StorageLocation> canDelete(StorageLocation storageLocation){
          WarehouseLocationRelation w = new WarehouseLocationRelation();
          w.setLocationId(storageLocation.getId());
         if(warehouseLocationRelationMapper.selectCountById(w) == 0){
             return ResponseResult.buildSuccessResponse();
         }
         return ResponseResult.build(WarehouseErrorCode.WAREHOUSE_LOCATION_USING_ERROR.getCode(), WarehouseErrorCode.WAREHOUSE_LOCATION_USING_ERROR.getMessage(), null);
        
    }
}
