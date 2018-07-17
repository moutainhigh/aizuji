package org.gz.oss.common.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author WenMing.Zhou
 * @Description: ${todo}
 * @date 2018/4/3 14:23
 */
@Mapper
public interface ProductDao {

    int queryProductIsHasConfig(@Param("productId") Long productId);
}
