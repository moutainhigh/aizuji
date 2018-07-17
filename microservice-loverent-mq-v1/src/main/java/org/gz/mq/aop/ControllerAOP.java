/**
 * 
 */
package org.gz.mq.aop;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.gz.mq.entity.resp.ResponseResult;
import org.gz.mq.utils.JsonUtils;
import org.gz.mq.utils.MD5Utils;
import org.gz.mq.utils.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 日志或安全AOP
 * 
 * @author hxj
 * @date 2017年11月10日下午4:14:13
 */
@Aspect
@Configuration
public class ControllerAOP {

	private static Logger log = LoggerFactory.getLogger(ControllerAOP.class);

	@Value("${ip.securityCheck.whitelist.enable}")
	private boolean ipCheckEnable;

	@Value("${ip.securityCheck.whitelist.value}")
	private String allowIpList;

	@Value("${mq.app.secret}")
	private String appSecret;

	/**
	 * 声明切入点为控制器层
	 */
	@Pointcut("within(org.gzjf.mq.controller..*)")
	public void controllerPointcut() {

	}

	/**
	 * 用于处理接口安全的环绕通知
	 * 
	 * @param joinPoint
	 * @return
	 */
	@Around("controllerPointcut()")
	public Object securityCheck(ProceedingJoinPoint joinPoint) {
		long start = System.currentTimeMillis();
		HttpServletRequest request = this.getHttpServetRequest();
		String remoteIP = RequestUtils.getRemoteIP(request);
		log.info("remoteIp:{}", remoteIP);
		// 1.执行IP白名单检查，如果ip.securityCheck.whitelist.enable=true,则必须配置ip.securityCheck.whitelist.value
		boolean remoteIpCheckResult = this.checkIpSecurityCheck(remoteIP);
		if (!remoteIpCheckResult) {
			return ResponseResult.OPT_IPWHITELIST_CHECK_FAILED;
		}
		// 2.执行接口次数调用阀值检查，可使用Redis集群实现
		// 3.检查SECRET
		ResponseResult result = ResponseResult.OPT_SECRET_CHECK_FAILED;
		Object[] args = joinPoint.getArgs();// 参数
		if (this.checkSecret(args)) {
			try {
				result=(ResponseResult)joinPoint.proceed(args);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		long end = System.currentTimeMillis();
		log.info("securityCheck take {} ms",end-start);
		return result;
	}

	/**
	 * 摘要认证实现:先将参数按字典顺序进行升序排序，再拼接成字符串，加上分配的appSecret，最后进行MD5加密，与调用者提供的secret比较，相等则返回true,否则返回false
	 * @param secret:调用者传递的加密内容
	 * @param param:调用者传递的消息参数
	 * @return
	 */
	private boolean checkSecret(Object[] args) {
		if (null != args && args.length > 1) {//只处理两个参数的
			String secret = String.valueOf(args[0]);
			String param = JsonUtils.toJsonString(args[1]);
			SortedMap<String, Object> sortedMap = JsonUtils.toSortedMap(param);
			Set<String> keys = sortedMap.keySet();
			StringBuilder result = new StringBuilder();
			for(String key:keys){
				String value=String.valueOf(sortedMap.get(key));
				log.info(key+"->"+value);
				result.append(value);
			}
			result.append(appSecret);
			String encodeResult = MD5Utils.encryptPassword(result.toString(), appSecret);
			return secret.equals(encodeResult);
		}
		return true;
	}

	/**
	 * IP白名单控制
	 */
	private boolean checkIpSecurityCheck(String remoteIP) {
		boolean checkResult = true;
		if (ipCheckEnable) {
			log.info("ip securityCheck is enabled");
			if (StringUtils.hasText(allowIpList)) {
				List<String> configRemoteIPList = Arrays.asList(allowIpList.split(","));
				checkResult = configRemoteIPList.contains(remoteIP);
			} else {
				log.error("to use ip securityCheck ,please config ip.securityCheck.whitelist.value");
				checkResult = false;
			}
		} else {
			log.info("ip securityCheck is not enabled");
		}
		return checkResult;
	}

	/**
	 * 获取当前HttpServletRequest
	 * 
	 * @return
	 */
	private HttpServletRequest getHttpServetRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
}
