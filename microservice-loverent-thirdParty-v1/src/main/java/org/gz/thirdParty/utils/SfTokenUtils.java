/**
 * 
 */
package org.gz.thirdParty.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.gz.thirdParty.service.shunFeng.ShunFengToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.sf.openapi.common.entity.MessageResp;
import com.sf.openapi.express.sample.security.dto.TokenRespDto;

import lombok.extern.slf4j.Slf4j;

/**
 * @Title: 顺丰Token工具类
 * @author hxj
 * @date 2018年3月13日 下午2:25:59
 */
@Component
@Slf4j
public class SfTokenUtils {

	public static final String accessTokenKey = "SF:accessToken";

	public static final String refreshTokenKey = "SF:refreshToken";

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Value("${enable.refreshToken}")
	private boolean enableRefreshToken;

	/**
	 * 
	 * @Description: 获取accessToken
	 * @return String
	 */
	public String getAccessToken() {
		String accessToken = redisTemplate.opsForValue().get(accessTokenKey);
		String refreshToken = redisTemplate.opsForValue().get(refreshTokenKey);
		String result = null;
		if (!StringUtils.hasText(accessToken) || !StringUtils.hasText(refreshToken)) {// 如果Redis缓存中不存在accessToken或refreshToken,则直接调用顺丰获取
			try {
				MessageResp<TokenRespDto> tokenResp = ShunFengToken.AccessTokenApply();
				parseMessageResp(tokenResp);
				result = redisTemplate.opsForValue().get(accessTokenKey);
			} catch (Exception e) {
				log.error("获取Token失败:" + e.getLocalizedMessage());
			}
		} else {
			result = accessToken;
		}
		return result;
	}

	private void parseMessageResp(MessageResp<TokenRespDto> tokenResp) {
		if (tokenResp != null && tokenResp.getHead().getCode().equals("EX_CODE_OPENAPI_0200")) {// 申请成功
			TokenRespDto dto = tokenResp.getBody();
			String accessToken = dto.getAccessToken();
			String refreshToken = dto.getRefreshToken();
			log.info("获取到的acessToken:" + accessToken + " refreshToken:" + refreshToken);
			redisTemplate.opsForValue().set(accessTokenKey, accessToken, 59, TimeUnit.MINUTES);// 访问token1小时有效期
			redisTemplate.opsForValue().set(refreshTokenKey, refreshToken, 1439, TimeUnit.MINUTES);// 刷新token24小时有效期
		} else {
			// 记录获取令牌失败信息
			log.info("获取Token失败:" + JSON.toJSONString(tokenResp));
		}
	}

	@Scheduled(cron = "0 0/50 * * * ?") // 每50分钟刷新一次accessToken
	public void refreshToken() {
		if (!enableRefreshToken) {
			log.info("刷新Token功能已被禁用");
			return;
		}
		String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		log.info("刷新Token开始:{}", s);
		long start = System.currentTimeMillis();
		String accessToken = redisTemplate.opsForValue().get(accessTokenKey);
		String refreshToken = redisTemplate.opsForValue().get(refreshTokenKey);
		if (StringUtils.hasText(accessToken) && StringUtils.hasText(refreshToken)) {
			try {
				MessageResp<TokenRespDto> tokenResp = ShunFengToken.AccessTokenRefresh(accessToken, refreshToken);
				parseMessageResp(tokenResp);
				log.info("刷新Token成功！");
			} catch (Exception e) {
				log.error("刷新Token失败：{}", e.getLocalizedMessage());
			}
		} else {
			getAccessToken();
		}
		long end = System.currentTimeMillis();
		log.info("take {} ms", end - start);
	}

}
