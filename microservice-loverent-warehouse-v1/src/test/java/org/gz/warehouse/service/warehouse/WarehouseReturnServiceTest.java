package org.gz.warehouse.service.warehouse;

import lombok.extern.slf4j.Slf4j;
import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.common.vo.ApplyReturnReq;
import org.gz.warehouse.entity.warehouse.WarehouseReturnImages;
import org.gz.warehouse.entity.warehouse.WarehouseReturnRecord;
import org.gz.warehouse.entity.warehouse.WarehouseReturnRecordVO;
import org.gz.warehouse.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author daiqingwen
 * @Description:  还机记录增删改查单元测试
 * @date 2018年3月6日 上午11:52
 */

@Slf4j
public class WarehouseReturnServiceTest extends BaseTest {

    @Autowired
    private WarehouseReturnService returnService;

    /**
     * 查询
     */
    @Test
    public void testQuery(){
        WarehouseReturnRecordVO page = new WarehouseReturnRecordVO();
        ResponseResult<ResultPager<WarehouseReturnRecord>> list = this.returnService.queryPageList(page);
        log.info(">>>>>>>>>>>>result is :" + list);
        assertTrue(list.isSuccess());
    }
    
    /**
     * 删除
     */
    @Test
    public void testDelete(){
        String id = "2,3";
        ResponseResult<?> result = this.returnService.deleteRecord(id);
        assertTrue(result.isSuccess());
        log.info("删除结果为：" + result.getErrCode() + ":" + result.getErrMsg());
    }

    /**
     * 获取附件详细信息
     */
    @Test
    public void testGetDetails(){
        Long id= 2L;
        ResponseResult<WarehouseReturnRecord> list = this.returnService.queryRecordDetail(id);
        log.info(">>>>>>>>>>>>result is :" + list);
        assertTrue(list.isSuccess());
    }

    /**
     *  申请还机
     */
    @Test
    public void applyReturn(){
        ApplyReturnReq mr = new ApplyReturnReq();
        mr.setSourceOrderNo("PO20180118165327908000045");
        mr.setSourceOrderStatus(16);
        mr.setMaterielBasicId(17L);
        mr.setBatchNo("001");
        mr.setSnNo("001sn");
        mr.setImieNo("001IMEI");
        mr.setReturnApplyTime(new Date());
        ResponseResult<ApplyReturnReq> result = this.returnService.applyReturn(mr);
        assertTrue(result.isSuccess());
        log.info(">>>>>>>>>result is :" + result);
    }
    
    /**
     * 还机入库
    * @Description: 
    * @param 
    * @return void
    * @throws
     */
    @Test
    public void testTransitln() {
    	WarehouseReturnRecord returnRecord = new WarehouseReturnRecord();
    	returnRecord.setId(4L);
    	returnRecord.setDamagePrice(500D);
    	returnRecord.setDamageRemark("500");
    	returnRecord.setImieNo("001IMEI");
    	returnRecord.setSnNo("001sn");
    	returnRecord.setMaterielStatus(10);
    	returnRecord.setOperatorId(1L);
    	returnRecord.setOperatorName("管理员");
		returnService.transitln(returnRecord);
    }
}