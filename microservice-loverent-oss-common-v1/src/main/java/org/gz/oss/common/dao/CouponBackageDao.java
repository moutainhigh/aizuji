package org.gz.oss.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.oss.common.entity.CouponBackage;
import org.gz.oss.common.entity.CouponBackageQuery;

/**
 * CouponBackage Dao
 *
 */
@Mapper
public interface CouponBackageDao {

	int queryPageCount(CouponBackageQuery c);

	List<CouponBackage> queryPageList(CouponBackageQuery c);

	int updateByPrimaryKeySelective(CouponBackage c);

	int insertSelective(CouponBackage c);

	List<CouponBackage> queryCouponBackList(String nowDate);

	CouponBackage selectByPrimaryKey(Long backageId);

}
