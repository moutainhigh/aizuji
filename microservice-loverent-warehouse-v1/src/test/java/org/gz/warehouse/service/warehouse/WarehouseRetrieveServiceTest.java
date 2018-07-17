/**
 * 
 */
package org.gz.warehouse.service.warehouse;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.gz.common.constants.ZTreeSimpleNode;
import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.common.vo.SigningQuery;
import org.gz.warehouse.common.vo.SigningResult;
import org.gz.warehouse.common.vo.WarehouseMaterielCount;
import org.gz.warehouse.common.vo.WarehouseMaterielCountQuery;
import org.gz.warehouse.entity.warehouse.WarehouseAggregationVO;
import org.gz.warehouse.entity.warehouse.WarehouseMaterielAggregationWrapper;
import org.gz.warehouse.entity.warehouse.WarehouseRetrieveQuery;
import org.gz.warehouse.feign.OSSServerService;
import org.gz.warehouse.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2017年12月22日 下午1:51:00
 */
public class WarehouseRetrieveServiceTest extends BaseTest{

	@Autowired
	private WarehouseRetrieveService retrieveService;

	@Autowired
    private WarehouseInfoService warehouseInfoService;
	
	@Autowired
	private OSSServerService ossServerService;
	
	@Test
	public void testGetTree() {
		ResponseResult<ZTreeSimpleNode> result = ResponseResult.buildSuccessResponse(this.warehouseInfoService.getTree(1));
		assertTrue(result.isSuccess());
		System.err.println(result);
	}
	
	
	@Test
	public void testQueryAggregationPage() {
		WarehouseRetrieveQuery q = new WarehouseRetrieveQuery();
		q.setCurrPage(1);
		q.setPageSize(20);
		q.setMaterielSpecBatchNo("S00010");
		System.err.println(q);
		ResponseResult<ResultPager<WarehouseAggregationVO>> result=this.retrieveService.queryAggregationPage(q);
		System.err.println(result);
		assertTrue(result.isSuccess());
	}

	@Test
	public void testQueryMaterielAggregationPage() {
		WarehouseRetrieveQuery q = new WarehouseRetrieveQuery();
		q.setCurrPage(1);
		q.setPageSize(20);
		q.setMaterielBasicId(3L);
		ResponseResult<ResultPager<WarehouseMaterielAggregationWrapper>> result=this.retrieveService.queryMaterielAggregationPage(q);
		System.err.println(result);
		assertTrue(result.isSuccess());
	}
	
	@Test
	public void testQueryWarehouseMaterielCount() {
		WarehouseMaterielCountQuery q = new WarehouseMaterielCountQuery();
		q.setMaterielBasicId(3L);
		ResponseResult<WarehouseMaterielCount>  result = this.retrieveService.queryWarehouseMaterielCount(q);
		System.err.println(result.getData());
	}
	
	@Test
	public void testSigning() {
		SigningQuery q = new SigningQuery();
		q.setMaterielBasicId(17L);
		q.setSourceOrderNo("SO1802060000001246");
		ResponseResult<SigningResult> result = this.retrieveService.signing(q);
		System.err.println(result.getData());
		assertTrue(result.isSuccess());
	}
	
	@Test
	public void testSignFailed() {
		SigningQuery q = new SigningQuery();
		q.setMaterielBasicId(3L);
		q.setSourceOrderNo("P2017121815171137700000");
		q.setBatchNo("B1");
		q.setImieNo("I1");
		q.setSnNo("B1");
		ResponseResult<SigningResult> result = this.retrieveService.signFailed(q);
		System.err.println(result.getData());
		assertTrue(result.isSuccess());
	}
	
	@Test
	public void testQuerySaleProductCountList() {
		JSONObject req = new JSONObject();
		List<Long> productIds = new ArrayList<Long>();
		productIds.add(1L);
		req.put("productIds", productIds);
		ResponseResult<List<JSONObject>> resp = this.ossServerService.querySaleProductCountList(req);
		if(resp.isSuccess()) {
			List<JSONObject> list = resp.getData();
			for(JSONObject obj:list) {
				System.err.println("snNo:"+obj.getString("snNo")+";imieNo:"+obj.getString("imeiNo"));
			}
		}
	}
	
	
}
