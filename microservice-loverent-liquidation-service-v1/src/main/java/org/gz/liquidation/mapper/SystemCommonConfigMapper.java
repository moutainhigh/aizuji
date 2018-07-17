package org.gz.liquidation.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.liquidation.entity.SystemCommonConfigEntity;
/**
 * 
 * @Description:系统公共配置Mapper
 * Author	Version		Date		Changes
 * liaoqingji 	1.0  		2018年2月12日 	Created
 */
@Mapper
public interface SystemCommonConfigMapper {
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
