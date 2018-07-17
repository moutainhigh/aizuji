package org.gz.warehouse.service.warehouse;

import static org.junit.Assert.*;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.entity.warehouse.WarehouseChangeRecordDetailQuery;
import org.gz.warehouse.entity.warehouse.WarehouseChangeRecordDetailVO;
import org.gz.warehouse.entity.warehouse.WarehouseChangeRecordQuery;
import org.gz.warehouse.entity.warehouse.WarehouseChangeRecordVO;
import org.gz.warehouse.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月23日 上午10:00:09
 */
public class WarehouseChangeRecordServiceTest extends BaseTest {

	@Autowired
	private WarehouseChangeRecordService changeRecordService;
	
	@Test
	public void testQueryAggregationPage() {
		WarehouseChangeRecordQuery q = new WarehouseChangeRecordQuery();
		q.setCurrPage(1);
		q.setPageSize(20);
		System.err.println(q);
		ResponseResult<ResultPager<WarehouseChangeRecordVO>> result = this.changeRecordService.queryAggregationPage(q);
		System.err.println(result);
		assertTrue(result.isSuccess());
	}

	@Test
	public void k() {
		WarehouseChangeRecordDetailQuery q = new WarehouseChangeRecordDetailQuery();
		q.setMaterielBasicId(3L);
		q.setStorageBatchNo("SBN2017122718233912700002");
		System.err.println(q);
		ResponseResult<ResultPager<WarehouseChangeRecordDetailVO>> result=this.changeRecordService.queryDetailPage(q);
		System.err.println(result);
		assertTrue(result.isSuccess());
	}

}
