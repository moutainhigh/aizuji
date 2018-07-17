package org.gz.loverent.timed.task.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
/**
 * 
 * @Description:TODO    定时任务配置
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2017年12月7日 	Created
 */
@Configuration
@ImportResource(locations={"classpath:spring-xxl-job.xml"})
public class XxlJobConfig {

}