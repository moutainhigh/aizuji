package org.gz.app.hystrix;

import java.util.List;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.gz.app.feign.RepaymentServiceClient;
import org.gz.common.resp.ResponseResult;
import org.gz.common.resp.ResponseStatus;
import org.gz.common.utils.ResultUtil;
import org.gz.liquidation.common.dto.DuesDetailResp;
import org.gz.liquidation.common.dto.RepaymentDetailResp;
import org.gz.liquidation.common.dto.SettleDetailResp;
import org.gz.liquidation.common.dto.repayment.ZmRepaymentScheduleResp;
import org.gz.liquidation.common.dto.repayment.ZmStatementDetailResp;
import org.gz.liquidation.common.entity.ReadyBuyoutReq;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RepaymentServiceHystirxImpl implements RepaymentServiceClient {

	@Override
	public ResponseResult<?> loadRepayPlanList(String orderSN) {
		log.error("-----------------清算服务不可用------------");
		ResponseResult<?> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<?> readyBuyout(@Valid ReadyBuyoutReq readyBuyoutReq) {
		log.error("-----------------清算服务不可用------------");
		ResponseResult<?> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<RepaymentDetailResp> queryBuyoutOrderDetail(
			String orderSN) {
		log.error("-----------------清算服务不可用------------");
		ResponseResult<RepaymentDetailResp> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<DuesDetailResp> repayRentDetail(String orderSN) {
		log.error("-----------------清算服务不可用------------");
		ResponseResult<DuesDetailResp> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<SettleDetailResp> settleDetail(String orderSN) {
		log.error("-----------------清算服务不可用------------");
		ResponseResult<SettleDetailResp> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<List<ZmRepaymentScheduleResp>> zmRepaymentSchedule(
			String orderSN) {
		log.error("-----------------清算服务不可用------------");
		ResponseResult<List<ZmRepaymentScheduleResp>> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}

	@Override
	public ResponseResult<ZmStatementDetailResp> queryZmStatementDetail(
			String orderSN) {
		log.error("-----------------清算服务不可用------------");
		ResponseResult<ZmStatementDetailResp> result = new ResponseResult<>();
		ResultUtil.buildResultWithResponseStatus(result, ResponseStatus.SERVICE_CALL_OVERTIME);
		return result;
	}
}
