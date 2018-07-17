/**
 * 
 */
package org.gz.warehouse.service.purchase;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderQuery;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderVO;
import org.gz.warehouse.entity.purchase.PurchaseApprovedOrderDetailVO;
import org.gz.warehouse.entity.purchase.PurchaseApprovedOrderVO;
import org.gz.warehouse.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月21日 上午8:57:40
 */
public class PurchaseApprovedServiceTest extends BaseTest {

	@Autowired
	private PurchaseApprovedService approvedService;
	
	@Test
	public void testQueryByPage() {
		PurchaseApplyOrderQuery q = new PurchaseApplyOrderQuery();
		ResponseResult<ResultPager<PurchaseApplyOrderVO>> result=approvedService.queryByPage(q);
		System.err.println(result);
	}

	@Test
	public void testQueryDetail() {
		ResponseResult<PurchaseApplyOrderVO> result=approvedService.queryDetail(4L);
		System.err.println(result);
	}

	@Test
	public void testApproved() {
		PurchaseApprovedOrderVO p = new PurchaseApprovedOrderVO();
		p.setId(4L);
		p.setDetailList(Lists.newArrayList(new PurchaseApprovedOrderDetailVO(1L,100)));
		System.err.println(p);
		ResponseResult<PurchaseApprovedOrderVO> result=approvedService.approved(p);
		System.err.println(result);
	}

	@Test
	public void testReject() {
		PurchaseApprovedOrderVO p = new PurchaseApprovedOrderVO();
		p.setId(24L);
		p.setApprovedId(1L);
		p.setApprovedName("管理员");
		ResponseResult<PurchaseApprovedOrderVO> result=approvedService.reject(p);
		System.err.println(result);
	}

}
