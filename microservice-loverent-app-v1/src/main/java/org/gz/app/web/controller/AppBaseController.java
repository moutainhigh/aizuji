package org.gz.app.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.gz.cache.service.user.UserCacheService;
import org.gz.common.utils.SessionUtil;
import org.gz.common.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;

public class AppBaseController extends BaseController {
	
	@Autowired
	protected UserCacheService userCacheService;

	public List<String> getUserFields(HttpServletRequest request, String... fields) {
		return userCacheService.getLoginUserCacheByToken(SessionUtil.getToken(request), fields);
	}
}
