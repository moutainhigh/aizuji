/**
 * 
 */
package org.gz.warehouse.service.warehouse;

import static org.junit.Assert.assertTrue;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.common.vo.WarehouseCommodityReq;
import org.gz.warehouse.common.vo.WarehouseCommodityResp;
import org.gz.warehouse.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2018年3月14日 下午5:32:16
 */
public class WarehouseCommodityServiceTest extends BaseTest{
	
	@Autowired
	private WarehouseCommodityService commodityService;

	@Test
	public void testQueryWarehouseLocationCommoditys() {
		WarehouseCommodityReq q = new WarehouseCommodityReq(1L);
		ResponseResult<ResultPager<WarehouseCommodityResp>> result = commodityService.queryWarehouseLocationCommoditys(q);
		assertTrue(result.isSuccess()&&result.getData().getTotalNum()>0);
		System.err.println(result);
	}

}
