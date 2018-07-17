package org.gz.liquidation.service.coupon.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.common.utils.BeanConvertUtil;
import org.gz.liquidation.common.Page;
import org.gz.liquidation.common.dto.QueryDto;
import org.gz.liquidation.common.dto.coupon.CouponStatisticsResp;
import org.gz.liquidation.common.dto.coupon.CouponUseLogResp;
import org.gz.liquidation.common.utils.LiquidationConstant;
import org.gz.liquidation.entity.CouponUseLogEntity;
import org.gz.liquidation.mapper.CouponUseLogMapper;
import org.gz.liquidation.service.coupon.CouponUseLogService;
import org.springframework.stereotype.Service;
@Service
public class CouponUseLogServiceImpl implements CouponUseLogService {

	@Resource
	private CouponUseLogMapper couponUseLogMapper;
	
	@Override
	public int insertSelective(CouponUseLogEntity record) {
		return couponUseLogMapper.insertSelective(record);
	}

	@Override
	public ResultPager<CouponUseLogResp> selectPage(QueryDto queryDto) {
		List<CouponUseLogEntity> sourceList = couponUseLogMapper.selectPage(queryDto);
		List<CouponUseLogResp> list = BeanConvertUtil.convertBeanList(sourceList, CouponUseLogResp.class);
		Page page = queryDto.getPage();
		ResultPager<CouponUseLogResp> resultPager =  new ResultPager<>(page.getTotalNum(), page.getStart(), page.getPageSize(), list);
		return resultPager;
	}

	@Override
	public List<CouponUseLogEntity> selectByCondition(CouponUseLogEntity couponUseLogEntity) {
		return couponUseLogMapper.selectByCondition(couponUseLogEntity);
	}

	@Override
	public BigDecimal sumAmount(Integer usageScenario) {
		return couponUseLogMapper.sumAmount(usageScenario);
	}

	@Override
	public ResponseResult<CouponStatisticsResp> queryCouponStatistics(Integer usageScenario) {
		CouponStatisticsResp couponStatisticsResp = new CouponStatisticsResp();
		couponStatisticsResp.setTotalAmount(sumAmount(null));
		couponStatisticsResp.setAmount(sumAmount(usageScenario));
		couponStatisticsResp.setReturnAmount(sumAmount(LiquidationConstant.COUPON_USAGE_SCENARIO_RETURN));
		return ResponseResult.buildSuccessResponse(couponStatisticsResp);
	}

}
