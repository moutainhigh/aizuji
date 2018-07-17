//package org.gz.liquidation.service.repayment.impl;
//
//import static org.junit.Assert.*;
//
//import org.gz.liquidation.entity.RepaymentStatistics;
//import org.gz.liquidation.service.repayment.RepaymentStatisticsService;
//import org.gz.liquidation.test.BaseTest;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class RepaymentStatisticsServiceTest extends BaseTest{
//	@Autowired
//	private RepaymentStatisticsService repaymentStatisticsService;
//	
//	@Test
//	public void testAdd() {
//		// 更新履约次数
//		RepaymentStatistics repaymentStatistics = new RepaymentStatistics();
//		repaymentStatistics.setUserId(888L);
//		repaymentStatistics.setPerformanceCount(1);
//		repaymentStatistics.setOrderSN("Sn201712301539855");
//		repaymentStatisticsService.add(repaymentStatistics);
//	}
//
//	@Test
//	public void testInsertPerformanceCountBatch() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testInsertBreachCountBatch() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testQueryList() {
//		fail("Not yet implemented");
//	}
//
//}
