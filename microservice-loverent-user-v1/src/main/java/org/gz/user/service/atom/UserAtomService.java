package org.gz.user.service.atom;

import java.util.Map;

import org.gz.user.dto.UserInfoDto;
import org.gz.user.entity.AppUser;

import com.alibaba.fastjson.JSONObject;

public interface UserAtomService {

	String login(JSONObject body);

	String register(JSONObject body);

	void logout(String token);

	void updateUser(AppUser user);

	AppUser queryUserById(Long userId);

	void modifyPassword(JSONObject body);

	void saveContacts(JSONObject body);

	Map<String, String> thirdPartyEmpower(JSONObject body);

	Map<String, String> bindPhone(JSONObject body);

	void saveOcrResult(JSONObject body);

	void resetPassword(AppUser user);

	UserInfoDto queryByCondition(AppUser user);

	AppUser queryUserByMobile(String mobile);

	void saveH5FaceResult(JSONObject body);

	Map<String, String> thirdPartyXcxEmpower(JSONObject body);
}
