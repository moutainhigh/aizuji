package org.gz.warehouse.service.warehouse;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.entity.warehouse.StorageLocation;
import org.gz.warehouse.entity.warehouse.StorageLocationQuery;
import org.gz.warehouse.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class StorageLocationServiceTest extends BaseTest {
    @Autowired
    private StorageLocationService warehouseLocationService;
    @Test
    public void testInsert() {
        StorageLocation record = new StorageLocation();
        record.setLocationCode("0300");
        record.setLocationName("冻结");
        record.setCreateBy(1L);
        record.setUpdateBy(1L);
        record.setCreateOn(new Date());
        record.setUpdateOn(new Date());
        ResponseResult<StorageLocation> result = warehouseLocationService.insertSelective(record);
        assertTrue(result.isSuccess());
    }
    
    @Test
    public void testUpdate() {
        StorageLocation record = new StorageLocation();
        record.setId(2);
        record.setEnableFlag(0);
        record.setRemark("不启用");
        record.setUpdateBy(123L);
        record.setUpdateOn(new Date());
        ResponseResult<StorageLocation> result = warehouseLocationService.updateByPrimaryKeySelective(record);
        assertTrue(result.isSuccess());
    }
    @Test
    public void testQuery(){
        StorageLocationQuery m = new StorageLocationQuery();
        ResponseResult<ResultPager<StorageLocation>> result = warehouseLocationService.queryByPage(m);
        assertTrue(result.isSuccess());
    }
    
    @Test
    public void testQueryById(){
        StorageLocation record = new StorageLocation();
        record.setId(2);
        ResponseResult<StorageLocation> result = warehouseLocationService.selectByPrimaryKey(2);
        assertTrue(result.isSuccess());
    }
    @Test
    public void testDeleteStorageLocation(){
        StorageLocation storageLocation = new StorageLocation();
        storageLocation.setId(3);
        ResponseResult<StorageLocation> result = warehouseLocationService.deleteStorageLocation(storageLocation);
        assertTrue(result.isSuccess());
    }
}
