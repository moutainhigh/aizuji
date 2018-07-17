package org.gz.user.service.atom;

import org.gz.user.dto.UserInfoDto;
import org.gz.user.entity.UserInfo;

public interface UserInfoAtomService {

	UserInfoDto queryByUserId(Long userId);

	void updateUserInfo(UserInfo userInfo);

}
