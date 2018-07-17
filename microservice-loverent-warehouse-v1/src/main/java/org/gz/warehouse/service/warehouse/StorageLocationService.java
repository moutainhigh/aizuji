package org.gz.warehouse.service.warehouse;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.entity.warehouse.StorageLocation;
import org.gz.warehouse.entity.warehouse.StorageLocationQuery;

public interface StorageLocationService {

    int insert(StorageLocation record);
    /**
     * 
     * @Description: TODO 新增方法
     * @param record
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月16日
     */
    ResponseResult<StorageLocation> insertSelective(StorageLocation record);

    ResponseResult<StorageLocation> selectByPrimaryKey(Integer id);
    /**
     * 
     * @Description: TODO 更新
     * @param record
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月16日
     */
    ResponseResult<StorageLocation> updateByPrimaryKeySelective(StorageLocation record);

    int updateByPrimaryKey(StorageLocation record);
    /**
     * 
     * @Description: TODO 分页查询
     * @param m
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月16日
     */
    public ResponseResult<ResultPager<StorageLocation>> queryByPage(StorageLocationQuery m);
    /**
     * 
     * @Description: TODO 更新启用、停用标识
     * @param storageLocation
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月18日
     */
    ResponseResult<?> updateEnableFlag(StorageLocation storageLocation);
    /**
     * 
     * @Description: TODO 删除库位
     * @param storageLocation
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2017年12月18日
     */
    ResponseResult<StorageLocation> deleteStorageLocation(StorageLocation storageLocation);
}