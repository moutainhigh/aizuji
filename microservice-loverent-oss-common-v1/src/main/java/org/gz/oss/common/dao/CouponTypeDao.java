package org.gz.oss.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.oss.common.entity.CouponType;

@Mapper
public interface CouponTypeDao {

	int insertSelective(CouponType ct);

	int updateByPrimaryKeySelective(CouponType ct);

	List<CouponType> queryDataList(Long couponId);

	void deleteByCouponId(Long couponId);

}
