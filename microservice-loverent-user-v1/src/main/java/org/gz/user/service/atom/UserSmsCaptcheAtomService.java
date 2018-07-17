package org.gz.user.service.atom;

import com.alibaba.fastjson.JSONObject;

public interface UserSmsCaptcheAtomService {

	void sendCaptche(JSONObject body);

}
