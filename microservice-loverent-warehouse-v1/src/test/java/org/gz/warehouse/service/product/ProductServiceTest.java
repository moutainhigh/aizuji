/**
 * 
 */
package org.gz.warehouse.service.product;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.common.vo.RentProductReq;
import org.gz.warehouse.common.vo.RentProductResp;
import org.gz.warehouse.common.vo.SaleProductReq;
import org.gz.warehouse.common.vo.SaleProductResp;
import org.gz.warehouse.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2018年3月26日 下午6:53:34
 */
public class ProductServiceTest extends BaseTest {

	@Autowired
	private ProductService productService;
	
	@Test
	public void testQueryAvailableProductList() {
		
		SaleProductReq req = new SaleProductReq();
		req.setProductIds(Lists.newArrayList(1L));
		ResponseResult<ResultPager<SaleProductResp>> resp = productService.queryAvailableProductList(req);
		System.err.println(resp);
	}
	
	@Test
	public void testQueryRentCommodityPage() {
		RentProductReq req = new RentProductReq();
		req.setCurrPage(1);
		req.setPageSize(1);
		req.setMaterielBrandId(1);
		ResultPager<RentProductResp> resp = productService.queryRentCommodityPage(req);
		System.err.println(resp);
	}
	
}
