package org.gz.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.user.entity.AppUser;

import feign.Param;

@Mapper
public interface AppUserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(AppUser record);

    int insertSelective(AppUser record);

    AppUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(AppUser record);

    int updateByPrimaryKey(AppUser record);

	AppUser findUserByTel(String phoneNum);

	AppUser selectByAlipayUserId(@Param("userId") String userId);

	List<AppUser> queryByCondition(AppUser user);

	AppUser queryUserByMobile(String mobile);
}