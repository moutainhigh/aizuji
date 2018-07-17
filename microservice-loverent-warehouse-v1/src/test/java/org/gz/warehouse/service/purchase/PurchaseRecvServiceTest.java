/**
 * 
 */
package org.gz.warehouse.service.purchase;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.UUIDUtils;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderQuery;
import org.gz.warehouse.entity.purchase.PurchaseApplyOrderVO;
import org.gz.warehouse.entity.purchase.PurchaseOrderRecvDetailVO;
import org.gz.warehouse.entity.purchase.PurchaseOrderRecvVO;
import org.gz.warehouse.entity.purchase.PurchaseOrderRecvWrapper;
import org.gz.warehouse.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

/**
 * @Title:
 * @author hxj
 * @Description:
 * @date 2017年12月21日 上午8:58:29
 */
public class PurchaseRecvServiceTest extends BaseTest {

	@Autowired
	private PurchaseRecvService purchaseRecvService;

	/**
	 */
	@Test
	public void testQueryByPage() {
		PurchaseApplyOrderQuery q = new PurchaseApplyOrderQuery();
		ResponseResult<ResultPager<PurchaseApplyOrderVO>>  result = this.purchaseRecvService.queryByPage(q);
		System.err.println(result);
		assertTrue(result.isSuccess());
	}

	/**
	 */
	@Test
	public void testQueryDetail() {
		ResponseResult<PurchaseApplyOrderVO> result = this.purchaseRecvService.queryDetail(4L);
		assertTrue(result.isSuccess());
		System.err.println(result);
	}

	/**
	 * 
	 */
	@Test
	public void testRecv1() {
		PurchaseOrderRecvWrapper p = new PurchaseOrderRecvWrapper();
		p.setPurchaseApplyOrderId(4L);
		Long operatorId=1L;
		String operatorName="管理员";
		Date operateOn = new Date();
		p.setOperatorId(operatorId);
		p.setOperatorName(operatorName);
		p.setOperateOn(operateOn);
		PurchaseOrderRecvVO v1 = new PurchaseOrderRecvVO(4L, UUIDUtils.genDateUUID("POR"), Lists.newArrayList(new PurchaseOrderRecvDetailVO("B1","B1","I1"),new PurchaseOrderRecvDetailVO("B2","S2","I2"),new PurchaseOrderRecvDetailVO("B3","S3","I3")));
		PurchaseOrderRecvVO v2 = new PurchaseOrderRecvVO(5L, UUIDUtils.genDateUUID("POR"), Lists.newArrayList(new PurchaseOrderRecvDetailVO("B11","B11","I11"),new PurchaseOrderRecvDetailVO("B22","S22","I22"),new PurchaseOrderRecvDetailVO("B33","S33","I33")));
		p.setRecvList(Lists.newArrayList(v1,v2));
		System.err.println(p);
		ResponseResult<PurchaseOrderRecvWrapper> result = this.purchaseRecvService.recv(p);
		assertTrue(result.isSuccess());
		System.err.println(result);
	}

}
