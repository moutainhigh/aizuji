package org.gz.oss.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gz.oss.common.entity.CouponRelation;
import org.gz.oss.common.entity.CouponUserQuery;

@Mapper
public interface CouponRelationDao {

	int getHasCount(Long couponId);

	int getUseCount(Long couponId);

	List<CouponRelation> queryCouponRelationList(CouponUserQuery cr);

	int queryPageCount(CouponUserQuery cr);

	int updateByPrimaryKeySelective(CouponRelation cr);

	CouponRelation queryDetail(Long couponId);

	int insertSelective(CouponRelation cr);

	CouponRelation selectByParams(@Param("userId") Long userId, @Param("couponId") Long couponId);

	List<CouponRelation> getCRListByUserId(Long userId);

}
