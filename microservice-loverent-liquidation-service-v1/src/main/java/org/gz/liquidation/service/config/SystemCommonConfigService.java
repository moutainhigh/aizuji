package org.gz.liquidation.service.config;

import java.util.List;

import org.gz.liquidation.entity.SystemCommonConfigEntity;
/**
 * 
 * @Description:系统配置服务接口
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年2月12日 	Created
 */
public interface SystemCommonConfigService {

	/**
	 * 
	 * @Description: 查询单个配置项
	 * @param scc
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年2月12日
	 */
	SystemCommonConfigEntity selectOne(SystemCommonConfigEntity scc);
	/**
	 * 
	 * @Description: 查询配置列表
	 * @param scc
	 * @return
	 * @throws
	 * createBy:liaoqingji            
	 * createDate:2018年3月7日
	 */
	List<SystemCommonConfigEntity> selectList(SystemCommonConfigEntity scc);
}
