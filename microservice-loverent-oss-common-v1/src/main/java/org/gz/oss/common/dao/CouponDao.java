package org.gz.oss.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.oss.common.entity.Coupon;
import org.gz.oss.common.entity.CouponQuery;


/**
 * Coupon Dao
 *
 */
@Mapper
public interface CouponDao {

	int queryPageCount(CouponQuery c);

	List<Coupon> queryPageList(CouponQuery c);

	int insertSelective(Coupon c);

	int updateByPrimaryKeySelective(Coupon c);

	Coupon selectByPrimaryKey(Long id);

	List<Coupon> getCouponList(Long backageId);

	List<Coupon> queryCouponList(String nowDate);

	int insert(Coupon c);

}
