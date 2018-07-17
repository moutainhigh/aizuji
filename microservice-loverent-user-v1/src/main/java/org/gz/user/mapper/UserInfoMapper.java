package org.gz.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.gz.user.entity.UserInfo;

@Mapper
public interface UserInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

	UserInfo selectByUserId(Long userId);

	void updateByUserIdSelective(UserInfo userInfo);
}