package org.gz.liquidation.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gz.liquidation.common.dto.QueryDto;
import org.gz.liquidation.entity.CouponUseLogEntity;
/**
 * 
 * @Description:优惠券使用记录Mapper
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年3月26日 	Created
 */
@Mapper
public interface CouponUseLogMapper {

    int insert(CouponUseLogEntity record);

    int insertSelective(CouponUseLogEntity record);
    
    CouponUseLogEntity selectByPrimaryKey(Long id);
    /**
     * 
     * @Description: 查询
     * @param couponUseLogEntity
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年3月27日
     */
    List<CouponUseLogEntity> selectByCondition(CouponUseLogEntity couponUseLogEntity);
    /**
     * 
     * @Description: 分页查询
     * @param queryDto
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年3月26日
     */
    List<CouponUseLogEntity> selectPage(QueryDto queryDto);
    /**
     * 
     * @Description: 统计各个场景优惠券金额
     * @param usageScenario
     * @return
     * @throws
     * createBy:liaoqingji            
     * createDate:2018年4月4日
     */
    BigDecimal sumAmount(@Param("usageScenario") Integer usageScenario);
}