package org.gz.liquidation.service.coupon;

import java.math.BigDecimal;
import java.util.List;

import org.gz.common.entity.ResultPager;
import org.gz.common.resp.ResponseResult;
import org.gz.liquidation.common.dto.QueryDto;
import org.gz.liquidation.common.dto.coupon.CouponStatisticsResp;
import org.gz.liquidation.common.dto.coupon.CouponUseLogResp;
import org.gz.liquidation.entity.CouponUseLogEntity;

public interface CouponUseLogService {
		/**
		 * 
		 * @Description: 新增
		 * @param record
		 * @return
		 * @throws
		 * createBy:liaoqingji            
		 * createDate:2018年3月26日
		 */
	    int insertSelective(CouponUseLogEntity record);
	    /**
	     * 
	     * @Description: 分页查询
	     * @param queryDto
	     * @return
	     * @throws
	     * createBy:liaoqingji            
	     * createDate:2018年3月26日
	     */
	    ResultPager<CouponUseLogResp> selectPage(QueryDto queryDto);
	    /**
	     * 
	     * @Description: 根据条件查询
	     * @param couponUseLogEntity
	     * @return
	     * @throws
	     * createBy:liaoqingji            
	     * createDate:2018年3月27日
	     */
	    List<CouponUseLogEntity> selectByCondition(CouponUseLogEntity couponUseLogEntity);
	    /**
	     * 
	     * @Description: 根据使用场景统计优惠券金额
	     * @param usageScenario 使用场景
	     * @return
	     * @throws
	     * createBy:liaoqingji            
	     * createDate:2018年4月4日
	     */
	    BigDecimal sumAmount(Integer usageScenario);
	    /**
	     * 
	     * @Description: 优惠券使用统计
	     * @param usageScenario
	     * @return
	     * @throws
	     * createBy:liaoqingji            
	     * createDate:2018年4月4日
	     */
	    ResponseResult<CouponStatisticsResp> queryCouponStatistics(Integer usageScenario);
}
