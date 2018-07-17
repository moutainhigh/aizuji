/**
 * 
 */
package org.gz.warehouse.service.warehouse;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.entity.warehouse.WarehousePickingRecord;
import org.gz.warehouse.entity.warehouse.WarehousePickingRecordQuery;
import org.gz.warehouse.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月29日 下午12:47:43
 */
public class WarehouseOutServiceTest extends BaseTest {

	@Autowired
	private WarehouseOutService outService;

	@Test
	public void testPick() {
		WarehousePickingRecord q =new WarehousePickingRecord();
		q.setSourceOrderNo("SO1802060000001244");
		q.setMaterielBasicId(22L);
		q.setSnNo("玫瑰金0002");
		q.setImieNo("IMEI000007");
		q.setApplyTimes("2017-12-29 12:00:00");
		System.err.println(q);
		ResponseResult<WarehousePickingRecord> result = outService.pick(q);
		System.err.println(result);
	}
	
	@Test
	public void testOut() {
		WarehousePickingRecord q =new WarehousePickingRecord();
		q.setSourceOrderNo("SO1801220000000236");
		q.setMaterielBasicId(17L);
		q.setRealName("胡小军");
		q.setProv("广东省");
		q.setCity("深圳市");
		q.setArea("南山区");
		q.setAddress("应人石");
		q.setInsuredAmount("5000");
		q.setPhoneNum("18682017315");
		q.setIdNo("522224198607190413");
		q.setProductId(18L);
		ResponseResult<WarehousePickingRecord> result =this.outService.out(q);
		System.err.println(result);
	}
	
	

	@Test
	public void testQueryByPage() {
		WarehousePickingRecordQuery q = new WarehousePickingRecordQuery();
		q.setStatusFlag(9);
		ResponseResult<ResultPager<WarehousePickingRecord>> result = this.outService.queryByPage(q);
		System.err.println(result);
	}
}
