//package org.gz.liquidation.service.repayment;
//
//import static org.junit.Assert.*;
//
//import java.math.BigDecimal;
//import org.gz.common.resp.ResponseResult;
//import org.gz.liquidation.common.entity.RepaymentScheduleReq;
//import org.gz.liquidation.service.repayment.RepaymentScheduleService;
//import org.gz.liquidation.test.BaseTest;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class RepaymentScheduleServiceTest extends BaseTest{
//	@Autowired
//	private RepaymentScheduleService repaymentScheduleService;
//	@Test
//	public void testAddRepaymentSchedule() {
//		RepaymentScheduleReq repaymentScheduleReq = new RepaymentScheduleReq();
//		repaymentScheduleReq.setAmount(new BigDecimal(8969));
//		repaymentScheduleReq.setOrderSN("s2017122017268955");
//		repaymentScheduleReq.setPeriods(3);
//		repaymentScheduleReq.setUserId(888L);
//		repaymentScheduleReq.setProductNo("fp00000017");
//		ResponseResult<RepaymentScheduleReq> result = repaymentScheduleService.addRepaymentSchedule(repaymentScheduleReq );
//		assertTrue(result.isSuccess());
//		//fail("Not yet implemented");
//	}
//
//	@Test
//	public void testQueryRepaymentScheduleList() {
//		fail("Not yet implemented");
//	}
//
//}
