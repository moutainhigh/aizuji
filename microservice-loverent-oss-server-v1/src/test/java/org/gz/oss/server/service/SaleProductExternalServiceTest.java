/**
 * 
 */
package org.gz.oss.server.service;

import java.util.List;

import org.gz.common.utils.JsonUtils;
import org.gz.oss.common.dto.SaleProductCountReq;
import org.gz.oss.common.dto.SaleProductCountResp;
import org.gz.oss.common.service.SaleProductExternalService;
import org.gz.oss.server.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

/**
 * @Title: 
 * @author hxj
 * @Description: 
 * @date 2018年4月2日 下午1:51:06
 */
public class SaleProductExternalServiceTest extends BaseTest {

	@Autowired
	private SaleProductExternalService saleProductExternalService;
	
	@Test
	public void testQuerySaleProductCountList() {
		SaleProductCountReq req = new SaleProductCountReq();
		req.setProductIds(Lists.newArrayList(1L,18L,19L,20L));
		List<SaleProductCountResp> list = saleProductExternalService.querySaleProductCountList(req);
		System.err.println(JsonUtils.toJsonString(list));
	}
}
