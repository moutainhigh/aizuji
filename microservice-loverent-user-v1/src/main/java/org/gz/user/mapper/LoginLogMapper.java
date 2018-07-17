package org.gz.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.gz.user.entity.LoginLog;

@Mapper
public interface LoginLogMapper {

	int insertSelective(LoginLog log);

	LoginLog selectNewlyRecordByUserId(Long userId);

}