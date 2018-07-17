package org.gz.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.gz.user.entity.ThirdInfoAlipay;

@Mapper
public interface ThirdInfoAlipayMapper {
    int deleteByPrimaryKey(Long thirdpartinfoid);

    int insert(ThirdInfoAlipay record);

    int insertSelective(ThirdInfoAlipay record);

    ThirdInfoAlipay selectByPrimaryKey(Long thirdpartinfoid);

    int updateByPrimaryKeySelective(ThirdInfoAlipay record);

    int updateByPrimaryKey(ThirdInfoAlipay record);

	ThirdInfoAlipay selectByAlipayUserId(String userId);
}