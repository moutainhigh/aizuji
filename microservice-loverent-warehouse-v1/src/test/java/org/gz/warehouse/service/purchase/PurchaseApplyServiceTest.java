package org.gz.warehouse.service.purchase;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrder;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderDetail;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderQuery;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderVO;
import org.gz.warehouse.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月18日 下午2:46:04
 */
public class PurchaseApplyServiceTest extends BaseTest {

	@Autowired
	private PurchaseApplyService purchaseApplyService;

	@Test
	public void testInsert() {
		PurchaseApplyOrder p = new PurchaseApplyOrder();
		p.setWarehouseInfoId(2);
		p.setSupplierId(1);
		p.setExpectedReceiveDate(new Date());
		p.setStatusFlag(10);
		p.setRemark("测试3");
		p.setCreateBy(1L);
		List<PurchaseApplyOrderDetail> detailList = new ArrayList<PurchaseApplyOrderDetail>();
		detailList.add(new PurchaseApplyOrderDetail(null,1000L,100,new BigDecimal(8888)));
		p.setDetailList(detailList);
		System.err.println(p);
		ResponseResult<PurchaseApplyOrder> result = this.purchaseApplyService.insert(p);
		assertTrue(result.isSuccess());
	}
	
	
	@Test
	public void testQueryPage() {
		PurchaseApplyOrderQuery q = new PurchaseApplyOrderQuery();
		q.setStatusFlag(-1);
		q.setCurrPage(1);
		q.setPageSize(10);
//		q.setApplyId(1L);
//		q.setApplyName("hxj");
//		q.setApplyId(1L);
//		q.setApprovedName("hxj");
//		q.setStartApplyDate("2017-12-18 00:00:00");
//		q.setEndApplyDate("2017-12-18 23:00:00");
//		q.setStartExpectedRecvDate("2017-12-18 00:00:00");
//		q.setEndExpectedRecvDate("2017-12-18 23:00:00");
//		q.setId(4L);
//		q.setPurchaseNo("PO2017121815171137700000");
//		q.setStatusFlag(30);
//		q.setWarehouseInfoId(2);
//		q.setSupplierId(1);
		System.err.println(q);
		ResponseResult<ResultPager<PurchaseApplyOrderVO>> result = this.purchaseApplyService.queryByPage(q);
		assertTrue(result.isSuccess());
		System.err.println(result);
	}

	@Test
	public void test() {
		ResponseResult<PurchaseApplyOrderVO> result = this.purchaseApplyService.queryDetail(64L);
		assertTrue(result.isSuccess());
		System.err.println(result);
	}
}
