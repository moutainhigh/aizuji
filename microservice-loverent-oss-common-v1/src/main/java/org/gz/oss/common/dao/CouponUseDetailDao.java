package org.gz.oss.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.oss.common.entity.CouponUseDetail;
import org.gz.oss.common.entity.CouponUseDetailQuery;

@Mapper
public interface CouponUseDetailDao {

	int queryPageCount(CouponUseDetailQuery cud);

	List<CouponUseDetail> queryCouponUseDetailList(CouponUseDetailQuery cud);

	int insertSelective(CouponUseDetail cud);

	int updateByParams(Long couponId, Long userId);

}
