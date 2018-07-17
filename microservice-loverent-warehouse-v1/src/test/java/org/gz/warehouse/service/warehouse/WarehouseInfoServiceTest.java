package org.gz.warehouse.service.warehouse;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gz.common.constants.ZTreeSimpleNode;
import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.entity.warehouse.WarehouseInfo;
import org.gz.warehouse.entity.warehouse.WarehouseInfoQuery;
import org.gz.warehouse.entity.warehouse.WarehouseLocationRelation;
import org.gz.warehouse.entity.warehouse.WarehouseLocationReq;
import org.gz.warehouse.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WarehouseInfoServiceTest extends BaseTest {
    @Autowired
    private WarehouseInfoService warehouseInfoService;
    
    @Test
    public void testInsert() {
        WarehouseInfo record = new WarehouseInfo();
        record.setWarehouseCode("0200");
        record.setWarehouseName("广州");
        record.setParentId(1L);
        record.setContacts("赵云");
        record.setPhone("18588888888");
        record.setCreateBy(1L);
        record.setUpdateBy(1L);
        record.setCreateOn(new Date());
        record.setUpdateOn(new Date());
        ResponseResult<WarehouseInfo> result = warehouseInfoService.insertSelective(record);
        assertTrue(result.isSuccess());
    }
    
    @Test
    public void testUpdate() {
        WarehouseInfo record = new WarehouseInfo();
        record.setId(3);
        record.setEnableFlag(1);
        record.setRemark("启用");
        record.setUpdateBy(123L);
        record.setUpdateOn(new Date());
        ResponseResult<WarehouseInfo> result = warehouseInfoService.updateByPrimaryKeySelective(record);
        assertTrue(result.isSuccess());
    }
    
    @Test
    public void testQuery(){
        WarehouseInfoQuery m = new WarehouseInfoQuery();
        ResponseResult<ResultPager<WarehouseInfo>> result = warehouseInfoService.queryByPage(m);
        assertTrue(result.isSuccess());
 
    }
    
    @Test
    public void testQueryById(){
        ResponseResult<WarehouseInfo> result = warehouseInfoService.selectByPrimaryKey(2);
        assertTrue(result.isSuccess());
    }
    
    @Test
    public void testInsertLocation(){
        WarehouseLocationRelation relation = new WarehouseLocationRelation();
        relation.setLocationId(1);
        relation.setWarehouseId(5);
        
        WarehouseLocationRelation relation1 = new WarehouseLocationRelation();
        relation1.setLocationId(2);
        relation1.setWarehouseId(5);
        
        List<WarehouseLocationRelation> paramList = new ArrayList<>();
        paramList.add(relation1);
        paramList.add(relation);
        WarehouseLocationReq req = new WarehouseLocationReq();
        req.setWarehouseId(1);
        req.setLocationIds("1");
        ResponseResult<WarehouseLocationRelation> result = warehouseInfoService.insertLocation(req);
        assertTrue(result.isSuccess());
 
    }
    
    @Test
    public void testQueryList(){
        Integer warehouseId = 1;
        ResponseResult<List<WarehouseLocationRelation>> result = warehouseInfoService.queryList(warehouseId);
        assertTrue(result.isSuccess());
    }
    
    @Test
    public void getTree() {
    	ZTreeSimpleNode node = this.warehouseInfoService.getTree(1);
    	System.err.println(node);
    }
}
