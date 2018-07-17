/**
 * 
 */
package org.gz.warehouse.service.warehouse;

import static org.junit.Assert.*;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.entity.warehouse.WarehouseCommodityTrackQuery;
import org.gz.warehouse.entity.warehouse.WarehouseCommodityTrackVO;
import org.gz.warehouse.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2018年1月2日 下午3:21:25
 */
public class WarehouseCommodityTrackServiceTest extends BaseTest{

	@Autowired
	private WarehouseCommodityTrackService trackService;
	
	@Test
	public void testQueryByPage() {
		WarehouseCommodityTrackQuery q = new WarehouseCommodityTrackQuery();
		q.setAdjustBatchNo("IVA2018011819493462300004");
		System.err.println(q);
		ResponseResult<ResultPager<WarehouseCommodityTrackVO>> result = trackService.queryByPage(q);
		assertTrue(result.isSuccess());
		System.err.println(result);
	}

}
